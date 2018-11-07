package com.github.pashmentov96.fragments;

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

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ListPersonsFragment extends Fragment {

    private PersonAdapter adapter;
    final String LOG = "MyLogs";
    final String BASE_URL = "http://demo1155324.mockable.io/";

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

        if (StorageOfPersons.getIsCached()) {
            adapter.setPersonList((ViewHolderListener) getActivity(), StorageOfPersons.getPersonList());
        } else {
            loadPersons();
        }
        Log.d(LOG, "after loading");
    }

    private void loadPersons() {
        Log.d(LOG, "start loading");
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PersonsAPI personsAPI = retrofit.create(PersonsAPI.class);

        Call<Persons> persons = personsAPI.getPersons();

        persons.enqueue(new Callback<Persons>() {
            @Override
            public void onResponse(Call<Persons> call, Response<Persons> response) {
                if (response.isSuccessful()) {
                    StorageOfPersons.addPersons(response.body().getPersonList());
                    Log.d(LOG, "SIZE = " + StorageOfPersons.getPersonList().size());
                    adapter.setPersonList((ViewHolderListener) getActivity(), StorageOfPersons.getPersonList());
                } else {
                    Log.d(LOG, "response code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Persons> call, Throwable t) {
                Log.d(LOG, "failure " + t);
            }
        });
        Log.d(LOG, "finish loading");
    }

    public class Persons {
        @SerializedName("persons")
        private List<Person> personList = new ArrayList<>();

        public List<Person> getPersonList() {
            return personList;
        }
    }

    public interface PersonsAPI {
        @GET("person/all")
        Call<Persons> getPersons();
    }
}
