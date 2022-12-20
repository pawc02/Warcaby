package com.js;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PieceMoveSet extends MoveSet{
	// tylko ruchy bez bicia pionka do przodu
	
	public static List<Move> getMoves(int x, int y, Tile[][] Tiles, Function<int[],Boolean> inBounds) {
		List<Move> Moves = new ArrayList<Move>();
		int[] origin = {x,y};
        
		int Ymod = 1;
		
		if (Tiles[x][y].color == Tile.Color.Black)
			Ymod = -1;

		int[][] moves = {{1,Ymod}, {-1,Ymod}};
		
		for (int[] move : moves){
			int[] end = {x+move[0],y+move[1]};
			if(inBounds.apply(end) && Tiles[end[0]][end[1]].type == Tile.Type.None){				
				Moves.add(new Move(origin,end,null));
			}
		}
		return Moves;
	}
}
