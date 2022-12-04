package com.tni.phitchaya.sdk_authentication_th_java;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.tni.phitchaya.cameraIDcardth.CameraActivity;
import com.tni.phitchaya.cameraIDcardth.CameraPreview;
import com.tni.phitchaya.sdk_authentication_th_java.POJO.POJO_IDcard;
import com.tni.phitchaya.sdk_authentication_th_java.Retrofit.NetworkConnectionManager;
import com.tni.phitchaya.sdk_authentication_th_java.Retrofit.OnNetworkCallback_IDCard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class Camera_IDcard_Activity extends AppCompatActivity {

    TextView fullname , number , Date;


    MultipartBody.Part body;
    ImageView imageView;
    Button Send_data , Next , Open_cam;

    TextView tv_name_part , tv_name_ans , tv_num_part ,tv_num_ans ,tv_status_text;
    LinearLayout part;
    RequestBody name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_idcard);
        imageView = (ImageView) findViewById(R.id.ImageView01);
        Send_data = (Button) findViewById(R.id.Button_Send_first);
        Next = (Button) findViewById(R.id.SecondCheck);
        Open_cam = (Button) findViewById(R.id.Button01);
        part = (LinearLayout) findViewById(R.id.part_button);
        tv_name_part = (TextView) findViewById(R.id.textView2);
        tv_name_ans = (TextView) findViewById(R.id.fullname);
        tv_num_part = (TextView) findViewById(R.id.textView4);
        tv_num_ans = (TextView) findViewById(R.id.Number);
        tv_status_text = (TextView) findViewById(R.id.status_text);
        getSupportActionBar().hide();
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == CameraActivity.REQUEST_CODE && resultCode == CameraActivity.RESULT_CODE) {

                final String path = CameraActivity.getResult(data);
                File file = new File(path);
                if (!TextUtils.isEmpty(path)) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imageView.setImageBitmap(myBitmap);

//                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), getRealPathFromURI(data.getData()));
//                    MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",file.getName(),requestFile);


                    //pass it like this
//                    File file = new File("/storage/emulated/0/Download/Corrections 6.jpg");
                    @SuppressWarnings("deprecation") RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    // MultipartBody.Part is used to send also the actual file name
                    // part for real database
//                    body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    // part for local database
                    body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                    // add another part within the multipart request
                    //noinspection deprecation
                    name = RequestBody.create(MediaType.parse("multipart/form-data"), "file");

//                    service.updateProfile(id, fullName, body, other);

//                    RequestBody reqFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
//                    body = MultipartBody.Part.createFormData("pic", file.getName(), reqFile);
//                    name = RequestBody.create("upload_test",MediaType.parse("text/plain"));

                    Log.e(TAG, "onActivityResult: "+file);
                    Log.e(TAG, "onActivityResult: "+file.getName());
//                    convertToMutiPartOne(getBaseContext(),path,"imageone");

//                    new NetworkConnectionManager().callServer_IDcard(Call_ID,body,1);
                    tv_status_text.setVisibility(View.GONE);
                    Open_cam.setVisibility(View.GONE);

                    new NetworkConnectionManager().callServer_IDcard(Call_ID,body,1);
                }
            }
        }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    //        public MultipartBody.Part convertToMutiPartOne(Context context , String path ,String fieldName ){
//
////            Uri uri = Uri.parse(path);
//            File file = new File(path);
//            try {
//                if (file.exists()){
//                    RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"),"upload_test");
//                    return MultipartBody.Part.createFormData(fieldName,file.getName(),requestBody);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return null;
//        }

    OnNetworkCallback_IDCard Call_ID = new OnNetworkCallback_IDCard() {
        @Override
        public void onResponse(POJO_IDcard call_idcard) {
//            Toast.makeText(Camera_IDcard_Activity.this, "Response"+call_idcard, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onResponse:  "+call_idcard.getError()+"     "+call_idcard.getIdnum());
//            Toast.makeText(Camera_IDcard_Activity.this, ""+ call_idcard.getError(), Toast.LENGTH_SHORT).show();


            tv_name_part.setVisibility(View.VISIBLE);
            tv_name_ans.setVisibility(View.VISIBLE);
            tv_num_part.setVisibility(View.VISIBLE);
            tv_num_ans.setVisibility(View.VISIBLE);
            tv_name_ans.setText(call_idcard.getFullnameTh());
            tv_num_ans.setText(call_idcard.getIdnum());

            Send_data.setVisibility(View.VISIBLE);
            part.setVisibility(View.VISIBLE);


            if ((tv_num_ans.getText().equals(""))||(tv_name_ans.getText().equals(""))||(tv_name_ans.getText() == null)||(tv_num_ans.getText() == null)){
                tv_name_ans.setText("ไม่เจอข้อมูลที่ต้องการ");
                tv_num_ans.setText("ไม่เจอข้อมูลที่ต้องการ");
            }else{
                Next.setVisibility(View.VISIBLE);
//                showToast();
                Toast.makeText(Camera_IDcard_Activity.this, "พบข้อมูลที่ต้องการแล้ววว !!", Toast.LENGTH_SHORT).show();
            }

            if (call_idcard.getError() == 1){
//                showToast_error();
                Toast.makeText(Camera_IDcard_Activity.this, "ไม่เจอรูปภาพที่ต้องการ โปรดลองใหม่อีกครั้ง !!  ", Toast.LENGTH_SHORT).show();
                tv_status_text.setVisibility(View.VISIBLE);
                tv_status_text.setText("ไม่เจอรูปภาพที่ต้องการโปรดลองใหม่อีกครั้ง");
            }


        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {
            Log.e(TAG, "onBodyError:  "+responseBodyError);
        }

        @Override
        public void onBodyErrorIsNull() {
            Log.e(TAG, "onBodyErrorIsNull:  111");
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e(TAG, "onFailure:  "+t);
        }
    };

////// req file
//    body = MultipartBody.Part.createFormData("image", destination.getName(), reqFile);

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_CAMERA_REQUEST_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    public void IDcardCheck(View view) {
        takePhoto(CameraActivity.TYPE_IDCARD_FRONT);
    }
    public void IDcardsendCheck(View view) {
        Call_Image_Check(body,1);
    }
    public void Call_Image_Check(MultipartBody.Part img,int Mode){new NetworkConnectionManager().callServer_IDcard(Call_ID,img,Mode);}

    private void takePhoto(int type) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0x12);
            return;
        }
        CameraActivity.openCertificateCamera(this, type);
    }


    public void SecondCheck(View view) {
        Intent intent = new Intent(Camera_IDcard_Activity.this, Camera_IDcard_with_face_Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

//    void showToast() {
//        Toast toast = new Toast(this);
//
//        View view = LayoutInflater.from(this)
//                .inflate(R.layout.custom_toast_success, null);
//
//
//        TextView tv = findViewById(R.id.titleTextView);
//        tv.setText("พบข้อมูลที่ต้องการแล้ววว !!    ");
//        toast.setView(view);
//        toast.show();
//    }
//
//    void showToast_error() {
//        Toast toast = new Toast(this);
//        View view = LayoutInflater.from(this)
//                .inflate(R.layout.custom_toast_error, null);
//
//        TextView tv = findViewById(R.id.titleTextView);
//        tv.setText("ไม่เจอรูปภาพที่ต้องการ โปรดลองใหม่อีกครั้ง !!    ");
//        toast.setView(view);
//        toast.show();
//    }
}