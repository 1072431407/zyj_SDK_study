package com.zyj.studyapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zyj.studyapp.app.BaseActivity;

public class GameActivity extends BaseActivity {
    public static void startActivity(BaseActivity activity){
        Intent intent = new Intent(activity,GameActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

    }
}
