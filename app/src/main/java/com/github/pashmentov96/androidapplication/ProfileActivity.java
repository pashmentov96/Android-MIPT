package com.github.pashmentov96.androidapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private static String ID_KEY = "ID_KEY";

    public static Intent getIntent(Context context, long id) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(ID_KEY, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        long id = getIntent().getLongExtra(ID_KEY, -1);
        Person person = StorageOfPersons.getPersonById(id);

        EditText aboutProfile = findViewById(R.id.aboutProfile);
        aboutProfile.setText(person.getNote());

        TextView name = findViewById(R.id.name);
        name.setText(person.getName());

        ImageView photo = findViewById(R.id.photo);
        photo.setImageResource(person.getImageRes());
    }
}
