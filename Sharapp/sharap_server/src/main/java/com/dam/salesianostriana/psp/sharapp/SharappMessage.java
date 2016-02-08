package com.dam.salesianostriana.psp.sharapp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Luismi on 02/02/2016.
 */
public class SharappMessage implements Serializable {

    public int typeMessage;
    public String userName;
    public String fileName;
    public byte[] content;
    public String message;
    public Date date;

}
