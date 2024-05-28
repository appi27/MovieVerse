package com.example.MovieVerse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;

import com.example.MovieVerse.FilmLogic.Film;
import com.example.MovieVerse.FilmLogic.FilmList;
import com.example.MovieVerse.Graphic.FilmAdapter;

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
}
