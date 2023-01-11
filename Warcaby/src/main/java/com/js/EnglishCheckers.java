package com.js;

import java.util.ArrayList;
import java.util.List;


public class EnglishCheckers extends RuleSet{
    // 8x8x2 - jakiekolwiek bicie
    EnglishCheckers(){
        super(new Board(8, 8, 3));
    }

    @Override
    public List<Move> getPossibleMoves(int x, int y) {
        List<Move> moves = new ArrayList<Move>();

        int[] origin = {x,y};

        if(!Board.inBounds.apply(origin))
            return null;
        
        
        Boolean backwardsMove = false, backwardsCap = false, infinite = false;
        
        List<Move> newMoves;
        // find best move OF ALL
        int max = 0;
        for(int i = 0; i < Board.X; i++)
            for(int j = 0; j < Board.Y; j++)
                if(board.Tiles[i][j].color == board.Tiles[x][y].color){
                    switch(board.Tiles[i][j].type){
                        case Pawn:
                            backwardsMove = false;
                            backwardsCap = false;
                            infinite = false;
                            break;
                        case Queen:
                            backwardsMove = true;
                            backwardsCap = true;
                            infinite = false;
                            break;
                        case None:
                            return moves;
                    }
                    newMoves = MultiMoveSet.getMoves(i,j,board, backwardsCap, infinite);
                    for(Move move : newMoves)
                        if(move.captured.size() > max)
                            max = move.captured.size();
                }
        // max max max max max max max max
        switch(board.Tiles[x][y].type){
            case Pawn:
                backwardsMove = false;
                backwardsCap = false;
                infinite = false;
                break;
            case Queen:
                backwardsMove = true;
                backwardsCap = true;
                infinite = false;
                break;
            case None:
                return moves;
        }
        newMoves = MultiMoveSet.getMoves(x,y,board, backwardsCap, infinite);
        for(Move move : newMoves)
            if(max > 0)
                moves.add(move);
                
        if(max == 0) moves.addAll(SingleMoveSet.getMoves(x,y,board, backwardsMove, infinite));

        return moves;
    }

    @Override
    public void confirm(Move move) {
        super.confirm(move);
    }
}
