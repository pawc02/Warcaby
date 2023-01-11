package com.js;

import java.io.*;
import java.net.Socket;

import com.js.Tile.Color;

/**
 * gra
 */
public class Game implements Runnable{

    private Socket firstPlayer;
    private Socket secondPlayer;
    private RuleSet ruleset;


    private final static int FIRST=1;
    private final static int SECOND=2;
    private static int currPlayer=FIRST;

    /**
     * Tworzy grę o danym trybie
     * @param firstPlayer
     * @param secondPlayer
     * @param mode  tryb gry
     */
    public Game(Socket firstPlayer, Socket secondPlayer, int mode){
        this.firstPlayer = firstPlayer;
        this.secondPlayer= secondPlayer;
        
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
                ruleset = new ThaiCheckers();

        }
    }
    /**
     * Włącza grę
     */
    @Override
    public void run() {
        try{
            System.out.println("Działam3");
            //Inicjalizacja Wysylania do socketa dla player1
            OutputStream outputF = firstPlayer.getOutputStream();
            ObjectOutputStream outF = new ObjectOutputStream(outputF);
            // PrintWriter outF = new PrintWriter(outputF, true);

            //Inicjalizacja Wysylania do socketa dla player2
            OutputStream outputS = secondPlayer.getOutputStream();
            ObjectOutputStream outS = new ObjectOutputStream(outputS);

            //Inicjalizacja pobieranie od socketa dla player1
            InputStream inputF = firstPlayer.getInputStream();
            ObjectInputStream inF = new ObjectInputStream(inputF);
            // BufferedReader inF = new BufferedReader(new InputStreamReader(inputF));

            //Inicjalizacja pobieranie od socketa dla player2
            InputStream inputS = secondPlayer.getInputStream();
            ObjectInputStream inS = new ObjectInputStream(inputS);
            
            outF.writeObject(Tile.Color.White);
            outF.writeObject(ruleset.board);
            outS.writeObject(Tile.Color.Black);
            outS.writeObject(ruleset.board);
            System.out.println("Info initialized");
            
            Move move = null;

            while(ruleset.ended() == Color.None){
                if(currPlayer == FIRST){
                    System.out.println("First's turn");
                    outF.writeObject(move);
                    
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
                    currPlayer = SECOND;
                }else{
                    System.out.println("Second's turn");
                    outS.writeObject(move);
                    
                    while(true){
                        move = (Move) inS.readObject();
                        if(move == null){
                            outS.writeObject(null);
                            continue;
                        }
                        if(move.end != null)
                            break;
                            System.out.println("Sending to second");
                        outS.writeObject(ruleset.getPossibleMoves(move.origin[0], move.origin[1]));
                    }
                    ruleset.confirm(move);
                    currPlayer = FIRST;
                }
            }

            // Wyślij wygrywający ruch
            if(currPlayer == FIRST){
                outF.writeObject(move);
            }else{
                outS.writeObject(move);
            }
            System.out.println("Koniec");
            System.exit(0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

