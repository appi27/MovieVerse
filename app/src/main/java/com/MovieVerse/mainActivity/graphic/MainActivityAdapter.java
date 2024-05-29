package com.MovieVerse.mainActivity.graphic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.MovieVerse.globalClasses.film.Film;
import com.MovieVerse.R;
import com.MovieVerse.globalClasses.series.Series;

import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    private List<Film> films;
    private List<Series> series;
    private Context context;

    // Constructor
    public MainActivityAdapter(List<Film> films, List<Series> series, Context context) {
        this.context = context;
        this.films = films;
        this.series = series;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_and_tv_poster, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (films != null) {
            Film film = films.get(position);
            Glide.with(context)
                    .load(film.getPathCopertina())
                    .into(holder.imageViewCopertina);
        } else if (series != null) {
            Series serie = series.get(position);
            Glide.with(context)
                    .load(serie.getPathCopertina())
                    .into(holder.imageViewCopertina);
        }
    }

    @Override
    public int getItemCount() {
        return films != null ? films.size() : (series != null ? series.size() : 0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCopertina;

        ViewHolder(View itemView) {
            super(itemView);
            imageViewCopertina = itemView.findViewById(R.id.imageViewCopertina);
        }
    }
}
