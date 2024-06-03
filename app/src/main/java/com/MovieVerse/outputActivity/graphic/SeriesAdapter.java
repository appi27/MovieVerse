package com.MovieVerse.outputActivity.graphic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MovieVerse.detailsActivity.logic.DetailsFilmActivity;
import com.bumptech.glide.Glide;
import com.MovieVerse.R;
import com.MovieVerse.globalClasses.series.Series;
import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {
    private final List<Series> series;
    private final Context context;

    public SeriesAdapter(Context context, List<Series> series) {
        this.context = context;
        this.series = series;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.infomation, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Series currentSeries = series.get(position);
        holder.textViewTitolo.setText(currentSeries.getTitolo());
        holder.textViewGenereAnno.setText(currentSeries.getGenere() + " | " + currentSeries.getAnnoProd());
        holder.textViewTrama.setText(currentSeries.getTrama());
        Glide.with(context).load(currentSeries.getPathCopertina()).into(holder.imageViewCopertina);

        // Imposta il click listener per l'elemento della RecyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsFilmActivity.class);
                intent.putExtra("pathCopertina", currentSeries.getPathCopertina());
                intent.putExtra("pathBG", currentSeries.getPathBG());
                intent.putExtra("titolo", currentSeries.getTitolo());
                intent.putExtra("genere", currentSeries.getGenere());
                intent.putExtra("annoProd", currentSeries.getAnnoProd());
                intent.putExtra("trama", currentSeries.getTrama());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return series.size();
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
