package com.zyj.studyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private final Context context = this;
    private Tencent tencent;
    private IUiListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Permissions.checkPermissions(this);

        tencent = Tencent.createInstance(AppConstants.APP_ID, MainActivity.this, AppConstants.APP_AUTHORITIES);
        TextView text = findViewById(R.id.text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qqLogin();
            }
        });
    }

    private void qqLogin() {
        listener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e(TAG, "onComplete: " + o.toString());
                Toast.makeText(context,"onComplete",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        }
    }
}