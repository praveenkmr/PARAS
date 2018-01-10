package com.praveen.naregaregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.praveen.naregaregistration.MainActivity.KEY_AADHAR;

public class AdminActivity extends AppCompatActivity {
    private TextView aadharID;
    private TextView Name;
    private Button addJob;
    private Button addWorker;
    private Button assignJob;
    private Button viewAttendence;
    private String aadharNo;
    private static String LOGIN_URL = "http://192.168.0.101:80/android/fetch_admin_name.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addJob = (Button) findViewById(R.id.addJob);
        addWorker = (Button) findViewById(R.id.addWorker);
        assignJob = (Button) findViewById(R.id.AssignJob);
        viewAttendence = (Button) findViewById(R.id.viewAttendence);
        aadharID = (TextView) findViewById(R.id.textViewAadharNumber);
        Name = (TextView) findViewById(R.id.textViewName);
        Intent intent = getIntent();
        aadharNo = intent.getStringExtra(KEY_AADHAR);
        aadharID.setText("Aadhar Number : " + aadharNo);
        fetch_data();


        addWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddWorkerActivity.class);
                startActivity(intent);
            }
        });

        addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddJobActivity.class);
                startActivity(intent);
            }
        });
        assignJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AssignJobActivity.class);
                startActivity(intent);
            }
        });
        viewAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewAttendenceActivity.class);
                startActivity(intent);
            }
        });

    }



    public void fetch_data() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("failure")) {
                            Name.setText("Name : NULL");
                        } else
                            Name.setText("Name : " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("aadharNo", aadharNo);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}