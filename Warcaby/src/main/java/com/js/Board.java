package com.js;

import java.util.function.Function;


public class Board {
    int X,Y;
    Tile[][] Tiles;

    Function<int[],Boolean> inBounds = move -> move[0]>= 0 && move[0] < X && move[1] >= 0 && move[1] < Y;

    Board(int X, int Y, int pawnRows){
        this.X = X;
        this.Y = Y;
        
        Tiles = new Tile[X][Y];
        
        // wypełnianie planszy
        for ( int x = 0; x < X; x++ ){
            for ( int y = 0; y < Y; y++){
                if ( (x+y) % 2 == 0){
                    if ( y < pawnRows )
                        Tiles[x][y] = new Tile(Tile.Type.Pawn, Tile.Color.White);
                    else if ( y >= Y - pawnRows)
                        Tiles[x][y] = new Tile(Tile.Type.Pawn, Tile.Color.Black);
                    else
                        Tiles[x][y] = new Tile(Tile.Type.None, Tile.Color.None);
                }else
                    Tiles[x][y] = new Tile(Tile.Type.None, Tile.Color.None);
            }
        }
        
    }



    public void confirm(Move move){
        Tiles[move.end[0]][move.end[1]] = Tiles[move.origin[0]][move.origin[1]];
        
        Tiles[move.origin[0]][move.origin[1]] = new Tile(Tile.Type.None, Tile.Color.None);
        
        if(move.captured != null) for (int[] pawn : move.captured)
            Tiles[pawn[0]][pawn[1]].capture();
    }


    //proste wyświetlanie planszy
    public void print(){
        for ( int y = 0; y < Y; y++){
            for ( int x = 0; x < X; x++ ){
                switch(Tiles[x][y].color){
                    case White:
                        System.out.print("w ");
                        break;
                    case Black:
                        System.out.print("b ");
                        break;
                    case None:
                        System.out.print("- ");
                        break;
                }
            }
            System.out.print("\n");
        }
    }
}