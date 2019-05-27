package com.bw.permissions;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

import com.bw.permissions.helper.PermissionHelper;

import java.util.Arrays;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/5/6
 * <br/>desc  :
 * </pre>
 */
class RationaleDialogClickListener implements Dialog.OnClickListener {


    private Object mHost;
    private RationaleDialogConfig mConfig;
    private BwPermissions.PermissionCallbacks mCallbacks;
    private BwPermissions.RationaleCallbacks mRationaleCallbacks;

    RationaleDialogClickListener(RationaleDialogFragmentCompat compatDialogFragment,
                                 RationaleDialogConfig config,
                                 BwPermissions.PermissionCallbacks callbacks,
                                 BwPermissions.RationaleCallbacks rationaleCallbacks) {

        mHost = compatDialogFragment.getParentFragment() != null
                ? compatDialogFragment.getParentFragment()
                : compatDialogFragment.getActivity();

        mConfig = config;
        mCallbacks = callbacks;
        mRationaleCallbacks = rationaleCallbacks;

    }

    RationaleDialogClickListener(RationaleDialogFragment dialogFragment,
                                 RationaleDialogConfig config,
                                 BwPermissions.PermissionCallbacks callbacks,
                                 BwPermissions.RationaleCallbacks dialogCallback) {

        mHost = dialogFragment.getActivity();

        mConfig = config;
        mCallbacks = callbacks;
        mRationaleCallbacks = dialogCallback;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        int requestCode = mConfig.requestCode;
        if (which == Dialog.BUTTON_POSITIVE) {
            String[] permissions = mConfig.permissions;
            if (mRationaleCallbacks != null) {
                mRationaleCallbacks.onRationaleAccepted(requestCode);
            }
            if (mHost instanceof Fragment) {
                PermissionHelper.newInstance((Fragment) mHost).directRequestPermissions(requestCode, permissions);
            } else if (mHost instanceof Activity) {
                PermissionHelper.newInstance((Activity) mHost).directRequestPermissions(requestCode, permissions);
            } else {
                throw new RuntimeException("Host must be an Activity or Fragment!");
            }
        } else {
            if (mRationaleCallbacks != null) {
                mRationaleCallbacks.onRationaleDenied(requestCode);
            }
            notifyPermissionDenied();
        }
    }

    private void notifyPermissionDenied() {
        if (mCallbacks != null) {
            mCallbacks.onPermissionsDenied(mConfig.requestCode, Arrays.asList(mConfig.permissions));
        }
    }

}
