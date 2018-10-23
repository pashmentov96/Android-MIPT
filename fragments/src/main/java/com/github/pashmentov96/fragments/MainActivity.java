package com.github.pashmentov96.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ViewHolderListener {

    @Override
    public void onPersonClicked(long id) {
        showProfileFragment(id);
    }

    private static String PROFILE_ON_DETAIL_CONTAINER = "Profile on detail container";
    private static String PROFILE_ON_MAIN_CONTAINER = "Profile on main container";
    private static long lastId;
    private static MyAsyncTask generatePersonsTask;
    private static Bundle savedInstanceState;

    public static Intent getIntent(@NonNull final Context context) {
        return new Intent(context, MainActivity.class);
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("MyLogs", "begin");
            StorageOfPersons.generate();
            Log.d("MyLogs", "end");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
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
            Log.d("MyLogs", "onPostExecute");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d("MyLogs", "onCancelled");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_main);
        generatePersonsTask = new MyAsyncTask();
        generatePersonsTask.execute();
    }

    public void showProfileFragment(long id) {
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

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MyLogs", "onStop");
        if (generatePersonsTask == null) {
            return ;
        }
        generatePersonsTask.cancel(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MyLogs", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MyLogs", "onDestroy");
    }
}
