package com.js;

import java.io.Serializable;
import java.util.List;

public class Move implements Serializable{
    public int[] origin;            // współrzędne początkowe
    public int[] end;               // współrzędne końcowe
    public List<int[]> captured;    // lista współrzędnych zbitych

    Move(int[] origin, int[] end, List<int[]> captured){
        this.origin = origin;
        this.end = end;
        this.captured = captured;
    }

    // Aby dało się wygodnie printować
    @Override
    public String toString() {
        String string = new String();
        string = string.concat("Pawn : "+ origin[0] + " " + origin[1]);
        string = string.concat("\nNew  : "+ end[0] + " " + end[1]);
        if(captured != null) for (int[] pawn : captured)
            string = string.concat("\nCap  : "+ pawn[0] + " " + pawn[1]);
        else
        string = string.concat("\nNo pieces captured");
        return string;
    }
}
