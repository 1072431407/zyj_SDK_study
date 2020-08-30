package com.zyj.studyapp.net;

import android.util.Log;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import proto.base.BaseResponse;

public abstract class HttpResponse<T> {
    private String TAG = HttpResponse.class.getSimpleName();

    public void onResponse(byte[] bytes){
        try {
            Class baseCl = Class.forName(BaseResponse.class.getCanonicalName());
            Method baseMethod = baseCl.getMethod("newBuilder");
            Message.Builder baseMsgBuilder = (Message.Builder) baseMethod.invoke(null, new Object[]{});
            BaseResponse baseResponse = (BaseResponse) baseMsgBuilder.mergeFrom(bytes).build();
            if (baseResponse.getCode() == HttpCode.SUCCEED.getCode()){
                try {
                    // 通过反射获取model的真实类型
                    ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
                    Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
                    String classNameStr = clazz.getCanonicalName();
                    Class cl = Class.forName(classNameStr);
                    Method method = cl.getMethod("newBuilder");
                    Message.Builder msgBuilder = (Message.Builder)method.invoke(null, new Object[]{});
                    T t = (T) msgBuilder.mergeFrom(baseResponse.getData()).build();
                    Log.e(TAG,t.toString());
                    onSucceed(t);
                }catch (Exception e){
                    Log.e(TAG,"onFailure" + HttpCode.ANALYSIS.toString());
                    onFailure(HttpCode.ANALYSIS.getCode(),HttpCode.ANALYSIS.getMessage());
                }
            }else{
                Log.e(TAG,"onFailure" + "HttpCode{" +
                        "code=" + baseResponse.getCode() +
                        ", message='" + baseResponse.getMessage() + '\'' +
                        '}');
                onFailure(baseResponse.getCode(),baseResponse.getMessage());
            }

        }catch (Exception e){
            Log.e(TAG,"onFailure" + HttpCode.ANALYSIS.toString());
            onFailure(HttpCode.ANALYSIS.getCode(),HttpCode.ANALYSIS.getMessage());
        }
    }

    public abstract void onSucceed(T res);
    public abstract void onFailure(int code, String message);

}
