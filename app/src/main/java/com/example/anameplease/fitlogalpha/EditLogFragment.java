package com.example.anameplease.fitlogalpha;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.example.anameplease.fitlogalpha.databinding.FragmentEditLogBinding;
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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditLogFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentEditLogBinding binding;

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    private appFunc heyump = new appFunc();

    private String name, date, note;

    private File root = android.os.Environment.getExternalStorageDirectory();
    private String rootPath = root.toString();

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference uploadReference;
    private UploadTask uploadTask;


    public EditLogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditLogFragment newInstance(String param1, String param2) {
        EditLogFragment fragment = new EditLogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_log, container, false);
        View view = binding.getRoot();

        final Context context  = getContext();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://fitlogalpha.appspot.com/LogContainer");

        binding.btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.expandableTextView.setInterpolator(new OvershootInterpolator());

                if (binding.expandableTextView.isExpanded())
                {
                    binding.expandableTextView.collapse();
                }
                else
                {
                    binding.expandableTextView.expand();
                }

            }
        });

        rfaLayout = binding.activityLogRfal;
        rfaBtn = binding.activityLogRfab;

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(context);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();

        items.add(new RFACLabelItem<Integer>().setLabel("Append")
                .setResId(R.mipmap.ic_launcher)
                .setWrapper(1));

        items.add(new RFACLabelItem<Integer>().setLabel("Upload")
                .setResId(R.mipmap.ic_launcher)
                .setWrapper(2));
        items.add(new RFACLabelItem<Integer>().setLabel("View")
                .setResId(R.mipmap.ic_launcher)
                .setWrapper(3));
        items.add(new RFACLabelItem<Integer>().setLabel("Clear")
                .setResId(R.mipmap.ic_launcher)
                .setWrapper(4));


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
        return view;
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch(item.getLabel()){
            case "Append":

                new ChooserDialog().with(getContext())
                        .withStartFile((rootPath))
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {

                               String date = binding.lazyDatePicker.getDate().toString()+"\n";
                               String note = binding.edttxtNotes.getText().toString()+"\n";
                               String append = date + note;
                               heyump.appendFile(pathFile, append);


                            }
                        })
                        .build()
                        .show();


                break;

            case "Upload":

                new ChooserDialog().with(getContext())
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

            case "View":

                new ChooserDialog().with(getContext())
                        .withStartFile((rootPath))
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {

                                String s = heyump.readFile(pathFile);

                                binding.expandableTextView.setText(s);


                            }
                        })
                        .build()
                        .show();
                break;

            case "Clear":
                binding.expandableTextView.setText("");

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
                Toast toast = Toast.makeText(getContext(), "Success!!!", Toast.LENGTH_LONG);
                toast.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}
