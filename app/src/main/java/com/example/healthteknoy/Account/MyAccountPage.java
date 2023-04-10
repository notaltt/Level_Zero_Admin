package com.example.healthteknoy.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.healthteknoy.R;
import com.example.healthteknoy.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyAccountPage extends AppCompatActivity {

    AppCompatButton saveButton, updateButton;
    private EditText textFirst, textLast, textMiddle, textEmail2, textAddress, textPhone;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    String[] arrayGender = {"MALE", "FEMALE"};
    DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_page);

        root = FirebaseDatabase.getInstance("https://healthteknoy-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        autoCompleteTextView = findViewById(R.id.autoComplete);
        adapter = new ArrayAdapter<String>(this, R.layout.list_gender,arrayGender);
        autoCompleteTextView.setAdapter(adapter);

        textFirst = findViewById(R.id.textFirst);
        textLast = findViewById(R.id.textLast);
        textMiddle = findViewById(R.id.textMiddle);
        textEmail2 = findViewById(R.id.textEmail2);
        textAddress = findViewById(R.id.textAddress);
        textPhone = findViewById(R.id.textPhone);

        saveButton = findViewById(R.id.saveButton);
        updateButton = findViewById(R.id.updateButton);

        showAllUserData();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        String lastName = intent.getStringExtra("lastName");
        String firstName = intent.getStringExtra("firstName");
        String classification = intent.getStringExtra("classification");
        String middleName = intent.getStringExtra("middleInitial");
        String phoneNumber = intent.getStringExtra("phoneNumber");
        String address = intent.getStringExtra("address");
        String email = intent.getStringExtra("email");

        textFirst.setText(firstName);
        textLast.setText(lastName);
        textEmail2.setText(email);
        textPhone.setText(phoneNumber);
    }


    private void saveUser() {
        User user = new User(textFirst.getText().toString(), textLast.getText().toString(),
                            textMiddle.getText().toString(), autoCompleteTextView.getText().toString(),
                            textAddress.getText().toString(), textEmail2.getText().toString(), Integer.parseInt(textPhone.getText().toString()));

        root.child("User").child(String.valueOf(user.getPhoneNumber())).setValue(user);
    }

}