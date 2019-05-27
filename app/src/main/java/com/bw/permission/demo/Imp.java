package com.bw.permission.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bw.permissions.BwPermissions;
import com.bw.permissions.PermissionsCore;

import java.util.List;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/5/7
 * <br/>desc  :
 * </pre>
 */
public class Imp implements BwPermissions.PermissionCallbacks{
    private Context ctx;
    private static final String TAG = "Imp";

    public Imp(Context context) {
        this.ctx = context;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //进行权限的处理
        if (requestCode != 10000) {
            return;
        }
        for (int i = 0; i < perms.size(); i++) {
            if (perms.get(i).equals(Manifest.permission.CAMERA)) {
                openCamera(ctx);
            } else if (perms.get(i).equals(Manifest.permission.RECORD_AUDIO)) {
                openAudio(ctx);
            }
        }


    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //进行权限的处理
        if (requestCode != 10000) {
            return;
        }
        for (int i = 0; i < perms.size(); i++) {
            if (perms.get(i).equals(Manifest.permission.CAMERA)) {
                Log.e(TAG, "onPermissionsDenied: " + "相机权限拒绝");
            } else if (perms.get(i).equals(Manifest.permission.RECORD_AUDIO)) {
                Log.e(TAG, "onPermissionsDenied: " + "录制音频权限拒绝");
            }
        }

        if (PermissionsCore.somePermissionPermanentlyDenied((Activity) ctx, perms)) {
            //某些权限被永久拒绝，需要开启弹框提醒用户
            Log.e(TAG, "在这里打开提示框");
        }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //外部权限设置回调
        if (requestCode == 10000) {
            Log.e(TAG, "从开启权限的页面转跳回来");
        }
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {
        PermissionsCore.onRequestPermissionsResult(i, strings, ints,this);
    }


    private void openCamera(Context context) {
        Log.e(TAG, "打开相机 ~~~");
    }

    private void openAudio(Context context) {
        Log.e(TAG, "录制音频权限成功 ~~~");
    }
}
