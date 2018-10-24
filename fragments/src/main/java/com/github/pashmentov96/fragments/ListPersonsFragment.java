package com.github.pashmentov96.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ListPersonsFragment extends Fragment {

    private static MyAsyncTask generatePersonsTask;
    final String LOG = "MyLogs";
    private PersonAdapter adapter;

    public static Fragment newInstance() {
        return new ListPersonsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_persons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.personRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new PersonAdapter();
        recyclerView.setAdapter(adapter);

        generatePersonsTask = new MyAsyncTask();
        generatePersonsTask.execute();
    }

    class MyAsyncTask extends AsyncTask<Void, Void, List<Person>> {
        @Override
        protected List<Person> doInBackground(Void... voids) {
            Log.d(LOG, "begin");
            StorageOfPersons.generate();
            Log.d(LOG, "end");
            return StorageOfPersons.getPersonList();
        }

        @Override
        protected void onPostExecute(List<Person> people) {
            super.onPostExecute(people);
            adapter.setPersonList((ViewHolderListener) getActivity(), people);
            Log.d(LOG, "onPostExecute");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d(LOG, "onCancelled");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG, "ListPersonsFragment: onStop");
        if (generatePersonsTask != null) {
            generatePersonsTask.cancel(true);
        }
    }
}
