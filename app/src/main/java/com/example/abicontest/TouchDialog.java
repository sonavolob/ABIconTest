package com.example.abicontest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import com.crashlytics.android.Crashlytics;


import io.fabric.sdk.android.Fabric;


public class TouchDialog extends DialogFragment {
    static String TAG = "TouchDialog";


    public static TouchDialog newInstance(String touchType, String selElem) {
        //  Through this we are able to transfer variables to DialogFragment
        TouchDialog frag = new TouchDialog();
        Bundle args = new Bundle();
        args.putSerializable("element", selElem);
        args.putSerializable("touchType", touchType);
        frag.setArguments(args);

        return frag;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Fabric.with(getActivity(), new Crashlytics());
        Log.d(TAG, "onCreateDialog");
        final String selElem = (String) getArguments().getSerializable("element");
        final String touchType = (String) getArguments().getSerializable("touchType");
        String title = "";


        title = getResources().getString(R.string.touch_dialog_title) + ", selElem: '" + selElem + "', touchType: " + touchType ;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setItems(R.array.pref_touch_dialog_entries,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case 0:
                                        Log.d(TAG, "Action 1 clicked: " + which);
                                        break;
                                    case 1:

                                        Log.d(TAG, "Action 2 clicked: " + which);
                                        break;
                                }

                            }
                        });
        return builder.create();

    }

    /**
     * Function - for Dialog width manipulation purposes
     * http://adilatwork.blogspot.mx/2012/11/android-dialogfragment-dialog-sizing.html
     */
    @Override
    public void onStart(){

        super.onStart();

        // safety check
        if (getDialog() == null)
            return;
        /*
        This helps to setup height or width to its defaults.
        http://stackoverflow.com/questions/2306503/how-to-make-an-alert-dialog-fill-90-of-screen-size
        search: "Dianne Hackborn"
         */
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getDialog().getWindow().getAttributes());

        int widthRatio = getResources().getInteger(
                R.integer.dialogFragmentWidthRatio);
        int minWidth = getResources().getInteger(
                R.integer.dialogFragmentMinWidth);
        int widthPixels = getActivity().getResources().getDisplayMetrics().widthPixels;
        //  spread dialog over whole window if it is smaller or equal than dialogFragmentMinWidth
        if (widthPixels <= minWidth) {
            widthRatio = 100;
        }

        int dialogWidth = (int) (widthPixels * (widthRatio / (float) 100)); // specify a value here
        int dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT; // specify a value here

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

}
