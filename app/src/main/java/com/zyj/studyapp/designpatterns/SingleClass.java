package com.zyj.studyapp.designpatterns;

/**
 * 子类为单例
 * @param <T>
 */
public abstract class SingleClass<T> {
    private T instance;
    protected abstract T newInstance();
    public final T getInstance(){
        if (instance == null){
            synchronized (SingleClass.class){
                if (instance == null){
                    instance = newInstance();
                }
            }
        }
        return instance;
    }
}
