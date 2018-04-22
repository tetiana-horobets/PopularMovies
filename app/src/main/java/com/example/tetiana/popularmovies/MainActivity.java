package com.example.tetiana.popularmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tetiana.popularmovies.Service.InternetConnection;

public class MainActivity extends AppCompatActivity {

    private int fragmentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mEmptyStateTextView = findViewById(R.id.empty_view);
        if (!InternetConnection.isOnline()) {
            mEmptyStateTextView.setText(R.string.no_internet);
        }

        if (savedInstanceState != null) {
            fragmentIndex = savedInstanceState.getInt("selectedFragment", 0);
        } else {
            fragmentIndex = 0;
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        switch (item.getItemId()) {
                            case R.id.navigation_popular:
                                selectedFragment = PopularMovieFragment.newInstance();
                                fragmentIndex = 0;
                                break;
                            case R.id.navigation_top_rated:
                                selectedFragment = TopRatedMovieFragment.newInstance();
                                fragmentIndex = 1;
                                break;
                            case R.id.navigation_favorite:
                                selectedFragment = FavoriteMovieFragment.newInstance();
                                fragmentIndex = 2;
                                break;
                        }

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, getFragment(fragmentIndex));
        transaction.commit();

        bottomNavigationView.getMenu().getItem(fragmentIndex).setChecked(true);
    }

    private Fragment getFragment(int index) {
        if (index == 1)
            return TopRatedMovieFragment.newInstance();

        if (index == 2) {
            return FavoriteMovieFragment.newInstance();
        }

        return PopularMovieFragment.newInstance();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("selectedFragment", fragmentIndex);
    }
}