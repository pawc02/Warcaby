package com.js;

import java.io.*;
import java.net.*;

/**
 * Serwer gry
 */
public class Server {

    public static void main(String[] args) {
        System.out.println("Wybierz rodzaj gry \n1 - Polskie\n2 - Tajskie\n3 - Angielskie");
        int mode;
        try{
            mode = Integer.parseInt(System.console().readLine());
        }catch(NumberFormatException ex){
            mode = 1;
        }

        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            System.out.println("Server is listening on port 4444");

            while (true) {
                Socket firstClient = serverSocket.accept();
                System.out.println("First client connected");
                System.out.println("Waiting for the second player");

                Socket secondClient = serverSocket.accept();
                System.out.println("Second client connected");

                Game g = new Game(firstClient, secondClient, mode);
                Thread gTh = new Thread(g);
                gTh.start();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
