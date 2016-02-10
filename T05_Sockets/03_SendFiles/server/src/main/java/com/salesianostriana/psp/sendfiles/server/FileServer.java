package com.salesianostriana.psp.sendfiles.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileServer {

    public static void main(String[] args) {


        ServerSocket server;
        ExecutorService executorService = Executors.newFixedThreadPool(3);


        try {
            server = new ServerSocket(10000);
            System.out.println("Servidor inicializado " + server.toString());
            while(true) {
                executorService.execute(new RecieveFile(server.accept()));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }







}
