package com.example.MovieVerse.FilmLogic;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MovieVerse.R;

import java.util.ArrayList;
import java.util.Collections;

public class DropDownGenres extends AppCompatActivity {
    // initialize variables
    TextView textView;
    boolean[] selectedGenres;
    ArrayList<Integer> list = new ArrayList<>();
    String[] array = (new FilmGenres()).getGenreArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters);

        textView = findViewById(R.id.txvGenres);
        selectedGenres = new boolean[array.length];

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(DropDownGenres.this);

                // set title
                builder.setTitle("Select Genres");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(array, selectedGenres, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            list.add(i);
                            // Sort array list
                            Collections.sort(list);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            list.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < list.size(); j++) {
                            // concat array value
                            stringBuilder.append(array[list.get(j)]);
                            // check condition
                            if (j != list.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textView.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedGenres.length; j++) {
                            // remove all selection
                            selectedGenres[j] = false;
                            // clear language list
                            list.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
    }
}
