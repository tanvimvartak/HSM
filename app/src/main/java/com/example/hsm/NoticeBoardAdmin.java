package com.example.hsm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class NoticeBoardAdmin extends AppCompatActivity {

    EditText titleInput, descInput;
    Button uploadBtn;

    ArrayList<Notice> noticeArrayList;
    CustomAdapterNotice adapter;
    ListView listView;

    DatabaseReference db;
    StorageReference storage;

    Uri filePath;
    String title, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board_admin);

        db = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();

        titleInput = findViewById(R.id.notice_titleInput);
        descInput = findViewById(R.id.notice_descriptionInput);
        uploadBtn = findViewById(R.id.notice_uploadBtn);

        noticeArrayList = new ArrayList<>();
        adapter = new CustomAdapterNotice(NoticeBoardAdmin.this, noticeArrayList, "Admin");
        listView = findViewById(R.id.noticeAdmin_displayNotice);
        listView.setAdapter(adapter);

        getNotice();

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = titleInput.getText().toString().trim();
                desc = descInput.getText().toString().trim();

                uploadNotice();

            }
        });

    }

    private void getNotice() {

        db.child("notice").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful() && task.getResult().exists()) {

                    for(DataSnapshot NOTICE : task.getResult().getChildren()) {

                        String timestamp = NOTICE.getKey().toString();
                        String title = NOTICE.child("title").getValue().toString();
                        String desc = NOTICE.child("desc").getValue().toString();
                        String img = NOTICE.child("img").getValue().toString();


                        Notice notice = new Notice(title, desc, img, timestamp);
                        noticeArrayList.add(notice);

                    }

                    adapter.notifyDataSetChanged();

                }

            }
        });

    }

    private String getTimeStamp() {
       // return String.valueOf(System.currentTimeMillis()/1000);
          return String.valueOf(System.currentTimeMillis()/1000);
    }


    private void uploadNotice() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();

            uploadImage(title, desc, filePath);

        }

    }

    private void uploadImage(String title, String desc, Uri filePath) {

        StorageReference ref = storage.child("images/" + UUID.randomUUID().toString());

        // adding listeners on upload
        // or failure of image
        ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // success
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete()) ;
                String imageUrl = uri.getResult().toString();
                putNoticeData(title, desc, imageUrl);
            }
        });
    }

    private void putNoticeData(String title, String desc, String imgUrl) {
        String timestamp = getTimeStamp();


        HashMap<String, String> notice = new HashMap<>();
        notice.put("title", title);
        notice.put("desc", desc);
        notice.put("img", imgUrl);

        db.child("notice").child(timestamp).setValue(notice).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {

                    Toast.makeText(NoticeBoardAdmin.this, "Notice Uploaded", Toast.LENGTH_SHORT).show();

                    Notice notice = new Notice(title, desc, imgUrl, timestamp);
                    noticeArrayList.add(notice);
                    Collections.reverse(noticeArrayList);
                    adapter.notifyDataSetChanged();

                }

            }
        });

    }
}