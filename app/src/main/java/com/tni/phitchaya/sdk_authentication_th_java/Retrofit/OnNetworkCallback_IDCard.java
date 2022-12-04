package com.tni.phitchaya.sdk_authentication_th_java.Retrofit;

import com.tni.phitchaya.sdk_authentication_th_java.POJO.POJO_IDcard;

import okhttp3.ResponseBody;

public interface OnNetworkCallback_IDCard {

    public void onResponse(POJO_IDcard call_idcard);
    public void onBodyError(ResponseBody responseBodyError);
    public void onBodyErrorIsNull();
    public void onFailure(Throwable t);
}
