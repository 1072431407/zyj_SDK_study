package com.az.vschool.net;

import android.util.Log;

import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import com.az.vschool.net.HttpCode;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import proto.base.BaseResponse;

/**
 * okHttp返回数据类，作为内部类使用
 * @param <T>
 */
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
                    ByteString byteString = baseResponse.getData();
                    if (byteString == null){
                        onSucceed(null);
                    }else{
                        T t = (T) msgBuilder.mergeFrom(byteString).build();
                        Log.e(TAG,"onSucceed : \n" + t.toString());
                        onSucceed(t);
                    }
                }catch (Exception e){
                    Log.e(TAG,"onFailure : \n" + HttpCode.ANALYSIS.toString());
                    onFailure(HttpCode.ANALYSIS.getCode(),HttpCode.ANALYSIS.getMessage());
                }
            }else{
                Log.e(TAG,"onFailure : \n" + "HttpCode{" +
                        "code=" + baseResponse.getCode() +
                        ", message='" + baseResponse.getMessage() + '\'' +
                        '}');
                onFailure(baseResponse.getCode(),baseResponse.getMessage());
            }

        }catch (Exception e){
            Log.e(TAG,"onFailure : \n" + HttpCode.ANALYSIS.toString());
            onFailure(HttpCode.ANALYSIS.getCode(),HttpCode.ANALYSIS.getMessage());
        }
    }
    public void onFailureLog(int code, String message) {
        Log.e(TAG,"onFailure : \n" + "HttpCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}');
        onFailure(code,message);
    }

    public abstract void onSucceed(T res);
    public abstract void onFailure(int code, String message);

}
