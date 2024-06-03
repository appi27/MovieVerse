package com.MovieVerse.outputActivity.logic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.MovieVerse.filtersActivity.logic.Filters;
import com.MovieVerse.globalClasses.film.Film;
import com.MovieVerse.globalClasses.film.FilmList;
import com.MovieVerse.globalClasses.series.Series;
import com.MovieVerse.globalClasses.series.SeriesList;
import com.MovieVerse.globalClasses.webService.WebServiceCall;
import com.MovieVerse.detailsActivity.logic.DetailsFilmActivity;
import com.MovieVerse.outputActivity.graphic.FilmAdapter;
import com.MovieVerse.R;
import com.MovieVerse.outputActivity.graphic.SeriesAdapter;

import org.json.JSONObject;
import java.util.ArrayList;

public class ActivityOutput extends AppCompatActivity {
    private JSONObject rispostaJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        Intent intent = getIntent();

        if (intent.hasExtra("query")) {
            // Gestione della ricerca
            String query = intent.getStringExtra("query");
            handleSearchQuery(query);
        } else {
            //gestione filtri
            Filters ff = (Filters) intent.getSerializableExtra("filmFilter");
            assert ff != null;
            handleFilter(ff);
        }
    }

    private void handleSearchQuery(String query) {
        String url = "https://api.themoviedb.org/3/search/movie?query=" + query;
        WebServiceCall wsc = new WebServiceCall();
        wsc.sedRequest(url);
        Log.d("ActivityOutput_sedRequest", "Request: " + url);

        try {
            wsc.join();
        } catch (InterruptedException e) {
            Log.e("ActivityOutput", "Errore nel join");
        }

        rispostaJson = wsc.getRispostaJson();
        Log.d("ActivityOutput_rispostaJson", "Risposta JSON: " + rispostaJson.toString());

        if (rispostaJson != null) {
            handleResponseJson(rispostaJson);
        } else {
            Log.e("ActivityOutput", "La risposta JSON è null");
        }
    }

    private void handleFilter(Filters ff) {
        WebServiceCall wsc = new WebServiceCall();
        wsc.sedRequest(ff.makeRequest());
        Log.d("ActivityOutput_sedRequest", "Request: " + ff.makeRequest());

        try {
            wsc.join();
        } catch (InterruptedException e) {
            Log.e("ActivityOutput", "Errore nel join");
        }

        rispostaJson = wsc.getRispostaJson();
        Log.d("ActivityOutput_rispostaJson", "Risposta JSON: " + rispostaJson.toString());

        if (rispostaJson != null) {
            handleResponseJson(rispostaJson);
        } else {
            Log.e("ActivityOutput", "La risposta JSON è null");
        }
    }

    private void handleResponseJson(JSONObject rispostaJson) {
        RecyclerView recyclerView;
        if (rispostaJson.toString().contains("first_air_date")) {
            SeriesList seriesList = new SeriesList(rispostaJson);
            ArrayList<Series> listaSeries = new ArrayList<>(seriesList.getSeriesList());

            for (Series s : listaSeries) {
                Log.d("Serie", s.toString());
            }

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            SeriesAdapter adaptS = new SeriesAdapter(this, listaSeries);
            recyclerView.setAdapter(adaptS);

        } else {
            FilmList filmList = new FilmList(rispostaJson);
            ArrayList<Film> listaFilm = new ArrayList<>(filmList.getFilms());

            for (Film film : listaFilm) {
                Log.d("Film", film.toString());
            }

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            FilmAdapter adaptF = new FilmAdapter(this, listaFilm);
            recyclerView.setAdapter(adaptF);
        }
    }
}
