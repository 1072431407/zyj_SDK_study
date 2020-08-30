package com.zyj.studyapp.designpatterns;

public class TestEntity extends SingleBase<TestEntity>{
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected TestEntity newInstance() {
        TestEntity entity = new TestEntity();
        entity.setPassword("123");
        entity.setUsername("111");
        return entity;
    }
}
