package com.js;

import java.io.Serializable;
import java.util.function.Function;

import com.js.Tile.Color;

/**
 * Plansza do gry
 */
public class Board implements Serializable{
    // Wymiary i plansza
    public static int X,Y;
    public Tile[][] Tiles;
    
    // liczba pionków danego koloru na planszy
    public int whitePawns, blackPawns, pawnRows;
    
    // Zwykła prostokątna plansza
    public static Function<int[],Boolean> inBounds = move -> move[0]>= 0 && move[0] < X && move[1] >= 0 && move[1] < Y;
/**
 * Tworzy nową planszę
 * @param X Szerokość
 * @param Y Wysokość
 * @param pawnRows Liczba rzędów pionków
 */
    Board(int X, int Y, int pawnRows){
        Board.X = X;
        Board.Y = Y;
        this.pawnRows = pawnRows;

        Tiles = new Tile[X][Y];
        
        // wypełnianie planszy
        for ( int x = 0; x < X; x++ ){
            for ( int y = 0; y < Y; y++){
                if ( (x+y) % 2 == 0){
                    if ( y < pawnRows ){
                        Tiles[x][y] = new Tile(Tile.Type.Pawn, Tile.Color.White);
                        this.whitePawns++;
                    }else if ( y >= Y - pawnRows){
                        Tiles[x][y] = new Tile(Tile.Type.Pawn, Tile.Color.Black);
                        this.blackPawns++;
                    }else
                        Tiles[x][y] = new Tile(Tile.Type.None, Tile.Color.None);
                }else
                    Tiles[x][y] = new Tile(Tile.Type.None, Tile.Color.None);
            }
        }
    }
/**
 * Potwierdza ruch na planszy
 * @param move ruch
 */
    public void confirm(Move move){
        if(move == null || move.end == null)
            return;
        Tiles[move.end[0]][move.end[1]] = Tiles[move.origin[0]][move.origin[1]];
        
        Tiles[move.origin[0]][move.origin[1]] = new Tile(Tile.Type.None, Tile.Color.None);
        
        if(move.captured != null) for (int[] pawn : move.captured){
            if( Tiles[pawn[0]][pawn[1]].color == Color.Black ){
                blackPawns--;
            }else{
                whitePawns--;
            }

            Tiles[pawn[0]][pawn[1]].capture();
        }
    

        // rankup
        if(Tiles[move.end[0]][move.end[1]].color == Color.White && move.end[1] == Y-1){
            Tiles[move.end[0]][move.end[1]].rankUp();
        }
        if(Tiles[move.end[0]][move.end[1]].color == Color.Black && move.end[1] == 0){
            Tiles[move.end[0]][move.end[1]].rankUp();
        }
    }

    @Override
    public String toString(){
        String string = "";
        for ( int x = 0; x < X; x++ ){ string = string.concat(" " + x);}
        for ( int y = 0; y < Y; y++){
            string = string.concat("\n" + y + "   ");
            
            for ( int x = 0; x < X; x++ ){
                switch(Tiles[x][y].color){
                    case White:
                        string = string.concat("w ");
                        break;
                    case Black:
                        string = string.concat("b ");
                        break;
                    case None:
                        string = string.concat("- ");
                        break;
                }
            }
        }
        return string;
    }
/**
 * duplikuje planszę
 */
    public Board clone(){
        Board newBoard = new Board(X, Y, pawnRows);
        newBoard.blackPawns = this.blackPawns;
        newBoard.whitePawns = this.whitePawns;
        
        for ( int x = 0; x < X; x++ ){
            for ( int y = 0; y < Y; y++){
                newBoard.Tiles[x][y].type = this.Tiles[x][y].type;
                newBoard.Tiles[x][y].color = this.Tiles[x][y].color;
            }
        }

        return newBoard;
    }
/**
 * Sprawdza czy gra się skończyła
 * @return kolor zwycięzcy albo None jeśli gra trwa
 */
    public Tile.Color ended(){
        if(this.whitePawns == 0)
            return Color.Black;
        if(this.blackPawns == 0)
            return Color.White;

        return Color.None;
    }
}