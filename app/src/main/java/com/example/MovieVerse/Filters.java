package com.example.MovieVerse;

import android.util.Log;

import java.io.Serializable;

public class Filters implements Serializable {
    private boolean includeAdult;
    private int primaryReleaseYear;
    private float voteAverage;
    private String withGenres;  // Stringa di ID dei generi separati da virgole
    private int withRuntime;

    public boolean isIncludeAdult() {
        return includeAdult;
    }

    public void setIncludeAdult(boolean includeAdult) {
        this.includeAdult = includeAdult;
    }

    public int getPrimaryReleaseYear() {
        return primaryReleaseYear;
    }

    public void setPrimaryReleaseYear(int primaryReleaseYear) {
        this.primaryReleaseYear = primaryReleaseYear;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getWithGenres() {
        return withGenres;
    }

    public void setWithGenres(String withGenres) {
        this.withGenres = withGenres;
    }

    public int getWithRuntime() {
        return withRuntime;
    }

    public void setWithRuntime(int withRuntime) {
        this.withRuntime = withRuntime;
    }

    public String makeRequest() {
        StringBuilder requestBuilder = new StringBuilder("https://api.themoviedb.org/3/discover/movie?");
        requestBuilder.append("include_adult=").append(includeAdult);

        if (primaryReleaseYear > 0) {
            requestBuilder.append("&primary_release_year=").append(primaryReleaseYear);
        }

        if (voteAverage > 0) {
            requestBuilder.append("&vote_average.gte=").append(voteAverage);
        }

        if (withGenres != null && !withGenres.isEmpty()) {
            requestBuilder.append("&with_genres=").append(withGenres);
        }

        if (withRuntime > 0) {
            requestBuilder.append("&with_runtime.lte=").append(withRuntime);
        }

        Log.d("filmFilter", requestBuilder.toString());

        return requestBuilder.toString();
    }
}
