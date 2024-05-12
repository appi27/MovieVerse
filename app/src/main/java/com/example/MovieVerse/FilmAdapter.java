package com.example.MovieVerse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

public class FilmAdapter extends ArrayAdapter<Film> {

    public FilmAdapter(Context context, List<Film> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.film, parent, false);
        }

        Film currentFilm = getItem(position);

        ImageView imageViewCopertina = listItemView.findViewById(R.id.imageViewCopertina);
        // Carica l'immagine di copertina utilizzando Glide
        Glide.with(getContext()).load(currentFilm.getPathCopertina()).into(imageViewCopertina);

        TextView textViewTitolo = listItemView.findViewById(R.id.textViewTitolo);
        textViewTitolo.setText(currentFilm.getTitolo());

        TextView textViewGenereAnno = listItemView.findViewById(R.id.textViewGenereAnno);
        String genereAnno = currentFilm.getGenere() + " | " + currentFilm.getAnnoProd();
        textViewGenereAnno.setText(genereAnno);

        TextView textViewTrama = listItemView.findViewById(R.id.textViewTrama);
        textViewTrama.setText(currentFilm.getTrama());
        return listItemView;
    }
}
