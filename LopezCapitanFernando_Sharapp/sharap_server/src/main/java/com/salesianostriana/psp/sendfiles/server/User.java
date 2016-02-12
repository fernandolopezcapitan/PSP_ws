package com.salesianostriana.psp.sendfiles.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Luismi on 03/02/2016.
 */
public class User {

    public Socket s;
    public ObjectInputStream input;
    public ObjectOutputStream output;
    public String userName;



}
