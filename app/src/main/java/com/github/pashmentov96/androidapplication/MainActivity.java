package com.github.pashmentov96.androidapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button buttonEmail;
    Button buttonProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEmail = (Button) findViewById(R.id.buttonSendEmail);
        buttonProfile = (Button) findViewById(R.id.buttonOpenProfile);

        // создаем обработчик нажатия
        View.OnClickListener onClickButtonEmail = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EmailIntentBuilder — Making life easier

                Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:developer@example.com" +
                                "?subject=" + Uri.encode("Homework 2") +
                                "&body=" + Uri.encode("Sent from AVD"))
                );
                startActivity(email);
            }
        };

        buttonEmail.setOnClickListener(onClickButtonEmail);
    }

    public void onClickButtonProfile(View v) {
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
    }
}
