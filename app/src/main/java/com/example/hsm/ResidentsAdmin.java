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

public class ResidentsAdmin extends AppCompatActivity {

    private CustomAdapterResidents adapter;

    ArrayList<ResidentQuery> residents;
    ListView listView;
    DatabaseReference db;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residents_admin);

        db = FirebaseDatabase.getInstance().getReference();
        fAuth= FirebaseAuth.getInstance();

        residents = new ArrayList<ResidentQuery>();

        listView = findViewById(R.id.residents_listview);
        adapter = new CustomAdapterResidents(ResidentsAdmin.this, residents, "Admin");

        listView.setAdapter(adapter);

        getResidents();

    }

    private void getResidents() {
        db.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    for (DataSnapshot UID : task.getResult().getChildren()){

                        String uid = UID.getKey().toString();

                        if(uid.equals("uIf9nNIQ5gdwWJj19qEZ8osTZR83")) {
                            continue;
                        }

                        String name = UID.child("Name").getValue().toString();
                        String wing =UID.child("Wing").getValue().toString();
                        String flatno = UID.child("Flat No").getValue().toString();

                        ResidentQuery rq = new ResidentQuery(name,wing,flatno, uid );
                        residents.add(rq);

                    }
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }
}