package com.MovieVerse.mainActivity.logic;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MovieVerse.R;
import com.MovieVerse.detailsActivity.logic.DetailsFilmActivity;
import com.MovieVerse.filtersActivity.logic.FiltersActivity;
import com.MovieVerse.globalClasses.film.FilmList;
import com.MovieVerse.globalClasses.series.SeriesList;
import com.MovieVerse.globalClasses.webService.WebServiceCall;
import com.MovieVerse.mainActivity.graphic.MainActivityAdapter;
import com.MovieVerse.outputActivity.logic.ActivityOutput;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
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
        RecyclerView recyclerViewFilm = findViewById(R.id.recyclerViewMovie);
        recyclerViewFilm.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Initialize the RecyclerView for TV series
        RecyclerView recyclerViewSeries = findViewById(R.id.recyclerViewSerieTV);
        recyclerViewSeries.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Configure the adapter for the RecyclerView for movies
        MainActivityAdapter movieAdapter = new MainActivityAdapter(movieList.getFilms(), null, this,
                film -> {
                    Intent intent = new Intent(MainActivity.this, DetailsFilmActivity.class);
                    intent.putExtra("pathCopertina", film.getPathCopertina());
                    intent.putExtra("pathBG", film.getPathBG());
                    intent.putExtra("titolo", film.getTitolo());
                    intent.putExtra("genere", film.getGenere());
                    intent.putExtra("annoProd", film.getAnnoProd());
                    intent.putExtra("trama", film.getTrama());
                    intent.putExtra("voto", film.getVoto());
                    startActivity(intent);
                }, null
        );
        recyclerViewFilm.setAdapter(movieAdapter);

        // Configure the adapter for the RecyclerView for TV series
        MainActivityAdapter seriesAdapter = new MainActivityAdapter(null, seriesList.getSeriesList(), this,
                null, series -> {
            Intent intent = new Intent(MainActivity.this, DetailsFilmActivity.class);
            intent.putExtra("pathCopertina", series.getPathCopertina());
            intent.putExtra("pathBG", series.getPathBG());
            intent.putExtra("titolo", series.getTitolo());
            intent.putExtra("genere", series.getGenere());
            intent.putExtra("annoProd", series.getAnnoProd());
            intent.putExtra("trama", series.getTrama());
            intent.putExtra("voto", series.getVote());
            startActivity(intent);
        }
        );
        recyclerViewSeries.setAdapter(seriesAdapter);

        // Initialize the DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        // Initialize the SearchView and set the query listener
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, ActivityOutput.class);
                intent.putExtra("query", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Optional: Handle text change events if needed
                return false;
            }
        });

        setStatusBarColor(getResources().getColor(R.color.black));
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

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }
}
