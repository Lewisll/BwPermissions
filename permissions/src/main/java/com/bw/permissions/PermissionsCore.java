package com.bw.permissions;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.app.Fragment;

import com.bw.permissions.helper.PermissionHelper;

import java.util.List;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/5/7
 * <br/>desc  :
 * </pre>
 */
public class PermissionsCore{

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults,@NonNull Object... receivers) {
        BwPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,receivers);
    }

    /**
     * 判断是否具有某个/些权限
     *
     * @param context 上下文
     * @param perms   权限
     * @return
     */
    public static boolean hasPermissions(@NonNull Context context,
                                         @Size(min = 1) @NonNull String... perms) {
        return BwPermissions.hasPermissions(context, perms);
    }

    /**
     * activity 上请求权限，如果系统请求会展示相应的说明
     *
     * @param host
     * @param rationale
     * @param requestCode
     * @param perms
     */
    public static void requestPermissions(
            @NonNull Activity host, @NonNull String rationale,
            int requestCode, @Size(min = 1) @NonNull String... perms) {
        BwPermissions.requestPermissions(host, rationale, requestCode, perms);
    }

    /**
     * Fragment上请求权限，如果系统请求会展示相应的说明
     *
     * @param host
     * @param rationale
     * @param requestCode
     * @param perms
     */
    public static void requestPermissions(@NonNull Fragment host, @NonNull String rationale,
                                          int requestCode, @Size(min = 1) @NonNull String... perms) {
        BwPermissions.requestPermissions(host, rationale, requestCode, perms);
    }

    /**
     * 使用PermissionRequest 通过控制对话框，请求权限
     *
     * @param request
     */
    public static void requestPermissions(PermissionRequest request) {
        BwPermissions.requestPermissions(request);
    }

    /**
     * 判断权限是否被永久拒绝
     *
     * @param host
     * @param deniedPermissions
     * @return
     */
    public static boolean somePermissionPermanentlyDenied(@NonNull Activity host,
                                                          @NonNull List<String> deniedPermissions) {
        return BwPermissions.somePermissionPermanentlyDenied(host, deniedPermissions);
    }

    public static boolean somePermissionPermanentlyDenied(@NonNull Fragment host,
                                                          @NonNull List<String> deniedPermissions) {
        return BwPermissions.somePermissionPermanentlyDenied(host, deniedPermissions);
    }

    /**
     * 检查某个权限是否被永久拒绝
     *
     * @param host
     * @param deniedPermission
     * @return
     */
    public static boolean permissionPermanentlyDenied(@NonNull Activity host,
                                                      @NonNull String deniedPermission) {
        return BwPermissions.permissionPermanentlyDenied(host, deniedPermission);
    }

    public static boolean permissionPermanentlyDenied(@NonNull Fragment host,
                                                      @NonNull String deniedPermission) {
        return BwPermissions.permissionPermanentlyDenied(host, deniedPermission);
    }

    /**
     * 判断某个/些权限是否被拒绝（不是永久拒绝）
     *
     * @param host
     * @param perms
     * @return
     */
    public static boolean somePermissionDenied(@NonNull Activity host,
                                               @NonNull String... perms) {
        return BwPermissions.somePermissionDenied(host, perms);
    }

    public static boolean somePermissionDenied(@NonNull Fragment host,
                                               @NonNull String... perms) {
        return BwPermissions.somePermissionDenied(host, perms);
    }

    /**
     * 直接请求权限
     *
     * @param host
     * @param requestCode
     * @param perms
     */
    public static void requestDirectPermissions(@NonNull Activity host,
                                                int requestCode,
                                                @Size(min = 1) @NonNull String... perms) {
        if (hasPermissions(host, perms)) {
            BwPermissions.notifyAlreadyHasPermissions(host, requestCode, perms);
        }
        PermissionHelper.newInstance(host).directRequestPermissions(requestCode, perms);
    }

    public static void requestDirectPermissions(@NonNull Fragment host,
                                                int requestCode,
                                                @Size(min = 1) @NonNull String... perms) {
        if (hasPermissions(host.getContext(), perms)) {
            BwPermissions.notifyAlreadyHasPermissions(host, requestCode, perms);
        }
        PermissionHelper.newInstance(host).directRequestPermissions(requestCode, perms);
    }


}
