package com.jakecho.viewtest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * @author Jake
 * Created by Jake on 2018-06-09 009.
 */
public class TouchTrackingService extends Service implements View.OnTouchListener {

    private static final String TAG = "TOUCH TRACKING";

    private View view;

    private WindowManager windowManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initView();
    }

    @Override
    public boolean stopService(Intent name) {
        stopSelf();
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (view != null) {
            windowManager.removeViewImmediate(view);
        }
    }

    private void initView() {
        view = new View(this);
//        view.setBackgroundResource(R.color.colorAccent);
        view.setOnTouchListener(this);
        view.setClickable(false);

        int windowType;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            windowType = WindowManager.LayoutParams.TYPE_PHONE;
        else windowType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        WindowManager.LayoutParams windowManagerParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                windowType,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                ,
                PixelFormat.TRANSLUCENT);

        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(view, windowManagerParams);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "x, y: " + event.getX() + ", " + event.getY());
        }
//        view.dispatchTouchEvent(event);

        return false;
    }
}
