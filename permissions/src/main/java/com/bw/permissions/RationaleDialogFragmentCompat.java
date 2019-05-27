package com.bw.permissions;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/5/6
 * <br/>desc  :
 * </pre>
 */
public class RationaleDialogFragmentCompat extends AppCompatDialogFragment {
    public static final String TAG = "RationaleDialogFragmentCompat";

    private BwPermissions.PermissionCallbacks mPermissionCallbacks;
    private BwPermissions.RationaleCallbacks mRationaleCallbacks;

    public static RationaleDialogFragmentCompat newInstance(
            @NonNull String rationaleMsg,
            @NonNull String positiveButton,
            @NonNull String negativeButton,
            @StyleRes int theme,
            int requestCode,
            @NonNull String[] permissions) {

        // Create new Fragment
        RationaleDialogFragmentCompat dialogFragment = new RationaleDialogFragmentCompat();

        // Initialize configuration as arguments
        RationaleDialogConfig config = new RationaleDialogConfig(
                positiveButton, negativeButton, rationaleMsg, theme, requestCode, permissions);
        dialogFragment.setArguments(config.toBundle());

        return dialogFragment;
    }

    /**
     * Version of {@link #show(FragmentManager, String)} that no-ops when an IllegalStateException
     * would otherwise occur.
     */
    public void showAllowingStateLoss(FragmentManager manager, String tag) {
        if (manager.isStateSaved()) {
            return;
        }

        show(manager, tag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() != null) {
            if (getParentFragment() instanceof BwPermissions.PermissionCallbacks) {
                mPermissionCallbacks = (BwPermissions.PermissionCallbacks) getParentFragment();
            }
            if (getParentFragment() instanceof BwPermissions.RationaleCallbacks){
                mRationaleCallbacks = (BwPermissions.RationaleCallbacks) getParentFragment();
            }
        }

        if (context instanceof BwPermissions.PermissionCallbacks) {
            mPermissionCallbacks = (BwPermissions.PermissionCallbacks) context;
        }

        if (context instanceof BwPermissions.RationaleCallbacks) {
            mRationaleCallbacks = (BwPermissions.RationaleCallbacks) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPermissionCallbacks = null;
        mRationaleCallbacks = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Rationale dialog should not be cancelable
        setCancelable(false);

        // Get config from arguments, create click listener
        RationaleDialogConfig config = new RationaleDialogConfig(getArguments());
        RationaleDialogClickListener clickListener =
                new RationaleDialogClickListener(this, config, mPermissionCallbacks, mRationaleCallbacks);

        // Create an AlertDialog
        return config.createSupportDialog(getContext(), clickListener);
    }
}
