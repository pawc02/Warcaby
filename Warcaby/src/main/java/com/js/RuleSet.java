package com.js;

import java.util.List;

/**
 * Zestaw zasad
 */
public abstract class RuleSet {

    Board board;
    
    /**
     * Tworzy zestaw zasad
     * @param board plansza
     */
    RuleSet(Board board){
        this.board = board;
    }

    /**
     * Zwaraca listę możliwych ruchów
     * @param x x pionka
     * @param y y pionka
     * @return Lista możliwych ruchów
     */
    public abstract List<Move> getPossibleMoves(int x, int y);

    /**
     * Potwierdza dany ruch na planszy
     * @param move ruch
     */
    public void confirm(Move move){
        board.confirm(move);
    }

    /**
     * Sprawdza czy gra się skończyła
     * @return kolor zwycięzcy albo None jeśli gra trwa
     */
    public Tile.Color ended(){
        return board.ended();
    }
}