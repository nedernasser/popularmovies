package com.nedernasser.popularmovies;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private int selectedOption = R.id.action_popular;
    private static final String FILTER1 = "popular";
    private static final String FILTER2 = "top_rated";
    private MoviesAdapter moviesAdapter;
    public static final int ID_FAVORITES_LOADER = 17;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, getSpan());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (savedInstanceState == null) {
            setMovieAdapterPopular();
        } else {
            loadAdapterPerOptionSelected(
                    savedInstanceState.getInt("optionSelected", R.id.action_popular));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("optionSelected", selectedOption);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        loadAdapterPerOptionSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void loadAdapterPerOptionSelected(int selectedOption) {
        this.selectedOption = selectedOption;
        setMoviesAdapter();
    }

    private void setMoviesAdapter() {
        if (selectedOption == R.id.action_popular) {
            setMovieAdapterPopular();
        }
        if (selectedOption == R.id.action_top_rated) {
            setMovieAdapterTopRated();
        }
        if (selectedOption == R.id.action_favorites) {
            setMovieAdapterFavorites();
        }
    }

    private void generateSnackBarMessage() {
        Snackbar snackbar = Snackbar.make(recyclerView, getString(R.string.texto_offline), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMoviesAdapter();
            }
        });
        snackbar.show();
    }

    private void setMovieAdapterFavorites() {
        if (NetworkUtils.isNetworkConnected(this)) {
            FavoritesAdapter favoritesAdapter = new FavoritesAdapter();
            recyclerView.setAdapter(favoritesAdapter);
            getSupportLoaderManager().initLoader(
                    ID_FAVORITES_LOADER, null, new FavoritesLoader(this, favoritesAdapter));
        } else {
            generateSnackBarMessage();
        }
    }

    private void setMovieAdapterTopRated() {
        if (NetworkUtils.isNetworkConnected(this)) {
            moviesAdapter = new MoviesAdapter();
            FetchMoviesTask moviesTask = new FetchMoviesTask(moviesAdapter);
            recyclerView.setAdapter(moviesAdapter);
            moviesTask.execute(FILTER2);
        } else {
            generateSnackBarMessage();
        }
    }

    private void setMovieAdapterPopular() {
        if (NetworkUtils.isNetworkConnected(this)) {
            moviesAdapter = new MoviesAdapter();
            FetchMoviesTask moviesTask = new FetchMoviesTask(moviesAdapter);
            recyclerView.setAdapter(moviesAdapter);
            moviesTask.execute(FILTER1);
        } else {
            generateSnackBarMessage();
        }
    }

    private int getSpan() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 4;
        }
        return 2;
    }
}
