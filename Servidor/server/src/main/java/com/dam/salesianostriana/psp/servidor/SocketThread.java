package com.dam.salesianostriana.psp.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by flopez on 25/01/2016.
 */
public class SocketThread implements Runnable {

    private Socket s;

    public SocketThread(Socket _s) {
        s = _s;
    }

    @Override
    public void run() {

        try {
            System.out.println("Conexión establecida " + s.toString());

            //Construimos un BufferedReader sobre
            //el flujo de entrada del socket
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    s.getInputStream()));

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(s.getOutputStream()),true);

            //El mensaje FIN será el que finalice la
            //comunicación entre cliente y servidor.
            //Mientras tanto, vamos leyendo los mensajes
            //y los escribimos en la consola.
            String mensaje;

            while(!((mensaje = bufferedReader.readLine())
                    .equalsIgnoreCase("FIN"))) {

                System.out.println(">> " + mensaje);
                // Enviar el mensaje recogido

                // enviamos la info
                printWriter.println(mensaje);
                // forzamos el envío
                printWriter.flush();

                // cerramos el socket y con ello lo socket definidos con él
            }

            //Finalizamos la conexión
            s.close();

            System.out.println("Conexión finalizada");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
