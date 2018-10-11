package com.github.pashmentov96.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
    private static String NAME_KEY = "NAME_KEY";

    public static Fragment newInstance(long id) {
        Fragment fragment = new ProfileFragment();

        Bundle arguments = new Bundle();
        arguments.putLong(NAME_KEY, id);

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

        long id = getArguments().getLong(NAME_KEY);

        Person person = StorageOfPersons.getPersonById(id);

        EditText aboutProfile = view.findViewById(R.id.aboutProfile);
        aboutProfile.setText(person.getNote());

        TextView name = view.findViewById(R.id.name);
        name.setText(person.getName());

        ImageView photo = view.findViewById(R.id.photo);
        photo.setImageResource(person.getImageRes());
    }
}
