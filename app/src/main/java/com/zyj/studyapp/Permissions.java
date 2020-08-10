package com.zyj.studyapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {

    public static void checkPermissions(Activity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.INTERNET)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.INTERNET}, 1);
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_NETWORK_STATE)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
                }
            }
        }
    }
}
