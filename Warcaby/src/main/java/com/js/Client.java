package com.js;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.List;

import javax.swing.*;

/**
 * Klient gry
 */
public class Client extends JFrame {
    
    Board board;
    DrawableBoard drawableBoard;
    Tile.Color color;


    Socket socket = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    /**
     * Tworzy nowego Klienta
     */
    Client(){
        super();
        listenSocket();
        //pobierz kolor i plansze
        try {
            color = (Tile.Color) in.readObject();
            board = (Board) in.readObject();
        } catch (ClassNotFoundException | IOException ie) {}

        Board.X = board.Tiles.length;
        Board.Y = board.Tiles[0].length;
        
        drawableBoard = new DrawableBoard(board, this, color);
        add(drawableBoard);
        
        if(color == Tile.Color.White){
            try {
                in.readObject(); //null
            } catch (ClassNotFoundException | IOException ie) {}
            
            sendMove(null);
        }else{
            Move oppMove=null;
            try {
                oppMove = (Move) in.readObject();
            } catch (ClassNotFoundException | IOException e) {}
            drawableBoard.board.confirm(oppMove);
            drawableBoard.enableAndColorMoves(null);
        }
    }
    
    private void listenSocket() {
        try {
            socket = new Socket("localhost", 4444);
            // Inicjalizacja wysylania do serwera
            InputStream input = socket.getInputStream();
            in = new ObjectInputStream(input);

            OutputStream output = socket.getOutputStream();
            out = new ObjectOutputStream(output);
            // Inicjalizacja odbierania z serwera
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }

    /**
     * Wysyła ruch do serwera
     * @param move ruch
     */
    public void sendMove(Move move){
        try{
            out.writeObject(move);
            if(move!= null && move.end != null){
                drawableBoard.board.confirm(move);
                
                if(finish(board.ended())) return;

                Move oppMove = (Move) in.readObject();
                drawableBoard.board.confirm(oppMove);
                drawableBoard.enableAndColorMoves(null);

                finish(board.ended());
            }else{
                drawableBoard.enableAndColorMoves((List<Move>) in.readObject());
            }
        }catch(Exception ex){}
    }

    private Boolean finish(Tile.Color color){
        if(board.ended() == Tile.Color.None)
            return false;

        remove(drawableBoard);

        JPanel panel = new JPanel();
        JTextField endMessage = new JTextField();
        panel.add(endMessage);
        add(panel);
        if(color == this.color){
            endMessage.setText("Wygrałeś!");
        }else{
            endMessage.setText("Przegrałeś!");
        }
        
        SwingUtilities.updateComponentTreeUI(this);
        return true;
    }

    public static void main(String[] args) {
        Client frame = new Client();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize(300, 300);
        frame.setVisible(true);
    }   
}