package com.zyj.studyapp.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zyj.library.lang.StringUtil;
import com.zyj.library.window.StatusBarUtil;
import com.zyj.studyapp.R;

public class BaseActivity extends Activity {

    protected void ToastShort(String message){
        if (StringUtil.isEmpty(message)) return;
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Permissions.checkPermissions(this);
        setBar();
    }

    public void setBar(){
        StatusBarUtil.setWindowStatusBarColor(this, R.color.colorPrimary);
        StatusBarUtil.statusBarLightMode(this);
    }
}
