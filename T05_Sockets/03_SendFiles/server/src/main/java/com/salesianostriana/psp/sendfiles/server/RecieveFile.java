package com.salesianostriana.psp.sendfiles.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Luismi on 02/02/2016.
 */
public class RecieveFile implements Runnable {


    Socket s;

    public RecieveFile(Socket _s) {
        s = _s;
        System.out.println("Conexión aceptada: " + s.toString());
    }




    @Override
    public void run() {

        //Definimos el flujo de entrada y de salida (a disco)
        BufferedInputStream bis;
        BufferedOutputStream bos;
        try {
            bis = new BufferedInputStream(s.getInputStream());
            String fileName = Utils.getRandomJPGFileName();

            System.out.println("Almacenando el fichero con nombre " + fileName);

            bos = new BufferedOutputStream(new FileOutputStream(fileName));

            //Leemos el fichero desde el socket, y lo escribimos en el fichero.
            int SIZE = 64 * 1024;
            byte[] buffer = new byte[SIZE];

            int c;

            while ((c = bis.read(buffer, 0, SIZE)) !=  -1) {
                bos.write(buffer, 0, c);
            }

            //Al terminar de escribir el fichero en disco, cerramos la conexión
            System.out.println("Finalización de la transmisión del fichero. Cerrando la comunicación");
            bos.close();
            bis.close();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
