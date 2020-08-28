package com.zyj.studyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zyj.library.log.Log;
import com.zyj.studyapp.app.AppConstants;
import com.zyj.studyapp.app.BaseActivity;
import com.zyj.studyapp.net.HttpRequest;
import com.zyj.studyapp.net.HttpResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import proto.user.UserRequest;

public class GameActivity extends BaseActivity {
    private static final String TAG = GameActivity.class.getSimpleName();

    public static void startActivity(BaseActivity activity){
        Intent intent = new Intent(activity,GameActivity.class);
        activity.startActivity(intent);
    }

    private Button request;
    private TextView response;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
        initListener();
    }

    private void initListener() {
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRequest userRequest = UserRequest.newBuilder()
                        .setUuid("0001")
                        .setUsername("111111")
                        .setPassword("123456")
                        .setPermissions("1")
                        .build();
                request1(userRequest.toByteArray(),"index");
            }
        });
    }
    private void request1(byte[] bytes,String api) {
        HttpRequest request = new HttpRequest();
        request.post(bytes, api, new HttpResponse<UserRequest>() {
            @Override
            public void onSucceed(UserRequest res) {
                Log.e(TAG,res.getUuid());
                Log.e(TAG,res.getUsername());
                Log.e(TAG,res.getPassword());
                Log.e(TAG,res.getPermissions());
            }
            @Override
            public void onFailure() {

            }
        });
    }



    private void request(byte[] bytes,String api) {
        RequestBody body = new FormBody.Builder()
                .add("param", new String(bytes))
                .build();
        Request request = new Request.Builder()
                .url(AppConstants.URL_DEBUG+api)
                .post(body)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String param = new String(response.body().bytes());
                Log.e(TAG,param);
            }
        });
    }

    private void initView() {
        request = findViewById(R.id.request);
        response = findViewById(R.id.response);
    }
}
