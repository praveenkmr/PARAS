package com.praveen.naregaregistration;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AssignJobActivity extends AppCompatActivity {

    public static final String LOGIN_URL = "http://192.168.0.101:80/android/assignJob.php";
    private EditText editTextAadharNumber;
    private EditText editTextJobID;
    private Button btn;

    private String aadharNumber;
    private String JobID;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_job);
        editTextAadharNumber = (EditText) findViewById(R.id.editTextAadharName);
        editTextJobID = (EditText) findViewById(R.id.editTextJobID);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Assigning the Job.. Please wait..");
        btn = (Button) findViewById(R.id.btnAssignJob);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                assignJob();
            }
        });
    }
    private void assignJob() {
        aadharNumber = editTextAadharNumber.getText().toString().trim();
        JobID = editTextJobID.getText().toString().trim();
        if (aadharNumber.length() == 0 || JobID.length() == 0) {
            Toast.makeText(getApplicationContext(), "No Field can remain empty.", Toast.LENGTH_SHORT).show();
        } else if (aadharNumber.length() != 12) {
            Toast.makeText(getApplicationContext(), "Please Enter a valid Aadhar Number", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            if (response.trim().equals("Successfully Job Assigned")) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
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
                    map.put("Aadhar_Number", aadharNumber);
                    map.put("Job_ID", JobID);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}
