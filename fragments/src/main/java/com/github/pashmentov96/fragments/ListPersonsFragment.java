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
import android.widget.Toast;

import java.util.List;

public class ListPersonsFragment extends Fragment {

    public static Fragment newInstance() {
        return new ListPersonsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_persons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.personRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        final PersonAdapter adapter = new PersonAdapter();
        recyclerView.setAdapter(adapter);



        class MyAsyncTask extends AsyncTask<Void, Integer, List<Person> > {
            @Override
            protected List<Person> doInBackground(Void... voids) {
                Log.d("MyLogs", "begin");
                StorageOfPersons.generate();
                return StorageOfPersons.getPersonList();
            }

            @Override
            protected void onPostExecute(List<Person> people) {
                super.onPostExecute(people);
                adapter.setPersonList((ViewHolderListener) getActivity(), people);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                Toast.makeText(view.getContext(), String.valueOf(values[0]), Toast.LENGTH_LONG);
            }
        };

        MyAsyncTask generatePersonsTask = new MyAsyncTask();
        generatePersonsTask.execute();
    }
}
