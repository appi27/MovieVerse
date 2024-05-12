package com.example.MovieVerse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ListView lw;
    private ArrayAdapter<Film> adapt;
    private ArrayList<Film> provaFilm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
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
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        ArrayList<Film> provaFilm = wsc.getFilmsFromResponse();

        for (Film film : provaFilm) {
            Log.d("Film", film.toString());
        }


        lw = findViewById(R.id.lista);

        adapt = new FilmAdapter(
                this,
                   provaFilm );

        lw.setAdapter(adapt);
    }

}