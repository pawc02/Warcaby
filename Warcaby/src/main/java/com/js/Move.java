package com.js;

import java.io.Serializable;
import java.util.List;

/*
 * Siła ruchu, czyli to czy ma priorytet nad innym,
 * to po prostu liczba captured pionków
 */


/**
 * Ruch
 */
public class Move implements Serializable{
    public int[] origin;            // współrzędne początkowe
    public int[] end;               // współrzędne końcowe
    public List<int[]> captured;    // lista współrzędnych zbitych

    /**
     * Tworzy nowy ruch
     * @param origin początek
     * @param end koniec
     * @param captured tablica przechwyconych pionków
     */
    Move(int[] origin, int[] end, List<int[]> captured){
        this.origin = origin;
        this.end = end;
        this.captured = captured;
    }

    @Override
    public String toString() {
        String string = new String();
        if(origin != null)
            string = string.concat("Pawn : "+ origin[0] + " " + origin[1]);
        else
            string = string.concat("No origin");
        if(end != null)
            string = string.concat("\nNew  : "+ end[0] + " " + end[1]);
        else
            string = string.concat("\nNo end");
        if(captured != null) for (int[] pawn : captured)
            string = string.concat("\nCap  : "+ pawn[0] + " " + pawn[1]);
        else
            string = string.concat("\nNo pieces captured");
        return string;
    }
}
