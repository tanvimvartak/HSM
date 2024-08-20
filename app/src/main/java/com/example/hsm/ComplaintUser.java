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

public class ComplaintUser extends AppCompatActivity {

    EditText nameInput , phoneInput, flatno, complaintInput ;
    Button submitBtn;
    DatabaseReference db;
    FirebaseAuth fAuth ;

    ArrayList<Query> userQueries;
    CustomAdapterUserQueries adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_user);

        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        flatno = findViewById(R.id.flatno);
        complaintInput = findViewById(R.id.complaintInput);

        submitBtn = findViewById(R.id.submitBtn);

        db = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();

        userQueries = new ArrayList<Query>();
        listView = findViewById(R.id.user_viewQueries);
        adapter = new CustomAdapterUserQueries(ComplaintUser.this, userQueries);
        listView.setAdapter(adapter);

        getUserQueries();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =nameInput.getText().toString();
                String phone = phoneInput.getText().toString();
                String flat = flatno.getText().toString();
                String complaint = complaintInput.getText().toString();

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(ComplaintUser.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(ComplaintUser.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(flat)){
                    Toast.makeText(ComplaintUser.this, "Enter Flat Number", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(complaint)){
                    Toast.makeText(ComplaintUser.this, "Enter your complaint", Toast.LENGTH_SHORT).show();
                    return;

                }

                putComplaint();




            }
        });

    }

    private void getUserQueries() {

        db.child("queries").child(fAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()) {

                    for(DataSnapshot TIMESTAMP : task.getResult().getChildren()) {

                        String timestamp = TIMESTAMP.getKey().toString();
                        String complaint = TIMESTAMP.child("complaint").getValue().toString();
                        String status = TIMESTAMP.child("status").getValue().toString();

                        Query q = new Query();
                        q.setComplaint(complaint);
                        q.setTimestamp(timestamp);
                        q.setStatus(status);

                        userQueries.add(q);

                    }

                    adapter.notifyDataSetChanged();

                }

            }
        });

    }

    private void putComplaint() {
        String  uid = fAuth.getCurrentUser().getUid().toString();
        String name =nameInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String flat = flatno.getText().toString();
        String complaint = complaintInput.getText().toString();
        String timestamp = getTimeStamp();

        if (TextUtils.isEmpty(name)){
            nameInput.setError("Please Enter Name!");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            phoneInput.setError("Please Enter Phone number!");
            return;
        }
        if (TextUtils.isEmpty(flat)){
            flatno.setError("Please Enter Flat No.!");
            return;
        }
        if (TextUtils.isEmpty(complaint)){
            complaintInput.setError("Please Enter Complaint!");
            return;
        }


        HashMap<String, String> values = new HashMap<>();
        values.put("name", name);
        values.put("phone", phone);
        values.put("flat", flat);
        values.put("complaint", complaint);
        values.put("status", "pending");

        db.child("queries").child(uid).child(timestamp).setValue(values).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ComplaintUser.this, "Complaint successfully submitted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ComplaintUser.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private String getTimeStamp(){
        return String.valueOf(System.currentTimeMillis()/1000);
    }
}