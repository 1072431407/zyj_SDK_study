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
import com.zyj.studyapp.app.AppApi;
import com.zyj.studyapp.app.AppConstants;
import com.zyj.studyapp.app.BaseActivity;
import com.zyj.studyapp.net.HttpRequest;
import com.zyj.studyapp.net.HttpResponse;

import okhttp3.Response;
import proto.base.BaseResponse;
import proto.user.UserRequest;
import proto.user.UserResponse;

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
                //客户端发送封装
                UserRequest userRequest = UserRequest.newBuilder()
                        .setUuid("0001")
                        .setUsername("111111")
                        .setPassword("123456")
                        .setPermissions("1")
                        .build();

                //服务端返回封装
                UserResponse userResponse = UserResponse.newBuilder()
                        .setNickName("zhangsan")
                        .setSign("asdasdasd")
                        .setAge("23")
                        .build();
                BaseResponse response = BaseResponse.newBuilder()
                        .setCode(0)
                        .setMessage("")
                        .setData(userResponse.toByteString())
                        .build();
                request(response.toByteArray(), AppApi.test_url);
            }
        });
    }
    private void request(byte[] bytes,String api) {
        HttpRequest request = new HttpRequest();
        request.post(bytes, api, new HttpResponse<UserResponse>() {
            @Override
            public void onSucceed(UserResponse response) {
                Log.e(TAG,response.getNickName());
                Log.e(TAG,response.getSign());
                Log.e(TAG,response.getAge());
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

    private void initView() {
        request = findViewById(R.id.request);
        response = findViewById(R.id.response);
    }
}
