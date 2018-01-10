package com.praveen.naregaregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

public class OfflineActivity extends AppCompatActivity {

    private EditText editTextSecretCode;
    private String SecretCode;
    private static String Original=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        editTextSecretCode = (EditText) findViewById(R.id.editTextSecretCode);
        SecretCode =editTextSecretCode.getText().toString().trim();
        if(SecretCode.length()==0){
            Toast.makeText(getApplicationContext(),"Please Enter Secret Code",Toast.LENGTH_SHORT).show();
        }
        else if(Original==null){
            Original = SecretCode;
        }
        else if(!SecretCode.equals(Original)){
            Toast.makeText(getApplicationContext(),"Incorrect Secret Code",Toast.LENGTH_SHORT).show();
        }
        else{
            startActivity(new Intent(getApplicationContext(),OfflineAttendance.class));
        }
    }
}
