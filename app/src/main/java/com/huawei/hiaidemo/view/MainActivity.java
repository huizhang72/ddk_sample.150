package com.huawei.hiaidemo.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.huawei.hiaidemo.ModelManager;
import com.huawei.hiaidemo.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnSync;
    private Button btnAsync;
    private boolean useNPU = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //Compatibility Process By get HiAI DDK version.
        String platformversion = getProperty("ro.config.hiaiversion","kirin960");
        Log.i(TAG, "platformversion : " + platformversion);

        if(platformversion.equals("") || platformversion.equals("000.000.000.000")){
            useNPU = false;
        }else{
            useNPU = true;
            /** load libhiai.so */
            boolean isSoLoadSuccess = ModelManager.init();
            if (isSoLoadSuccess) {
                Toast.makeText(this, "load libhiai.so success.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "load libhiai.so fail.", Toast.LENGTH_SHORT).show();
            }
             /*init classify labels */
            initLabels();
        }

        btnSync = (Button) findViewById(R.id.btn_sync);
        btnAsync = (Button) findViewById(R.id.btn_async);

        btnSync.setOnClickListener(this);
        btnAsync.setOnClickListener(this);
    }

    private String getProperty(String key, String defaultvalue){
        String value = defaultvalue;
        try{
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            value = (String) (get.invoke(c,key));
            Log.i(TAG, "original verison is : " + value);

            if(value == null){
                value = "";
            }
        }catch (Exception e){
            Log.e(TAG, "error info : " + e.getMessage());
            value = "";
        }finally {
            return value;
        }
    }

    private void initLabels() {
        byte[] labels;
        try {
            InputStream assetsInputStream = getAssets().open("labels.txt");
            int available = assetsInputStream.available();
            labels = new byte[available];
            assetsInputStream.read(labels);
            assetsInputStream.close();
            ModelManager.initLabels(labels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
      //switch (v.getId()) {
      if (v.getId() == R.id.btn_sync) {
                if(useNPU) {
                    startActivity(new Intent(MainActivity.this, SyncClassifyActivity.class));
                }else {
                    startActivity(new Intent(MainActivity.this, RunModelInCPU.class));
                    Toast.makeText(this, "Your system is not support NPU, Now run model on CPU", Toast.LENGTH_SHORT).show();
                }
                // break;
      } else if (v.getId() == R.id.btn_async) {
                if(useNPU) {
                    startActivity(new Intent(MainActivity.this, AsyncClassifyActivity.class));
                }else {
                    startActivity(new Intent(MainActivity.this, RunModelInCPU.class));
                    Toast.makeText(this, "Your system is not support NPU, Now run model on CPU", Toast.LENGTH_SHORT).show();
                }
                // break;
      }
    }
}
