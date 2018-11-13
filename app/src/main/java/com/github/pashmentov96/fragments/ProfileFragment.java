package com.github.pashmentov96.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    private static final String NAME_KEY = "NAME_KEY";

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int id = getArguments().getInt(NAME_KEY);
        Log.d("MyLogs", String.valueOf(id));
        DatabasePersonStorage personRepository = new DatabasePersonStorage(view.getContext());
        Person person = personRepository.findPersonById(id);
        Log.d("MyLogs", person.getName());

        EditText aboutProfile = view.findViewById(R.id.aboutProfile);
        aboutProfile.setText(person.getNote());

        TextView name = view.findViewById(R.id.name);
        name.setText(person.getName());

        ImageView photo = view.findViewById(R.id.photo);
        Picasso.get()
                .load(person.getImageURL())
                .into(photo);
    }
}
