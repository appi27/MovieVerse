package com.MovieVerse.globalClasses.series;

import android.util.Log;

import com.MovieVerse.globalClasses.webService.WebServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class SeriesGenres {
    private HashMap<Integer, String> seriesGenresMap;
    public SeriesGenres() {
        WebServiceCall wsc = new WebServiceCall();
        wsc.sedRequest("https://api.themoviedb.org/3/genre/tv/list?language=en");

        try {
            wsc.join();
        }catch (Exception e){
            Log.e("FilmGenres", Objects.requireNonNull(e.getMessage()));
        }

        this.initMap(wsc.getRispostaJson());

        Log.d("FilmGenres", seriesGenresMap.toString());
    }
    private void initMap(JSONObject rispostaJson) {
        //init map
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
            Log.e("FilmGenres", Objects.requireNonNull(e.getMessage()));
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

    public String[] getGenreArray() {
        ArrayList<String> arrayGenres = new ArrayList<String>();
        for (Map.Entry<Integer, String> mapEntry : seriesGenresMap.entrySet()) {
            arrayGenres.add(mapEntry.getValue());
        }
        return arrayGenres.toArray(new String[0]);
    }

    public String getGenreById(int id) {
        return seriesGenresMap.get(id);
    }

    public String getIdByGenre(String gen) {
        for (Map.Entry<Integer, String> mapEntry : seriesGenresMap.entrySet()) {
            if (mapEntry.getValue().equals(gen)) {
                return mapEntry.getKey().toString();
            }
        }

        return null;
    }

}
