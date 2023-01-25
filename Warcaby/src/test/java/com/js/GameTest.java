package com.js;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.js.Tile.Color;

/**
 * Testowe rozgrywki
 */
public class GameTest 
{
    public static void main(String[] args){
        
        new GameTest().RandomGame();

    }
    
    @Test
    public void multiRandGame(){
        for(int i = 0; i< 20; i++)
            RandomGame();
    }


    @Test
    public void RandomGame()
    {
        Random rand = new Random();
        RuleSet ruleset = new PolishCheckers();
        Tile player = new Tile(null,Color.White);
        while(ruleset.ended() == Color.None){
            System.out.println(ruleset.board);
            List<int[]> poss = new ArrayList<int[]>();
            for(int i=0;i<Board.X; i++)
            for(int j=0;j<Board.Y; j++){
                if(ruleset.board.Tiles[i][j].color == player.color){
                    int[] el = {i,j};
                    poss.add(el);
                }
            }
            List<Move> list = new ArrayList<Move>();
            while(list.size() == 0){
                if(poss.size() == 0) return;
                int r = rand.nextInt(poss.size());
                int[] el = poss.get(r);
                list = ruleset.getPossibleMoves(el[0], el[1]);
                poss.remove(r);
            }
            ruleset.confirm(list.get(rand.nextInt(list.size())));

            player.color = player.enemy();
        }
        System.out.println(ruleset.board);
        
        assertTrue( ruleset.board.whitePawns == 0 || ruleset.board.blackPawns == 0 );
    }
}
