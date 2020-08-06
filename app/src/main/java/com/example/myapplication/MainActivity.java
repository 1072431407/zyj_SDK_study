package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.tencent.connect.auth.AuthAgent;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
//    public static final String APP_ID="222222";
//    public static final String APP_AUTHORITIES="com.tencent.sample.fileprovider";
    private Button qqLogin;
    private Tencent tencent;
    private com.tencent.tauth.IUiListener iUiListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tencent = Tencent.createInstance(AppConstants.APP_ID, this,AppConstants.APP_AUTHORITIES);

        init();
        initListener();
    }

    private void initListener() {
        qqLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogin();
            }
        });
        iUiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "onComplete: " + o.toString());
            }

            @Override
            public void onError(UiError uiError) {
                Log.e(TAG, "onError: " + JSONObject.toJSONString(uiError));

            }

            @Override
            public void onCancel() {
                Log.e(TAG, "onCancel" );
            }
        };
    }

    private void init() {
        qqLogin = findViewById(R.id.qqLogin);
    }

    private void onClickLogin() {
        if (!tencent.isSessionValid()) {
            tencent.login(this, "all", iUiListener, true);
            Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        }
    }

}