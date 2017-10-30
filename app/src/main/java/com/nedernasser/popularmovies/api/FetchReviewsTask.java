package com.nedernasser.popularmovies.api;

import android.os.AsyncTask;

import com.nedernasser.popularmovies.BuildConfig;
import com.nedernasser.popularmovies.data.Review;
import com.nedernasser.popularmovies.data.ReviewCollection;

import java.net.URL;
import java.util.List;

public abstract class FetchReviewsTask extends AsyncTask<String, Void, List<Review>> {

    private static final String LOG_TAG = FetchReviewsTask.class.getSimpleName();
    private String id;

    public FetchReviewsTask(String id) {
        this.id = id;
    }

    @Override
    protected List<Review> doInBackground(String... params) {
        URL reviewsRequestUrl = NetworkUtils.buildTrailersOrReviewsUrl(BuildConfig.MOVIESDB_API_KEY, id, "reviews");
        try {
            String jsonReviewsResponse = NetworkUtils
                    .getResponseFromHttpUrl(reviewsRequestUrl);
            ReviewCollection reviewCollection = Utils.parseJsonReview(jsonReviewsResponse);
            return reviewCollection.getReviews();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
