package com.praveen.naregaregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    public static final String LOGIN_URL = "http://192.168.0.101:80/android/userLogin.php";
    public static final String KEY_AADHAR = "aadharID";
    public static final String KEY_PASSWORD = "password";
    private EditText aadharNumberET;
    private EditText passwordET;
    private TextView newAccount;
    private Button loginbtn;
    private ProgressDialog dialog;
    private String user_aadhar;
    private String user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(MainActivity.this);
        aadharNumberET = (EditText) findViewById(R.id.input_aadhar);
        passwordET = (EditText) findViewById(R.id.input_password);
        loginbtn = (Button) findViewById(R.id.loginbtn);
        newAccount = (TextView) findViewById(R.id.newaccountTV);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Verifying Credentials. Please wait...");
                user_aadhar = aadharNumberET.getText().toString().trim();
                user_password = passwordET.getText().toString().trim();
                if (user_aadhar.length() == 0 || user_password.length() == 0) {
                    Toast.makeText(MainActivity.this, "AADHAAR Number or Password can't be Empty.", Toast.LENGTH_SHORT).show();
                } else if (user_aadhar.length() != 12) {
                    Toast.makeText(MainActivity.this, "AADHAAR Number must be of 12 digit", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    check_credential();
                }
            }
        });
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
    }

    public void check_credential() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        String result = response.trim();
                        if (result.equals("success0")) {
                            dialog.dismiss();
                            openUserProfile();
                        } else if (result.equals("success1")) {
                            dialog.dismiss();
                            openAdminProfile();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Error : "+ error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_AADHAR, user_aadhar);
                map.put(KEY_PASSWORD, user_password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openUserProfile() {
        Intent intent = new Intent(this, JobSelectionActivity.class);
        intent.putExtra(KEY_AADHAR, user_aadhar);
        startActivity(intent);
    }

    private void openAdminProfile() {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra(KEY_AADHAR, user_aadhar);
        startActivity(intent);
    }
}
