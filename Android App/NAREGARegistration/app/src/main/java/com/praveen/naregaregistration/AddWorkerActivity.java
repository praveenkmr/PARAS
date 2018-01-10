package com.praveen.naregaregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddWorkerActivity extends AppCompatActivity {

    private String AadharNo;
    private String Name;

    private EditText editTextWorkerAadhar;
    private EditText editTextWorkerName;
    private Button leftIris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);

        editTextWorkerAadhar = (EditText) findViewById(R.id.editTextWorkerAAdhar);
        editTextWorkerName = (EditText) findViewById(R.id.editTextWorkerName);

        leftIris = (Button) findViewById(R.id.btnLeftIris);
        leftIris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AadharNo = editTextWorkerAadhar.getText().toString();
                Name = editTextWorkerName.getText().toString();
                Intent intent = new Intent(getApplicationContext(),LeftIrisRegistrationActivity.class);
                intent.putExtra("Worker_Aadhar",AadharNo);
                intent.putExtra("Worker_Name",Name);
                startActivity(intent);
            }
        });

    }
}
