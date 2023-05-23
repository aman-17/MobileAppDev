package com.example.androidnotes;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "NoteViewHolder";
    public TextView title,content, date;


    NoteViewHolder(View view){
        super(view);
        title = view.findViewById(R.id.notes1);
        content = view.findViewById(R.id.notes4);
        date = view.findViewById(R.id.notes2);
    }
}
