package com.example.anameplease.fitlogalpha;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.anameplease.fitlogalpha.databinding.ActivityLogCreatorBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class LogCreator extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{

    private ActivityLogCreatorBinding binding;

    private Notes noteToInsert;

    private static AppDatabase db;

    private appFunc heyump = new appFunc();

    private File root = android.os.Environment.getExternalStorageDirectory();
    private String rootPath = root.toString();

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference uploadReference;
    private UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_creator);


        Toolbar toolbar = binding.toolbar2;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        final Context context  = getApplicationContext();
        rfaLayout = binding.activityLogRfal;
        rfaBtn = binding.activityLogRfab;

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://fitlogalpha.appspot.com/LogContainer");

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(context);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();

        items.add(new RFACLabelItem<Integer>().setLabel("Add")
                .setResId(R.mipmap.ic_launcher)
                .setWrapper(1));

        items.add(new RFACLabelItem<Integer>().setLabel("Save")
                .setResId(R.mipmap.ic_launcher)
                .setWrapper(2));
        items.add(new RFACLabelItem<Integer>().setLabel("Upload")
                .setResId(R.mipmap.ic_launcher)
                .setWrapper(3));


        rfaContent
                .setItems(items)
                .setIconShadowRadius(RFABTextUtil.dip2px(context, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(RFABTextUtil.dip2px(context, 5))
        ;

        rfabHelper = new RapidFloatingActionHelper(
                context,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_out);
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch (item.getLabel()){

            case "Save":

                String selectedDate1 = binding.simpleDatePicker.getDayOfMonth()+"/"+(binding.simpleDatePicker.getMonth()+1)+"/"+binding.simpleDatePicker.getYear();
                if ( TextUtils.isEmpty(binding.txtNm.getText()) || (binding.simpleDatePicker.getYear() == 0) || TextUtils.isEmpty(binding.txtNt.getText()) || (binding.simpleDatePicker.getMonth() == 0) || (binding.simpleDatePicker.getDayOfMonth() == 0)){
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Please enter the appropriate data", Toast.LENGTH_LONG);
                    toast1.show();
                } else {
                    heyump.writeToSDFile(binding.txtNm.getText().toString(), selectedDate1, binding.txtNt.getText().toString(), "Log " + binding.txtNm.getText().toString(), root);

                    Toast toast1 = Toast.makeText(getApplicationContext(), "Log Created!!! "+binding.txtNm.getText().toString()+" "+binding.txtID.getText().toString(), Toast.LENGTH_LONG);
                    toast1.show();
                }

                break;
            case "Upload":

                new ChooserDialog().with(this)
                        .withStartFile((rootPath))
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {

                                fireBaseUpload(path, pathFile.getName());

                            }
                        })
                        .build()
                        .show();
                break;
            case "Add":


                Integer id = Integer.valueOf(binding.txtID.getText().toString());
                try {
                    List<Notes> notesList = new Async1(getApplicationContext()).getAllByID(id);

                    for (int i = 0; i < notesList.size(); i++) {
                        if (id == notesList.get(i).getId()) {
                            id = new Integer(notesList.get(i).getId()+1);
                            Toast toast1 = Toast.makeText(getApplicationContext(), "Duplicate ID caught. New ID: "+id, Toast.LENGTH_LONG);
                            toast1.show();
                        }
                    }
                } catch (android.database.sqlite.SQLiteConstraintException e){
                    Toast toast1 = Toast.makeText(getApplicationContext(),"This"+e, Toast.LENGTH_LONG);
                    toast1.show();
                }



                String name = binding.txtNm.getText().toString();
                int month = binding.simpleDatePicker.getMonth() + 1;
                String selectedDate = binding.simpleDatePicker.getDayOfMonth()+""+month+""+binding.simpleDatePicker.getYear();
                Integer date = Integer.valueOf(selectedDate);
                String note = binding.txtNt.getText().toString();



                if ( TextUtils.isEmpty(binding.txtNm.getText()) || TextUtils.isEmpty(binding.txtID.getText()) || (binding.simpleDatePicker.getYear() == 0) || TextUtils.isEmpty(binding.txtNt.getText()) || (binding.simpleDatePicker.getMonth() == 0) || (binding.simpleDatePicker.getDayOfMonth() == 0)){
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Please enter the appropriate data", Toast.LENGTH_LONG);
                    toast1.show();
                } else {

                    noteToInsert  = new Notes(id, name, date, note);

                    new Async1(getApplicationContext(), noteToInsert).execute(noteToInsert);

                    Toast toast = Toast.makeText(getApplicationContext(), "Success!!! "+noteToInsert.getName()+" "+noteToInsert.getId(), Toast.LENGTH_LONG);
                    toast.show();
                }

                break;

        }
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {

    }

    public void fireBaseUpload (String path, String FileName){
        File file = new File(path);


        uploadReference = storageReference.child(FileName);

        uploadTask= uploadReference.putFile(Uri.fromFile(file));

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast toast = Toast.makeText(getApplicationContext(), "Success!!!", Toast.LENGTH_LONG);
                toast.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(), "Nope", Toast.LENGTH_LONG);
                toast.show();

            }
        });

    }

    private class Async1 extends NotesServices {

        public Async1(Context context){
            super(context);
        }

        public Async1(Context context, Notes notes) {
            super(context, notes);
        }

    }


}
