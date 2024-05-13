// MainActivity.java
package com.example.MovieVerse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FilmAdapter adapt;
    private ArrayList<Film> provaFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_films);

        WebServiceCall wsc = new WebServiceCall();
        FilmFilter test = new FilmFilter();
        test.setIncludeAdult(true);
        test.setLanguage("en-US");
        test.setPrimaryReleaseYear(2022);
        test.setVoteAverage(4);
        wsc.sedRequest(test.makeRequest());

        try {
            wsc.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        provaFilm = wsc.getFilmsFromResponse();

        for (Film film : provaFilm) {
            Log.d("Film", film.toString());
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapt = new FilmAdapter(this, provaFilm);
        recyclerView.setAdapter(adapt);
    }
}
