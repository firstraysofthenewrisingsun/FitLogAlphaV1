package com.example.anameplease.fitlogalpha;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private List<Notes> mDataset;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_notes, viewGroup, false );

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        int safePosition = myViewHolder.getAdapterPosition();

      Notes notes = mDataset.get(safePosition);

      myViewHolder.name.setText(String.valueOf(notes.getName()));
      myViewHolder.date.setText(String.valueOf(notes.getDate()));
      myViewHolder.note.setText(String.valueOf(notes.getNote()));

    }

    @Override
    public int getItemCount() {
        if (mDataset != null){

            return mDataset.size();
        } else {
            return 0;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public TextView name;
        public TextView date;
        public TextView note;

        public MyViewHolder(View view){
            super(view);
            this.view = view;
            name = view.findViewById(R.id.txtvwname);
            date = view.findViewById(R.id.txtvwdate);
            note = view.findViewById(R.id.txtvwnote);

        }


    }


    public NotesAdapter(List<Notes> myDataset){
        this.mDataset = myDataset;
    }
}
