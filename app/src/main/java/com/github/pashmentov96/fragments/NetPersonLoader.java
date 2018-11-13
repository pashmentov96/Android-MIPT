package com.github.pashmentov96.fragments;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class NetPersonLoader {
    private final String LOG = "MyLogs";
    private final String BASE_URL = "https://demo1155324.mockable.io/";
    private List<Person> personList;

    public List<Person> loadPersons() {
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
                    personList = response.body().getPersonList();
                    Log.d(LOG, "SIZE = " + personList.size());
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
        return personList;
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
