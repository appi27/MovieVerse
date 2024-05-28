package com.example.MovieVerse;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MovieVerse.FilmLogic.FilmFilter;

public class FiltersActivity extends AppCompatActivity {
    private FilmFilter ff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters);
    }

    public void addFilters(View view) {
        TextView txvGenres = findViewById(R.id.txvGenres);
        EditText edtRuntime = findViewById(R.id.edtRuntime);
        EditText edtYear = findViewById(R.id.edtYear);
        EditText edtVote = findViewById(R.id.edtVote);
        Spinner spAdult = findViewById(R.id.spAdult);

        ff = new FilmFilter();

        ff.setWithGenres(txvGenres.getText().toString());

        String runtimeText = edtRuntime.getText().toString();
        if (!runtimeText.isEmpty()) {
            ff.setWithRuntime(Integer.parseInt(runtimeText));
        }

        String yearText = edtYear.getText().toString();
        if (!yearText.isEmpty()) {
            ff.setPrimaryReleaseYear(Integer.parseInt(yearText));
        }

        String voteText = edtVote.getText().toString();
        if (!voteText.isEmpty()) {
            ff.setVoteAverage(Float.parseFloat(voteText));
        }

        String adultSelection = spAdult.getSelectedItem().toString();
        ff.setIncludeAdult(adultSelection.equalsIgnoreCase("Yes"));

        Intent intent = new Intent(this, ActivityOutput.class);
        intent.putExtra("filmFilter", ff);
        startActivity(intent);
    }



}
