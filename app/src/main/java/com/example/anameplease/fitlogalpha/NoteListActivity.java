package com.example.anameplease.fitlogalpha;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.anameplease.fitlogalpha.databinding.ActivityNoteListBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

public class NoteListActivity extends AppCompatActivity {

    private ActivityNoteListBinding binding;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Notes> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note_list);

        binding.recyclerView.setHasFixedSize(true);

        List<Notes> init = initNotes();

        mLayoutManager = new LinearLayoutManager(this);

        binding.recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NotesAdapter(init);

        binding.recyclerView.setAdapter(mAdapter);

        Toolbar toolbar = binding.toolbar4;
        setSupportActionBar(toolbar);


    }

    public List<Notes> initNotes(){
        List<Notes> notesArrayList;
        notesArrayList = new Async1(getApplicationContext()).getAll();
        return notesArrayList;
    }

    private class Async1 extends NotesServices{


        public Async1(Context context) {
            super(context);
        }

        public Async1(Context context, Integer id){
            super(context, id);
        }
    }
}
