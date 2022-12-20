package com.js;

import java.util.List;

public abstract class RuleSet {
    Board board;
    RuleSet(Board board){
        this.board = board;
    }

    public abstract List<Move> getPossibleMoves(int x, int y); // zwraca listę możliwych ruchów
    public abstract void confirm(Move move); // potwierdza dany ruch
}