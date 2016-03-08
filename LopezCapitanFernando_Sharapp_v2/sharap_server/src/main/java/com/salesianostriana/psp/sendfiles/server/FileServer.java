package com.salesianostriana.psp.sendfiles.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class FileServer {

    //Constantes
    public static int SHARAPP_CONNECT_MESSAGE = 1;
    public static int SHARAPP_NORMAL_MESSAGE = 2;
    public static int SHARAPP_DISCONNECT_MESSAGE = 3;



    //Colecci�n compartida de usuarios
    static LinkedBlockingQueue<User> usuarios;

    //Pool de hilos. Lo montamos como est�tico
    //para poder usarlo desde varias clases.
    static ExecutorService executorService;


    public static void main(String[] args) {

        //Al igual que siempre, inicializamos el servidor
        ServerSocket server;
        executorService = Executors.newCachedThreadPool();

        //Inicializamos tambi�n la lista de usuarios
        usuarios = new LinkedBlockingQueue<>();



        try {
            server = new ServerSocket(10000);
            System.out.println("Servidor inicializado " + server.toString());
            while(true) {

                //Aceptamos una nueva conexi�n
                executorService.execute(new Connect(server.accept()));

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }







}
