package com.example.bavithrathangaraj.oneserviceinnovationhackathon.utilities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;

import com.example.bavithrathangaraj.oneserviceinnovationhackathon.R;


/**
Deepak */
public class NavigationUtility {

    /**
     * Method to override the hardware back button action.
     * Custom animation is added to the transition, and the fragment's activity is killed(using finish()).
     * @param fragment The current visible fragment from which the back button is tapped.
     */
    public static void addAnimationToHardwareBackButtonForFragment(final Fragment fragment) {
        /* Request for the focus */
        fragment.getView().setFocusableInTouchMode(true);
        fragment.getView().requestFocus();

        /* Attach the custom action */
        fragment.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        fragment.getActivity().finish();
                        fragment.getActivity().overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * Method to animate an activity to Left.
     * Use it for animating the navigation while starting one activity to another.
     * @param activity
     */
    public static void animateLeft(Activity activity) {
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
