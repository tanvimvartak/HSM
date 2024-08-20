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

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth Fauth;
    private static int RC_SIGN_IN=100;
    private ProgressBar progressBar;
    Button register, already;
    EditText uname, email, pword1, pword2, userWing, flatnum;
    SignInButton signbutton;
    DatabaseReference db1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Fauth = FirebaseAuth.getInstance();
        db1 = FirebaseDatabase.getInstance().getReference();

        uname = findViewById(R.id.uname);
        email = findViewById(R.id.email);
        pword1 = findViewById(R.id.pword1);
        pword2 = findViewById(R.id.pword2);
        userWing = findViewById(R.id.userWing);
        flatnum= findViewById(R.id.flatnum);

        register = findViewById(R.id.register);
        already = findViewById(R.id.already);

        progressBar = findViewById(R.id.progressBar);

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        progressBar.setVisibility(View.GONE);


        String gmail=email.getText().toString();
        String p1=pword1.getText().toString();
        String p2=pword2.getText().toString();
        String name=uname.getText().toString();
        String wing = userWing.getText().toString();
        String flatNo = flatnum.getText().toString();

        if (TextUtils.isEmpty(gmail)){
            email.setError("Please Enter Email!");
            return;
        }
        if (TextUtils.isEmpty(name)){
            userWing.setError("Please Enter Name!");
            return;
        }


        if (TextUtils.isEmpty(p1)){
            //Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            pword1.setError("Please Enter Password!");

            return;

        }
        if (TextUtils.isEmpty(p2)){
          //  Toast.makeText(this, "Please Enter Confirm Password ", Toast.LENGTH_SHORT).show();
            pword2.setError("Please Enter Confirm Password!");

            return;

        }
        if (TextUtils.isEmpty(wing)){
          //  Toast.makeText(this, "Please Enter Wing ", Toast.LENGTH_SHORT).show();
            userWing.setError("Please Enter Wing!");

            return;

        }
        if (TextUtils.isEmpty(flatNo)){
            flatnum.setError("Please Enter Flat Number!");

            return;

        }
        if (!p1.equals(p2)){
            pword2.setError("Passwords do not match!");

            return;

        }

        Fauth.createUserWithEmailAndPassword(gmail,p1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.GONE);

                    Map<String, Object> user = new HashMap<>();
                    user.put("Name",name);
                    user.put("Email",getEmailModified(gmail));
                    user.put("Wing", wing);
                    user.put("Flat No", flatNo);
                    user.put("type", "user");

                    db1.child("users").child(Fauth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUp.this, "Data Stored Successfully", Toast.LENGTH_SHORT).show();

                                db1.child("uid_email_mapping").child(getEmailModified(gmail)).setValue(Fauth.getCurrentUser().getUid().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Toast.makeText(SignUp.this, "Email modified added", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                            else {
                                Toast.makeText(SignUp.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    Intent intent = new Intent(SignUp.this,HomeScreen.class);
                    startActivity(intent);
                }
                else {
                    //Toast.makeText(SignUp.this, "Registration Failed! Please Try again", Toast.LENGTH_SHORT).show();
                    Toast.makeText(SignUp.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });




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

}