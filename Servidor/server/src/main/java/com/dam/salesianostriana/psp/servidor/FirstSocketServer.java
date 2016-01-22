package com.dam.salesianostriana.psp.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class FirstSocketServer {


    public static void main (String[] arg){

        // Creamos el servidor
        ServerSocket serverSocket = null;
        Socket s = null;

        try {
            serverSocket = new ServerSocket(10000);// numero de puerto de 1024 en adelante y llega hasta 65000
            System.out.println("Servidor inicializado "+ serverSocket.toString());

            // Aceptamos la conexión de un cliente, metiéndola en un bucle
            s = serverSocket.accept();
            System.out.println("Conexión establecida " + s.toString());

            // Construímos un BufferedReader sobre el flujo de entrada del Socket
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));

            // El mensaje FIN será el que finalice la comunicación entre cliente y servidor.
            // Mientras tanto, vamos leyendo los mensajes y los escribimos en consola.
            String mensaje;

            while (!((mensaje = bufferedReader.readLine()).equalsIgnoreCase("FIN"))) {
                System.out.println(">> " + mensaje);
            }

            // Finalizamos la conexión
            s.close();

            System.out.println("Conexión finalizada");




        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
