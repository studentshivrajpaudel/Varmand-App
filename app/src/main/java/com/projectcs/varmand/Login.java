package com.projectcs.varmand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText  editTextemail, editTextpassword ;
    private TextView loginbtn1;
    private MaterialButton loginbtn;
    private TextView registerbtn;
    private FirebaseAuth mh_Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextemail=findViewById(R.id.email);
        editTextpassword=findViewById(R.id.password);
        loginbtn=findViewById(R.id.loginbtn);
        registerbtn=findViewById(R.id.registerbtn);
        mh_Auth= FirebaseAuth.getInstance();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =editTextemail.getText().toString();
                String Password_1 = editTextpassword.getText().toString();
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(Password_1)) {
                    Toast.makeText(Login.this, "Please Add Your Credentials", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mh_Auth.signInWithEmailAndPassword(email, Password_1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "User Successfully Log In..", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Login.this, Feed.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Fail To Login", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }
            }
        });
    }
    }