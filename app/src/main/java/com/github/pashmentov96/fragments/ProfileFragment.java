package com.github.pashmentov96.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements GestureDetector.OnGestureListener {
    private static final String NAME_KEY = "NAME_KEY";
    private static final String LOG = "MyLogs";
    private static final float SWIPE_THRESHOLD = 100;
    private GestureDetectorCompat gestureDetector;

    public static Fragment newInstance(int id) {
        Fragment fragment = new ProfileFragment();

        Bundle arguments = new Bundle();
        arguments.putInt(NAME_KEY, id);

        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final int id = getArguments().getInt(NAME_KEY);
        Log.d(LOG, "Id in onViewCreated " + String.valueOf(id));

        gestureDetector = new GestureDetectorCompat(view.getContext(), this);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        @SuppressLint("StaticFieldLeak")
        AsyncTask task = new AsyncTask<Void, Void, Person>() {
            @Override
            protected Person doInBackground(Void... voids) {
                DatabasePersonStorage personRepository = new DatabasePersonStorage(view.getContext());
                return personRepository.findPersonById(id);
            }

            @Override
            protected void onPostExecute(Person person) {
                super.onPostExecute(person);
                Log.d(LOG, person.getName());

                EditText aboutProfile = view.findViewById(R.id.aboutProfile);
                aboutProfile.setText(person.getNote());

                TextView name = view.findViewById(R.id.name);
                name.setText(person.getName());

                ImageView photo = view.findViewById(R.id.photo);
                Picasso.get()
                        .load(person.getImageURL())
                        .into(photo);
            }
        }.execute();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(LOG, "onDown: " + e.toString());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(LOG, "onShowPress: " + e.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(LOG, "onSingleTapUp: " + e.toString());
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(LOG, "onScroll: " + e1.toString() + " " + e2.toString());
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(LOG, "onLongPress: " + e.toString());
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(LOG, "onFling: " + e1.toString() + " " + e2.toString());
        float dy = e2.getY() - e1.getY();
        float dx = e2.getX() - e1.getX();
        if (Math.abs(dy) > Math.abs(dx) && dy > SWIPE_THRESHOLD) {
            ((ProfileListener)getActivity()).onProfileSwipe();
        }
        return false;
    }
}
