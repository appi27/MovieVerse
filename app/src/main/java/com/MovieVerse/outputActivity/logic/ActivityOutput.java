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
import com.MovieVerse.globalClasses.webService.WebServiceCall;
import com.MovieVerse.detailsActivity.logic.DetailsFilmActivity;
import com.MovieVerse.outputActivity.graphic.FilmAdapter;
import com.MovieVerse.R;

import org.json.JSONObject;
import java.util.ArrayList;

public class ActivityOutput extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FilmAdapter adapt;
    private ArrayList<Film> provaFilm;
    private JSONObject rispostaJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_films);

        Filters ff = (Filters) getIntent().getSerializableExtra("filmFilter");

        WebServiceCall wsc = new WebServiceCall();
        wsc.sedRequest(ff.makeRequest());
        Log.d("ActivityOutput_sedRequest", "Request: " + ff.makeRequest());

        try {
            wsc.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rispostaJson = wsc.getRispostaJson();
        Log.d("ActivityOutput_rispostaJson", "Risposta JSON: " + rispostaJson.toString());

        if (rispostaJson != null) {
            FilmList filmList = new FilmList(rispostaJson);
            provaFilm = new ArrayList<>();
            provaFilm.addAll(filmList.getFilms());
        } else {
            Log.e("ActivityOutput", "La risposta JSON è null");
        }

        for (Film film : provaFilm) {
            Log.d("Film", film.toString());
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapt = new FilmAdapter(this, provaFilm);
        recyclerView.setAdapter(adapt);
    }

    public void openMovie(View view){
        Intent intent = new Intent(this, DetailsFilmActivity.class);
        startActivity(intent);
    }
}
