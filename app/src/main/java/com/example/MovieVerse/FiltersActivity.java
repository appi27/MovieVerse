package com.example.MovieVerse;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MovieVerse.FilmLogic.FilmFilter;

public class FiltersActivity extends AppCompatActivity {

    TextView txvGenres = findViewById(R.id.txvGenres);
    EditText edtRuntime = findViewById(R.id.edtRuntime);
    EditText edtYear = findViewById(R.id.edtYear);
    EditText edtVote = findViewById(R.id.edtVote);
    Spinner spAdult = findViewById(R.id.spAdult);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters);
    }

    public void addFilters(View view){
        Intent intent = new Intent(this, ActivityOutput.class);
        startActivity(intent);

        FilmFilter ff = new FilmFilter();

        ff.setWithGenres(txvGenres.getText().toString());
        ff.setWithRuntime(Integer.parseInt(edtRuntime.getText().toString()));
        ff.setPrimaryReleaseYear(Integer.parseInt(edtYear.getText().toString()));
        ff.setVoteAverage(Integer.parseInt(edtVote.getText().toString()));
        if(spAdult.getSelectedItem().toString().equals(" Yes, do include ")){
            ff.setIncludeAdult(true);
        }else{
            ff.setIncludeAdult(false);
        }

    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt("CONTATORE", conta);
    }
}
