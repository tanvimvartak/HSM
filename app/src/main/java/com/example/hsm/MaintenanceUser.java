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

public class MaintenanceUser extends AppCompatActivity {

    ArrayList<MaintenanceQuery> maintenance;
    CustomAdapterMaintenance adapter;
    ListView listView;
    DatabaseReference db;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_user);

        db = FirebaseDatabase.getInstance().getReference();
        fAuth= FirebaseAuth.getInstance();

        maintenance = new ArrayList<MaintenanceQuery>();
        adapter = new CustomAdapterMaintenance(MaintenanceUser.this, maintenance, "User");

        listView = findViewById(R.id.user_ViewMaintenance);
        listView.setAdapter(adapter);

        getMaintenance();



    }

    private void getMaintenance() {

        db.child("Maintenance").child(fAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot result = task.getResult();

                        MaintenanceQuery userMaintenance = new MaintenanceQuery();

                        userMaintenance.setUid(task.getResult().getValue().toString());
                        userMaintenance.setM_wing(result.child("wing").getValue().toString());
                        userMaintenance.setM_flat(result.child("flat_no").getValue().toString());
                        userMaintenance.setM_name(result.child("name").getValue().toString());
                        userMaintenance.setMq(result.child("mq").getValue().toString());
                        userMaintenance.setM_email(result.child("email").getValue().toString());
                        userMaintenance.setM_maintenance(result.child("maintenance").getValue().toString());
                        userMaintenance.setM_sinkingfund(result.child("sinkingfund").getValue().toString());
                        userMaintenance.setM_charges(result.child("charges").getValue().toString());


                        maintenance.add(userMaintenance);

                        adapter.notifyDataSetChanged();

                    }
                }
            }
        });
    }
}