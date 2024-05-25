package FilmLogic;

import android.util.Log;

public class FilmFilter {
    private boolean includeAdult;
    private String language;
    private int primaryReleaseYear;
    private String sort_by;
    private float voteAverage;
    private String withGenres;
    private int withRuntime;

    public boolean isIncludeAdult() {
        return includeAdult;
    }

    public void setIncludeAdult(boolean includeAdult) {
        this.includeAdult = includeAdult;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPrimaryReleaseYear() {
        return primaryReleaseYear;
    }

    public void setPrimaryReleaseYear(int primaryReleaseYear) {
        this.primaryReleaseYear = primaryReleaseYear;
    }

    public String getSort_by() {
        return sort_by;
    }

    public void setSort_by(String sort_by) {
        this.sort_by = sort_by;
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

        if (language != null && !language.isEmpty()) {
            requestBuilder.append("&language=").append(language);
        }

        if (primaryReleaseYear > 0) {
            requestBuilder.append("&primary_release_year=").append(primaryReleaseYear);
        }

        if (sort_by != null && !sort_by.isEmpty()) {
            requestBuilder.append("&sort_by=").append(sort_by);
        }

        if (voteAverage > 0) {
            requestBuilder.append("&vote_average.gte=").append(voteAverage);
        }

        if (withGenres != null && !withGenres.isEmpty()) {
            requestBuilder.append("&with_genres=").append(withGenres);
        }

        if (withRuntime > 0) {
            requestBuilder.append("&with_runtime.gte=").append(withRuntime);
        }

        Log.d("filmFilter",requestBuilder.toString());

        return requestBuilder.toString();
    }
}


