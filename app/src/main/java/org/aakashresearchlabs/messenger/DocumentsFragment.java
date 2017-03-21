package org.aakashresearchlabs.messenger;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anuragmaravi on 18/03/17.
 */

public class DocumentsFragment extends Fragment {

      /*ToDo: Add a recyclerView for documents
        Download documents
        Upload Documents
        View Documents
         */

    ProgressBar uploadProgress;
    CardView uploadCard;
    TextView progressText;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager layoutmanager;
     SubjectAdapter subjectAdapter;
    ArrayList<SubjectClass> fileList;
    String fileNameText=null;
    String subjectNameText=null;


    public DocumentsFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
<<<<<<< HEAD
       setHasOptionsMenu(true);
        View v=inflater.inflate(R.layout.fragment_documents, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutmanager=new StaggeredGridLayoutManager(3,1);
        recyclerView.setLayoutManager(layoutmanager);
        fileList=new ArrayList<>();
        fileList.add(new SubjectClass("Microprocessors ","df sdfdfdfs"));
        fileList.add(new SubjectClass("Java ","df sdfdfdfs"));
        fileList.add(new SubjectClass("OOAD ","df sdfdfdfs"));
        fileList.add(new SubjectClass("Requirements ","df sdfdfdfs"));
        fileList.add(new SubjectClass("Java ","df sdfdfdfs"));
        fileList.add(new SubjectClass("dfsdfsf ","df sdfdfdfshjghg"));
        fileList.add(new SubjectClass("Maths ","df sdfdfdfs"));
        fileList.add(new SubjectClass("Maths ","df sdfdfdfs"));
        fileList.add(new SubjectClass("Verbal ","df sdfdfdfs"));
        fileList.add(new SubjectClass("French ","df sdfdfdfs"));
        fileList.add(new SubjectClass("Microprocessors ","df sdfdfdfs"));

        subjectAdapter=new SubjectAdapter(fileList,getActivity());
        recyclerView.setAdapter(subjectAdapter);



        uploadCard=(CardView)v.findViewById(R.id.uploadCard);
        uploadProgress=(ProgressBar)v.findViewById(R.id.uploadProgress);
        progressText=(TextView)v.findViewById(R.id.progressText);
        FloatingActionButton uploadFAB=(FloatingActionButton)v.findViewById(R.id.uploadFAB);
        uploadFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();
            }
        });
        return v;
    }
    void showPopUp()
    {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_upload, null);
        final Spinner subject_spinner=(Spinner) dialogLayout.findViewById(R.id.subject_spinner);
        final EditText fileName=(EditText) dialogLayout.findViewById(R.id.new_file_name);
        final TextInputLayout textInputLayout=(TextInputLayout)dialogLayout.findViewById(R.id.textinputlayout);
        String spinnerArray[] ={"Choose a course","Java","2","3","4","5"};

            ArrayAdapter spinnerAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,spinnerArray);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subject_spinner.setAdapter(spinnerAdapter);
        ;



        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(dialogLayout);
        builder.setPositiveButton("Cancel",null)
                .setNegativeButton("Upload", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                fileNameText=fileName.getText().toString();
                                subjectNameText=subject_spinner.getSelectedItem().toString();
                                mockLoader();
                            }
                        });
        final AlertDialog customDialog=builder.create();
                customDialog.show();
        customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
        fileName.setEnabled(false);
        textInputLayout.setEnabled(false);

        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(subject_spinner.getSelectedItem().toString().equals("Choose a course")){
                customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
                fileName.setEnabled(false);
                textInputLayout.setEnabled(false);}
                else
                { customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(true);
                fileName.setEnabled(true);
                textInputLayout.setEnabled(true);}

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    void mockLoader()
    {
        uploadCard.setVisibility(View.VISIBLE);
        uploadProgress.setMax(5);
        uploadProgress.setProgress(0);
        myCountTimer countTimer =new myCountTimer(5000,500);
        countTimer.start();
    }

    public class myCountTimer extends CountDownTimer{

        public myCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onTick(long l) {
            int progress=(int)l/1000;
            uploadProgress.setProgress(uploadProgress.getMax()-progress);
        }

        @Override
        public void onFinish() {
            uploadCard.setVisibility(View.GONE);
            fileList.add(0,new SubjectClass(subjectNameText,fileNameText));
            subjectAdapter.notifyDataSetChanged();

        }
=======
        return inflater.inflate(R.layout.fragment_documents, container, false);

        /*ToDo: Add a recyclerView for documents
        Download documents
        Upload Documents
        View Documents
         */

>>>>>>> origin/master
    }
}

