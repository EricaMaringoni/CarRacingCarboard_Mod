package it.unibg.p3d4amb.carracingcarboard.Thread;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import it.unibg.p3d4amb.carracingcarboard.Activity.SplashActivity;

/**
 * Created by matteo on 02/04/2015.
 */
public class TutorialThread extends  Thread {

    private TextView text;
    private Activity activity;
    private String id_user;
    private int pause=4000;


    public TutorialThread(Activity activity,TextView text,String id_user){
        this.activity=activity;
        this.text=text;
        this.id_user=id_user;
    }


    @Override
    public void run() {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                text.setText("Pick up your phone and connect to the earphones" +'\n'+"(with volume control keys)");

            }
        });
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                text.setText("+ key move to left"+'\n'+"- key move to right");

            }
        });
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText("insert your phone"+'\n'+" in cardboard");


            }
        });
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText("the game will start "+'\n'+"in a few seconds");

            }
        });
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                text.setText("ENJOY ;)");
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent i = new Intent(activity, SplashActivity.class);
        i.putExtra("id_user", id_user);
        activity.startActivity(i);
        activity.finish();

    }
}






