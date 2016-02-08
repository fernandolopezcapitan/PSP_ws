package com.salesianostriana.psp.sendfiles.server;

import com.salesianostriana.psp.sendfiles.SharappMessage;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Luismi on 02/02/2016.
 */
public class Connect implements Runnable {


    Socket s;
    User u;

    public Connect(Socket _s) {

        u = new User();
        s = _s;
        System.out.println("Conexión aceptada: " + s.toString());
    }




    @Override
    public void run() {


        try {
            //Inicializamos el usuario
            u.s = s;
            u.input = new ObjectInputStream(u.s.getInputStream());
            u.output = new ObjectOutputStream(u.s.getOutputStream());


            //Recibimos el mensaje de conexión, que incluye el
            //nombre de usuario
            Object messageAsObject = u.input.readObject();

            if (messageAsObject instanceof SharappMessage) {
                SharappMessage message = (SharappMessage) messageAsObject;
                u.userName = message.userName;
            } else {
                throw new ClassCastException();
            }


            //Añadimos al usuario a la colección
            FileServer.usuarios.add(u);

            //Inicializamos el mecanismo de recepción de imágenes
            FileServer.executorService.execute(new Recieve(u));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }






    }

}
