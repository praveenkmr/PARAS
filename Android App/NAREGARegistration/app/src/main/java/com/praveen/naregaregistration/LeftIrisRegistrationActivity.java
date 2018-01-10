package com.praveen.naregaregistration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.biomatiques.Api.Biomatiques;
import com.biomatiques.Api.BiomatiquesException;
import com.biomatiques.Api.IrisCapture;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;


public class LeftIrisRegistrationActivity extends AppCompatActivity implements IrisCapture {
    private ImageView image;

    private Button startBtn;
    private Button stopBtn;

    private BitmapWorkerTask task;
    private Bitmap PreviewBmp = null;
    private long capTimeOut;
    private long startTime;

    private int Position= 1;
    private int capKind = 7;

    private String Logtag = "Test";
    private static ShowText tst;
    private static Biomatiques BioObj;
    private String IrisCode;
    private String WorkerName;
    private String WorkerAadharNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_iris_registration);

        image = (ImageView) findViewById(R.id.imageView1);
        startBtn = (Button) findViewById(R.id.startBtn);
        stopBtn = (Button) findViewById(R.id.stopBtn);

        Intent intent = getIntent();
        WorkerAadharNo = intent.getStringExtra("Worker_Aadhar");
        WorkerName = intent.getStringExtra("Worker_Name");
        try {
            BioObj= new Biomatiques(this);
            if (BioObj != null) {
                if (BioObj.IsDeviceConnected()) {
                    if (BioObj.InitializeDevice()) {
                        Toast.makeText(getApplicationContext(),"Device Initialization Completed ... ",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Device Initialization Failed ... ",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Device Not Connected ... ",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getApplicationContext(),"Failed to initialize object ... ",Toast.LENGTH_SHORT).show();
        } catch (BiomatiquesException e) {
            e.printStackTrace();
        }
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeginCapture();

            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BioObj.StopAutoCapture();
                    BioObj.stopPreview();
                } catch (BiomatiquesException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    public void onDestroy() {

        try {
            BioObj.StopAutoCapture();
            BioObj.stopPreview();
            BioObj.FinalizeDevice();
        } catch (BiomatiquesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.onDestroy();
    }


    class BitmapWorkerTask extends AsyncTask<Bitmap, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            Bitmap b = params[0];
            return b;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    @Override
    public void OnCaptureCompleted(byte[] bytes){
        //IrisCode = Base64.encodeToString(bytes, Base64.DEFAULT );
        IrisCode = String.valueOf(bytes);
        tst = new ShowText();
        tst.execute("Capture Completed ");
        //onDestroy();
        try {
            BioObj.StopAutoCapture();
            BioObj.stopPreview();
            BioObj.FinalizeDevice();
        } catch (BiomatiquesException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(),RightIrisRegistrationActivity.class);
        intent.putExtra("Worker_Aadhar",WorkerAadharNo);
        intent.putExtra("Worker_Name",WorkerName);
        intent.putExtra("Worker_left_Iris",IrisCode);
        startActivity(intent);
        bytes = null;
    }

    @Override
    public void OnCaptureCompletedImage(byte[] arr, int kind, int width, int height, int[] Points) {
        try {
            task = new BitmapWorkerTask(image);
            Bitmap bm = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            task.execute(bm);
        } catch (Exception ex) {

        }

    }

    @Override
    public void OnFrameRecieve(byte[] arr, int width, int height) {
        task = new BitmapWorkerTask(image);
        if (PreviewBmp == null) {
            PreviewBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        PreviewBmp.copyPixelsFromBuffer(ByteBuffer.wrap(arr));
        task.execute(PreviewBmp);
        arr = null;
    }

    @Override
    public void OnTimeOut() {
        tst = new ShowText();
        tst.execute("Capture Time Out");
    }

    @Override
    public void OnError(int i, String s) {
        tst = new ShowText();
        tst.execute("restart Capture Process");
    }

    class ShowText extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String res = params[0];
            return res;
        }

        @Override
        protected void onPostExecute(String res) {
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
        }
    }
    int BeginCapture() {
        int retval = 0;
        try {
            BioObj.StartPreview(this);
            capTimeOut = 9 * 1000;
            startTime = System.currentTimeMillis();
            BioObj.StartAutoCapture(capKind, Position, true, 10 * 1000);
        } catch (Exception ex) {
            Log.i(Logtag, " Biomatiques Exception Begin Capture " + ex.toString());
        }
        return retval;
    }
}