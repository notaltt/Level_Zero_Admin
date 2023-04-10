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

import com.example.healthteknoy.Account.MyAccountPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginForm extends AppCompatActivity {
    TextView clickNewAccount;
    private AppCompatButton loginButton;
    private EditText email, password;
    private FirebaseAuth mauth;
    DatabaseReference root;
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

    /*private void login(){
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
    }*/

    private void login(){
        String user = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        root = FirebaseDatabase.getInstance("https://healthteknoy-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        Query checkUser = root.orderByChild("email").equalTo(user);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                     email.setError(null);
                    String passwordDB = snapshot.child(user).child("password").getValue(String.class);
                    if(passwordDB.equals(pass)){
                        String firstNameDB = snapshot.child(user).child("firstName").getValue(String.class);
                        String lastNameDB = snapshot.child(user).child("lirstName").getValue(String.class);
                        String emailDB = snapshot.child(user).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), MyAccountPage.class);
                        Intent intentDash = new Intent(getApplicationContext(), Dashboard.class);

                        intent.putExtra("firstName", firstNameDB);
                        intent.putExtra("lastName", lastNameDB);
                        intent.putExtra("email", emailDB);

                        intentDash.putExtra("email", emailDB);
                        startActivity(intent);
                        startActivity(intentDash);
                    }else{
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                } else {
                    email.setError("No such Email exists");
                    email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}