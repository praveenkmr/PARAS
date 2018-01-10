package com.praveen.naregaregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class SignUpActivity extends AppCompatActivity {
    public static final String LOGIN_URL = "http://192.168.0.101:80/android/createUser.php";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_AAdharID = "aadharID";
    public static final String KEY_PASSWORD = "password";
    private EditText aadharID, name, password;
    private Button btn;
    private ProgressDialog dialog;
    private Spinner userSpinner;
    private String user_name;
    private String user_password;
    private String user_aadhar;
    private String user_type;
    private String[] userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        aadharID = (EditText) findViewById(R.id.aadharID);
        name = (EditText) findViewById(R.id.nameET);
        password = (EditText) findViewById(R.id.code);
        btn = (Button) findViewById(R.id.button);
        dialog = new ProgressDialog(SignUpActivity.this);
        userSpinner = (Spinner) findViewById(R.id.userSpinner);
        this.userType = new String[]{"Account Type", "Administrator", "Supervisor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, userType);
        userSpinner.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Verifying Credentials. Please wait...");
                loginUser();
            }
        });
    }

    private void loginUser() {
        user_name = name.getText().toString().trim();
        user_password = password.getText().toString().trim();
        user_aadhar = aadharID.getText().toString().trim();
        user_type = userSpinner.getSelectedItem().toString().trim();
        if(user_type.equals("Administrator"))
            user_type = "1";
        else
            user_type= "0";
        //Toast.makeText(getApplicationContext(),"User Type : "+ user_type, Toast.LENGTH_SHORT).show();
        if (user_aadhar.length() == 0 || user_password.length() == 0 || user_name.length() == 0) {
            Toast.makeText(SignUpActivity.this, "No Field can remain empty.", Toast.LENGTH_SHORT).show();
        } else if (user_type.equals("Account Type")) {
            Toast.makeText(getApplicationContext(), "Select Account Type", Toast.LENGTH_SHORT).show();
        } else if (user_aadhar.length() != 12) {
            Toast.makeText(SignUpActivity.this, "AADHAAR Number must be of 12 digit", Toast.LENGTH_SHORT).show();
        } else {
            dialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            if (response.trim().equals("success")) {
                                Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(KEY_USERNAME, user_name);
                    map.put(KEY_PASSWORD, user_password);
                    map.put(KEY_AAdharID, user_aadhar);
                    map.put("userType", user_type);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}
