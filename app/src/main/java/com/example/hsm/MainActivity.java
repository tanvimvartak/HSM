package com.example.hsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    Button login1 , signup ;
    EditText EmailAddress, password;
    private FirebaseAuth Fauth;

    private DatabaseReference db1;

    private ProgressBar progressBar1;

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = Fauth.getCurrentUser();
        if (currentUser != null){
            checkUserType(currentUser.getUid());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login1 = findViewById(R.id.login1);
        signup = findViewById(R.id.signup);
        EmailAddress = findViewById(R.id.EmailAddress);
        password = findViewById(R.id.password);

        progressBar1 = findViewById(R.id.progressBar3);

        Fauth = FirebaseAuth.getInstance();
        db1 = FirebaseDatabase.getInstance().getReference();

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar1.setVisibility(View.VISIBLE);

                String gmail = EmailAddress.getText().toString();
                String p1 = password.getText().toString();

                if (TextUtils.isEmpty(gmail)){
                   // Toast.makeText(MainActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    EmailAddress.setError("Please Enter Email!");
                    return;
                }
                if (TextUtils.isEmpty(p1)){
                  //  Toast.makeText(MainActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    password.setError("Please Enter Password!");
                    return;

                }

                Fauth.signInWithEmailAndPassword(gmail, p1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            progressBar1.setVisibility(View.GONE);

                            checkUserType(Fauth.getUid());
                        }

                        else {
                            Toast.makeText(MainActivity.this , task.getException().getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                            progressBar1.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }


    private void checkUserType(String uid) {

        db1.child("users").child(Fauth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                String type = dataSnapshot.child("type").getValue().toString();

                if (type.equals("user")){
                    Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, AdminHomeScreen.class);
                    startActivity(intent);
                }

            }
        });

    }
}
