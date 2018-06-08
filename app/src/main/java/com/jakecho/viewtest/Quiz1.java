package com.jakecho.viewtest;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;

public class Quiz1 extends AppCompatActivity {

    private static final int REQUEST_OVERLAY_PERMISSION = 99;

    private static int MAX_VIEW_COUNT = 50;

    private static int MAX_LISTENER_COUNT = (int) (MAX_VIEW_COUNT * 0.3);

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        addRandomizeListener();
        printViews();

        Quiz2.start(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkDrawOverlays();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Quiz2.unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            checkDrawOverlays();
        }
    }

    // Q.1 Create views
    private void initView() {

        mFrameLayout = new FrameLayout(this);
        mFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        for (int i = 0; i < MAX_VIEW_COUNT; i++) {
            int random = new Random().nextInt(2);

            View view;

            if (random == 0) {
                view = new TextView(this);
                ((TextView) view).setText("TextView " + i);
            } else {
                view = new Button(this);
                ((Button) view).setText("Button " + i);
            }

            DisplayMetrics metrics = getScreenSize();

            int width = new Random().nextInt(metrics.widthPixels);
            int height = new Random().nextInt(metrics.heightPixels);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
            params.gravity = Gravity.CENTER;
            params.leftMargin = new Random().nextInt(metrics.widthPixels - width);
            params.topMargin = new Random().nextInt(metrics.heightPixels - height);

            view.setBackgroundResource(R.drawable.border);

            mFrameLayout.addView(view, params);

            setContentView(mFrameLayout);
        }
    }

    private DisplayMetrics getScreenSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    // Q.1 Add listener

    private void addRandomizeListener() {

        int count = 0;

        while (count < MAX_LISTENER_COUNT) {

            int random = new Random().nextInt(MAX_VIEW_COUNT);

            if (!mFrameLayout.getChildAt(random).hasOnClickListeners()) {
                setOnClickListener(random);
                count++;
            }
        }
    }

    private void setOnClickListener(int index) {
        mFrameLayout.getChildAt(index).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // click !!
            }
        });
    }

    private void printViews() {
        for (int i = 0; i < mFrameLayout.getChildCount(); i++) {
            View childView = mFrameLayout.getChildAt(i);
            String hasListener = childView.hasOnClickListeners() ? "Yes" : "No";
            Log.d("Quiz 1", (((TextView) childView).getText() + " has Listener? " + hasListener));
        }
    }

    private void checkDrawOverlays() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(this)) {

            showSnackBar(
                    "[다른 앱 위에 그리기 허용]을 체크해야 앱 사용 가능합니다.",
                    "설정으로 이동",
                    new View.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                    Uri.parse("package:" + getPackageName()));
                            startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION);
                        }
                    });

        }
    }

    private void showSnackBar(String text, String action, View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_INDEFINITE)
                .setAction(action, listener).show();
    }
}