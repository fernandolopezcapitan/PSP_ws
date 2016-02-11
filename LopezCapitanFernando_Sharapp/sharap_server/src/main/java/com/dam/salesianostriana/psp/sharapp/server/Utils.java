package com.dam.salesianostriana.psp.sharapp.server;

import java.util.Random;

/**
 * Created by Luismi on 02/02/2016.
 */
public class Utils {


    private static String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    public static String getRandomJPGFileName() {

        Random r = new Random(System.currentTimeMillis());

        int length = 10 + r.nextInt(5);

        StringBuffer filename = new StringBuffer(length);

        for(int i = 0; i < length; i++) {
            filename.append(abc.charAt(r.nextInt(abc.length())));
        }

        return filename.toString() + ".jpg";

    }


}
