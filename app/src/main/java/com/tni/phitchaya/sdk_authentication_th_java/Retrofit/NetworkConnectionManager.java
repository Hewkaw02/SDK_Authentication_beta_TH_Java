package com.tni.phitchaya.sdk_authentication_th_java.Retrofit;

import static com.tni.phitchaya.sdk_authentication_th_java.MainActivity.BASEURL;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tni.phitchaya.sdk_authentication_th_java.BuildConfig;
import com.tni.phitchaya.sdk_authentication_th_java.POJO.POJO_IDcard;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkConnectionManager {

    public NetworkConnectionManager(){

    }



    public void callServer_IDcard(final OnNetworkCallback_IDCard callback_idCard , MultipartBody.Part image,int Mode){

        OkHttpClient.Builder http = new OkHttpClient.Builder();

        /// log check
        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            http.addInterceptor(loggingInterceptor);

//            Request.Builder requestBuilder = chain.request().newBuilder();
//            requestBuilder.header("Content-Type", "application/json");
//            return chain.proceed(requestBuilder.build());

        }

        http.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder request = chain.request().newBuilder().header("Content-Type","application/json");
                return chain.proceed(request.build());
            }
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(http.build())
                .build();

        APISERVER callapi = retrofit.create(APISERVER.class);


        Log.e("Network connected","Response code = "+image+" "+Mode);

        Call<POJO_IDcard> call = callapi.Call_IDcard(image,Mode);
        call.enqueue(new Callback<POJO_IDcard>() {
            @Override
            public void onResponse(Call<POJO_IDcard> call, Response<POJO_IDcard> response) {

                try {

                    POJO_IDcard call_IDcard = response.body();

                    if (call_IDcard == null) {
                        //404 or the response cannot be converted to User.
                        ResponseBody responseBody = response.errorBody();
                        if (responseBody != null) {
                            callback_idCard.onBodyError(responseBody);
                        } else {
                            callback_idCard.onBodyErrorIsNull();
                        }
                    } else {
                        //callback_idCard
                        callback_idCard.onResponse(response.body());
//                        Log.e("ResNet", "" + call_IDcard.getFullnameTh());
                    }

                } catch (Exception e) {
                    Log.e("Network connect error1", e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<POJO_IDcard> call, Throwable t) {

                try {

                    callback_idCard.onFailure(t);

                } catch (Exception e) {

                    callback_idCard.onFailure(t);
                }

            }
        });

    }


}

