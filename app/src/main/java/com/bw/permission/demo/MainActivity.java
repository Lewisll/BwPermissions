package com.bw.permission.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import com.bw.permissions.BwPermissions;
import com.bw.permissions.PermissionRequest;
import com.bw.permissions.PermissionsCore;
import java.util.List;

public class MainActivity extends Activity implements BwPermissions.RationaleCallbacks, BwPermissions.PermissionCallbacks {

    @Override
    public void onRationaleAccepted(int requestCode) {
        //对话框确定
        Log.e(TAG, "确定 ~~~");
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        //对话框取消
        Log.e(TAG, "取消 ~~~");
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {//权限被授予的回调
        //进行权限的处理
        if (requestCode != RC_CAMERA_AND_RECORD_AUDIO) {
            return;
        }
        for (int i = 0; i < perms.size(); i++) {
            if (perms.get(i).equals(Manifest.permission.CAMERA)) {
                openCamera();
            } else if (perms.get(i).equals(Manifest.permission.RECORD_AUDIO)) {
                openAudio();
            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //进行权限的处理
        if (requestCode != RC_CAMERA_AND_RECORD_AUDIO) {
            return;
        }
        for (int i = 0; i < perms.size(); i++) {
            if (perms.get(i).equals(Manifest.permission.CAMERA)) {
                Log.e(TAG, "onPermissionsDenied: " + "相机权限拒绝");
            } else if (perms.get(i).equals(Manifest.permission.RECORD_AUDIO)) {
                Log.e(TAG, "onPermissionsDenied: " + "录制音频权限拒绝");
            }
        }

        if (PermissionsCore.somePermissionPermanentlyDenied(MainActivity.this, perms)) {
            //某些权限被永久拒绝，需要开启弹框提醒用户
            Log.e(TAG, "在这里打开提示框");
        }
    }

    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};

    private static final String TAG = "MainActivity";
    private static final int RC_CAMERA_AND_RECORD_AUDIO = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_requst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PermissionsCore.hasPermissions(MainActivity.this, perms)) {
                    //直接权限申请
                    PermissionRequest permissionRequest = new PermissionRequest.Builder(MainActivity.this, RC_CAMERA_AND_RECORD_AUDIO, perms)
                            .setTheme(R.style.BwPermissions_White)
                            .setNegativeButtonText("取消")
                            .setPositiveButtonText("确定")
                            .setRationale("不请求权限将无法使用该功能,05-08 11:04:06.984 /Imp: onPermissionsDenied: 录制音频权限拒绝" + "\n 05-08 11:04:06.984 E/Imp: onPermissionsDenied: 录制音频权限拒绝")
                            .build();
                    PermissionsCore.requestPermissions(permissionRequest);

//                    PermissionsCore.requestDirectPermissions(MainActivity.this, RC_CAMERA_AND_RECORD_AUDIO, perms);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        imp.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsCore.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
    }


    private void openCamera() {
        Log.e(TAG, "打开相机 ~~~");
    }

    private void openAudio() {
        Log.e(TAG, "录制音频权限成功 ~~~");
    }
}

