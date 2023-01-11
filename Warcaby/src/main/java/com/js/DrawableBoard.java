package com.js;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 * Rysowalna plansza do gry
 */
public class DrawableBoard extends JPanel{
    int X,Y;
    public Field [][]array;
    private Client client;
    Board board;
    Tile.Color color;

    private class Field extends JButton implements ActionListener{
        int x,y;
        Move move;
        Border normal, queen;
        
        public void associateMove(Move move){
            this.move = move;
        }

        public void resetMove(){
            int[] origin = {x,y};
            this.move = new Move(origin, null, null);
        }

        Field(int x, int y){
            super();
            this.x = x;
            this.y = y;
            this.addActionListener(this);
            queen = BorderFactory.createLineBorder(Color.red, 5);
            normal = this.getBorder();
        }

        public void actionPerformed(ActionEvent event) {
            DrawableBoard.this.click(this.x, this.y);
        }

        public void black(){
            this.setBackground(Color.BLACK);
        }
        public void white(){
            this.setBackground(Color.WHITE);
        }
        public void pawn(){
            resetBorder();
        }
        public void queen(){
            this.setBorder(queen);
        }
        public void even(){
            this.setBackground(Color.YELLOW);
            resetBorder();
        }
        public void odd(){
            this.setBackground(Color.ORANGE);
            resetBorder();
        }
        public void capture(){
            this.setBackground(Color.GRAY);
        }
        public void end(){
            this.setBackground(Color.GREEN);
        }
        public void origin(){
            this.setBackground(Color.GREEN);
        }
        private void resetBorder(){
            this.setBorder(normal);
        }
    }


    private void click(int x, int y){
        for(Field[] f : array)
            for(Field field : f)
                field.setEnabled(false);

        client.sendMove(array[x][y].move);        
    }

    /**
     * Tworzy nową Rysowalną planszę
     * @param board Plansza wirtualna
     * @param client Klient
     * @param color Kolor gracza
     */
    public DrawableBoard(Board board, Client client, Tile.Color color)
    {
        this.client = client;
        X = board.Tiles.length;
        Y = board.Tiles[0].length;
        this.board = board;
        this.color = color;

        array = new Field[X][Y];
        this.setLayout(new GridLayout(X,Y));
        
        for(int i=0;i<X;i++){
            for(int j=0;j<Y;j++){
                array[i][j] = new Field(i, j);
                array[i][j].setEnabled(false);
                add(array[i][j]);
            }
        }
    }

    /**
     * Koloruje planszę według danego ruchu i odblokowuje pionki gracza
     * @param moves ruch
     */
    public void enableAndColorMoves(List<Move> moves){
        render();
        enableTiles();
        if(moves != null)
            for(Move move : moves){
                enableMove(move);
            }
    }

    private void enableTiles(){
        for(int i=0;i<X;i++){
            for(int j=0;j<Y;j++){
                if(board.Tiles[i][j].color == color){
                    array[i][j].setEnabled(true);
                    array[i][j].resetMove();
                }else
                    array[i][j].setEnabled(false);
            }
        }
    }

    private void enableMove(Move move){
        array[move.end[0]][move.end[1]].associateMove(move);
        array[move.end[0]][move.end[1]].setEnabled(true);
        colorMove(move);
    }

    private void colorMove(Move move){
        array[move.origin[0]][move.origin[1]].origin();
        array[move.end[0]][move.end[1]].end();
        if(move.captured != null)
            for(int[] cap : move.captured)
                array[cap[0]][cap[1]].capture();
    }

    private void render(){
        for(int i=0;i<X;i++){
            for(int j=0;j<Y;j++){
                array[i][j].setEnabled(false);
                if((i+j)%2 == 0){
                    array[i][j].even();
                }else{
                    array[i][j].odd();
                }

                if(board.Tiles[i][j].color == Tile.Color.Black){
                    array[i][j].black();
                }
                
                if(board.Tiles[i][j].color == Tile.Color.White){
                    array[i][j].white();
                }

                switch(board.Tiles[i][j].type){
                    case Pawn:
                        array[i][j].pawn();
                        break;
                    case Queen:
                        array[i][j].queen();
                        break;
                    case None:
                        break;
                }
            }
        }
    }
        
}
