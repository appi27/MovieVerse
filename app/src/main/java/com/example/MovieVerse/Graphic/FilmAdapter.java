package com.example.MovieVerse.Graphic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.MovieVerse.FilmLogic.Film;
import com.example.MovieVerse.R;

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
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitolo, textViewGenereAnno, textViewTrama;
        ImageView imageViewCopertina;

        ImageView bGimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitolo = itemView.findViewById(R.id.textViewTitolo);
            textViewGenereAnno = itemView.findViewById(R.id.textViewGenereAnno);
            textViewTrama = itemView.findViewById(R.id.textViewTrama);
            imageViewCopertina = itemView.findViewById(R.id.imageViewCopertina);
        }
    }
}