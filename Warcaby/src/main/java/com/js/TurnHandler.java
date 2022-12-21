package com.js;

import java.util.List;

import com.js.Tile.Color;
import com.js.Tile.Type;

/*
 * Zastępuje klase App, pozwala na lokalną prostą grę na terminalu
 */

public class TurnHandler {
    // obecny gracz jest olną płytką o danym kolorze.
    static Tile currPlayer = new Tile(Type.None, Color.White);
    

    public static void main(String[] args){

        RuleSet ruleset = new OrdinaryRuleSet(3,3,1); // określamy wymiary i liczbę rzędów pionków

        while(ruleset.ended() == Color.None){
            //ruchy po kolei z wybieraniem move'ów

            //kolejka
            while (true){
                ruleset.board.print();
                
                System.out.print("X: ");
                int x = Integer.parseInt( System.console().readLine() );
                System.out.print("Y: ");
                int y = Integer.parseInt( System.console().readLine() );

                //sprawdza czy to na pewno pionek właściciela
                int[] pos = {x,y};
                if( ruleset.board.inBounds.apply(pos) == false ){
                    continue; // w przypadku złych danych wpisujemy je jeszcze raz
                }else{
                    if( currPlayer.color == ruleset.board.Tiles[x][y].color ){
                        List<Move> moves = ruleset.getPossibleMoves(x, y);
                
                        for(Move move : moves){
                            System.out.println(move);
                        }

                        System.out.println("Wybierz ruch, albo kliknij -1 Aby wybrać nowy pionek");
                        
                        int choice = Integer.parseInt( System.console().readLine() );
                    
                        if(choice >= 0 && choice < moves.size()){
                            ruleset.confirm(moves.get(choice));
                            break;
                        }
                    }
                }
                
            }
            currPlayer.color = currPlayer.enemy();         

        }
        if(ruleset.ended() == Color.White){
            System.out.println("Białe wygrały");
        }else{
            System.out.println("Czarne wygrały");
        }
    }
}
