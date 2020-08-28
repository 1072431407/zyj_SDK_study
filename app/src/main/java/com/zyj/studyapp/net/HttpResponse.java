package com.zyj.studyapp.net;

public abstract class HttpResponse<T> {
    public void onResponse(byte[] bytes){
        try {
            T res = (T)bytes;
            onSucceed(res);
        }catch (Exception e){
            onFailure();
        }
    }

    public abstract void onSucceed(T res);
    public abstract void onFailure();

}
