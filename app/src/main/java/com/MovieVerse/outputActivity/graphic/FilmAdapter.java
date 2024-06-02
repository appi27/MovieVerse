package com.MovieVerse.outputActivity.graphic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.MovieVerse.globalClasses.film.Film;
import com.MovieVerse.R;
import com.MovieVerse.detailsActivity.logic.DetailsFilmActivity;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private List<Film> films;
    private Context context;

    public FilmAdapter(Context context, List<Film> films) {
        this.context = context;
        this.films = films;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Film currentFilm = films.get(position);
        holder.textViewTitolo.setText(currentFilm.getTitolo());
        holder.textViewGenereAnno.setText(currentFilm.getGenere() + " | " + currentFilm.getAnnoProd());
        holder.textViewTrama.setText(currentFilm.getTrama());
        Glide.with(context).load(currentFilm.getPathCopertina()).into(holder.imageViewCopertina);

        // Imposta il click listener per l'elemento della RecyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsFilmActivity.class);
                intent.putExtra("pathCopertina", currentFilm.getPathCopertina());
                intent.putExtra("pathBG", currentFilm.getPathBG());
                intent.putExtra("titolo", currentFilm.getTitolo());
                intent.putExtra("genere", currentFilm.getGenere());
                intent.putExtra("annoProd", currentFilm.getAnnoProd());
                intent.putExtra("trama", currentFilm.getTrama());
                intent.putExtra("voto", currentFilm.getVoto());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitolo, textViewGenereAnno, textViewTrama;
        ImageView imageViewCopertina;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitolo = itemView.findViewById(R.id.textViewTitolo);
            textViewGenereAnno = itemView.findViewById(R.id.textViewGenereAnno);
            textViewTrama = itemView.findViewById(R.id.textViewTrama);
            imageViewCopertina = itemView.findViewById(R.id.imageViewCopertina);
        }
    }
}
