package com.MovieVerse.globalClasses.film;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FilmList{
    private final ArrayList<Film> films;

    public FilmList(JSONObject rispostaJson){
        films = new ArrayList<>();
        FilmGenres filmGenres = new FilmGenres();

        try {
            if (rispostaJson != null && rispostaJson.has("results")) {
                JSONArray results = rispostaJson.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject movie = results.getJSONObject(i);
                    String pathCopertina = "https://image.tmdb.org/t/p/original" + (movie.has("poster_path") ? movie.getString("poster_path") : "errore");
                    String pathBG = "https://image.tmdb.org/t/p/original" + (movie.has("backdrop_path") ? movie.getString("backdrop_path") : "errore");
                    String titolo = movie.has("title") ? movie.getString("title") : "";
                    String generi = movie.has("genre_ids") ? filmGenres.getGeneri(movie.getJSONArray("genre_ids")) : "errore";
                    int annoProd = 0;
                    if (movie.has("release_date")) {
                        String releaseDate = movie.getString("release_date");
                        if (!releaseDate.isEmpty()) {
                            annoProd = Integer.parseInt(releaseDate.substring(0, 4));
                        }
                    }
                    String trama = movie.has("overview") ? movie.getString("overview") : "";
                    float vote = movie.has("vote_average") ? (float) movie.getDouble("vote_average") : 0;

                    Film film = new Film(pathCopertina, pathBG, titolo, generi, annoProd, trama,vote);
                    films.add(film);
                }
            } else {
                Log.w("WebServiceCall", "Il JSON non contiene il campo 'results'");
            }
        } catch (JSONException e) {
            Log.e("WebServiceCall", "Error durante il parsing JSON", e);
        }
    }

    public ArrayList<Film> getFilms() {
        Log.d("FilmList_valoreRitorno", films.toString());
        return films;
    }
}