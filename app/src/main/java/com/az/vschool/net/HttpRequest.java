package com.az.vschool.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.zyj.library.log.Log;
import com.az.vschool.app.AppConstants;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okHttp请求类
 * 2020-8-31 未加密
 */
public class HttpRequest {
    private final String TAG = HttpRequest.class.getSimpleName();
    private HttpResponse response;
    public void post(byte[] bytes, String api,HttpResponse response) {
        this.response = response;
        RequestBody body = new FormBody.Builder()
                .add("param", new String(bytes))
                .build();
        final Request request = new Request.Builder()
                .url(AppConstants.URL_DEBUG+api)
                .post(body)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"onFailure" + e.getMessage());
                Message message = new Message();
                message.what = 2;
                Bundle bundle = new Bundle();
                bundle.putInt("code",HttpCode.ERROR_404.getCode());
                bundle.putString("message",HttpCode.ERROR_404.getMessage());
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG,"onResponse");
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putByteArray("bytes",response.body().bytes());
                bundle.putInt("code",response.code());
                bundle.putString("message",response.message());
                message.setData(bundle);
                message.what = 1;
                handler.sendMessage(message);
            }
        });
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            switch (msg.what){
                case 1:
                    byte[] bytes = bundle.getByteArray("bytes");
                    response.onResponse(bytes);
                    break;
                case 2:
                    response.onFailureLog(bundle.getInt("code"),bundle.getString("message"));
                    break;
            }

        }
    };

}
