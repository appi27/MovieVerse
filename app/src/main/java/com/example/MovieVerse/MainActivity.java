package com.example.MovieVerse;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MovieVerse.FilmLogic.FilmList;
import com.example.MovieVerse.Graphic.MainActivityAdapter;
import com.example.MovieVerse.TvSeriesLogic.SeriesList;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewFilm;
    private RecyclerView recyclerViewSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the footer text gradient
        TextView footerTextView = findViewById(R.id.footer);
        LinearGradient linearGradient = new LinearGradient(
                0, 0, 0, footerTextView.getTextSize(),
                new int[]{getColor(R.color.startColor), getColor(R.color.endColor)},
                null,
                Shader.TileMode.CLAMP);
        footerTextView.getPaint().setShader(linearGradient);

        // Initialize the WebServiceCall to get the movie data
        WebServiceCall movieCall = new WebServiceCall();
        movieCall.sedRequest("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc");

        try {
            movieCall.join(); // Wait for the WebService response
        } catch (Exception e) {
            Log.e("Error", Objects.requireNonNull(e.getMessage()));
        }

        // Get the movie list from the response JSON
        FilmList movieList = new FilmList(movieCall.getRispostaJson());

        // Initialize the WebServiceCall to get the TV series data
        WebServiceCall seriesCall = new WebServiceCall();
        seriesCall.sedRequest("https://api.themoviedb.org/3/discover/tv?sort_by=popularity.desc");

        try {
            seriesCall.join(); // Wait for the WebService response
        } catch (Exception e) {
            Log.e("Error", Objects.requireNonNull(e.getMessage()));
        }

        // Get the TV series list from the response JSON
        SeriesList seriesList = new SeriesList(seriesCall.getRispostaJson());

        // Initialize the RecyclerView for movies
        recyclerViewFilm = findViewById(R.id.recyclerViewMovie);
        recyclerViewFilm.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Initialize the RecyclerView for TV series
        recyclerViewSeries = findViewById(R.id.recyclerViewSerieTV);
        recyclerViewSeries.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Configure the adapter for the RecyclerView
        MainActivityAdapter movieAdapter = new MainActivityAdapter(movieList.getFilms(), null, this);
        MainActivityAdapter seriesAdapter = new MainActivityAdapter(null, seriesList.getSeriesList(), this);

        recyclerViewFilm.setAdapter(movieAdapter);
        recyclerViewSeries.setAdapter(seriesAdapter);
    }

    public void openFilters(View view){
        Intent intent = new Intent(this, FiltersActivity.class);
        startActivity(intent);
    }

    public void openMovie(View view){
        Intent intent = new Intent(this, DetailsFilmActivity.class);
        startActivity(intent);
    }
}
