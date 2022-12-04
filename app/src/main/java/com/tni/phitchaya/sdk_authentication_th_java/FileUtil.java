package com.tni.phitchaya.sdk_authentication_th_java;

import android.content.Context;

import java.io.File;

public class FileUtil {

    public static File getSaveFile(Context context) {
        return new File(context.getFilesDir(), "pic.jpg");
    }

}
