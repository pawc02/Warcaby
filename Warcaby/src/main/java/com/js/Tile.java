package com.js; //done

import java.io.Serializable;

/**
 * Jeden kafelek planszy
 */
public class Tile implements Serializable{
    /**
     * Typ Kafelka
     */
    public enum Type{
        Pawn, Queen, None;
    }
    /**
     * Kolor kafelka
     */
    public enum Color{
        Black, White, None;
    }

    public Type type;
    public Color color;
    /**
     * Tworzy nowy kafelek
     * @param type typ
     * @param color kolor
     */
    public Tile(Type type, Color color){
        this.type = type;
        this.color = color;
    }
    /**
     * Zbija dany kafelek
     */
    public void capture(){
        type = Type.None;
        color = Color.None;
    }
    /**
     * Zwraca kolor wroga
     * @return kolor wroga
     */
    public Tile.Color enemy(){
        if (color == Color.Black)
            return Color.White;
        else
            return Color.Black;
    }
    
    /**
     * Awans
     */
    public void rankUp(){
        if (type == Type.Pawn)
            type = Type.Queen;
    }
}
