package com.projectcs.varmand;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity implements View.OnClickListener {

    private TextView signuptitle, info, signupbtn;
    private EditText editTextusername, editTextemail, editTextpassword, editTextage;
    private FirebaseAuth mAuth;
    private TextView loginbtn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginbtn1 = (TextView) findViewById(R.id.loginbtn1);
        loginbtn1.setOnClickListener(this);

        signupbtn = (TextView) findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(this);

        editTextusername = (EditText) findViewById(R.id.username);
        editTextemail = (EditText) findViewById(R.id.email);
        editTextpassword = (EditText) findViewById(R.id.password);
        editTextage = (EditText) findViewById(R.id.age);


        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginbtn1:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.signupbtn:
                signupbtn();
                break;
        }

    }

    private void signupbtn() {

        String username = editTextusername.getText().toString().trim();
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String age = editTextage.getText().toString().trim();

        if (username.isEmpty()) {
            editTextusername.setError("Username is required!");
            editTextusername.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextemail.setError("Email is required!");
            editTextemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextpassword.setError("Password is required!");
            editTextpassword.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            editTextage.setError("Age is required!");
            editTextage.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextemail.setError("Please provide valid Email!");
            editTextemail.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextpassword.setError("min password lenght should be 8 character");
            editTextpassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(username, age, email);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register.this, "The Account Has been create !", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(Register.this, Profile.class);
                                        startActivity(i);
                                        finish();

                                    } else {
                                        Toast.makeText(Register.this, "User not Reg", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(Register.this, "User not Reg1", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
