package com.MovieVerse.outputActivity.graphic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MovieVerse.R;
import com.MovieVerse.globalClasses.series.Series;
import com.bumptech.glide.Glide;

import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder>{

    private List<Series> series;
    private Context context;

    public SeriesAdapter(Context context, List<Series> series) {
        this.context = context;
        this.series = series;
    }

    @NonNull
    @Override
    public FilmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film, parent, false);
        return new FilmAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapter.ViewHolder holder, int position) {
        Series currentFilm = series.get(position);
        holder.textViewTitolo.setText(currentFilm.getTitolo());
        holder.textViewGenereAnno.setText(currentFilm.getGenere() + " | " + currentFilm.getAnnoProd());
        holder.textViewTrama.setText(currentFilm.getTrama());
        Glide.with(context).load(currentFilm.getPathCopertina()).into(holder.imageViewCopertina);
    }

    @Override
    public int getItemCount() {
        return series.size();
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
