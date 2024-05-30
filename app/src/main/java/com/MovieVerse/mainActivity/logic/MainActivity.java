package com.MovieVerse.mainActivity.logic;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MovieVerse.mainActivity.graphic.MainActivityAdapter;
import com.MovieVerse.globalClasses.film.FilmList;
import com.MovieVerse.globalClasses.webService.WebServiceCall;
import com.MovieVerse.detailsActivity.logic.DetailsFilmActivity;
import com.MovieVerse.filtersActivity.logic.FiltersActivity;
import com.MovieVerse.globalClasses.series.SeriesList;
import com.MovieVerse.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewFilm;
    private RecyclerView recyclerViewSeries;
    private DrawerLayout drawerLayout;

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

        // Initialize the DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void openFilters(View view){
        Intent intent = new Intent(this, FiltersActivity.class);
        startActivity(intent);
    }

    public void openMovie(View view){
        Intent intent = new Intent(this, DetailsFilmActivity.class);
        startActivity(intent);
    }

    public void openSideBarMenu(View view){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void aboutUsClick (View view){
        Intent intent = new Intent(this, AboutUsClick.class);
        startActivity(intent);
    }

    public void onInfoClick (View view){
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }
}
