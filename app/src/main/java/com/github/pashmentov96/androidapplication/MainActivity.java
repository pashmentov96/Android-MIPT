package com.github.pashmentov96.androidapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.personRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final PersonAdapter adapter = new PersonAdapter();
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            StorageOfPersons.generate();
        }
        adapter.setPersonList(StorageOfPersons.getPersonList());

    }
}
