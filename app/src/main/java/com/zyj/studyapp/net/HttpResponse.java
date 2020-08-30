package com.zyj.studyapp.net;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Method;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import proto.user.UserRequest;

public abstract class HttpResponse<T> {
    private Class<T> clazz;
    private T res;
    public void onResponse(byte[] bytes){
        try {
            UserRequest userRequest = UserRequest.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        try {
            // 通过反射获取model的真实类型
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            clazz = (Class<T>) pt.getActualTypeArguments()[0];
            clazz.getClass().getSimpleName();
//            UserRequest userRequest = BuildMessage.buildMessage("UserRequest")
            // 通过反射创建model的实例
            res = clazz.newInstance();
            onSucceed(res);
        }catch (Exception e){
            onFailure();
        }
    }

    public abstract void onSucceed(T res);
    public abstract void onFailure();

}
