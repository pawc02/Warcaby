package com.js;

import java.util.ArrayList;
import java.util.List;

import com.js.Tile.Color;
/**
 * Zestaw ruchów pionka
 */
public class SingleMoveSet{
	/**
	 * Zwarca listę możliwych ruchów
	 * @param x x pionka
	 * @param y	y pionka
	 * @param board	plansza
	 * @param backwards	czy może iść do tyłu
	 * @param infinite czy może ruszać się o wiele pól
	 * @return lista możliwych ruchów
	 */
	public static List<Move> getMoves(int x, int y, Board board, Boolean backwards, Boolean infinite) {
		List<Move> Moves = new ArrayList<Move>();
		int[] origin = {x,y};
		int[][] directions;

		if(backwards){
			int[][] dir = {{1,1}, {-1,1}, {-1,-1}, {1,-1}};
			directions = dir;
		}else{
			if(board.Tiles[x][y].color == Color.White){
				int[][] dir = {{-1,1},{1,1}};
				directions = dir;
			}else{
				int[][] dir = {{-1,-1},{1,-1}};
				directions = dir;
			}
		}


		for (int[] move : directions){
			do {
				int[] end = {x+move[0],y+move[1]};
				if(Board.inBounds.apply(end) && board.Tiles[end[0]][end[1]].type == Tile.Type.None){				
					Moves.add(new Move(origin,end,null));
				}else{
					break;
				}
				move[0] += Integer.signum(move[0]);
				move[1] += Integer.signum(move[1]);
			} while (infinite);
		}
		return Moves;
	}
}
