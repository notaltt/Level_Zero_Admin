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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterForm extends AppCompatActivity {
    DatabaseReference root;
    private FirebaseAuth regauth;
    private EditText textLastName, textFirstName, textregEmail, textPassword, textConfirmPassword;
    private AppCompatButton buttonCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        regauth = FirebaseAuth.getInstance();

        root = FirebaseDatabase.getInstance("https://healthteknoy-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

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
        String lastName = textLastName.getText().toString();
        String firstName = textFirstName.getText().toString();
        String regEmail = textregEmail.getText().toString();
        String password = textPassword.getText().toString();
        String confirmPassword = textConfirmPassword.getText().toString();

        if(regEmail.isEmpty() || password.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || confirmPassword.isEmpty()){
            if(regEmail.isEmpty()){
                textregEmail.setError("Input your Email");
            }if(password.isEmpty()){
                textPassword.setError("Input your Password");
            }if(lastName.isEmpty()){
                textLastName.setError("Input your Last Name");
            }if(firstName.isEmpty()){
                textFirstName.setError("Input your First Name");
            }if(confirmPassword.isEmpty()){
                textConfirmPassword.setError("Input your Confirm Password");
            }
            Toast.makeText(RegisterForm.this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show();
        } else {
            if(password.equals(confirmPassword)){
                regauth.createUserWithEmailAndPassword(regEmail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(firstName, lastName, regEmail, password);
                            root.child("User").child(firstName).setValue(user);;
                            Toast.makeText(RegisterForm.this, "Creating an account is success.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterForm.this, LoginForm.class));
                        }else{
                            Toast.makeText(RegisterForm.this, "Creating an account is failed."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(RegisterForm.this, "Password mismatch.", Toast.LENGTH_SHORT).show();
            }
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