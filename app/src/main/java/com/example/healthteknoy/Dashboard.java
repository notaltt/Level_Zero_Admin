package com.example.healthteknoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthteknoy.About.AboutPage;
import com.example.healthteknoy.Account.MyAccountPage;
import com.example.healthteknoy.Calendar.CalendarPage;
import com.example.healthteknoy.History.HistoryPage;
import com.example.healthteknoy.QR.MyQRPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private TextView textEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //firebase instances
        firebaseDatabase = FirebaseDatabase.getInstance("https://healthteknoy-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("Email");

        //text view
        textEmail = findViewById(R.id.textEmail);

        getData();
    }

    public void getData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                textEmail.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Dashboard.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openCalendar(View view) {
        Intent intent = new Intent(this, CalendarPage.class);
        startActivity(intent);
    }

    public void openQR(View view) {
        Intent intent = new Intent(this, MyQRPage.class);
        startActivity(intent);
    }

    public void openAccount(View view) {
        Intent intent = new Intent(this, MyAccountPage.class);
        startActivity(intent);
    }

    public void openHistory(View view) {
        Intent intent = new Intent(this, HistoryPage.class);
        startActivity(intent);
    }

    public void openAbout(View view) {
        Intent intent = new Intent(this, AboutPage.class);
        startActivity(intent);
    }

}