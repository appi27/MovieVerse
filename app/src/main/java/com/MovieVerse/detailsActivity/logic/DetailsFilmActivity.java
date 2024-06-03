package com.MovieVerse.detailsActivity.logic;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MovieVerse.R;
import com.bumptech.glide.Glide;

public class DetailsFilmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opened_film);

        // Recupera i dati dal Intent
        String pathCopertina = getIntent().getStringExtra("pathCopertina");
        String pathBG = getIntent().getStringExtra("pathBG");
        String titolo = getIntent().getStringExtra("titolo");
        String genere = getIntent().getStringExtra("genere");
        int annoProd = getIntent().getIntExtra("annoProd", 0);
        String trama = getIntent().getStringExtra("trama");
        float voto = getIntent().getFloatExtra("voto", 0.0f);

        //Popola i campi nell'activity
        ImageView imageViewBanner = findViewById(R.id.dBanner);
        Glide.with(this).load(pathBG).into(imageViewBanner);

        TextView textViewTitle = findViewById(R.id.dTitle);
        textViewTitle.setText(titolo);

        TextView textViewYear = findViewById(R.id.dYear);
        textViewYear.setText(String.valueOf(annoProd));

        TextView textViewVote = findViewById(R.id.dVote);
        textViewVote.setText(Float.toString(voto));

        TextView textViewGenres = findViewById(R.id.dGenres);
        textViewGenres.setText(genere);

        TextView textViewPlot = findViewById(R.id.dPlot);
        textViewPlot.setText(trama);
    }
}
