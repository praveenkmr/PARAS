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

public class AddJobActivity extends AppCompatActivity {

    public static final String LOGIN_URL = "http://192.168.0.101:80/android/addJob.php";
    private EditText editTextJobName;
    private EditText editTextJobDescription;
    private EditText editTextJobLongitude;
    private EditText editTextJobLatitude;
    private EditText editTextJobRadius;
    private EditText editTextJobRate;
    private Button btnAddJob;
    private ProgressDialog dialog;
    private String jobName;
    private String jobDescription;
    private String jobLongitude;
    private String jobLattitude;
    private String jobRadius;
    private String jobRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        dialog = new ProgressDialog(getApplicationContext());
        editTextJobName = (EditText) findViewById(R.id.editTextJobName);
        editTextJobDescription = (EditText) findViewById(R.id.editTextJobDescription);
        editTextJobLatitude = (EditText) findViewById(R.id.editTextJobLatitute);
        editTextJobLongitude = (EditText) findViewById(R.id.editTextJobLongitude);
        editTextJobRadius = (EditText) findViewById(R.id.editTextJobRadius);
        editTextJobRate = (EditText) findViewById(R.id.editTextJobRate);

        btnAddJob = (Button) findViewById(R.id.btnAddJob);
        btnAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Verifying Credentials. Please wait...");
                addJob();
            }
        });
    }

    private void addJob() {
        jobName = editTextJobName.getText().toString().trim();
        jobDescription = editTextJobDescription.getText().toString().trim();
        jobLattitude = editTextJobLatitude.getText().toString().trim();
        jobLongitude = editTextJobLongitude.getText().toString().trim();
        jobRadius = editTextJobName.getText().toString().trim();
        jobRate = editTextJobRate.getText().toString().trim();
        if (jobName.length() == 0 || jobDescription.length() == 0 || jobLongitude.length() == 0 || jobLattitude.length() == 0 || jobRadius.length() == 0 || jobRate.length() == 0) {
            Toast.makeText(getApplicationContext(), "No Field can remain empty.", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            if (response.trim().equals("Successfully Job Registered")) {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            } else {
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
                    map.put("jobName", jobName);
                    map.put("jobDescription", jobDescription);
                    map.put("jobLattitude", jobLattitude);
                    map.put("jobLongitude", jobLongitude);
                    map.put("jobRadius", jobRadius);
                    map.put("jobRate", jobRate);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}
