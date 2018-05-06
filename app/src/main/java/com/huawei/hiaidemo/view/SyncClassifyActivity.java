package com.huawei.hiaidemo.view;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.huawei.hiaidemo.ModelManager;
import com.huawei.hiaidemo.R;
import com.huawei.hiaidemo.adapter.ClassifyAdapter;
import com.huawei.hiaidemo.bean.ClassifyItemModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;
import static com.huawei.hiaidemo.Constant.AI_OK;
import static com.huawei.hiaidemo.Constant.GALLERY_REQUEST_CODE;
import static com.huawei.hiaidemo.Constant.IMAGE_CAPTURE_REQUEST_CODE;
import static com.huawei.hiaidemo.Constant.RESIZED_HEIGHT;
import static com.huawei.hiaidemo.Constant.RESIZED_WIDTH;
import static com.huawei.hiaidemo.Constant.meanValueOfBlue;
import static com.huawei.hiaidemo.Constant.meanValueOfGreen;
import static com.huawei.hiaidemo.Constant.meanValueOfRed;

public class SyncClassifyActivity extends AppCompatActivity {

    private static final String TAG = SyncClassifyActivity.class.getSimpleName();
    private List<ClassifyItemModel> items;

    private RecyclerView rv;

    private AssetManager mgr;

    private String[] predictedClass;

    private Bitmap initClassifiedImg;

    private ClassifyAdapter adapter;

    private Button btnGallery;
    private Button btnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sync_classify);

        items = new ArrayList<>();

        mgr = getResources().getAssets();

        initView();

        new loadModelTask().execute();
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
        //manager.setStackFromEnd(true);
        //manager.setReverseLayout(true);
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

    private class loadModelTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {

            int ret = ModelManager.loadModelSync("InceptionV3", mgr);

            return ret;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (AI_OK == result) {
                Toast.makeText(SyncClassifyActivity.this,
                        "load model success.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SyncClassifyActivity.this,
                        "load model fail.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class RunModelTask extends AsyncTask<Bitmap, Void, String[]> {

        @Override
        protected String[] doInBackground(Bitmap... bitmaps) {
            float[] buffer = getPixel(bitmaps[0], RESIZED_WIDTH, RESIZED_HEIGHT);
            initClassifiedImg = bitmaps[0];
            predictedClass = ModelManager.runModelSync("InceptionV3", buffer);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GALLERY_REQUEST_CODE &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImageAndClassify();
            } else {
                Toast.makeText(SyncClassifyActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureAndClassify();
            } else {
                Toast.makeText(SyncClassifyActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
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
            Toast.makeText(SyncClassifyActivity.this,
                    "Return without selecting pictures|Gallery has no pictures|Return without taking pictures", Toast.LENGTH_SHORT).show();
        }

    }


    private float[] getPixel(Bitmap bitmap, int resizedWidth, int resizedHeight) {
        int channel = 3;
        float[] buff = new float[channel * resizedWidth * resizedHeight];

        int rIndex, gIndex, bIndex;
        int k = 0;
        for (int i = 0; i < resizedHeight; i++) {
            for (int j = 0; j < resizedWidth; j++) {
                bIndex = i * resizedWidth + j;
                gIndex = bIndex + resizedWidth * resizedHeight;
                rIndex = gIndex + resizedWidth * resizedHeight;

                int color = bitmap.getPixel(j, i);

                buff[rIndex] = (float) ((red(color) - meanValueOfRed))/255;
                buff[gIndex] = (float) ((green(color) - meanValueOfGreen))/255;
                buff[bIndex] = (float) ((blue(color) - meanValueOfBlue))/255;

            }
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
        int result = ModelManager.unloadModelSync();

        if (AI_OK == result) {
            Toast.makeText(this, "unload model success.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "unload model fail.", Toast.LENGTH_SHORT).show();
        }
    }
}
