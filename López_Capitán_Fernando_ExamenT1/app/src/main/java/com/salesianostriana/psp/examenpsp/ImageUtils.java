package com.salesianostriana.psp.examenpsp;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Luismi on 25/10/2015.
 */
public class ImageUtils {

    /*
        Este método estático devuelve la URL de una imagen aleatoria del contenido
        popular de Instragram.

        Es el método a utilizar para obtener la URL de la imagen a descargar
     */
    public static String getRandomPhoto() {
        Random r = new Random();
        ArrayList<String> listaURLs = getPhotoURLs();
        return listaURLs.get(r.nextInt(listaURLs.size()));
    }



    /*
	 * Método estático que recibe un Bitmap y nos devuelve otro con los colores
	 * invertidos
	 */
    public static Bitmap doInvert(Bitmap src) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // color info
        int A, R, G, B;
        int pixelColor;
        // image size
        int height = src.getHeight();
        int width = src.getWidth();

        // scan through every pixel
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                // get one pixel
                pixelColor = src.getPixel(x, y);
                // saving alpha channel
                A = Color.alpha(pixelColor);
                // inverting byte for each R/G/B channel
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);
                // set newly-inverted pixel to output image
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final bitmap
        return bmOut;
    }




    /*
        Método privado auxiliar para obtener la información del API de Instragram
     */
    private static String readInstagramAPI() {
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL("https://api.instagram.com/v1/media/popular?client_id=c15d8acfdb2a4511940092c9b208eab1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /*
        Método privado auxiliar para parsear la información obtenida del API de Instragram
     */
    private static ArrayList<String> getPhotoURLs() {
        ArrayList<String> lista = new ArrayList<String>();
        String jsonInstagramFeed = readInstagramAPI();
        try {
            JSONObject jsonObject = (JSONObject) new JSONTokener(jsonInstagramFeed).nextValue();
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++) {
                lista.add(jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }





}

