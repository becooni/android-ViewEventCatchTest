package com.jakecho.viewtest;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;

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
//        activity.getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d("TOUCH TRACKING", "x, y: " + event.getX() + ", " + event.getY());
//
//                if(event.getActionMasked() == MotionEvent.ACTION_OUTSIDE) {
//                    Log.d("TOUCH TRACKING", "x, y: " + event.getRawX() + ", " + event.getRawY());
//                }
//
//                return false;
//            }
//        });
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
//                getUnknownMethod(parent);
                parent.setClickable(false);
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
//        activity.stopService(intent);
//        intent = null;
    }

    private static void getUnknownMethod(View view) {
        // get the nested class `android.view.View$ListenerInfo`
        Field listenerInfoField = null;

        try {
            listenerInfoField = Class.forName("android.view.View").getDeclaredField("mListenerInfo");
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (listenerInfoField != null) {
            listenerInfoField.setAccessible(true);
        }

        Object listenerObject = null;
        try {
            listenerObject = listenerInfoField.get(view);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // get the field mOnClickListener, that holds the listener and cast it to a listener
        Field listenerField = null;
        try {
            listenerField = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnClickListener");
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (listenerField != null && listenerObject != null) {
            try {
                View.OnClickListener myListener = (View.OnClickListener) listenerField.get(listenerObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
