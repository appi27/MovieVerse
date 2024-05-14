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
import java.util.HashMap;
import java.util.Map;

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
                    String generi = getGeneri(movie.getString("genre_ids"));
                    int annoProd = movie.has("release_date") ? Integer.parseInt(movie.getString("release_date").substring(0, 4)) : 0;
                    String trama = movie.has("overview") ? movie.getString("overview") : "";

                    Film film = new Film(pathCopertina, pathBG, titolo, generi, annoProd, trama);
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

    String getGeneri(String s){
        s = s.substring(1,s.length()-1);
        String[] stringSplit = s.split(",");
        String res = "";

        for(int i=0; i<stringSplit.length; i++){
            res += getGenById(stringSplit[i]) + ", ";
        }
        return res.substring(0,res.length()-2);
    }

    String getGenById(String id){
        switch(id){
            case "28":
                return "Action";
            case "12":
                return "Adventure";
            case "16":
                return "Animation";
            case "35":
                return "Comedy";
            case "80":
                return "Crime";
            case "99":
                return "Documentary";
            case "18":
                return "Drama";
            case "10751":
                return "Family";
            case "14":
                return "Fantasy";
            case "36":
                return "History";
            case "27":
                return "Horror";
            case "10402":
                return "Music";
            case "9648":
                return "Mystery";
            case "10749":
                return "Romance";
            case "878":
                return "Sci-fi";
            case "10770":
                return "TV Movie";
            case "53":
                return "Thriller";
            case "10752":
                return "War";
            case "37":
                return "Western";
            default:
                return id;
        }
    }

}