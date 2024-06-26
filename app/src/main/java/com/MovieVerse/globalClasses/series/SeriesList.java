package com.MovieVerse.globalClasses.series;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeriesList {
    private final ArrayList<Series> seriesList;

    public SeriesList(JSONObject rispostaJson){
        seriesList = new ArrayList<>();
        SeriesGenres seriesGenres = new SeriesGenres();

        try {
            if (rispostaJson != null && rispostaJson.has("results")) {
                JSONArray results = rispostaJson.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject movie = results.getJSONObject(i);
                    String pathCopertina = "https://image.tmdb.org/t/p/original" + (movie.has("poster_path") ? movie.getString("poster_path") : "errore");
                    String pathBG = "https://image.tmdb.org/t/p/original" + (movie.has("backdrop_path") ? movie.getString("backdrop_path") : "errore");
                    String titolo = movie.has("name") ? movie.getString("name") : "";
                    String generi = movie.has("genre_ids") ? seriesGenres.getGeneri(movie.getJSONArray("genre_ids")) : "errore";
                    int annoProd = movie.has("first_air_date") ? Integer.parseInt(movie.getString("first_air_date").substring(0, 4)) : 0;
                    String trama = movie.has("overview") ? movie.getString("overview") : "";
                    float vote = movie.has("vote_average") ? (float) movie.getDouble("vote_average") : 0;

                    Series serie = new Series(pathCopertina, pathBG, titolo, generi, annoProd, trama,vote);
                    seriesList.add(serie);
                }
            } else {
                Log.w("WebServiceCall", "Il JSON non contiene il campo 'results'");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Series> getSeriesList() {
        Log.d("FilmList_valoreRitorno", seriesList.toString());
        return seriesList;
    }
}
