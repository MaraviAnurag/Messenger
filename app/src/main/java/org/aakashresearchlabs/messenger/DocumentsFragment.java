package org.aakashresearchlabs.messenger;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.provider.OpenableColumns;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.app.Activity.RESULT_OK;

/**
 * Created by anuragmaravi on 18/03/17.
 */

public class DocumentsFragment extends Fragment {

      /*ToDo: Add a recyclerView for documents
        Download documents
        Upload Documents
        View Documents
        Get thumbnails
         */

    ProgressBar uploadProgress;
    CardView uploadCard;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager layoutmanager;
     SubjectAdapter subjectAdapter;
    ArrayList<SubjectClass> fileList;
    String fileNameText=null;
    String subjectNameText=null;
    private static final int FILE_REQUEST = 9002;
    private static final int REQUEST_READ_PERMISSION = 9003;
    Uri fileURI;
    private StorageReference mStorageReference;
    SubjectClass file;
    boolean ascending;
    private DatabaseReference mDatabaseReference;
    Spinner spinnerCardFilter;
    ImageView cancelUpload,thumbnail;



    public DocumentsFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       setHasOptionsMenu(true);
        View v=inflater.inflate(R.layout.fragment_documents, container, false);
        Toolbar documentsToolbar=(Toolbar)v.findViewById(R.id.tool_documents);
        ((AppCompatActivity)getActivity()).setSupportActionBar(documentsToolbar);
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerview);
        //Thumbnail in the fragment which i removed for the time being
       // thumbnail=(ImageView)v.findViewById(R.id.thumbnail);

        ascending=true;
        spinnerCardFilter=(Spinner)v.findViewById(R.id.spinnerCardFilter);
        String filterOptions[] ={"Date","Name","Subject"};

        ArrayAdapter spinnerAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,filterOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCardFilter.setAdapter(spinnerAdapter);

        spinnerCardFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filter(spinnerCardFilter.getSelectedItem().toString(),ascending);
                Toast.makeText(getActivity(),spinnerCardFilter.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recyclerView.setHasFixedSize(true);
        layoutmanager=new StaggeredGridLayoutManager(3,1);
        recyclerView.setLayoutManager(layoutmanager);
        file=new SubjectClass(null,null,null);
        fileList=new ArrayList<>();
        subjectAdapter=new SubjectAdapter(fileList,getActivity());
        recyclerView.setAdapter(subjectAdapter);

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Files");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                file=dataSnapshot.getValue(SubjectClass.class);
                fileList.add(file);
                subjectAdapter.notifyDataSetChanged();
                Log.i("Erroe",file.getFileName());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        uploadCard=(CardView)v.findViewById(R.id.uploadCard);
        uploadProgress=(ProgressBar)v.findViewById(R.id.uploadProgress);
        cancelUpload=(ImageView)v.findViewById(R.id.cancelTask);
        FloatingActionButton uploadFAB=(FloatingActionButton)v.findViewById(R.id.uploadFAB);
        uploadFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               requestPermission();
               // showPopUp();
            }
        });
        return v;
    }

    private void requestPermission() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PERMISSION);
            } else {
                openFilePicker();
            }
        } else {
            openFilePicker();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openFilePicker();

                } else {
                    Toast.makeText(getActivity(), "Cannot pick file from storage", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, FILE_REQUEST);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    //Thumbnail retrieving function

//    public static Bitmap getThumbnail(ContentResolver cr, String path) throws Exception {
//
//        Cursor ca = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.MediaColumns._ID }, MediaStore.MediaColumns.DATA + "=?", new String[] {path}, null);
//        if (ca != null && ca.moveToFirst()) {
//            int id = ca.getInt(ca.getColumnIndex(MediaStore.MediaColumns._ID));
//            ca.close();
//            Log.i("Errorr","aint null");
//            return MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MICRO_KIND, null );
//        }
//
//        ca.close();
//        Log.i("Errorr","null");
//        return null;
//
//    }


    void showPopUp()
    {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_upload, null);
        final Spinner subject_spinner=(Spinner) dialogLayout.findViewById(R.id.subject_spinner);
        final EditText fileName=(EditText) dialogLayout.findViewById(R.id.new_file_name);
        final TextInputLayout textInputLayout=(TextInputLayout)dialogLayout.findViewById(R.id.textinputlayout);
        String spinnerArray[] ={"Choose a course","Java","Subject 2","Subject 3","Subject 4","Subject 5"};

            ArrayAdapter spinnerAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,spinnerArray);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subject_spinner.setAdapter(spinnerAdapter);

            fileName.setText(fileNameText);



        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(dialogLayout);
        builder.setPositiveButton("Cancel",null)
                .setNegativeButton("Upload", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                fileNameText=fileName.getText().toString();
                                subjectNameText=subject_spinner.getSelectedItem().toString();
                                file.setFileName(fileNameText);
                                file.setSubjectName(subjectNameText);

                                loader();
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

    void loader()
    {
        //To load an imageView with the thumbnail

//        try {
//            thumbnail.setImageBitmap(getThumbnail(getActivity().getContentResolver(),fileURI.getPath().toString()));
//            Log.i("Erroe","works");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i("Erroe","sorry");
//        }
        final StorageReference onlineStoragePhotoRef = mStorageReference.child("Files").child(file.getSubjectName()).child(file.getFileName());
        uploadCard.setVisibility(View.VISIBLE);
     final UploadTask uploadTask=   onlineStoragePhotoRef.putFile(fileURI);

               uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploadCard.setVisibility(View.GONE);
                file.setDateOfUpload(String.valueOf(System.currentTimeMillis()));
                //noinspection VisibleForTests
                file.setFileURL(taskSnapshot.getDownloadUrl().toString());
                mDatabaseReference.child("Files").push().setValue(file);
           //     fileList.add(0,new SubjectClass(file.getSubjectName(),file.getFileName()));
            //   subjectAdapter.notifyDataSetChanged();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Didnt work :(", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //noinspection VisibleForTests
                        uploadProgress.setMax(100);


                        @SuppressWarnings("VisibleForTests") int progress=(int)((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                        Log.i("Progress",String.valueOf(progress));
                        uploadProgress.setProgress(progress);
                    }
                });
        cancelUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Erroer","trying to deleted");
                uploadCard.setVisibility(View.GONE);
                uploadTask.cancel();
                StorageReference storageReference=mStorageReference.child("Files").child(file.getSubjectName()).child(file.getFileName());
                storageReference.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.i("Erroer","deleted");
                            }
                        });
            }
        });

    //    myCountTimer countTimer =new myCountTimer(5000,500);
      //  countTimer.start();
    }





    void filter(final String sort, boolean ascending)
    {

        Collections.sort(fileList, new Comparator<SubjectClass>() {
            @Override
            public int compare(SubjectClass file1, SubjectClass file2)
            {

                if(sort=="Date")
                return  file1.getDateOfUpload().compareTo(file2.getDateOfUpload());
                else if(sort=="Name")
                    return  file1.getFileName().compareTo(file2.getFileName());
                else if(sort=="Subject")
                    return  file1.getSubjectName().compareTo(file2.getSubjectName());
                else
                    return  file1.getFileName().compareTo(file2.getFileName());            }
        });

        if(!ascending)
            Collections.reverse(fileList);

        subjectAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.documents_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_filter)
        {
            ascending=!ascending;
            if(ascending)
                Toast.makeText(getActivity(), "Ascending", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getActivity(), "Descending", Toast.LENGTH_SHORT).show();

            filter(spinnerCardFilter.getSelectedItem().toString(),ascending);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            fileURI = data.getData();
            fileNameText=getFileName(fileURI);
            showPopUp();

        }
    }
}

