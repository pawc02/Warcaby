package com.js;

import java.util.ArrayList;
import java.util.List;

public class OrdinaryRuleSet extends RuleSet{

    OrdinaryRuleSet(int X, int Y, int pawnRows){
        super(new Board(X, Y, pawnRows));
    }

    @Override
    public List<Move> getPossibleMoves(int x, int y) {
        List<Move> moves = new ArrayList<Move>();

        int[] pos = {x,y};
        if(!board.inBounds.apply(pos))
            return null;

        switch(board.Tiles[x][y].type){
            case Pawn:
                moves.addAll(PieceMoveSet.getMoves(x,y,board.Tiles,board.inBounds));
                moves.addAll(PieceCaptureMoveSet.getMoves(x,y,board.Tiles,board.inBounds));
                break;
            default:
                break;
        }

        return moves;
    }

    @Override
    public void confirm(Move move) {
        board.confirm(move);
    }
}
