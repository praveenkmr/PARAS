package com.praveen.naregaregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class WorkerRegistration extends AppCompatActivity {

    private ProgressDialog dialog;
    private static String LOGIN_URL = "http://192.168.0.101:80/android/addWorker.php";

    private String AadharNumber;
    private String Name;
    private String Left_iris;
    private String Right_iris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_registration);
        Intent intent = getIntent();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Adding Details to database");
        AadharNumber = intent.getStringExtra("Worker_Aadhar");
        Name = intent.getStringExtra("Worker_Name");
        Left_iris = intent.getStringExtra("Left_Iris");
        Right_iris = intent.getStringExtra("Right_Iris");
        insertData();
    }

    void insertData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        if (response.equals("success")) {
                            Toast.makeText(getApplicationContext(), "Successful worker Added..", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Unable To add Worker..", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("aadharID", AadharNumber);
                map.put("Name", Name);
                map.put("Left_Iris", Left_iris);
                map.put("Right_Iris", Right_iris);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
