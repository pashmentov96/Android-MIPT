package com.github.pashmentov96.fragments;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class NetPersonLoader {
    private static final String LOG = "MyLogs";
    private static final String BASE_URL = "https://demo1155324.mockable.io/";
    private List<Person> personList;

    public List<Person> loadPersons() {
        Log.d(LOG, "start loading");
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PersonsAPI personsAPI = retrofit.create(PersonsAPI.class);

        final Call<Persons> persons = personsAPI.getPersons();

        try {
            Response<Persons> response = persons.execute();
            if (response.isSuccessful()) {
                personList = response.body().getPersonList();
                Log.d(LOG, "SIZE = " + personList.size());
            } else {
                Log.d(LOG, "response code " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
