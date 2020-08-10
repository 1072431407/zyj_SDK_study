package com.zyj.studyapp.app;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zyj.studyapp.util.StringUtil;

public class BaseActivity extends AppCompatActivity {

    protected void ToastShort(String message){
        if (StringUtil.isEmpty(message)) return;
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }
}
