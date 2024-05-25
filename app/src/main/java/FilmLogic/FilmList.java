package FilmLogic;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FilmList{
    private ArrayList<Film> films;
    private FilmGenres filmGenres;

    public FilmList(JSONObject rispostaJson){
        films = new ArrayList<>();
        filmGenres = new FilmGenres();

        try {
            if (rispostaJson != null && rispostaJson.has("results")) {
                JSONArray results = rispostaJson.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject movie = results.getJSONObject(i);
                    String pathCopertina = "https://image.tmdb.org/t/p/original" + (movie.has("poster_path") ? movie.getString("poster_path") : "errore");
                    String pathBG = "https://image.tmdb.org/t/p/original" + (movie.has("backdrop_path") ? movie.getString("backdrop_path") : "errore");
                    String titolo = movie.has("title") ? movie.getString("title") : "";
                    String generi = movie.has("genre_ids") ? filmGenres.getGeneri(movie.getJSONArray("genre_ids")) : "errore";
                    int annoProd = movie.has("release_date") ? Integer.parseInt(movie.getString("release_date").substring(0, 4)) : 0;
                    String trama = movie.has("overview") ? movie.getString("overview") : "";

                    Film film = new Film(pathCopertina, pathBG, titolo, generi, annoProd, trama);
                    films.add(film);
                }
            } else {
                Log.w("WebServiceCall", "Il JSON non contiene il campo 'results'");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Film> getFilms() {
        Log.d("FilmList_valoreRitorno", films.toString());
        return films;
    }
}