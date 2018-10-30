package com.example.anameplease.fitlogalpha;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.anameplease.fitlogalpha.databinding.ActivityLogCreatorBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LogCreator extends AppCompatActivity {

    private ActivityLogCreatorBinding binding;

    private Notes noteToInsert;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_creator);


        Toolbar toolbar = binding.toolbar2;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();


        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer id = Integer.valueOf(binding.txtID.getText().toString());
                String name = binding.txtNm.getText().toString();
                Integer date = binding.simpleDatePicker.getDayOfMonth();
                String note = binding.txtNt.getText().toString();


                noteToInsert  = new Notes(id, name, date, note);

                new Async1(getApplicationContext(), noteToInsert).execute(noteToInsert);

                Toast toast = Toast.makeText(getApplicationContext(), "Success!!! "+noteToInsert.getName()+" "+noteToInsert.getId(), Toast.LENGTH_LONG);
                toast.show();

            }
        });


    }

    private class Async1 extends NotesServices {


        public Async1(Context context, Notes notes) {
            super(context, notes);
        }


    }


}
