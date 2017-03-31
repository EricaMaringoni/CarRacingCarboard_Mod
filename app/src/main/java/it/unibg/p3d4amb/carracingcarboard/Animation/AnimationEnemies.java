package it.unibg.p3d4amb.carracingcarboard.Animation;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Created by fabio on 24/01/2015.
 */
public class AnimationEnemies extends Animation implements AnimationEnemiesInterface{

    public AnimationSet animationSetLane1=new AnimationSet(false);
    public AnimationSet animationSetLane2=new AnimationSet(false);
    public AnimationSet animationSetLane3=new AnimationSet(false);


    //Create AnimationSet Lane 1



    public void animateFrontCarLane1(final ImageView ivLeft, final ImageView ivRight, int displayWidth, int displayHeight) {

        TranslateAnimation TranslateAnimation1 = new TranslateAnimation(0, ((int) (Animation.RELATIVE_TO_SELF - (displayWidth * 0.04))),
                0,((int) (Animation.RELATIVE_TO_SELF + (displayHeight * 0.08))));
        ScaleAnimation ScaleAnimation1 = new ScaleAnimation(1, 5.2f,
                1, 5.2f,
                Animation.RELATIVE_TO_SELF, 0.9f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animationSetLane1.addAnimation(TranslateAnimation1);
        animationSetLane1.addAnimation(ScaleAnimation1);
        animationSetLane1.setDuration(4600);
        animationSetLane1.setFillAfter(true);

        ivLeft.startAnimation(animationSetLane1);
        ivRight.startAnimation(animationSetLane1);

    }



    public void animateFrontCarLane2(final ImageView ivLeft, final ImageView ivRight, int displayWidth, int displayHeight) {

        TranslateAnimation TranslateAnimation2 = new TranslateAnimation(0, 0, 0, ((int) (Animation.RELATIVE_TO_SELF + (displayHeight * 0.0965))));
        ScaleAnimation ScaleAnimation2 = new ScaleAnimation(1, 4f,
                1, 4f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        //Create AnimationSet Lane 2
        animationSetLane2.addAnimation(TranslateAnimation2);
        animationSetLane2.addAnimation(ScaleAnimation2);
        animationSetLane2.setDuration(3500);
        animationSetLane2.setFillAfter(true);

        ivLeft.startAnimation(animationSetLane2);
        ivRight.startAnimation(animationSetLane2);

    }

    public void animateFrontCarLane3(final ImageView ivLeft, final ImageView ivRight, int displayWidth, int displayHeight) {
        TranslateAnimation TranslateAnimation3 = new TranslateAnimation(0, ((int) (Animation.RELATIVE_TO_SELF + (displayWidth * 0.04))),
                0,((int) (Animation.RELATIVE_TO_SELF + (displayHeight * 0.08))));
        ScaleAnimation ScaleAnimation3 = new ScaleAnimation(1, 5.2f,
                1, 5.2f,
                Animation.RELATIVE_TO_SELF, 0.1f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        //Create AnimationSet Lane 3
        animationSetLane3.addAnimation(TranslateAnimation3);
        animationSetLane3.addAnimation(ScaleAnimation3);
        animationSetLane3.setDuration(4600);
        animationSetLane3.setFillAfter(true);


        ivLeft.startAnimation(animationSetLane3);
        ivRight.startAnimation(animationSetLane3);
    }





    public void hideImage(ImageView img){
        img.setAlpha(0f);
    }

    public void showImage(ImageView img){
        img.setAlpha(255f);
    }
}