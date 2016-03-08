package com.dam.salesianostriana.psp.sendfiles;

import android.os.Handler;
import android.os.Message;

import com.salesianostriana.psp.sendfiles.SharappMessage;

import java.io.IOException;
import java.io.ObjectInputStream;


/**
 * Created by Luismi on 03/02/2016.
 */
public class Recieve implements Runnable{

    Handler handler;
    ObjectInputStream ois;

    public Recieve(ObjectInputStream ois, Handler handler){
        this.ois = ois;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {

            while (true){
                if(ois!=null){
                    Object object = ois.readObject();
                    if (object instanceof SharappMessage) {
                        Message msg = new Message();
                        SharappMessage obj = (SharappMessage) object;
                        msg.obj = obj;
                        handler.sendMessage(msg);

                    } else {

                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
