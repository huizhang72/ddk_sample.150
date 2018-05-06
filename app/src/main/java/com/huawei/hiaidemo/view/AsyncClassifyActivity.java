package com.huawei.hiaidemo.view;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.huawei.hiaidemo.ModelManagerListener;
import com.huawei.hiaidemo.R;
import com.huawei.hiaidemo.adapter.ClassifyAdapter;
import com.huawei.hiaidemo.bean.ClassifyItemModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class AsyncClassifyActivity extends AppCompatActivity {

    private static final String TAG = AsyncClassifyActivity.class.getSimpleName();

    private List<ClassifyItemModel> items;

    private RecyclerView rv;

    private AssetManager mgr;

    private Bitmap show;

    private ClassifyAdapter adapter;

    private Button btnGallery;
    private Button btnCamera;

    ModelManagerListener listener = new ModelManagerListener() {

        @Override
        public void onStartDone(final int taskId) {
            Log.e(TAG, " java layer onStartDone: " + taskId);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (taskId > 0) {
                        Toast.makeText(AsyncClassifyActivity.this, "load model success. taskId is:" + taskId, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AsyncClassifyActivity.this, "load model fail. taskId is:" + taskId, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onRunDone(final int taskId, final String[] output) {

            for (int i = 0; i < output.length; i++) {
                Log.e(TAG, "java layer onRunDone: output[" + i + "]:" + output[i]);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (taskId > 0) {
                        Toast toast = Toast.makeText(AsyncClassifyActivity.this, "run model success. taskId is:" + taskId, Toast.LENGTH_SHORT);
                        CustomToast.showToast(toast, 50);
                    } else {
                        Toast toast = Toast.makeText(AsyncClassifyActivity.this, "run model fail. taskId is:" + taskId, Toast.LENGTH_SHORT);
                        CustomToast.showToast(toast, 50);
                    }


                    items.add(new ClassifyItemModel(output[0], output[1], output[2], show));

                    adapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onStopDone(final int taskId) {
            Log.e(TAG, "java layer onStopDone: " + taskId);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (taskId > 0) {
                        Toast.makeText(AsyncClassifyActivity.this, "unload model success. taskId is:" + taskId, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AsyncClassifyActivity.this, "unload model fail. taskId is:" + taskId, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onTimeout(final int taskId) {
            Log.e(TAG, "java layer onTimeout: " + taskId);
        }

        @Override
        public void onError(final int taskId, final int errCode) {
            Log.e(TAG, "onError:" + taskId + " errCode:" + errCode);
        }

        @Override
        public void onServiceDied() {
            Log.e(TAG, "onServiceDied: ");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_async_classify);

        mgr = getResources().getAssets();

        int ret = ModelManager.registerListenerJNI(listener);

        Log.e(TAG, "onCreate: " + ret);

        ModelManager.loadModelAsync("InceptionV3", mgr);

        items = new ArrayList<>();

        mgr = getResources().getAssets();

        initView();
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GALLERY_REQUEST_CODE &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImageAndClassify();
            } else {
                Toast.makeText(AsyncClassifyActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureAndClassify();
            } else {
                Toast.makeText(AsyncClassifyActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
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
                    final Bitmap initClassifiedImg = Bitmap.createScaledBitmap(rgba, RESIZED_WIDTH, RESIZED_HEIGHT, false);

                    final float[] pixels = getPixel(initClassifiedImg, RESIZED_WIDTH, RESIZED_HEIGHT);

                    show = initClassifiedImg;
                    ModelManager.runModelAsync("InceptionV3", pixels);

                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                }

                break;
            case IMAGE_CAPTURE_REQUEST_CODE:
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Bitmap rgba = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);

                Bitmap initClassifiedImg = Bitmap.createScaledBitmap(rgba, RESIZED_WIDTH, RESIZED_HEIGHT, false);

                final float[] pixels = getPixel(initClassifiedImg, RESIZED_WIDTH, RESIZED_HEIGHT);

                ModelManager.runModelAsync("InceptionV3", pixels);

                show = initClassifiedImg;
                break;

            default:
                break;
        }
        else {
            Toast.makeText(AsyncClassifyActivity.this,
                    "Return without selecting pictures|Gallery has no pictures|Return without taking pictures", Toast.LENGTH_SHORT).show();
        }

    }

    private float[] getPixel(Bitmap bitmap, int resizedWidth, int resizedHeight) {
        int channel = 3;
        float[] buff = new float[channel * resizedWidth * resizedHeight];

        int rIndex, gIndex, bIndex;
        for (int i = 0; i < resizedHeight; i++) {
            for (int j = 0; j < resizedWidth; j++) {
                bIndex = i * resizedWidth + j;
                gIndex = bIndex + resizedWidth * resizedHeight;
                rIndex = gIndex + resizedWidth * resizedHeight;

                int color = bitmap.getPixel(j, i);

                buff[bIndex] = (float) ((blue(color) - meanValueOfBlue))/255;
                buff[gIndex] = (float) ((green(color) - meanValueOfGreen))/255;
                buff[rIndex] = (float) ((red(color) - meanValueOfRed))/255;
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
        ModelManager.unloadModelAsync();
    }
}
