package com.js;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.js.Tile.Color;

/**
 * gra solo
 */
public class SoloGame implements Runnable{

    private Socket player;
    private RuleSet ruleset;
    private Random rand;

    /**
     * Tworzy grę o danym trybie
     * @param firstPlayer
     * @param secondPlayer
     * @param mode  tryb gry
     */
    public SoloGame(Socket player, int mode){
        this.player = player;
        rand = new Random();
        
        switch(mode){
            case 1: // Polskie
                ruleset = new PolishCheckers();
                break;
            case 2: // Tajskie
                ruleset = new ThaiCheckers();
                break;
            case 3: // Angielskie
                ruleset = new EnglishCheckers();
                break;
            default: // Polskie
                ruleset = new PolishCheckers();

        }
    }
    
    private Move aiMove(){
        List<int[]> poss = new ArrayList<int[]>();
        for(int i=0;i<Board.X; i++)
        for(int j=0;j<Board.Y; j++){
            if(ruleset.board.Tiles[i][j].color == Color.Black){
                int[] el = {i,j};
                poss.add(el);
            }
        }
        List<Move> list = new ArrayList<Move>();
        while(list.size() == 0){
            if(poss.size() == 0) return null;
            int r = rand.nextInt(poss.size());
            int[] el = poss.get(r);
            list = ruleset.getPossibleMoves(el[0], el[1]);
            poss.remove(r);
        }
        Move move = list.get(rand.nextInt(list.size()));
        return move;
    }


    /**
     * Włącza grę
     */
    @Override
    public void run() {
        try{
            //Inicjalizacja Wysylania do socketa dla player1
            OutputStream outputF = player.getOutputStream();
            ObjectOutputStream outF = new ObjectOutputStream(outputF);
            // PrintWriter outF = new PrintWriter(outputF, true);

            //Inicjalizacja pobieranie od socketa dla player1
            InputStream inputF = player.getInputStream();
            ObjectInputStream inF = new ObjectInputStream(inputF);
            // BufferedReader inF = new BufferedReader(new InputStreamReader(inputF));
            
            outF.writeObject(Tile.Color.White);
            outF.writeObject(ruleset.board);
            System.out.println("Info initialized");
            
            Move move = null;

            Tile.Color winner = Color.None;

            outF.writeObject(move);

            while(ruleset.ended() == Color.None){

                while(true){
                    move = (Move) inF.readObject();
                    if(move == null){
                        outF.writeObject(null);
                        continue;
                    }
                    if(move.end != null)
                        break;
                    System.out.println("Sending to first");
                    outF.writeObject(ruleset.getPossibleMoves(move.origin[0], move.origin[1]));
                    
                }
                ruleset.confirm(move);
                if(ruleset.ended() != Color.None){
                    winner = ruleset.ended() ;
                    break;
                }
                move = aiMove();
                if(move == null){
                    winner = Color.White;
                    break;
                }
                ruleset.confirm(move);

                outF.writeObject(move);
            }
            System.out.println("Koniec");
            System.exit(0);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

