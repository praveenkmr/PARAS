package com.praveen.naregaregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobSelectionActivity extends AppCompatActivity {
    public static final String LOGIN_URL = "http://192.168.0.101:80/android/jobList.php";
    private RecyclerView recyclerView;
    private TextView aadharTV, JobAssignTV;
    private List<ListItem> listItems;
    private DataAdaptor adaptor;
    private String ClientAadharNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_selection);
        Drawable Background = findViewById(R.id.activity_job_selection).getBackground();
        Background.setAlpha(97);
        Intent intent = getIntent();
        ClientAadharNumber = intent.getStringExtra(MainActivity.KEY_AADHAR);
        recyclerView = (RecyclerView) findViewById(R.id.rec_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        aadharTV = (TextView) findViewById(R.id.textViewUserAadhar);
        aadharTV.setText("");
        aadharTV.setText("Aadhar Card : " + ClientAadharNumber);
        loadRecyclerViewData();
    }
    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loadind Data");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    listItems = new ArrayList<>();
                    Log.i("tagconvertstr", "[" + response + "]");
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("result");
                    JobAssignTV = (TextView) findViewById(R.id.textViewJbAssigned);
                    JobAssignTV.setText("");
                    JobAssignTV.setText("Job Assigned : " + array.length());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        ListItem item = new ListItem();
                        item.setClientAdhar(ClientAadharNumber);
                        item.setJobID(o.getString("JobID"));
                        item.setJobname(o.getString("JobName"));
                        item.setJobDetails(o.getString("JobDetails"));
                        item.setJobLattitude(o.getDouble("JobLattitude"));
                        item.setJobLongitude(o.getDouble("JobLongitude"));
                        listItems.add(item);
                    }
                    adaptor = new DataAdaptor(listItems, getApplicationContext());
                    recyclerView.setAdapter(adaptor);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("aadharNumber", ClientAadharNumber);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
