package com.github.pashmentov96.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class PersonAdapter extends RecyclerView.Adapter <PersonAdapter.PersonViewHolder> {
    private List<Person> personList = new ArrayList<>();
    private ViewHolderListener listener;
    private static final String LOG = "MyLogs";

    public void setPersonList(ViewHolderListener listener, List<Person> personList) {
        this.listener = listener;
        this.personList = personList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.person_item_view, parent, false);
        return new PersonViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person currentPerson = personList.get(position);
        holder.personName.setText(currentPerson.getName());
        Picasso.get()
                .load(currentPerson.getImageURL())
                .into(holder.photo);
        holder.setId(position);
        Log.d(LOG, "Position = " + position);
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        private TextView personName;
        private ImageView photo;
        private int id;
        private ViewHolderListener listener;

        public PersonViewHolder(View itemView, ViewHolderListener listener) {
            super(itemView);
            this.listener = listener;
            personName = itemView.findViewById(R.id.personName);
            photo = itemView.findViewById(R.id.miniPhoto);
            View personItemView = itemView.findViewById(R.id.personItemView);
            personItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(LOG, "Id in Listener " + id);
                    PersonViewHolder.this.listener.onPersonClicked(id);
                }
            });
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}