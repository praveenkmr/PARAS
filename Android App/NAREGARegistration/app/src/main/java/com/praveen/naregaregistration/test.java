package com.praveen.naregaregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class test extends AppCompatActivity {
    private String AadharNo;
    private String IrisCode;
    private String EntryTime;
    private Button btn;
    private int num;
    private ProgressDialog dialog;
    private static String LOGIN_URL = "http://192.168.0.101:80/android/verifyWorker.php";
    private static String LOGIN_URL_1 = "http://192.168.0.101:80/android/attendenceUpdate.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Verificating Details");
        AadharNo = intent.getStringExtra("Aadhar_No");
        IrisCode = intent.getStringExtra("Iris_Code");
        EntryTime = intent.getStringExtra("EntryType");
        dialog.show();
        verifyData();
        btn = (Button) findViewById(R.id.skip);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Updating Attendance..");
                dialog.show();
                updateAttendance();
            }
        });
    }

    void verifyData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        if (response.equals("success")) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            //updateAttendance();
                        } else {
                            Toast.makeText(getApplicationContext(), "Verification Failed...", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("aadharID", AadharNo);
                map.put("IrisCode", IrisCode);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    void updateAttendance(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL_1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        if (response.trim().equals("Success")) {
                            Toast.makeText(getApplicationContext(),"Successfully authenticated",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"Failure to authenticate",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("aadharID",AadharNo);
                map.put("time",EntryTime);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}