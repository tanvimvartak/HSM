package com.example.hsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreen extends AppCompatActivity {

    Button residents, notice , complaint, logout,user_maintenance;
    private FirebaseAuth Fauth;

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = Fauth.getCurrentUser();
        if (currentUser == null){
            Intent intent = new Intent(HomeScreen.this, MainActivity.class);
            startActivity(intent);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        residents = findViewById(R.id.residents);
        notice = findViewById(R.id.notice);
        complaint = findViewById(R.id.complaint);
        logout = findViewById(R.id.logout);
        user_maintenance = findViewById(R.id.user_maintenance);


        Fauth = FirebaseAuth.getInstance();

        residents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this,Residents.class );
                startActivity(intent);
            }
        });

        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this , ComplaintUser.class);
                startActivity(intent);
            }
        });

        user_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, MaintenanceUser.class);
                startActivity(intent);
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, NoticeBoardUser.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fauth.signOut();

                Toast.makeText(HomeScreen.this, "You have been logged out ", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(HomeScreen.this, MainActivity.class);
                startActivity(intent);

               // Toast.makeText(HomeScreen.this, "You have been logged out ", Toast.LENGTH_SHORT).show();

            }
        });
    }
}