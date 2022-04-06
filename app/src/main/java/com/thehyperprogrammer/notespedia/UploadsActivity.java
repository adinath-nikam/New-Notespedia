package com.thehyperprogrammer.notespedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;
import static com.thehyperprogrammer.notespedia.R.layout.pdf_list_view;

public class UploadsActivity extends AppCompatActivity {

    String Department,Semester,Username;
    EditText FileName,FileSubject,FileDescription;
    View myLayout;
    String FilenameStr,FileSubjectStr,FileDescriptionStr;
    ProgressDialog progressDialog;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploads);

        Bundle extras = getIntent().getExtras();
        Department = extras.getString("Department");
        Semester = extras.getString("Semester");
        Username = extras.getString("Username");

        FirebaseRecyclerOptions<UploadFileHelper> options = new FirebaseRecyclerOptions.Builder<UploadFileHelper>()
                .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("UPLOADS")
                                .child(Department)
                                .child(Semester)
                        ,UploadFileHelper.class)
                .build();


        FirebaseRecyclerAdapter<UploadFileHelper, UploadsActivity.PdfViewholder> adapter
                = new FirebaseRecyclerAdapter<UploadFileHelper, PdfViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PdfViewholder holder,final int position, @NonNull UploadFileHelper model) {

                holder.PDFtitle.setText(model.getFilename());
                holder.PDFdate.setText(model.getDate());
                holder.PDFusername.setText(model.getUsername());
                holder.PDFtime.setText(model.getTime() + "\t-");
                holder.PDFdesc.setText(model.getFileDesc());
                holder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Sub_Key = getRef(position).getKey();
                        Toast.makeText(UploadsActivity.this, "Please wait..",LENGTH_LONG).show();


                        Intent intent = new Intent(UploadsActivity.this,PDF_view.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Dept",Department);
                        bundle.putString("Sem",Semester);
                        bundle.putString("SubKey",Sub_Key);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

            }


            @NonNull
            @Override
            public PdfViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(pdf_list_view, parent, false);
                PdfViewholder viewHolder = new PdfViewholder(view);
                return viewHolder;
            }
        };

        RecyclerView recyclerView = findViewById(R.id.pdf_listview_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        progressDialog = new ProgressDialog(UploadsActivity.this);


        findViewById(R.id.upload_btn_ID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creating an intent for file chooser
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select File"), 123);

                //
            }
        });

    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user chooses the file
        if (requestCode == 123 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {

                setContentView(R.layout.file_upload_layout);


                // get a reference to the already created main layout
                RelativeLayout UploadLayout = (RelativeLayout) findViewById(R.id.UploadsActivity);

                LayoutInflater inflater = getLayoutInflater();
                myLayout = inflater.inflate(R.layout.file_upload_layout, UploadLayout, false);


                FileName = findViewById(R.id.ET_filename_ID);
                FileSubject = findViewById(R.id.ET_Subject_ID);
                FileDescription = findViewById(R.id.ET_Description_ID);

                findViewById(R.id.uploadCancel_btn_ID).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                findViewById(R.id.uploadOK_btn_ID).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //

                        FilenameStr = FileName.getText().toString();
                        FileSubjectStr = FileSubject.getText().toString();
                        FileDescriptionStr = FileDescription.getText().toString();


                        if(FilenameStr.isEmpty() || FileDescriptionStr.isEmpty() || FileSubjectStr.isEmpty()){
                            if(FileDescriptionStr.isEmpty()){
                                FileDescription.setError("Required");
                                FileDescription.requestFocus();
                            }
                            if(FileSubjectStr.isEmpty()){
                                FileSubject.setError("Required");
                                FileSubject.requestFocus();
                            }
                            if(FilenameStr.isEmpty()){
                                FileName.setError("Required");
                                FileName.requestFocus();
                            }

                            progressDialog.dismiss();

                        }
                        else{

                            //uploading the file
                uploadFile(data.getData());
                        }

                    }
                });



            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //this method is uploading the file
    //the code is same as the previous tutorial
    //so we are not explaining it
    private void uploadFile(Uri data) {

        FirebaseStorage
                .getInstance()
                .getReference()
                .child("Uploads")
                .child(Department)
                .child(Semester)
                .child(FilenameStr+".pdf")
                .putFile(data)

                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                progressDialog.setMessage("Just a Moment...");

                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url = uri.toString();


                        Calendar calForDate = Calendar.getInstance();
                        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
                        String CURRENTDATE = currentDateFormat.format(calForDate.getTime());

                        Calendar calForTime = Calendar.getInstance();
                        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
                        String CURRENTTIME = currentTimeFormat.format(calForTime.getTime());

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("Username",Username);
                        hashMap.put("Filename",FilenameStr);
                        hashMap.put("FileDesc",FileDescriptionStr);
                        hashMap.put("FileSub",FileSubjectStr);
                        hashMap.put("Date",CURRENTDATE);
                        hashMap.put("Time",CURRENTTIME);
                        hashMap.put("Url",url);

                        UploadFileHelper uploadFileHelper = new UploadFileHelper(Username,FilenameStr,FileDescriptionStr,FileSubjectStr,CURRENTTIME,CURRENTDATE,url);
                        FirebaseDatabase.getInstance().getReference()
                                .child("UPLOADS")
                                .child(Department)
                                .child(Semester)
                                .child(CURRENTTIME)
                                .setValue(uploadFileHelper);

                        progressDialog.dismiss();
                    }
                });


            }

        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(UploadsActivity.this, "Uploading...", Toast.LENGTH_SHORT).show();
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded:\t"+(int)progress+"%");
                        progressDialog.show();
                    }
                });

    }

    public static class PdfViewholder extends RecyclerView.ViewHolder{

        TextView PDFtitle,PDFusername,PDFtime,PDFdate,PDFdesc;
        ImageView PDF_ic;
        CardView item;

        public PdfViewholder(@NonNull View itemView) {
            super(itemView);
            PDFtitle = itemView.findViewById(R.id.pdf_title);
            PDFusername = itemView.findViewById(R.id.pdfUsername);
            PDFtime = itemView.findViewById(R.id.pdf_time);
            PDFdate = itemView.findViewById(R.id.pdf_date);
            PDF_ic = itemView.findViewById(R.id.pdf_ic);
            PDFdesc = itemView.findViewById(R.id.pdf_desc);
            item = itemView.findViewById(R.id.item);
        }
    }

    //
}
