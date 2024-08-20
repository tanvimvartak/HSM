package com.example.hsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class NoticeBoardUser extends AppCompatActivity {

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
        setContentView(R.layout.activity_notice_board_user);

        db = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();


        noticeArrayList = new ArrayList<>();
        adapter = new CustomAdapterNotice(NoticeBoardUser.this, noticeArrayList, "User");
        listView = findViewById(R.id.noticeUser_displayNotice);
        listView.setAdapter(adapter);

        getNotice();

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
}