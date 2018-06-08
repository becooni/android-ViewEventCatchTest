package com.jakecho.viewtest;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Jake
 * Created by Jake on 2018-06-06 006.
 */
public class Quiz2 {

    private void main() throws ClassNotFoundException, NoSuchFieldException {

        Class c = Class.forName("MainActivity");

        Field f = c.getDeclaredField("mViewList");
    }

}
