package com.nedernasser.popularmovies.api;

import android.os.AsyncTask;

import com.nedernasser.popularmovies.BuildConfig;
import com.nedernasser.popularmovies.data.Trailer;
import com.nedernasser.popularmovies.data.TrailerCollection;

import java.net.URL;
import java.util.List;

public abstract class FetchTrailersTask extends AsyncTask<String, Void, List<Trailer>> {

    private static final String LOG_TAG = FetchTrailersTask.class.getSimpleName();
    private String id;

    public FetchTrailersTask(String id) {
        this.id = id;
    }

    @Override
    protected List<Trailer> doInBackground(String... params) {
        URL trailersRequestUrl = NetworkUtils.buildTrailersOrReviewsUrl(BuildConfig.MOVIESDB_API_KEY, id, "videos");
        try {
            String jsonTrailersResponse = NetworkUtils
                    .getResponseFromHttpUrl(trailersRequestUrl);
            TrailerCollection trailerCollection = Utils.parseJsonTrailer(jsonTrailersResponse);
            return trailerCollection.getTrailers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
