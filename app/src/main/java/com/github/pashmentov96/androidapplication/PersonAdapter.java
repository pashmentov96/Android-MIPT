package com.github.pashmentov96.androidapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class PersonAdapter extends RecyclerView.Adapter <PersonAdapter.PersonViewHolder> {
    private List<Person> personList = new ArrayList<>();

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.person_item_view, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.personName.setText(personList.get(position).getName());
        holder.photo.setImageResource(personList.get(position).getImageRes());
        holder.setId(personList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        private TextView personName;
        private ImageView photo;
        private long id;
        public PersonViewHolder(View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.personName);
            photo = itemView.findViewById(R.id.miniPhoto);
            //personItemView == itemView ???
            View personItemView = itemView.findViewById(R.id.personItemView);
            personItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    context.startActivity(profile.getIntent(context, id));
                    //Toast.makeText(context, personName.getText(), Toast.LENGTH_LONG).show();
                }
            });
        }
        public void setId(long id) {
            this.id = id;
        }
    }

    public Person getPersonByIndex(int index) {
        return personList.get(index);
    }


}
