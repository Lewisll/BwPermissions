package com.bw.permissions;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/5/6
 * <br/>desc  :
 * </pre>
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class RationaleDialogFragment extends DialogFragment {

    public static final String TAG = "RationaleDialogFragment";

    private BwPermissions.PermissionCallbacks mPermissionCallbacks;
    private BwPermissions.RationaleCallbacks mRationaleCallbacks;
    private boolean mStateSaved = false;

    public static RationaleDialogFragment newInstance(
            @NonNull String positiveButton,
            @NonNull String negativeButton,
            @NonNull String rationaleMsg,
            @StyleRes int theme,
            int requestCode,
            @NonNull String[] permissions) {

        // Create new Fragment
        RationaleDialogFragment dialogFragment = new RationaleDialogFragment();

        // Initialize configuration as arguments
        RationaleDialogConfig config = new RationaleDialogConfig(
                positiveButton, negativeButton, rationaleMsg, theme, requestCode, permissions);
        dialogFragment.setArguments(config.toBundle());

        return dialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && getParentFragment() != null) {
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
    public void onSaveInstanceState(Bundle outState) {
        mStateSaved = true;
        super.onSaveInstanceState(outState);
    }

    /**
     * Version of {@link #show(FragmentManager, String)} that no-ops when an IllegalStateException
     * would otherwise occur.
     */
    public void showAllowingStateLoss(FragmentManager manager, String tag) {
        // API 26 added this convenient method
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.isStateSaved()) {
                return;
            }
        }

        if (mStateSaved) {
            return;
        }

        show(manager, tag);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPermissionCallbacks = null;
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
        return config.createFrameworkDialog(getActivity(), clickListener);
    }

}
