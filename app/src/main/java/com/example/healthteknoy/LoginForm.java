package com.example.healthteknoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginForm extends AppCompatActivity {
    TextView clickNewAccount;
    private AppCompatButton loginButton;
    private EditText email, password;
    private FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        mauth = FirebaseAuth.getInstance();
        //TEXT VIEWS
        clickNewAccount = (TextView) findViewById(R.id.clickNewAccount);

        //BUTTON
        loginButton = (AppCompatButton) findViewById(R.id.loginButton);

        //EDIT TEXT
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void createNewAccount(View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoginForm.this, RegisterForm.class));
                finish();
            }
        },500);
    }

    private void login(){
        String user = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (user.isEmpty()){
            email.setError("!!PLEASE INPUT YOUR EMAIL!!");
        }if (pass.isEmpty()){
            password.setError("!!PLEASE INPUT YOUR PASSWORD!!");
        }else {
            mauth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginForm.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginForm.this, Dashboard.class));
                    }else{
                        Toast.makeText(LoginForm.this, "Login Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}