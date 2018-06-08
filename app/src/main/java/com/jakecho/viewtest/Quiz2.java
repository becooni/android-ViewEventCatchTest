package com.jakecho.viewtest;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Jake
 * Created by Jake on 2018-06-06 006.
 */
public class Quiz2 {

    private static Intent intent;

    public static void start(Activity activity) {
        findContentView(activity.getWindow().getDecorView());
//        intent = new Intent(activity, TouchTrackingService.class);
//        activity.startService(intent);
    }

    // find ContentFrameLayout view group
    private static void findContentView(View parent) {
        if (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) parent;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childView = viewGroup.getChildAt(i);
                if (parent.getClass().getSimpleName().equals("ContentFrameLayout")) {
                    printViewHasListener(childView);
                    break;
                } else {
                    findContentView(childView);
                }
            }
        }
    }

    private static void printViewHasListener(final View parent) {
        if (parent instanceof TextView) {
            if (parent.hasOnClickListeners()) {
                Log.d("Quiz 2", String.valueOf(((TextView) parent).getText()));
            }
        }

        if (parent instanceof ViewGroup) {

            ViewGroup viewGroup = (ViewGroup) parent;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                printViewHasListener(viewGroup.getChildAt(i));
            }
        }
    }

    public static void unregister(Activity activity) {
        activity.stopService(intent);
        intent = null;
    }

}
