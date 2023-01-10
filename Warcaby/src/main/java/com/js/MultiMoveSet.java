package com.js;

import java.util.ArrayList;
import java.util.List;

import com.js.Tile.Color;
/**
 * Zbiór ruchów przechwycających pionki
 */
public class MultiMoveSet{
	public static Tile pawn;
	/**
	 * Zwarca listę możliwych bić
	 * @param x x pionka
	 * @param y	y pionka
	 * @param board	plansza
	 * @param backwards	czy może bić do tyłu
	 * @param infinite czy może ruszać się o wiele pól
	 * @return lista możliwych bić
	 */
	public static List<Move> getMoves(int x, int y, Board board, Boolean backwards, Boolean infinite) {
		int[] origin = {x,y};
		pawn = board.Tiles[x][y];
		Move primary = new Move(origin, null, new ArrayList<int[]>());
		
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


		List<Move> moves = getSingleMove(x, y, board, primary, directions, infinite);
		
		return moves;
	}

	
	private static List<Move> getSingleMove(int x, int y, Board board, Move moveUpToNow, int[][] moves, Boolean infinite){
		
		Board newBoard = board.clone();
		newBoard.confirm(moveUpToNow);
		
		List<Move> listOfMoves = new ArrayList<Move>();

		for (int[] move : moves){
			int[] end = {x+move[0],y+move[1]};
			if(infinite)
				while(Board.inBounds.apply(end) && newBoard.Tiles[end[0]][end[1]].type  == Tile.Type.None ){
					end[0] += move[0];
					end[1] += move[1];
				}
			end[0] += move[0];
			end[1] += move[1];

			if(!Board.inBounds.apply(end))
				continue;


			int[] cap = {end[0] - Integer.signum(move[0]), end[1] - Integer.signum(move[1])};

			// warunek zbicia ====================================================
			if ( newBoard.Tiles[end[0]][end[1]].type  == Tile.Type.None ){
				if ( newBoard.Tiles[cap[0]][cap[1]].color == pawn.enemy() ){
						//    ====================================================
						
					List<int[]> captured = new ArrayList<int[]>();
					for(int[] c : moveUpToNow.captured){
						int[] cp = {c[0],c[1]};
						captured.add(cp);
					}
					captured.add(cap);

					listOfMoves.add(new Move(moveUpToNow.origin, end, captured));
				}
			}	
		}
		
		List<Move> listOfFollowingMoves = new ArrayList<Move>();
		for(Move move : listOfMoves){
			listOfFollowingMoves.addAll(getSingleMove(move.end[0], move.end[1], board, move, moves, infinite));
		}
		listOfMoves.addAll(listOfFollowingMoves);

		return listOfMoves;
	}
}