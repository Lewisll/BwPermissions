package com.bw.permissions.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/5/6
 * <br/>desc  :
 * </pre>
 */
class SupportFragmentPermissionHelper extends BaseSupportPermissionsHelper<Fragment> {

    public SupportFragmentPermissionHelper(@NonNull Fragment host) {
        super(host);
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return getHost().getChildFragmentManager();
    }

    @Override
    public void directRequestPermissions(int requestCode, @NonNull String... perms) {
        getHost().requestPermissions(perms, requestCode);
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String perm) {
        return getHost().shouldShowRequestPermissionRationale(perm);
    }

    @Override
    public Context getContext() {
        return getHost().getActivity();
    }
}
