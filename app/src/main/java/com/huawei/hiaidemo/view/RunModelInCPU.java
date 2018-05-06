package com.huawei.hiaidemo.view;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.huawei.hiaidemo.R;
import com.huawei.hiaidemo.adapter.ClassifyAdapter;
import com.huawei.hiaidemo.bean.ClassifyItemModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;
import static com.huawei.hiaidemo.Constant.GALLERY_REQUEST_CODE;
import static com.huawei.hiaidemo.Constant.IMAGE_CAPTURE_REQUEST_CODE;
import static com.huawei.hiaidemo.Constant.RESIZED_HEIGHT;
import static com.huawei.hiaidemo.Constant.RESIZED_WIDTH;
import static com.huawei.hiaidemo.Constant.meanValueOfBlue;
import static com.huawei.hiaidemo.Constant.meanValueOfGreen;
import static com.huawei.hiaidemo.Constant.meanValueOfRed;

public class RunModelInCPU extends AppCompatActivity {

    private static final String TAG = RunModelInCPU.class.getSimpleName();
    private List<ClassifyItemModel> items;

    private RecyclerView rv;

    private String[] predictedClass;

    private Bitmap initClassifiedImg;

    private ClassifyAdapter adapter;

    private Button btnGallery;
    private Button btnCamera;

    private TensorFlowInferenceInterface mInferenceInterface;
    private final String INPUT_NAME ="input";
    private final String OUTPUT_NAME ="InceptionV3/Predictions/Reshape_1";
    private Vector<String> word_label = new Vector<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_classify);
        items = new ArrayList<>();

        mInferenceInterface = new TensorFlowInferenceInterface(getAssets(), "file:///android_asset/inceptionv3_cpu.pb");
        initLabel();
        initView();

    }

    private void initLabel(){
        byte[] labels;
        try {
            InputStream assetsInputStream = getAssets().open("labels.txt");
            int available = assetsInputStream.available();
            labels = new byte[available];
            assetsInputStream.read(labels);
            assetsInputStream.close();
            String words = new String(labels);
            String[] contens = words.split("\n");

            for(String conten:contens){
                word_label.add(conten);
            }
            Log.i(TAG, "initLabel size: " + word_label.size());
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
    }


    private void setHeaderView(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_hewader, view, false);

        btnGallery = header.findViewById(R.id.btn_gallery);
        btnCamera = header.findViewById(R.id.btn_camera);

        adapter.setHeaderView(header);
    }

    private void initView() {
        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);

        adapter = new ClassifyAdapter(items);
        rv.setAdapter(adapter);

        setHeaderView(rv);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStoragePermission();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
            }
        });
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    GALLERY_REQUEST_CODE);
        } else {
            chooseImageAndClassify();
        }
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    IMAGE_CAPTURE_REQUEST_CODE);
        } else {
            takePictureAndClassify();
        }
    }

    private void takePictureAndClassify() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, IMAGE_CAPTURE_REQUEST_CODE);
        }
    }

    private void chooseImageAndClassify() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GALLERY_REQUEST_CODE &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImageAndClassify();
            } else {
                Toast.makeText(RunModelInCPU.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureAndClassify();
            } else {
                Toast.makeText(RunModelInCPU.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) switch (requestCode) {
            case GALLERY_REQUEST_CODE:
                try {
                    Bitmap bitmap;
                    ContentResolver resolver = getContentResolver();
                    Uri originalUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                    cursor.moveToFirst();
                    Bitmap rgba = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                    final Bitmap initClassifiedImg = Bitmap.createScaledBitmap(rgba, RESIZED_WIDTH, RESIZED_HEIGHT, true);

                    new RunModelTask().execute(initClassifiedImg);

                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                }

                break;
            case IMAGE_CAPTURE_REQUEST_CODE:
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Bitmap rgba = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);

                initClassifiedImg = Bitmap.createScaledBitmap(rgba, RESIZED_WIDTH, RESIZED_HEIGHT, true);
                new RunModelTask().execute(initClassifiedImg);
                break;

            default:
                break;
        }
        else {
            Toast.makeText(RunModelInCPU.this,
                    "Return without selecting pictures|Gallery has no pictures|Return without taking pictures", Toast.LENGTH_SHORT).show();
        }

    }

    private String[] runModel(Bitmap inputmap){

        float[] input =  getPixel(inputmap,RESIZED_WIDTH,RESIZED_HEIGHT);

        mInferenceInterface.feed(INPUT_NAME, input, 1,RESIZED_WIDTH,RESIZED_HEIGHT,3);

        long start = System.currentTimeMillis();
        mInferenceInterface.run(new String[]{OUTPUT_NAME});

        long end = System.currentTimeMillis();

        Log.e(TAG, "CPU: run time: " + (end - start) + " ms .");
        long inference_time = end - start;

        float[] output = new float[1001];

        mInferenceInterface.fetch(OUTPUT_NAME, output);

        int max_index[] = {0,0,0};
        double max_num[] = {0,0,0};

        for (int i = 0; i < 1001; i++) {

            double tmp = output[i];
            int tmp_index = i;
            for (int j = 0; j < 3; j++) {
                if (tmp > max_num[j]) {
                    tmp_index += max_index[j];
                    max_index[j] = tmp_index - max_index[j];
                    tmp_index -= max_index[j];
                    tmp += max_num[j];
                    max_num[j] = tmp - max_num[j];
                    tmp -= max_num[j];
                }
            }
        }

        String reuslt1 = word_label.get(max_index[0]) + " - " + max_num[0] * 100 +"%\n";
        String otherreuslt2 = word_label.get(max_index[1]) + " - " + max_num[1] * 100 +"%\n"+
                word_label.get(max_index[2]) + " - " + max_num[2] * 100 +"%\n";
        String inferencetiem ="inference time:" +inference_time+ "ms\n";
        String res[] = {reuslt1,otherreuslt2,inferencetiem};
        return res;
    }

    private class RunModelTask extends AsyncTask<Bitmap, Void, String[]> {

        @Override
        protected String[] doInBackground(Bitmap... bitmaps) {
            Log.i(TAG, "doInBackground: start to execute." );
            initClassifiedImg = bitmaps[0];
            predictedClass = runModel(initClassifiedImg);

            return predictedClass;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);

            for (int i = 0; i < result.length; i++) {
                Log.e(TAG, "onPostExecute: " + result[i]);
            }

            items.add(new ClassifyItemModel(predictedClass[0], predictedClass[1], predictedClass[2], initClassifiedImg));
            adapter.notifyDataSetChanged();
        }
    }

    private float[] getPixel(Bitmap bit,int resizedWidth, int resizedHeight){
        Log.i(TAG,"bit.width " + bit.getWidth() + " bit.height :" + bit.getHeight());
        int[] pixels = new int[resizedWidth*resizedHeight];//保存所有的像素的数组，图片宽×高
        float[] buff = new float[3 * resizedWidth * resizedHeight];
        bit.getPixels(pixels,0,bit.getWidth(),0,0,bit.getWidth(),bit.getHeight());
        for (int i = 0; i < pixels.length; i++) {
            int clr = pixels[i];

            buff[3 * i ] = ((float)red(clr))/255;
            buff[3 * i + 1] = ((float)green(clr))/255;
            buff[3 * i + 2] = ((float)blue(clr))/255;

        }
        return buff;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInferenceInterface.close();
        Log.e(TAG, "CPU run model destory ");
    }

}
