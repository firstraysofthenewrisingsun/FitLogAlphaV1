package com.example.anameplease.fitlogalpha;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotesServices extends AsyncTask<Notes, Void, Notes> {

    private static AppDatabase db;
    private String name;
    private int date;
    private String note;
    private Integer id;

    private Notes newnote;


    public  NotesServices(Context context){
        this.db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "notes").allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public  NotesServices(Context context, Notes newnote){
        this.db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "notes").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        this.newnote = newnote;
    }

    public  NotesServices(Context context, Integer id){
        this.db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "notes").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        this.id = id;
    }


    public void insertItem(Notes notes){

        execute(notes);
    }

    public List<Notes> getAll(){
        List<Notes> all = db.notesDao().getAll();

        return all;
    }

    public Notes findByName(String name){

        Notes notes = db.notesDao().searchByName(name);

         return  notes;
    }

    public Notes findByID(){

        Notes notes = db.notesDao().searchByID(id);

        return  notes;
    }

    public void updateNotes(Notes notes){
        db.notesDao().updateNote(notes);
    }


    @Override
    protected Notes doInBackground(Notes... notes) {

        db.notesDao().insert(notes[0]);

        Notes notes1 = db.notesDao().searchByID(id);

        return notes1;
    }
}


