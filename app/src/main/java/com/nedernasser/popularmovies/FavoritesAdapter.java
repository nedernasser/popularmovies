package com.nedernasser.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nedernasser.popularmovies.api.NetworkUtils;
import com.nedernasser.popularmovies.data.Movie;
import com.nedernasser.popularmovies.data.MoviesContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder> {

    private Cursor cursor;

    public FavoritesAdapter() {
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    public class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_movie)
        ImageView imagePoster;

        public FavoritesAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            cursor.moveToPosition(getAdapterPosition());
            Movie currentMovie = getMovieFromCursor();
            intent.putExtra("movieDetails", currentMovie);
            view.getContext().startActivity(intent);
        }

        @NonNull
        private Movie getMovieFromCursor() {
            Movie currentMovie = new Movie();
            currentMovie.setTitle(cursor.getString(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_TITLE)));
            currentMovie.setId(cursor.getInt(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID)));
            currentMovie.setOverview(cursor.getString(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_DESCRIPTION)));
            currentMovie.setPosterPath(cursor.getString(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER_PATH)));
            currentMovie.setReleaseDate(cursor.getString(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE)));
            currentMovie.setVoteAverage(cursor.getLong(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_VOTE_AVERAGE)));
            return currentMovie;
        }
    }

    @Override
    public FavoritesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_movies, parent, false);
        FavoritesAdapterViewHolder viewHolder = new FavoritesAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoritesAdapterViewHolder holder, int position) {
        cursor.moveToPosition(position);
        String posterPath = cursor.getString(
                cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER_PATH));
        Picasso.with(holder.imagePoster.getContext())
                .load(NetworkUtils.buildPosterUrl(posterPath))
                .placeholder(R.drawable.shape_movie_poster)
                .into(holder.imagePoster);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }
}
