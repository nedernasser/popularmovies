package com.nedernasser.popularmovies;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static final String LOG_TAG = Utils.class.getSimpleName();
    private static final String statusError = "status_code";
    private static final String reviews = "results";
    private static final String movies = "results";
    private static final String trailers = "results";
    private static final String posterKey = "poster_path";
    private static final String overviewKey = "overview";
    private static final String releaseDateKey = "release_date";
    private static final String titleKey = "title";
    private static final String idKey = "id";
    private static final String voteAverageKey = "vote_average";
    private static final String author = "author";
    private static final String content = "content";
    private static final String url = "url";
    private static final String key = "key";
    private static final String name = "name";
    private static final String site = "site";
    private static final String type = "type";

    public static MovieCollection parseJsonMovie(String json)
            throws JSONException {
        JSONObject responseJson = new JSONObject(json);
        if (responseJson.has(statusError)) {
            int errorCode = responseJson.getInt(statusError);
            Log.e(LOG_TAG, "parse json movies error code: " + String.valueOf(errorCode));
        }
        JSONArray moviesArray = responseJson.getJSONArray(movies);
        List<Movie> movieList = parseMovieList(moviesArray);
        MovieCollection movieCollection = new MovieCollection();
        movieCollection.setMovies(movieList);
        return movieCollection;
    }

    @NonNull
    private static List<Movie> parseMovieList(JSONArray moviesArray) throws JSONException {
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movie = moviesArray.getJSONObject(i);
            Movie currentMovie = parseMovie(movie);
            movieList.add(currentMovie);
        }
        return movieList;
    }

    @NonNull
    private static Movie parseMovie(JSONObject movie) throws JSONException {
        Movie currentMovie = new Movie();
        currentMovie.setPosterPath(movie.getString(posterKey));
        currentMovie.setOverview(movie.getString(overviewKey));
        currentMovie.setReleaseDate(movie.getString(releaseDateKey));
        currentMovie.setTitle(movie.getString(titleKey));
        currentMovie.setId(movie.getInt(idKey));
        currentMovie.setVoteAverage(movie.getLong(voteAverageKey));
        return currentMovie;
    }

    public static ReviewCollection parseJsonReview(String json)
            throws JSONException {
        JSONObject responseJson = new JSONObject(json);
        if (responseJson.has(statusError)) {
            int errorCode = responseJson.getInt(statusError);
            Log.e(LOG_TAG, "parse json reviews error code: " + String.valueOf(errorCode));
        }
        JSONArray reviewsArray = responseJson.getJSONArray(reviews);
        List<Review> reviewList = parseReviewList(reviewsArray);
        ReviewCollection reviewCollection = new ReviewCollection();
        reviewCollection.setReviews(reviewList);
        return reviewCollection;
    }

    @NonNull
    private static List<Review> parseReviewList(JSONArray reviewArray) throws JSONException {
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < reviewArray.length(); i++) {
            JSONObject review = reviewArray.getJSONObject(i);
            Review currentReview = parseReview(review);
            reviews.add(currentReview);
        }
        return reviews;
    }

    @NonNull
    private static Review parseReview(JSONObject review) throws JSONException {
        Review currentReview = new Review();
        currentReview.setAuthor(review.getString(author));
        currentReview.setContent(review.getString(content));
        currentReview.setUrl(review.getString(url));
        return currentReview;
    }

    public static TrailerCollection parseJsonTrailer(String json)
            throws JSONException {
        JSONObject responseJson = new JSONObject(json);
        if (responseJson.has(statusError)) {
            int errorCode = responseJson.getInt(statusError);
            Log.e(LOG_TAG, "parse json trailers error code: " + String.valueOf(errorCode));
        }
        JSONArray trailersArray = responseJson.getJSONArray(trailers);
        List<Trailer> trailerList = parseTrailerList(trailersArray);
        TrailerCollection trailerCollection = new TrailerCollection();
        trailerCollection.setTrailers(trailerList);
        return trailerCollection;
    }

    @NonNull
    private static List<Trailer> parseTrailerList(JSONArray trailerArray) throws JSONException {
        List<Trailer> trailers = new ArrayList<>();
        for (int i = 0; i < trailerArray.length(); i++) {
            JSONObject trailer = trailerArray.getJSONObject(i);
            Trailer currentTrailer = parseTrailer(trailer);
            trailers.add(currentTrailer);
        }
        return trailers;
    }

    @NonNull
    private static Trailer parseTrailer(JSONObject trailer) throws JSONException {
        Trailer currentTrailer = new Trailer();
        currentTrailer.setKey(trailer.getString(key));
        currentTrailer.setName(trailer.getString(name));
        currentTrailer.setSite(trailer.getString(site));
        currentTrailer.setType(trailer.getString(type));
        return currentTrailer;
    }
}
