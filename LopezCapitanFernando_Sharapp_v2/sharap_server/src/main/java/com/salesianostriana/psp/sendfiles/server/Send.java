package com.salesianostriana.psp.sendfiles.server;

import com.salesianostriana.psp.sendfiles.SharappMessage;

import java.io.IOException;

/**
 * Created by Luismi on 03/02/2016.
 */
public class Send implements Runnable {

    //Mensaje a enviar a todos los usuarios
    SharappMessage message;

    public Send(SharappMessage _message) {
        message = _message;
    }

    @Override
    public void run() {

        for (User usuario : FileServer.usuarios) {
            System.out.println("Enviando el mensaje con el fichero " + message.fileName + "al usuario " + usuario.userName);
            try {
                usuario.output.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
