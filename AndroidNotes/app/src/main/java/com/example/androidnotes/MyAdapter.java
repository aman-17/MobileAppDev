package com.example.androidnotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<NoteViewHolder>{
    private static final String TAG = "MyAdapter";
    private List<Notes> notesList;
    private MainActivity mainAct;

    MyAdapter(List<Notes> noteList, MainActivity main) {
        this.notesList = noteList;
        this.mainAct = main;
    }

    public NoteViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Creating new Holder");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.displaynotes, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Creating new Holder " + position);

        Notes note = notesList.get(position);

        if (note.getTitle().length() <= 80){
            holder.title.setText(note.getTitle());

        }
        else {
            holder.title.setText(String.format("%s...", note.getTitle().substring(0, 80)));

        }

        if (note.getContent().length() <= 80){
            holder.content.setText(note.getContent());

        }
        else {
            holder.content.setText(String.format("%s...", note.getContent().substring(0, 80)));
        }
        holder.date.setText(note.getDate());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}
