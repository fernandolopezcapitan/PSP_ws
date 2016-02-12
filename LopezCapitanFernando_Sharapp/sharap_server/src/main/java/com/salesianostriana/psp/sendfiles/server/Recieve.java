package com.salesianostriana.psp.sendfiles.server;

import com.salesianostriana.psp.sendfiles.SharappMessage;

import java.io.IOException;

/**
 * Created by Luismi on 03/02/2016.
 */
public class Recieve implements Runnable {

    //Usuario del cual recibimos las im�genes
    User u;

    public Recieve(User _u) {
        u = _u;
    }


    @Override
    public void run() {


        Object objectReaded = null;
        try {


            while(true) {
                //Leemos el objeto
                objectReaded = u.input.readObject();
                //Si es una instancia de nuestra clase
                if (objectReaded instanceof SharappMessage) {

                    SharappMessage asSharappMessage = (SharappMessage) objectReaded;

                    if (asSharappMessage.typeMessage == FileServer.SHARAPP_NORMAL_MESSAGE) {
                        //bos = new BufferedOutputStream(new FileOutputStream(asSharappMessage.fileName));
                        //bais = new ByteArrayInputStream(asSharappMessage.content);

                        System.out.println("Mensaje recibido del usuario  " + asSharappMessage.userName
                                + "[Fichero: " + asSharappMessage.fileName + "]");

                        //Enviamos el mensaje al resto de usuarios
                        FileServer.executorService.execute(new Send(asSharappMessage));


                    } else if (asSharappMessage.typeMessage == FileServer.SHARAPP_DISCONNECT_MESSAGE) {

                        //El mensaje indica que tenemos que desconectar a nuestro usuario

                        //Cerramos los flujos
                        u.input.close();
                        u.output.close();
                        u.s.close();
                        //Eliminamos al usuario de la lista
                        FileServer.usuarios.remove(u);
                        //Salimos del bucle
                        break;
                    }




                } else {
                    throw new ClassCastException();
                }
            }





        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }







    }


}
