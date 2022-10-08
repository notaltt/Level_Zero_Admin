package com.example.healthteknoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterForm extends AppCompatActivity {
    private FirebaseAuth regauth;
    private EditText textLastName, textFirstName, textregEmail, textPassword, textConfirmPassword;
    private AppCompatButton buttonCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        regauth = FirebaseAuth.getInstance();

        //EDIT TEXT
        textLastName = (EditText) findViewById(R.id.textLastName);
        textFirstName = (EditText) findViewById(R.id.textFirstName);
        textregEmail = (EditText) findViewById(R.id.textregEmail);
        textPassword = (EditText) findViewById(R.id.textPassword);
        textConfirmPassword = (EditText) findViewById(R.id.textConfirmPassword);

        //BUTTON
        buttonCreate = (AppCompatButton) findViewById(R.id.buttonCreate);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
    }

    private void create() {
        String user = textregEmail.getText().toString().trim();
        String pass = textPassword.getText().toString().trim();
        String confirmPass = textConfirmPassword.getText().toString().trim();

        if (user.isEmpty()){
            textregEmail.setError("!!PLEASE INPUT YOUR EMAIL!!");
        }if (pass.isEmpty()) {
            textPassword.setError("!!PLEASE INPUT YOUR PASSWORD!!");
        }else {
            regauth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterForm.this, "Creating an account is success.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterForm.this, LoginForm.class));
                    }else{
                        Toast.makeText(RegisterForm.this, "Creating an account is failed."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void LOGIN(View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(RegisterForm.this, LoginForm.class));
                finish();
            }
        },500);
    }
}