package com.github.pashmentov96.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static Intent getIntent(@NonNull final Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, ListPersonsFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void showDetailContainer(@NonNull final long id) {
        View detailView = findViewById(R.id.detailContainer);
        if (detailView == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, ProfileFragment.newInstance(id))
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detailContainer, ProfileFragment.newInstance(id))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
