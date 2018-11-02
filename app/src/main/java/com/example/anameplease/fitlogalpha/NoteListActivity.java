package com.example.anameplease.fitlogalpha;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.anameplease.fitlogalpha.databinding.ActivityNoteListBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.*;

public class NoteListActivity extends AppCompatActivity {

    private ActivityNoteListBinding binding;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Notes> list = new ArrayList<>();
    private OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note_list);

        binding.recyclerView.setHasFixedSize(true);

        List<Notes> init = initNotes();

        mLayoutManager = new LinearLayoutManager(this);

        binding.recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NotesAdapter(init, new OnItemClickListener() {
            @Override
            public void onItemClick(Notes item) {

               new Async1(getApplicationContext()).appendData(getApplicationContext(), binding.edttxtAppend.getText().toString(), item);

                Toast.makeText(getApplicationContext(), item.getNote(), Toast.LENGTH_LONG).show();

                mAdapter.notifyDataSetChanged();

            }
        });

        binding.recyclerView.setAdapter(mAdapter);

        Toolbar toolbar = binding.toolbar4;
        setSupportActionBar(toolbar);




        ItemTouchHelper helper =  new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                int position1 = viewHolder.getAdapterPosition();

                Notes notessss = ((NotesAdapter) mAdapter).getNoteAtPosition(position1);

                Toast.makeText(getApplicationContext(), "Deleting " +
                        notessss.getNote(), Toast.LENGTH_LONG).show();

                new Async1(getApplicationContext()).deleteNote(notessss);

                Toast.makeText(getApplicationContext(), "Deleted " +
                        notessss.getNote(), Toast.LENGTH_LONG).show();
            }
        });

        helper.attachToRecyclerView(binding.recyclerView);


    }

    public List<Notes> initNotes(){
        List<Notes> notesArrayList;
        notesArrayList = new Async1(getApplicationContext()).getAll();
        return notesArrayList;
    }

    public void deleteNote(Notes item){
        new Async1(getApplicationContext()).deleteNote(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:

            default:
                return onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_out);
    }

    private class Async1 extends NotesServices{

        private AppDatabase db;

        public Async1(Context context) {
            super(context);
        }

        public Async1(Context context, Integer id){
            super(context, id);
        }

        @Override
        protected Notes doInBackground(Notes... notes) {
            db.notesDao().delete(notes[0]);
            return super.doInBackground(notes);
        }
    }
}
