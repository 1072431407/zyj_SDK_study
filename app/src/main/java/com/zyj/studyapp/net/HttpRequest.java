package com.zyj.studyapp.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.zyj.library.log.Log;
import com.zyj.studyapp.app.AppConstants;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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
                    String param = new String(bytes);
                    Log.e(TAG,param);
                    break;
                case 2:
                    response.onFailure(bundle.getInt("code"),bundle.getString("message"));
                    break;
            }

        }
    };

}
