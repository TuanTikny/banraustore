package com.tuanbapk.banrau.View.MyAnimation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by buituan on 2017-10-27.
 */

public class myanimation {

    // Hàm xử lí các sự kiện liên qua đến animation

    public static void makeAnimation(Context c,View v, int amindId){
        Animation animation = AnimationUtils.loadAnimation(c, amindId);
        v.startAnimation(animation);
    }
}
