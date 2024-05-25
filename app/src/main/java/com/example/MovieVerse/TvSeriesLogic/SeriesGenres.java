package com.example.MovieVerse.TvSeriesLogic;

import android.util.Log;

import com.example.MovieVerse.WebServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SeriesGenres {
    private WebServiceCall wsc;
    private HashMap<Integer, String> seriesGenresMap;
    SeriesGenres() {
        wsc = new WebServiceCall();
        wsc.sedRequest("https://api.themoviedb.org/3/genre/tv/list?language=en");

        try {
            wsc.join();
        }catch (Exception e){
            e.printStackTrace();
        }

        this.initMap(wsc.getRispostaJson());

        Log.d("FilmGenres", seriesGenresMap.toString());
    }
    private void initMap(JSONObject rispostaJson) {
        seriesGenresMap = new HashMap<>();
        try {
            JSONArray genresArray = rispostaJson.getJSONArray("genres");

            for (int i = 0; i < genresArray.length(); i++) {
                JSONObject genre = genresArray.getJSONObject(i);
                int id = genre.getInt("id");
                String name = genre.getString("name");
                seriesGenresMap.put(id, name);
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
        return seriesGenresMap.get(id);
    }
}
