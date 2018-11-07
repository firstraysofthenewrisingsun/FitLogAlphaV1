package com.example.anameplease.fitlogalpha;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NotesServices extends AsyncTask<Notes, Void, Notes> {

    private static AppDatabase db;
    private String name;
    private int date;
    private String note;
    private Integer id;

    private Notes newnote;
    private appFunc func = new appFunc();
    private File root = android.os.Environment.getExternalStorageDirectory();
    private String rootPath = root.toString();


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

    public void appendData(Context context, String data, Notes item){
        func.conCat(data, item, context);
        db.notesDao().updateNote(item);
        Toast.makeText(context.getApplicationContext(), item.getName()+" has been appended.", Toast.LENGTH_LONG).show();
    }

    public void writeToSD(String n, String d, String nts, String FileName, File root, Context context ){
        func.writeToSDFile(n,d,nts, FileName, root);
        Toast.makeText(context.getApplicationContext(), "Log File Created.", Toast.LENGTH_LONG).show();
    }

    public void appendToSD(File FileName, String data, Context context){
        func.appendFile(FileName, data);
        Toast.makeText(context.getApplicationContext(), "Log File Appended.", Toast.LENGTH_LONG).show();
    }

    public List<Notes> getAllByID(Integer id){
        List<Notes> allByID = db.notesDao().getAllByID(id);

        return allByID;
    }

    public void deleteNote(Notes item){

        db.notesDao().delete(item);

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

    public ArrayList<String> getFiles(){
        ArrayList<String> getFiles = func.GetFiles();

        return getFiles;
    }
}


