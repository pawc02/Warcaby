package com.js;
// package com.mycompany.app;

import java.util.List;

public class App {
    /*
     * Na razie mam Zwykłe zasady (OrdinaryRuleSet) ze zwykłymi pionkami
     * Aby stworzyć planszę i pobierać ruchy a następnie je potwierdzać
     * tworzymy RuleSet ruleset = new OrdinaryRuleSet(szerokość, wysokość, liczba rzędów pionków);
     * następnie aby dostać ruchy wywołujemy List<Move> moves = ruleset.getPossibleMoves(xPionka, yPionka);
     * a aby pootwierdzić ruch robimy ruleset.confirm(ruch) //ruch to obiekt klasy Move
     * 
     * RuleSet przechowuje nam planszę i informacje o ruchach pionków, mogę dodać możliwość pobierania planszy do wysyłania,
     * ale wstępnie myśle że przesyłanie samych ruchów ObjectStreamem zadziała równie dobrze
     * 
     * na razie prawie wszystkie metody są publiczne dal wygody działania, potem się najwyżej je zabezpieczy
     */
    
    public static void main(String[] args){
        //przykładowe stworzenie zasad, pobranie ruchu i wysłanie
        RuleSet ruleset = new OrdinaryRuleSet(8,8,3);
        
        ruleset.board.print();

        List<Move> moves = ruleset.getPossibleMoves(2,2);

        System.out.println(" --------- ");
        
        for (Move move : moves)
            System.out.println(move);

        ruleset.confirm(moves.get(1));

        System.out.println(" --------- ");
        ruleset.board.print();
    }
}
