package com.MovieVerse.filtersActivity.logic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.MovieVerse.globalClasses.film.FilmGenres;
import com.MovieVerse.globalClasses.series.SeriesGenres;
import com.MovieVerse.outputActivity.logic.ActivityOutput;
import com.MovieVerse.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FiltersActivity extends AppCompatActivity {
    private Filters ff;
    private boolean[] selectedGenres;
    private ArrayList<Integer> genreList = new ArrayList<>();
    private String[] mergedGenres;
    private FilmGenres fg;
    private SeriesGenres sg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters);
        TextView txvGenres = findViewById(R.id.txvGenres);

        fg = new FilmGenres();
        sg = new SeriesGenres();

        String[] genres = fg.getGenreArray();
        String[] seriesGenres = sg.getGenreArray();
        mergedGenres = mergeArraysWithoutDuplicates(genres, seriesGenres);

        selectedGenres = new boolean[mergedGenres.length];

        txvGenres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGenreSelectionDialog(txvGenres);
            }
        });
    }

    public void addFilters(View view) {
        TextView txvGenres = findViewById(R.id.txvGenres);
        EditText edtRuntime = findViewById(R.id.edtRuntime);
        EditText edtYear = findViewById(R.id.edtYear);
        EditText edtVote = findViewById(R.id.edtVote);
        Spinner spAdult = findViewById(R.id.spAdult);

        ff = new Filters();

        StringBuilder genresIds = new StringBuilder();
        String[] selectedGenreNames = txvGenres.getText().toString().split(", ");
        for (String genreName : selectedGenreNames) {
            String genreId = fg.getIdByGenre(genreName);
            if (genreId == null) {
                genreId = sg.getIdByGenre(genreName);
            }
            if (genreId != null) {
                if (genresIds.length() > 0) {
                    genresIds.append(",");
                }
                genresIds.append(genreId);
            }
        }
        ff.setWithGenres(genresIds.toString());

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

    private void showGenreSelectionDialog(TextView txvGenres) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FiltersActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_genres, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();  // Declare dialog here

        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        ListView genreListView = dialogView.findViewById(R.id.genreListView);
        Button btnClearAll = dialogView.findViewById(R.id.btnClearAll);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnOK = dialogView.findViewById(R.id.btnOK);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, mergedGenres);
        genreListView.setAdapter(adapter);

        // Set initial selections
        for (int i = 0; i < selectedGenres.length; i++) {
            genreListView.setItemChecked(i, selectedGenres[i]);
        }

        // Clear all selections
        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < selectedGenres.length; i++) {
                    selectedGenres[i] = false;
                    genreListView.setItemChecked(i, false);
                }
                genreList.clear();
                txvGenres.setText("");
            }
        });

        // Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // OK button
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genreList.clear();
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < genreListView.getCount(); i++) {
                    if (genreListView.isItemChecked(i)) {
                        selectedGenres[i] = true;
                        genreList.add(i);
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append(", ");
                        }
                        stringBuilder.append(mergedGenres[i]);
                    } else {
                        selectedGenres[i] = false;
                    }
                }
                txvGenres.setText(stringBuilder.toString());
                dialog.dismiss();
            }
        });

        dialog.show();  // Show dialog here
    }

    public static String[] mergeArraysWithoutDuplicates(String[] array1, String[] array2) {
        Set<String> set = new HashSet<>();

        for (String element : array1) {
            set.add(element);
        }

        for (String element : array2) {
            set.add(element);
        }

        return set.toArray(new String[0]);
    }
}
