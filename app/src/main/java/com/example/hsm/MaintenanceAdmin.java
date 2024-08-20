package com.example.hsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MaintenanceAdmin extends AppCompatActivity {
    EditText nameInput,emailInput, wingInput, flatInput, maintenanceInput, sinkingfundInput, chargesInput, mqInput;
    Button m_submitBtn;
    DatabaseReference db;
    FirebaseAuth Fauth;
    ArrayList<MaintenanceQuery> maintenance;
    CustomAdapterMaintenance adapter;
    ListView listView;
    String calling;
    MaintenanceQuery existingMaintenance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_admin);

        db = FirebaseDatabase.getInstance().getReference();
        Fauth= FirebaseAuth.getInstance();

        nameInput = findViewById(R.id.nameInput);
        emailInput=findViewById(R.id.emailInput);
        wingInput = findViewById(R.id.wingInput);
        flatInput = findViewById(R.id.flatInput);
        maintenanceInput =findViewById(R.id.maintenanceInput);
        sinkingfundInput = findViewById(R.id.sinkingfundInput);
        chargesInput = findViewById(R.id.chargesInput);
        mqInput = findViewById(R.id.mq);

        m_submitBtn = findViewById(R.id.m_submitBtn);

        calling = getIntent().getStringExtra("calling");
        existingMaintenance = (MaintenanceQuery) getIntent().getSerializableExtra("caseObject");

        getMaintenance();

        maintenance = new ArrayList<MaintenanceQuery>();
        adapter = new CustomAdapterMaintenance(MaintenanceAdmin.this, maintenance, "Admin");

        listView = findViewById(R.id.admin_ViewMaintenance);
        listView.setAdapter(adapter);

        if(calling.equals("editBtn")) {
            displayExistingMaintenanceDetails(existingMaintenance);
        }

        m_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(calling.equals("editBtn")) {
                    updateMaintenanceDetails(existingMaintenance);
                }
                else {
                    registerMaintenance();
                }

            }
        });



    }

    private void updateMaintenanceDetails(MaintenanceQuery existingMaintenance) {

         registerMaintenance();
    }


    private void registerMaintenance() {

        String name = nameInput.getText().toString().trim();
        String wing = wingInput.getText().toString().trim();
        String flat = flatInput.getText().toString().trim();
        String maintenance = maintenanceInput.getText().toString().trim();
        String sinkingfund = sinkingfundInput.getText().toString().trim();
        String charges = chargesInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String mmq = mqInput.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            nameInput.setError("Please Enter Name!");
            return;
        }
        if (TextUtils.isEmpty(wing)){
            wingInput.setError("Please Enter Wing!");
            return;
        }
        if (TextUtils.isEmpty(flat)){
            flatInput.setError("Please Enter Flat No.!");
            return;
        }
        if (TextUtils.isEmpty(maintenance)){
            maintenanceInput.setError("Please Enter Maintenance!");
            return;
        }
        if (TextUtils.isEmpty(sinkingfund)){
            sinkingfundInput.setError("Please Enter Sinking Fund!");
            return;
        }

        if (TextUtils.isEmpty(email)){
            emailInput.setError("Please Enter Email!");
            return;
        }


        db.child("uid_email_mapping").child(getEmailModified(email)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    String userUID = task.getResult().getValue().toString();

                    HashMap<String, String> values = new HashMap<>();
                    values.put("name", name);
                    values.put("email", email);
                    values.put("wing", wing);
                    values.put("mq", mmq);
                    values.put("flat_no", flat);
                    values.put("maintenance", maintenance);
                    values.put("sinkingfund",sinkingfund);
                    values.put("charges", charges);


                    db.child("Maintenance").child(userUID).setValue(values).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(calling.equals("editBtn")) {
                                Toast.makeText(MaintenanceAdmin.this, "Maintenance Details Updated", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MaintenanceAdmin.this, "Maintenance Details Added", Toast.LENGTH_SHORT).show();
                            }

                            clearTextBoxes();
                        }
                    });
                }

            }
        });




    }

    private void clearTextBoxes() {

        wingInput.setText("");
        flatInput.setText("");
        nameInput.setText("");
        emailInput.setText("");
        maintenanceInput.setText("");
        sinkingfundInput.setText("");
        chargesInput.setText("");
        mqInput.setText("");

    }

    private String getEmailModified(String email) {

        // remove @ and .

        StringBuilder result = new StringBuilder();

        for(int i = 0; i<email.length(); i++) {
            char curChar = email.charAt(i);
            if(curChar == '@' || curChar == '.') {
                continue;
            }
            else {
                result.append(curChar);
            }
        }

        return result.toString();
    }
    private void displayExistingMaintenanceDetails(MaintenanceQuery existingMaintenance) {

        m_submitBtn.setText("Edit Maintenance");

        wingInput.setText(existingMaintenance.getM_wing());
        flatInput.setText(existingMaintenance.getM_flat());
        nameInput.setText(existingMaintenance.getM_name());
        emailInput.setText(existingMaintenance.getM_email());
        maintenanceInput.setText(existingMaintenance.getM_maintenance());
        sinkingfundInput.setText(existingMaintenance.getM_sinkingfund());
        chargesInput.setText(existingMaintenance.getM_charges());
        mqInput.setText(existingMaintenance.getMq());


    }

    private void getMaintenance() {

        db.child("Maintenance").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful() && task.getResult().exists()){
                    for (DataSnapshot MAINTENANCE : task.getResult().getChildren()){

                        MaintenanceQuery userMaintenance = new MaintenanceQuery();
                        userMaintenance.setUid(MAINTENANCE.getKey().toString());
                        userMaintenance.setM_wing(MAINTENANCE.child("wing").getValue().toString());
                        userMaintenance.setM_flat(MAINTENANCE.child("flat_no").getValue().toString());
                        userMaintenance.setM_name(MAINTENANCE.child("name").getValue().toString());
                        userMaintenance.setMq(MAINTENANCE.child("mq").getValue().toString());
                        userMaintenance.setM_email(MAINTENANCE.child("email").getValue().toString());
                        userMaintenance.setM_maintenance(MAINTENANCE.child("maintenance").getValue().toString());
                        userMaintenance.setM_sinkingfund(MAINTENANCE.child("sinkingfund").getValue().toString());
                        userMaintenance.setM_charges(MAINTENANCE.child("charges").getValue().toString());



                        maintenance.add(userMaintenance);


                    }

                    adapter.notifyDataSetChanged();
                }

            }
        });


    }
}