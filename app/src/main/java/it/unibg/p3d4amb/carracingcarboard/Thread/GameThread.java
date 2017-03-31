package it.unibg.p3d4amb.carracingcarboard.Thread;

import android.app.Activity;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import it.unibg.p3d4amb.carracingcarboard.Animation.AnimationEnemies;
import it.unibg.p3d4amb.carracingcarboard.Animation.AnimationTarget;
import it.unibg.p3d4amb.carracingcarboard.DB.PostCall;
import it.unibg.p3d4amb.carracingcarboard.Manager.Enum.Eye;
import it.unibg.p3d4amb.carracingcarboard.Manager.Enum.TypeCall;
import it.unibg.p3d4amb.carracingcarboard.Manager.GameManager;
import it.unibg.p3d4amb.carracingcarboard.Manager.GlobalData;
import it.unibg.p3d4amb.carracingcarboard.Manager.PenalizationEnemyManager;
import it.unibg.p3d4amb.carracingcarboard.R;

import java.util.ArrayList;

/**
 * Created by matteo on 27/01/2015.
 */
public class GameThread extends Thread{

    public GameManager gameManager;
    private AnimationEnemies animationEnemies;
    private AnimationTarget animationTarget;


    private ArrayList<ImageView> enemyLeftLane1;
    private ArrayList<ImageView> enemyLeftLane2;
    private ArrayList<ImageView> enemyLeftLane3;
    private ArrayList<ImageView> enemyRightLane1;
    private ArrayList<ImageView> enemyRightLane2;
    private ArrayList<ImageView> enemyRightLane3;


    private ImageView target1;
    private ImageView target2;
    private ImageView target3;
    private TextView t1;
    private TextView t2;
    private TextView textLevelLeft;
    private TextView textLifeLeft;
    private TextView textScoreLeft;
    private TextView textLevelRight;
    private TextView textLifeRight;
    private TextView textScoreRight;
    private TextView textLevelGameOverLeft;
    private TextView textLevelGameOverRight;
    private TextView textScoreGameOverLeft;
    private TextView textScoreGameOverRight;
    private boolean state;
    private Activity activity;
    private int pick;
    private int size;
    private int i;
    private GlobalData globalData;
    private PenalizationEnemyManager penalizationManager;
    private Eye eye;
    private RelativeLayout relativeLayoutAnimationLeft;
    private RelativeLayout relativeLayoutAnimationRight;
    private RelativeLayout relativeLayoutLeft;
    private RelativeLayout relativeLayoutRight;
    private FrameLayout frameLayoutGameOverLeft;
    private FrameLayout frameLayoutGameOverRight;
    private AnimationExplosionView animationExplosionViewLeft;
    private AnimationExplosionView animationExplosionViewRight;
    private int displayWidth;
    private int displayHeight;
    private String id_user;
    private int countNotInMyLane=0;


