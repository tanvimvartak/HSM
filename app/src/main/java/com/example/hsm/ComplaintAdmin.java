package com.example.hsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ComplaintAdmin extends AppCompatActivity {

    ArrayList<Query> queries;
    CustomAdapterQuery adapter;
    ListView listView;

    DatabaseReference db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_admin);


        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        queries = new ArrayList<Query>();
        adapter = new CustomAdapterQuery(ComplaintAdmin.this, queries);
        listView = findViewById(R.id.queries_ListView);

        listView.setAdapter(adapter);

        getQueries();

    }

    private void getQueries() {

        db.child("queries").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()) {

                    for(DataSnapshot UID : task.getResult().getChildren()) {

                        String uid = UID.getKey().toString();

                        for(DataSnapshot TIMESTAMP : UID.getChildren()) {

                            String timestamp = TIMESTAMP.getKey().toString();
                            String complaint = TIMESTAMP.child("complaint").getValue().toString();
                            String flat = TIMESTAMP.child("flat").getValue().toString();
                            String name = TIMESTAMP.child("name").getValue().toString();
                            String phone = TIMESTAMP.child("phone").getValue().toString();
                            String status = TIMESTAMP.child("status").getValue().toString();

                            if(status.equals("pending")) {

                                Query q = new Query(uid, name, phone, flat, complaint, timestamp, status);
                                queries.add(q);

                            }

                        }


                    }

                    adapter.notifyDataSetChanged();

                }

            }
        });

    }
}