package com.github.pashmentov96.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ListPersonsFragment extends Fragment {

    private PersonAdapter adapter;
    private static final String LOG = "MyLogs";

    private PersonRepository personRepository;

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

        personRepository = new PersonRepository(view.getContext());

        @SuppressLint("StaticFieldLeak")
        AsyncTask task = new AsyncTask<Void, Void, List<Person>>() {
            @Override
            protected List<Person> doInBackground(Void... voids) {
                return personRepository.loadPersons();
            }

            @Override
            protected void onPostExecute(List<Person> personList) {
                super.onPostExecute(personList);

                adapter.setPersonList((ViewHolderListener) getActivity(), personList);
            }

        }.execute();
    }
}