    /**
     *
     * @param activity
     * @param text1
     * @param text2
     * @param tLevelLeft
     * @param tLifeLeft
     * @param tScoreLeft
     * @param tLevelRight
     * @param tLifeRight
     * @param tScoreRight
     * @param i1
     * @param i2
     * @param i3
     * @param i4
     * @param i5
     * @param i6
     * @param target1
     * @param target2
     * @param target3
     * @param globalData
     * @param eye
     * @param RLAnimationLeft
     * @param RLAnimationRight
     * @param width
     * @param height
     * @param id_user
     */
    public GameThread(Activity activity, TextView text1, TextView text2, TextView tLevelLeft, TextView tLifeLeft, TextView tScoreLeft,
                      TextView tLevelRight, TextView tLifeRight, TextView tScoreRight, ArrayList<ImageView> i1,ArrayList<ImageView> i2,ArrayList<ImageView> i3,ArrayList<ImageView> i4,ArrayList<ImageView> i5,ArrayList<ImageView> i6,ImageView target1, ImageView target2, ImageView target3,
                      GlobalData globalData, Eye eye, RelativeLayout RLAnimationLeft, RelativeLayout RLAnimationRight,
                      int width, int height,String id_user) {
        this.activity=activity;
        relativeLayoutLeft=(RelativeLayout)activity.findViewById(R.id.relativeLayoutLeft);
        relativeLayoutRight=(RelativeLayout)activity.findViewById(R.id.relativeLayoutRight);

        frameLayoutGameOverLeft=(FrameLayout)activity.findViewById(R.id.frameLayoutGameOverLeft);
        frameLayoutGameOverRight=(FrameLayout)activity.findViewById(R.id.frameLayoutGameOverRight);

        relativeLayoutLeft.removeView(frameLayoutGameOverLeft);
        relativeLayoutRight.removeView(frameLayoutGameOverRight);

        gameManager = new GameManager();
        gameManager.generateGameData();

        animationEnemies = new AnimationEnemies();
        relativeLayoutAnimationLeft=RLAnimationLeft;
        relativeLayoutAnimationRight=RLAnimationRight;
        t1 =text1;
        t2=text2;
        textLifeLeft=tLifeLeft;
        textLevelLeft=tLevelLeft;
        textScoreLeft =tScoreLeft;
        textLifeRight=tLifeRight;
        textLevelRight=tLevelRight;
        textScoreRight =tScoreRight;
        displayHeight=height;
        displayWidth=width;
        enemyLeftLane1=i1;
        enemyLeftLane2=i2;
        enemyLeftLane3=i3;

        enemyRightLane1=i4;
        enemyRightLane2=i5;
        enemyRightLane3=i6;
        animationEnemies.hideImage(enemyLeftLane1.get(0));
        animationEnemies.hideImage(enemyLeftLane2.get(0));
        animationEnemies.hideImage(enemyLeftLane3.get(0));
        animationEnemies.hideImage(enemyLeftLane1.get(1));
        animationEnemies.hideImage(enemyLeftLane2.get(1));
        animationEnemies.hideImage(enemyLeftLane3.get(1));
        animationEnemies.hideImage(enemyLeftLane1.get(2));
        animationEnemies.hideImage(enemyLeftLane2.get(2));
        animationEnemies.hideImage(enemyLeftLane3.get(2));
        animationEnemies.hideImage(enemyLeftLane1.get(3));
        animationEnemies.hideImage(enemyLeftLane2.get(3));
        animationEnemies.hideImage(enemyLeftLane3.get(3));

        animationEnemies.hideImage(enemyRightLane1.get(0));
        animationEnemies.hideImage(enemyRightLane2.get(0));
        animationEnemies.hideImage(enemyRightLane3.get(0));
        animationEnemies.hideImage(enemyRightLane1.get(1));
        animationEnemies.hideImage(enemyRightLane2.get(1));
        animationEnemies.hideImage(enemyRightLane3.get(1));
        animationEnemies.hideImage(enemyRightLane1.get(2));
        animationEnemies.hideImage(enemyRightLane2.get(2));
        animationEnemies.hideImage(enemyRightLane3.get(2));
        animationEnemies.hideImage(enemyRightLane1.get(3));
        animationEnemies.hideImage(enemyRightLane2.get(3));
        animationEnemies.hideImage(enemyRightLane3.get(3));

        this.target1=target1;
        this.target2=target2;
        this.target3=target3;
        this.globalData=globalData;
        animationTarget=new AnimationTarget();
        penalizationManager=new PenalizationEnemyManager(enemyLeftLane1,enemyLeftLane2, enemyLeftLane3,
                enemyRightLane1, enemyRightLane2,  enemyRightLane3, globalData);
        this.eye=eye;
        this.id_user=id_user;



    }

