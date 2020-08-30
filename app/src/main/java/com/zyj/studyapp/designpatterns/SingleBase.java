package com.zyj.studyapp.designpatterns;

public abstract class SingleBase<T> {
    private T instance;
    protected abstract T newInstance();
    public final T getInstance(){
        if (instance == null){
            synchronized (SingleBase.class){
                if (instance == null){
                    instance = newInstance();
                }
            }
        }
        return instance;
    }
}
