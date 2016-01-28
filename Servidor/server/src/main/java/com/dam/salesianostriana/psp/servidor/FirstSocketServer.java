package com.dam.salesianostriana.psp.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstSocketServer {


    public static void main(String[] args) {

        //Creamos el servidor
        ServerSocket serverSocket = null;
        Socket s = null;

        try {

            serverSocket =
                    new ServerSocket(10000);
            System.out.println("Servidor inicializado " +
                    serverSocket.toString());

            /********************************************/
            // Ejecución con multiprocesamiento
            /********************************************/

            ExecutorService executorService = Executors.newFixedThreadPool(10);

            while(true) {
                //Aceptamos la conexión de un cliente
                executorService.execute(new SocketThread(serverSocket.accept()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
