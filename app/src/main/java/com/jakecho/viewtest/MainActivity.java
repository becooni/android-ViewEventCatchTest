package com.jakecho.viewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static int MAX_VIEW_COUNT = 2000;

    private static int MAX_LISTENER_COUNT = (int) (MAX_VIEW_COUNT * 0.3);

    private List<View> mViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        addRandomizeListener();
        findView();
    }

    // Q.1 Create views
    private void initView() {
        mViewList = new ArrayList<>(MAX_VIEW_COUNT);

        for (int i = 0; i < MAX_VIEW_COUNT; i++) {
            int random = new Random().nextInt(2);

            if (random == 0) {
                TextView textView = new TextView(this);
                textView.setText("TextView " + i);
                mViewList.add(textView);
            } else if (random == 1) {
                Button button = new Button(this);
                button.setText("Button " + i);
                mViewList.add(button);
            }
        }
    }

    // Q.1 Add listener
    private void addRandomizeListener() {

        int count = 0;

        while (count < MAX_LISTENER_COUNT) {

            int random = new Random().nextInt(MAX_VIEW_COUNT);

            if (!mViewList.get(random).hasOnClickListeners()) {
                setOnClickListener(random);
                count++;
            }
        }
    }

    private void setOnClickListener(int index) {
        mViewList.get(index).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // click !!

            }
        });
    }

    // Q.2 Find view
    private void findView() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();



        for (View view : mViewList) {
            view.measure(0, 0);

            if ((view.getX() <= touchX && touchX <= view.getMeasuredWidth())
                && (view.getY() <= touchY && touchY <= view.getMeasuredHeight())) {

            }

        }

        mViewList.get(0).hasOnClickListeners();

        return super.onTouchEvent(event);
    }
}