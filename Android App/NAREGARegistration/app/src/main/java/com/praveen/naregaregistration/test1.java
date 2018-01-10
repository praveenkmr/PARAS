package com.praveen.naregaregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
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

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class test1 extends AppCompatActivity {
    private String AadharNo;
    private String IrisCode;
    private String EntryType;
    private Button btn;
    private int num;
    private ProgressDialog dialog;
    private String inserQuery;
    private String jsonArrayString;
    private SQLiteDatabase dbase;
    private String updateQuery;
    private static String LOGIN_URL = "http://192.168.0.101:80/android/getOfflineAttendance.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbase=openOrCreateDatabase("mydb", Context.MODE_PRIVATE,null);
        dbase.execSQL("create table if not exists worker_table(s_no number, UDAadharNumber varchar(12),WorkerInTime varchar(100),WorkerOutTime varchar(100)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Verificating Details");
        AadharNo = intent.getStringExtra("Aadhar_No");
        IrisCode = intent.getStringExtra("Iris_Code");
        EntryType = intent.getStringExtra("EntryType");
        dialog.show();
        updateOffline();
    }
    void updateOffline(){
        if(EntryType.equals("1")){
            insert();
        }
        else if(EntryType.equals("2"))
            update();
        else
            Toast.makeText(getApplicationContext(), "Please select Entry or Exit Time..", Toast.LENGTH_SHORT).show();
    }
    public void insert()
    {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        inserQuery="insert into worker_table(UDAadharNumber, WorkerInTime, WorkerOutTime)values('"+AadharNo+"',"+"'"+currentDateTimeString+"'"+","+"'"+""+"'"+")";
        dbase.execSQL(inserQuery);
        Toast.makeText(getApplicationContext(),"Sucessfully Insert",Toast.LENGTH_LONG).show();

    }
    public void read()
    {
        Cursor c=dbase.query("worker_table",null,null,null,null,null,null);
        JSONObject obj;
        JSONArray jsonArray=new JSONArray();
        while (c.moveToNext())
        {
            obj=new JSONObject();
            try {
                obj.put("s_no",c.getInt(0));
                obj.put("UDAadharNumber",c.getString(1));
                obj.put("WorkerInTime",c.getString(2));
                obj.put("WorkerOutTime",c.getString(3));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(obj);

            //Toast.makeText(getApplicationContext(),c.getInt(0)+"\n"+c.getString(1)+"\n"+c.getString(2)+"\n"+c.getString(3)+"\n",Toast.LENGTH_LONG).show();
        }
        jsonArrayString=jsonArray.toString();
        //Toast.makeText(this,jsonArrayString,Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("JsonValue", jsonArrayString);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void update()
    {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        updateQuery="update worker_table set WorkerOutTime='"+currentDateTimeString+"'where id="+AadharNo;
        //Toast.makeText(this,updateQuery,Toast.LENGTH_LONG).show();
        dbase.execSQL(updateQuery);
        Toast.makeText(getApplicationContext(),"Sucessfully Updated",Toast.LENGTH_LONG).show();
    }
}