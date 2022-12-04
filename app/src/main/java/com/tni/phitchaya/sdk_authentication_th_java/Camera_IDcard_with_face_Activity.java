package com.tni.phitchaya.sdk_authentication_th_java;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tni.phitchaya.cameraIDcardth.CameraActivity;
import com.tni.phitchaya.cameraIDcardth.CameraPreview;
import com.tni.phitchaya.sdk_authentication_th_java.POJO.POJO_IDcard;
import com.tni.phitchaya.sdk_authentication_th_java.Retrofit.NetworkConnectionManager;
import com.tni.phitchaya.sdk_authentication_th_java.Retrofit.OnNetworkCallback_IDCard;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class Camera_IDcard_with_face_Activity extends AppCompatActivity {

    TextView fullname , number , Date ,tv_status_text;
    ImageView imageView;
    Intent intent;
    RequestBody name;
    MultipartBody.Part body_part_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_idcard_with_face);
        Context context = getBaseContext();
        tv_status_text = (TextView) findViewById(R.id.status_text);
        getSupportActionBar().hide();
        intent = new Intent(Camera_IDcard_with_face_Activity.this, SucceedActivity.class);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CameraActivity.REQUEST_CODE && resultCode == CameraActivity.RESULT_CODE) {

            final String path = CameraActivity.getResult(data);
            File file = new File(path);
            if (!TextUtils.isEmpty(path)) {
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                imageView.setImageBitmap(myBitmap);
                @SuppressWarnings("deprecation") RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
                body_part_2 = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                name = RequestBody.create(MediaType.parse("multipart/form-data"), "file");


                new NetworkConnectionManager().callServer_IDcard(Call_Check,body_part_2,2);

            }
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    OnNetworkCallback_IDCard Call_Check = new OnNetworkCallback_IDCard() {
        @Override
        public void onResponse(POJO_IDcard call_idcard) {


            Log.e(TAG, "onResponse: "+call_idcard.getStaus());
            String pass_id = call_idcard.getStaus();



            switch (pass_id){
                case "pass":
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case "not pass":
                    Log.e(TAG, "onResponse: "+pass_id);
//                    showToast_error("ดูเหมือนยังไม่เจอรูปที่ต้องการลองใหม่อีกครั้ง !!    ");
                    Toast.makeText(Camera_IDcard_with_face_Activity.this, "ดูเหมือนยังไม่เจอรูปที่ต้องการลองใหม่อีกครั้ง !!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

            if (call_idcard.getError() != null){
//                showToast_error("ไม่เจอรูปภาพที่ต้องการ  โปรดลองใหม่อีกครั้ง !!    ");
                Toast.makeText(Camera_IDcard_with_face_Activity.this, "ไม่เจอรูปภาพที่ต้องการ  โปรดลองใหม่อีกครั้ง !!", Toast.LENGTH_SHORT).show();
                tv_status_text.setVisibility(View.VISIBLE);
                tv_status_text.setText("ไม่เจอรูปภาพที่ต้องการโปรดลองใหม่อีกครั้ง");
            }





        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {

        }

        @Override
        public void onBodyErrorIsNull() {

        }

        @Override
        public void onFailure(Throwable t) {

        }
    };

    public void OpenCamera_2(View view){
        takaPhoto(CameraActivity.TYPE_COMPANY_PORTRAIT);
    }

    void showToast_error(String msg) {
        Toast toast = new Toast(this);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.custom_toast_error, null);

        TextView tv = findViewById(R.id.titleTextView);
        tv.setText(msg);
        toast.setView(view);
        toast.show();
    }

    private void takaPhoto(int type){
        CameraActivity.openCertificateCamera(this, type);
    }
}