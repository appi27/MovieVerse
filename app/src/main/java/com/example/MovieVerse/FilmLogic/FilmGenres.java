package com.example.MovieVerse.FilmLogic;

import android.util.Log;

import com.example.MovieVerse.WebServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class FilmGenres {
    private WebServiceCall wsc;
    private HashMap<Integer, String> filmGenresMap;
    FilmGenres() {
        wsc = new WebServiceCall();
        wsc.sedRequest("https://api.themoviedb.org/3/genre/movie/list?language=en");

        try {
            wsc.join();
        }catch (Exception e){
            e.printStackTrace();
        }

        this.initMap(wsc.getRispostaJson());

        Log.d("FilmGenres", filmGenresMap.toString());
    }
    private void initMap(JSONObject rispostaJson) {
        filmGenresMap = new HashMap<>();
        try {
            JSONArray genresArray = rispostaJson.getJSONArray("genres");

            for (int i = 0; i < genresArray.length(); i++) {
                JSONObject genre = genresArray.getJSONObject(i);
                int id = genre.getInt("id");
                String name = genre.getString("name");
                filmGenresMap.put(id, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getGeneri(JSONArray genreIdsArray) throws JSONException {
        StringBuilder generi = new StringBuilder();
        for (int j = 0; j < genreIdsArray.length(); j++) {
            int genreId = genreIdsArray.getInt(j);
            String genreName = getGenreById(genreId);
            generi.append(genreName);
            if (j < genreIdsArray.length() - 1) {
                generi.append(", ");
            }
        }
        return generi.toString();
    }
    public String getGenreById(int id) {
        return filmGenresMap.get(id);
    }
}
