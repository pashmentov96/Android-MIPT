package com.github.pashmentov96.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ViewHolderListener {

    @Override
    public void onPersonClicked(int id) {
        showProfileFragment(id);
    }

    private static final String PROFILE_ON_DETAIL_CONTAINER = "Profile on detail container";
    private static final String PROFILE_ON_MAIN_CONTAINER = "Profile on main container";
    private static final String LOG = "MyLogs";
    private static int lastId;

    public static Intent getIntent(@NonNull final Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BackupService.getAllBackups(this);
        BackupService.addBackup(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, ListPersonsFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(PROFILE_ON_MAIN_CONTAINER);
            if (fragment != null) {
                View detailView = findViewById(R.id.detailContainer);
                if (detailView != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.detailContainer, ProfileFragment.newInstance(lastId), PROFILE_ON_DETAIL_CONTAINER)
                            .commit();

                    getSupportFragmentManager().popBackStack();
                }
            }
        }
    }

    public void showProfileFragment(int id) {
        Log.d(LOG, "ProfileId = " + id);
        lastId = id;
        View detailView = findViewById(R.id.detailContainer);
        if (detailView == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, ProfileFragment.newInstance(id), PROFILE_ON_MAIN_CONTAINER)
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detailContainer, ProfileFragment.newInstance(id), PROFILE_ON_DETAIL_CONTAINER)
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
