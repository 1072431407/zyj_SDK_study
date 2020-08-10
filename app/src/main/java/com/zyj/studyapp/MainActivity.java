package com.zyj.studyapp;

import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zyj.studyapp.app.AppConstants;
import com.zyj.studyapp.app.BaseActivity;
import com.zyj.studyapp.app.Permissions;

import org.json.JSONObject;

public class MainActivity extends BaseActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private Context context = this;
    private Tencent tencent;
    private IUiListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Permissions.checkPermissions(this);

        tencent = Tencent.createInstance(AppConstants.APP_ID, MainActivity.this, AppConstants.APP_AUTHORITIES);
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qqLogin();
            }
        });

        final Button getUserInfo = findViewById(R.id.getUserInfo);
        getUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInfo();
            }
        });
        Button logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tencent.logout(MainActivity.this);
            }
        });

    }

    private void getUserInfo() {
        if (tencent != null && tencent.isSessionValid()) {
            UserInfo info = new UserInfo(context,tencent.getQQToken());
            info.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    Log.e(TAG, "onComplete: " + o.toString());
                    ToastShort("onComplete");
                }

                @Override
                public void onError(UiError uiError) {
                    Log.e(TAG, "onError: " + uiError.toString());
                    ToastShort("onError");
                }

                @Override
                public void onCancel() {
                    ToastShort("onCancel");
                }
            });
        }
    }

    private void qqLogin() {
        listener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "onComplete: " + o.toString());
                Toast.makeText(context,"onComplete",Toast.LENGTH_SHORT).show();
                initOpenidAndToken((JSONObject)o);
            }

            @Override
            public void onError(UiError uiError) {
                Log.e(TAG, "onError: " + uiError.toString());
                Toast.makeText(context,"onError",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "onCancel:");
            }
        };
        tencent.login(MainActivity.this, "all", listener, true);
    }
    private void initOpenidAndToken(JSONObject jsonObject) {//不执行登陆就无效
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                tencent.setAccessToken(token, expires);
                tencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}