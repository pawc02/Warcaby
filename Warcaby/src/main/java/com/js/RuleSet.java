package com.js;

import java.util.List;

import com.js.Tile.Color;

public abstract class RuleSet {

    Board board;
    RuleSet(Board board){
        this.board = board;
    }

    public abstract List<Move> getPossibleMoves(int x, int y); // zwraca listę możliwych ruchów
    // zmodyfikowałem confirm żeby odejmował pionki od sumy
    public void confirm(Move move){
        
        if(move.captured != null)
        for(int[] cap : move.captured){
            
            if( board.Tiles[cap[0]][cap[1]].color == Color.Black ){
                board.blackPawns--;
            }else{
                board.whitePawns--;
            }
        }
        board.confirm(move);
    }; // potwierdza dany ruch

    // zwraca kolor który wygrał, albo żaden
    public Tile.Color ended(){
        if(board.whitePawns == 0)
            return Color.Black;
        if(board.blackPawns == 0)
            return Color.White;
        
        return Color.None;
    }
}