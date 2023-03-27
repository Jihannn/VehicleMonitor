package com.jihan.lib_common.base;



import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jihan.lib_common.utils.LogUtils;


public abstract class BaseActivity extends AppCompatActivity {

    private final String TAG = LogUtils.TAG_VEHICLE + getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.logV(TAG, "[onCreate]");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.logV(TAG, "[onStart]");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.logV(TAG, "[onRestart]");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.logV(TAG, "[onSaveInstanceState]");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            LogUtils.logV(TAG, "[onNewIntent]" + intent.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.logV(TAG, "[onActivityResult] requestCode:" + requestCode + ";"
                + "resultCode:" + requestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.logV(TAG, "[onResume]");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.logV(TAG, "[onPause]");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.logV(TAG, "[onStop]");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.logV(TAG, "[onDestroy]");
    }
}