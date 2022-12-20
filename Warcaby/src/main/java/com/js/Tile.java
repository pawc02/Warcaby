package com.js;

public class Tile{
    public enum Type{
        Pawn, Queen, None;
    }

    public enum Color{
        Black, White, None;
    }

    Type type;
    Color color;

    public Tile(Type type, Color color){
        this.type = type;
        this.color = color;
    }

    void capture(){ // zbicie
        type = Type.None;
        color = Color.None;
    }

    Tile.Color enemy(){ // zwraca wrogi kolor
        if (color == Color.Black)
            return Color.White;
        else
            return Color.Black;
    }
    
    void rankUp(){ // awans
        if (type == Type.Pawn)
            type = Type.Queen;
    }
}
