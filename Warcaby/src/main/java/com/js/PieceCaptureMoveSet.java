package com.js;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PieceCaptureMoveSet{
	// tylko bicia pionka

	public static List<Move> getMoves(int x, int y, Tile[][] Tiles, Function<int[],Boolean> inBounds) {
		List<Move> Moves = new ArrayList<Move>();
        Tile pawn = Tiles[x][y];
		
		int[][] moves = {{2,2}, {-2,2}, {-2,-2}, {2,-2}};
		
		for (int[] move : moves){
			int[] origin = {x,y};
			int[] end = {x+move[0],y+move[1]};
			int[] cap = {x+move[0]/2,y+move[1]/2};
			if(inBounds.apply(end)){
				if ( Tiles[end[0]][end[1]].type  == Tile.Type.None ){
					if ( Tiles[cap[0]][cap[1]].color == pawn.enemy() ){
						List<int[]> captured = new ArrayList<int[]>();
						captured.add(cap);
						Moves.add( new Move(origin, end, captured) );
					}
				}
			}
		}
		return Moves;
	}
}
