package com.example.hsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeScreen extends AppCompatActivity {

    Button complaintAdmin, residentsAdmin, maintenanceAdmin, logout1, admin_notice;
    FirebaseAuth Fauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);

        complaintAdmin = findViewById(R.id.complaintAdmin);
        residentsAdmin = findViewById(R.id.residentsAdmin);
        maintenanceAdmin= findViewById(R.id.maintenanceAdmin);
        admin_notice = findViewById(R.id.admin_notice);
        logout1 = findViewById(R.id.logout1);

        Fauth = FirebaseAuth.getInstance();

        complaintAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeScreen.this, ComplaintAdmin.class);
                startActivity(intent);

            }
        });

        residentsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeScreen.this, ResidentsAdmin.class);
                startActivity(intent);

            }
        });
        maintenanceAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeScreen.this, MaintenanceAdmin.class);
                intent.putExtra("calling", "maintenancebtn");
                startActivity(intent);

            }
        });

        admin_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeScreen.this, NoticeBoardAdmin.class);
                startActivity(intent);
            }
        });

        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fauth.signOut();

                Toast.makeText(AdminHomeScreen.this, "You have been logged out ", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(AdminHomeScreen.this, MainActivity.class);
                startActivity(intent);

                // Toast.makeText(HomeScreen.this, "You have been logged out ", Toast.LENGTH_SHORT).show();

            }
        });
    }
}