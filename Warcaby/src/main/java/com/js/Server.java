package com.js;

import java.io.*;
import java.net.*;

/**
 * Serwer gry
 */
public class Server {

    public static void main(String[] args) {
        int mode, type;

        System.out.println("Wybierz tryb \n1 - Gra z graczem\n2 - Gra z komputerem");
        try{
            type = Integer.parseInt(System.console().readLine());
        }catch(NumberFormatException ex){
            type = 2;
        }
        
        System.out.println("Wybierz rodzaj gry \n1 - Polskie\n2 - Tajskie\n3 - Angielskie");
        
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
                Runnable g;
                if(type == 2){
                    g = new SoloGame(firstClient, mode);
                }else{
                    System.out.println("Waiting for the second player");

                    Socket secondClient = serverSocket.accept();
                    System.out.println("Second client connected");

                    g = new Game(firstClient, secondClient, mode);
                }

                Thread gTh = new Thread(g);
                gTh.start();                
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
