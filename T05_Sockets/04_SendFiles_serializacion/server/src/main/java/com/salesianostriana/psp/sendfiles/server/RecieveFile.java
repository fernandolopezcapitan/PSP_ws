package com.salesianostriana.psp.sendfiles.server;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        BufferedOutputStream bos = null;
        ObjectInputStream ois;
        ByteArrayInputStream bais = null;
        try {
            ois = new ObjectInputStream(s.getInputStream());

            Object objectReaded = ois.readObject();

            if (objectReaded instanceof com.salesianostriana.psp.sendfiles.ImageToSend) {
                com.salesianostriana.psp.sendfiles.ImageToSend asImageToSend = (com.salesianostriana.psp.sendfiles.ImageToSend) objectReaded;
                bos = new BufferedOutputStream(new FileOutputStream(asImageToSend.fileName));

                bais = new ByteArrayInputStream(asImageToSend.content);

                System.out.println("Almacenando el fichero con nombre " + asImageToSend.fileName);


                //Leemos el fichero desde el socket, y lo escribimos en el fichero.
                int SIZE = 64 * 1024;
                byte[] buffer = new byte[SIZE];

                int c;

                while ((c = bais.read(buffer, 0, SIZE)) !=  -1) {
                    bos.write(buffer, 0, c);
                }


            } else {

            }


            //Al terminar de escribir el fichero en disco, cerramos la conexión
            System.out.println("Finalización de la transmisión del fichero. Cerrando la comunicación");
            bos.close();
            bais.close();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
