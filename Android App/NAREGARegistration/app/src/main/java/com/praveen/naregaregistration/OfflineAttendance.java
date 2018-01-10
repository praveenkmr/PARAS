package com.praveen.naregaregistration;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class OfflineAttendance extends AppCompatActivity {

    private TextView textViewJobId;
    private TextView textViewJobDate;
    private TextView textViewAadharNumber;
    private String ClientAadharNumber;
    private String WorkerAadharNumber;
    private String JobId;
    private String EntryType;
    private EditText AadharNumber;
    private Button btn;
    private RadioGroup radioGroup;
    private RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_attendance);
        btn = (Button) findViewById(R.id.btn);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                AadharNumber = (EditText) findViewById(R.id.aadharNo);
                WorkerAadharNumber = AadharNumber.getText().toString().trim();
                EntryType = radioButton.getText().toString().trim();
                if(EntryType.equals("Entry Time"))
                    EntryType = "1";
                else
                    EntryType = "2";
                if(WorkerAadharNumber.length()!=12) {
                    Toast.makeText(getApplicationContext(), "Please Enter a valid Aadhar Number..", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),IrisActivity1.class);
                    intent.putExtra("Worker_Aadhar_No",WorkerAadharNumber);
                    intent.putExtra("EntryType",EntryType);
                    startActivity(intent);
                }
            }
        });
    }


}
