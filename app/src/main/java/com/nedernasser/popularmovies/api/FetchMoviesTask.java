package com.nedernasser.popularmovies.api;

import android.os.AsyncTask;

import com.nedernasser.popularmovies.BuildConfig;
import com.nedernasser.popularmovies.R;
import com.nedernasser.popularmovies.data.Movie;
import com.nedernasser.popularmovies.data.MovieCollection;
import com.nedernasser.popularmovies.MoviesAdapter;

import java.net.URL;
import java.util.List;

public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

    private MoviesAdapter adapter;


    public FetchMoviesTask(MoviesAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        URL moviesRequestUrl = NetworkUtils.buildUrl(BuildConfig.MOVIESDB_API_KEY, params[0]);
        try {
            String jsonMoviesResponse = NetworkUtils
                    .getResponseFromHttpUrl(moviesRequestUrl);
            MovieCollection movieCollection = Utils.parseJsonMovie(jsonMoviesResponse);
            return movieCollection.getMovies();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            adapter.setMoviesData(movies);
        }
    }
}