    @Override
    public void run() {
        while(globalData.isRunnable()){
            if(state){
                // Se è vero fai questo
            }else{
                // Se non è vero fai altro
            }
            state = !state;



            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (globalData.getLife() == 0){
                        PostCall postCall= new PostCall(globalData.getScore().toString(),globalData.getLevel().toString(),id_user,t2,true);
                        postCall.myPostCall(TypeCall.REPORT,activity);
                        globalData.setRunnable(false);
                        //todo , HOME PER RIGIOCARE

                        globalData.setGameover(true);

                        textLevelGameOverLeft = (TextView) frameLayoutGameOverLeft.findViewById(R.id.textLevelGameOverLeft);
                        textLevelGameOverLeft.setText("LEVEL: "+(gameManager.getIdLevel()));
                        textLevelGameOverRight = (TextView) frameLayoutGameOverRight.findViewById(R.id.textLevelGameOverRight);
                        textLevelGameOverRight.setText("LEVEL: "+(gameManager.getIdLevel()));
                        textScoreGameOverLeft = (TextView) frameLayoutGameOverLeft.findViewById(R.id.textScoreGameOverLeft);
                        textScoreGameOverLeft.setText("SCORE: "+(globalData.getScore().toString()));
                        textScoreGameOverRight = (TextView) frameLayoutGameOverRight.findViewById(R.id.textScoreGameOverRight);
                        textScoreGameOverRight.setText("SCORE: "+(globalData.getScore().toString()));

                        relativeLayoutLeft.addView(frameLayoutGameOverLeft);
                        relativeLayoutRight.addView(frameLayoutGameOverRight);

                    }

                    else { //only if globalData.getLife() >0

                        globalData.setRunnable(true);

                        if(globalData.isGameover()){
                            globalData.resetLife();
                            gameManager=new GameManager();
                            gameManager.generateGameData();
                            globalData.setGameover(false);
                            i=0;
                        }

                        textLevelLeft.setText("LEVEL: " + gameManager.getIdLevel());
                        textLifeLeft.setText("LIFE: " + globalData.getLife().toString());
                        textScoreLeft.setText("SCORE: " + globalData.getScore().toString());

                        textLevelRight.setText("LEVEL: " + gameManager.getIdLevel());
                        textLifeRight.setText("LIFE: " + globalData.getLife().toString());
                        textScoreRight.setText("SCORE: " + globalData.getScore().toString());

                        //todo da resettare GameManager
                        pick = gameManager.getIdEnemy().get(i).getSelectedLane();
                        size = gameManager.getIdEnemy().size();
                        penalizationManager.penalize(eye);
                        //collision(animationTarget);
                        //onAnimationTimer();

                        if(globalData.getAbsolutePosition()!=pick){
                            countNotInMyLane++;
                        }
                        if(countNotInMyLane>2){
                            pick=globalData.getAbsolutePosition();
                        }
                        if (pick == 1) {
                            if(gameManager.getIdEnemy().get(i).getNumberOfCar()==1){
                                animationEnemies.showImage(enemyLeftLane1.get(0));
                                animationEnemies.showImage(enemyRightLane1.get(0));
                                animationEnemies.animateFrontCarLane1(enemyLeftLane1.get(0), enemyRightLane1.get(0),
                                        displayWidth, displayHeight);
                            }
                            if(gameManager.getIdEnemy().get(i).getNumberOfCar()==2){
                                animationEnemies.showImage(enemyLeftLane1.get(1));
                                animationEnemies.showImage(enemyRightLane1.get(1));
                                animationEnemies.animateFrontCarLane1(enemyLeftLane1.get(1), enemyRightLane1.get(1),
                                        displayWidth, displayHeight);
                            }
                            if(gameManager.getIdEnemy().get(i).getNumberOfCar()==3){
                                animationEnemies.showImage(enemyLeftLane1.get(2));
                                animationEnemies.showImage(enemyRightLane1.get(2));
                                animationEnemies.animateFrontCarLane1(enemyLeftLane1.get(2), enemyRightLane1.get(2),
                                        displayWidth, displayHeight);
                            }
                            if(gameManager.getIdEnemy().get(i).getNumberOfCar()==4){
                                animationEnemies.showImage(enemyLeftLane1.get(3));
                                animationEnemies.showImage(enemyRightLane1.get(3));
                                animationEnemies.animateFrontCarLane1(enemyLeftLane1.get(3), enemyRightLane1.get(3),
                                        displayWidth, displayHeight);
                            }

                            onAnimationTimer();
                            animationTarget.animateTarget1(target1, displayWidth, displayHeight);

                            animationEnemies = new AnimationEnemies();
                        }
                        if (pick == 2) {
                            if(gameManager.getIdEnemy().get(i).getNumberOfCar()==1){
                                animationEnemies.showImage(enemyLeftLane2.get(0));
                                animationEnemies.showImage(enemyRightLane2.get(0));
                                animationEnemies.animateFrontCarLane2(enemyLeftLane2.get(0), enemyRightLane2.get(0),
                                        displayWidth, displayHeight);
                            }
                            if(gameManager.getIdEnemy().get(i).getNumberOfCar()==2){
                                animationEnemies.showImage(enemyLeftLane2.get(1));
                                animationEnemies.showImage(enemyRightLane2.get(1));
                                animationEnemies.animateFrontCarLane2(enemyLeftLane2.get(1), enemyRightLane2.get(1),
                                        displayWidth, displayHeight);
                            }
                            if(gameManager.getIdEnemy().get(i).getNumberOfCar()==3){
                                animationEnemies.showImage(enemyLeftLane2.get(2));
                                animationEnemies.showImage(enemyRightLane2.get(2));
                                animationEnemies.animateFrontCarLane2(enemyLeftLane2.get(2), enemyRightLane2.get(2),
                                        displayWidth, displayHeight);
                            }
                            if(gameManager.getIdEnemy().get(i).getNumberOfCar()==4){
                                animationEnemies.showImage(enemyLeftLane2.get(3));
                                animationEnemies.showImage(enemyRightLane2.get(3));
                                animationEnemies.animateFrontCarLane2(enemyLeftLane2.get(3), enemyRightLane2.get(3),
                                        displayWidth, displayHeight);
                            }

                            onAnimationTimer();

                            animationTarget.animateTarget2(target2, displayWidth, displayHeight);


                            animationEnemies = new AnimationEnemies();


                            // t1.setText("lane 2");
                        }
                        if (pick == 3) {
                            if(gameManager.getIdEnemy().get(i).getNumberOfCar()==1){
                                animationEnemies.showImage(enemyLeftLane3.get(0));
                                animationEnemies.showImage(enemyRightLane3.get(0));
                                animationEnemies.animateFrontCarLane3(enemyLeftLane3.get(0), enemyRightLane3.get(0),
                                        displayWidth, displayHeight);
                            }
                            if(gameManager.getIdEnemy().get(i).getNumberOfCar()==2){
                                animationEnemies.showImage(enemyLeftLane3.get(1));
                                animationEnemies.showImage(enemyRightLane3.get(1));
                                animationEnemies.animateFrontCarLane3(enemyLeftLane3.get(1), enemyRightLane3.get(1),
                                        displayWidth, displayHeight);
                            }
                            if(gameManager.getIdEnemy().get(i).getNumberOfCar()==3){
                                animationEnemies.showImage(enemyLeftLane3.get(2));
                                animationEnemies.showImage(enemyRightLane3.get(2));
                                animationEnemies.animateFrontCarLane3(enemyLeftLane3.get(2), enemyRightLane3.get(2),
                                        displayWidth, displayHeight);
                            }
                            if(gameManager.getIdEnemy().get(i).getNumberOfCar()==4){
                                animationEnemies.showImage(enemyLeftLane3.get(3));
                                animationEnemies.showImage(enemyRightLane3.get(3));
                                animationEnemies.animateFrontCarLane3(enemyLeftLane3.get(3), enemyRightLane3.get(3),
                                        displayWidth, displayHeight);
                            }

                            onAnimationTimer();

                            animationTarget.animateTarget3(target3, displayWidth, displayHeight);


                            animationEnemies = new AnimationEnemies();

                            //t1.setText("lane 3");
                        }

                        //animationEnemies=new AnimationEnemies();
                        animationTarget = new AnimationTarget();
                    }

                }
            });
          try {
                Thread.sleep(gameManager.getInterval());

                i++;
                if(i==size) {
                    gameManager.generateGameData();
                    globalData.increaseLevel();
                    i=0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void onAnimationTimer(){

        animationEnemies.animationSetLane1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(animationExplosionViewLeft!= null || animationExplosionViewLeft!= null) {
                    relativeLayoutAnimationLeft.removeView(animationExplosionViewLeft);
                    relativeLayoutAnimationRight.removeView(animationExplosionViewRight);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationEnemies.animationSetLane2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(animationExplosionViewLeft!= null || animationExplosionViewLeft!= null) {
                    relativeLayoutAnimationLeft.removeView(animationExplosionViewLeft);
                    relativeLayoutAnimationRight.removeView(animationExplosionViewRight);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationEnemies.animationSetLane3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(animationExplosionViewLeft!= null || animationExplosionViewLeft!= null) {
                    relativeLayoutAnimationLeft.removeView(animationExplosionViewLeft);
                    relativeLayoutAnimationRight.removeView(animationExplosionViewRight);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationTarget.animationTarget1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                globalData.setEnd1(false);
                globalData.setEnd2(false);
                globalData.setEnd3(false);
                t1.setText("CREATO ANIMAZIONE 1");

                if(!(globalData.isEnd1() && globalData.getAbsolutePosition()==1) && !(globalData.isEnd2() && globalData.getAbsolutePosition()==2) && !(globalData.isEnd3() && globalData.getAbsolutePosition()==3)){
                    t2.setText("NO COLLISIONE");
                }

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                globalData.setEnd1(true);
                t1.setText("FINITA ANIMAZIONE 1");
                if(globalData.isEnd1() && globalData.getAbsolutePosition()==1){
                    globalData.decreaseLife();
                    textLifeLeft.setText("LIFE: " + globalData.getLife().toString());
                    textLifeRight.setText("LIFE: " + globalData.getLife().toString());
                    t2.setText("COLLISIONE SU 1");
                    animationExplosionViewLeft = new AnimationExplosionView(activity.getApplicationContext());
                    animationExplosionViewRight = new AnimationExplosionView(activity.getApplicationContext());
                    animationExplosionViewLeft.setX((float) (displayWidth*0.001));
                    animationExplosionViewLeft.setY((float) (displayHeight*0.25));
                    animationExplosionViewRight.setX((float) (displayWidth*0.001));
                    animationExplosionViewRight.setY((float) (displayHeight*0.25));
                    relativeLayoutAnimationLeft.addView(animationExplosionViewLeft);
                    relativeLayoutAnimationRight.addView(animationExplosionViewRight);
                    animationEnemies.hideImage(enemyLeftLane1.get(0));
                    animationEnemies.hideImage(enemyRightLane1.get(0));
                    animationEnemies.hideImage(enemyLeftLane1.get(1));
                    animationEnemies.hideImage(enemyRightLane1.get(1));
                    animationEnemies.hideImage(enemyLeftLane1.get(2));
                    animationEnemies.hideImage(enemyRightLane1.get(2));
                    animationEnemies.hideImage(enemyLeftLane1.get(3));
                    animationEnemies.hideImage(enemyRightLane1.get(3));
                }
                else{
                    globalData.increaseScore();
                    textScoreLeft.setText("SCORE: " + globalData.getScore().toString());
                    textScoreRight.setText("SCORE: " + globalData.getScore().toString());
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });

        animationTarget.animationTarget2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                globalData.setEnd1(false);
                globalData.setEnd2(false);
                globalData.setEnd3(false);
                t1.setText("CREATO ANIMAZIONE 2");

                if(!(globalData.isEnd1() && globalData.getAbsolutePosition()==1) && !(globalData.isEnd2() && globalData.getAbsolutePosition()==2) && !(globalData.isEnd3() && globalData.getAbsolutePosition()==3)){
                    t2.setText("NO COLLISIONE");
                }


            }

            @Override
            public void onAnimationEnd(Animation animation) {
                globalData.setEnd2(true);
                t1.setText("FINITA ANIMAZIONE 2");
                if(globalData.isEnd2() && globalData.getAbsolutePosition()==2){
                    globalData.decreaseLife();
                    textLifeLeft.setText("LIFE: " + globalData.getLife().toString());
                    textLifeRight.setText("LIFE: " + globalData.getLife().toString());
                    t2.setText("COLLISIONE SU 2");
                    animationExplosionViewLeft = new AnimationExplosionView(activity.getApplicationContext());
                    animationExplosionViewRight = new AnimationExplosionView(activity.getApplicationContext());
                    animationExplosionViewLeft.setX((float) (displayWidth*0.135));
                    animationExplosionViewLeft.setY((float) (displayHeight*0.25));
                    animationExplosionViewRight.setX((float) (displayWidth*0.135));
                    animationExplosionViewRight.setY((float) (displayHeight*0.25));
                    relativeLayoutAnimationLeft.addView(animationExplosionViewLeft);
                    relativeLayoutAnimationRight.addView(animationExplosionViewRight);
                    animationEnemies.hideImage(enemyLeftLane2.get(0));
                    animationEnemies.hideImage(enemyRightLane2.get(0));
                    animationEnemies.hideImage(enemyLeftLane2.get(1));
                    animationEnemies.hideImage(enemyRightLane2.get(1));
                    animationEnemies.hideImage(enemyLeftLane2.get(2));
                    animationEnemies.hideImage(enemyRightLane2.get(2));
                    animationEnemies.hideImage(enemyLeftLane2.get(3));
                    animationEnemies.hideImage(enemyRightLane2.get(3));
                }
                else{
                    globalData.increaseScore();
                    textScoreLeft.setText("SCORE: " + globalData.getScore().toString());
                    textScoreRight.setText("SCORE: " + globalData.getScore().toString());
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationTarget.animationTarget3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                globalData.setEnd1(false);
                globalData.setEnd2(false);
                globalData.setEnd3(false);
                t1.setText("CREATO ANIMAZIONE 3");

                if(!(globalData.isEnd1() && globalData.getAbsolutePosition()==1) && !(globalData.isEnd2() && globalData.getAbsolutePosition()==2) && !(globalData.isEnd3() && globalData.getAbsolutePosition()==3)){
                    t2.setText("NO COLLISIONE");
                }


            }

            @Override
            public void onAnimationEnd(Animation animation) {
                globalData.setEnd3(true);
                t1.setText("FINITA ANIMAZIONE 3");
                if(globalData.isEnd3() && globalData.getAbsolutePosition()==3){
                    globalData.decreaseLife();
                    t2.setText("COLLISIONE SU 3");
                    textLifeLeft.setText("LIFE: " + globalData.getLife().toString());
                    textLifeRight.setText("LIFE: " + globalData.getLife().toString());
                    animationExplosionViewLeft = new AnimationExplosionView(activity.getApplicationContext());
                    animationExplosionViewRight = new AnimationExplosionView(activity.getApplicationContext());
                    animationExplosionViewLeft.setX((float) (displayWidth*0.3));
                    animationExplosionViewLeft.setY((float) (displayHeight*0.25));
                    animationExplosionViewRight.setX((float) (displayWidth*0.3));
                    animationExplosionViewRight.setY((float) (displayHeight*0.25));
                    relativeLayoutAnimationLeft.addView(animationExplosionViewLeft);
                    relativeLayoutAnimationRight.addView(animationExplosionViewRight);
                    animationEnemies.hideImage(enemyLeftLane3.get(0));
                    animationEnemies.hideImage(enemyRightLane3.get(0));
                    animationEnemies.hideImage(enemyLeftLane3.get(1));
                    animationEnemies.hideImage(enemyRightLane3.get(1));
                    animationEnemies.hideImage(enemyLeftLane3.get(2));
                    animationEnemies.hideImage(enemyRightLane3.get(2));
                    animationEnemies.hideImage(enemyLeftLane3.get(3));
                    animationEnemies.hideImage(enemyRightLane3.get(3));
                }
                else{
                    globalData.increaseScore();
                    textScoreLeft.setText("SCORE: " + globalData.getScore().toString());
                    textScoreRight.setText("SCORE: " + globalData.getScore().toString());
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}