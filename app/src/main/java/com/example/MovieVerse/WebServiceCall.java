package com.example.MovieVerse;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class WebServiceCall extends Thread {

    private String richiesta;
    private JSONObject rispostaJson;
    public void sedRequest (String x){
        this.richiesta = x;
        start();
    }
    @Override
    public void run() {
        super.run();
        try {
            URL url = new URL(this.richiesta);
            URLConnection uc = url.openConnection();
            String auth = "Bearer " + new String("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlZGM3Nzc1MTU2YzBjM2U2NmE0MTJjNTI3ZGYzNDEzMyIsInN1YiI6IjY2Mjc3MTI1YjI2ODFmMDE3YzczZDM3NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.yyv-z4Za_38FbEJ4yJVV7MqWRILPIddgQmdJQTuuMVU");
            uc.setRequestProperty("Authorization", auth);

            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            String response  = "";
            while ((inputLine = in.readLine()) != null)
                response += inputLine;
            in.close();


            Log.d("WSP",response);
            rispostaJson = new JSONObject(response);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    public ArrayList<Film> getFilmsFromResponse() {
        ArrayList<Film> films = new ArrayList<>();
        try {
            if (rispostaJson != null && rispostaJson.has("results")) {
                JSONArray results = rispostaJson.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject movie = results.getJSONObject(i);
                    String pathCopertina = "https://image.tmdb.org/t/p/original" + (movie.has("poster_path") ? movie.getString("poster_path") : "");
                    String pathBG = "https://image.tmdb.org/t/p/original" + (movie.has("backdrop_path") ? movie.getString("backdrop_path") : "");
                    String titolo = movie.has("title") ? movie.getString("title") : "";
                    int genere = 1; // Bisogna mappare i generi
                    int annoProd = movie.has("release_date") ? Integer.parseInt(movie.getString("release_date").substring(0, 4)) : 0;
                    String trama = movie.has("overview") ? movie.getString("overview") : "";

                    Film film = new Film(pathCopertina, pathBG, titolo, genere, annoProd, trama);
                    films.add(film);
                }
            } else {
                Log.e("WebServiceCall", "Il JSON non contiene il campo 'results'");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return films;
    }
}