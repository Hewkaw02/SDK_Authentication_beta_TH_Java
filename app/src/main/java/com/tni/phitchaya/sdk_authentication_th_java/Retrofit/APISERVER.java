package com.tni.phitchaya.sdk_authentication_th_java.Retrofit;

import com.tni.phitchaya.sdk_authentication_th_java.POJO.POJO_IDcard;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APISERVER {


    ///// Work for real database
//    @Multipart
//    @POST("upload_image.php")
//    Call<POJO_IDcard> Call_IDcard (
//            @Part MultipartBody.Part image ,
//            @Part("mode_id") String Mode);

    ///// Test for local database

    @Multipart
    @Headers({"Accept: application/json"})
    @POST("api.php")
    Call<POJO_IDcard> Call_IDcard (
            @Part MultipartBody.Part image ,
            @Part("mode_id") int Mode);

}
