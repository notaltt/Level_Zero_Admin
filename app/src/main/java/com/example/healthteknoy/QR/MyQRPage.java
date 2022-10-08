package com.example.healthteknoy.QR;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthteknoy.R;

public class MyQRPage extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    String[] arraySymptoms = {"FEVER", "COUGH", "SNEEZE", "CHILLS", "COLDS", "DIFFICULTY BREATHING",
                                "MUSCLE PAIN", "SORE THROAT", "LOSS OF TASTE/SMELL", "HEADACHE", "NONE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrpage);

        //list view
        listView = findViewById(R.id.listview_symptoms);

        //adapter for list view
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,
                    arraySymptoms);

        listView.setAdapter(adapter);
    }
}