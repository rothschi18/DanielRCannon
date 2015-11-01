package cs301.cannon;

import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

import cs301.cannon.Animator;

public class Cannon implements Animator {

    // instance variables
    private int count = 0;                  // counts the number of logical clock ticks
    private int score = 0;                  //Keeps track of the players score
    private boolean goBackwards = false;    //whether clock is ticking backwards
    private boolean isDrawn = false;        //Draw the targets only once
    public boolean reloadAndFire = false;   //Determines whether the player can fire
    boolean isFlying = false;               //Keep track of whether their is already a shot made
    int startX = 150;                       //Initial starting x
    int startY = 1200;                      //Initial starting y
    //Set a default ball w/ angle pi/4 and normal gravity
    public static Ball shot = new Ball(100, 0, 50, 3.1415/4, 9.81/5);

    private int x1;
    private int x2;
    private int x3;

    private int y1;
    private int y2;
    private int y3;

    /**
     * Interval between animation frames: .03 seconds (i.e., about 33 times
     * per second).
     *
     * @return the time interval between frames, in milliseconds.
     */
    public int interval() {
        return 30;
    }

    /**
     * The background color: a light blue.
     *
     * @return the background color onto which we will draw the image.
     */
    public int backgroundColor() {
        // create/return the background color
        return Color.rgb(180, 200, 255);
    }

    /*
    *   Draws the targets to the canvas
     */
    public void drawTargets(Canvas g)
    {
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);

        Random rand = new Random();
        int x1 = rand.nextInt(1500 - 200 + 1) + 200;
        int x2 = rand.nextInt(1500 - 200 + 1) + 200;
        int x3 = rand.nextInt(1500 - 200 + 1) + 200;

        int y1 = rand.nextInt(1000 + 1);
        int y1 = rand.nextInt(1000 + 1);
        int y1 = rand.nextInt(1000 + 1);
        g.drawCircle(x, 100, 100, blackPaint);

        g.drawCircle(x, 100, 100,blackPaint);
        g.drawCircle(x, 300, 50, blackPaint);
        g.drawCircle(1200, 700, 25, blackPaint);
    }


    /**
     * Action to perform on clock tick
     *
     * @param g the graphics object on which to draw
     */
    public void tick(Canvas g) {
        //Draw the targets to shoot at
        if(!isDrawn)
            drawTargets(g);

        drawCannon(g);


        //draw the cannon

        //define the paint to use
        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        // bump our count either up or down by one, depending on whether
        // we are in "backwards mode".
        if(reloadAndFire) {
            count++;


            int x = (int) (shot.getxVelocity() * Math.cos(shot.getTheta()) * count) + startX;
            int y = -(int) (shot.getVelocity() * Math.sin(shot.getTheta()) * count) + (int) (.5 * shot.getGravity() * count * count) + startY;
            shot.setX(x);
            shot.setY(y);



            g.drawCircle(shot.getX(), shot.getY(), 20, redPaint);
        }
        //If the shot is outside of the desired boundaries clear the shot from the screen and mark that we are reloading
        if(shot.getY()>1240){
            count = 0;
            startX = shot.getX();
            startY = shot.getY();
            shot.setVelocity(shot.getVelocity()/1.2);
            isFlying = true;
        }
        if(shot.getX()> 1800)
        {
            isFlying = false;
            count = 0;
            reloadAndFire = false;
            shot.setY(0);
            shot.setX(0);
            startX = 150;
            startY = 1200;
        }
        // g.drawCircle(1500, 100, 100,blackPaint);
        //g.drawCircle(700, 300, 50, blackPaint);
        //g.drawCircle(1200, 700, 25, blackPaint);
        if (shot.getX()> 1400 && shot.getX() < 1600 && shot.getY()>0 && shot.getY() < 200) {
            score += 1;
            shot.setY(0);
            shot.setX(0);
            count = 0;
            reloadAndFire = false;
            isFlying = false;
            Paint white = new Paint(Color.rgb(180, 200, 255));
            g.drawCircle(1500, 100, 100,blackPaint);
        }
        if (shot.getX()> 650 && shot.getX() < 750 && shot.getY()>250 && shot.getY() < 350) {
            score += 1;
            shot.setY(0);
            shot.setX(0);
            count = 0;
            reloadAndFire = false;
            isFlying = false;
        }
        if (shot.getX()> 1175 && shot.getX() < 1225 && shot.getY()>675 && shot.getY() < 725) {
            score += 1;
            shot.setY(0);
            shot.setX(0);
            count = 0;
            reloadAndFire = false;
            isFlying = false;
        }


        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        g.drawText("Score = " + score, 200, 50, paint);
        g.drawLine(0, 1250, 1800, 1250, paint);

    }

    private void drawCannon(Canvas g) {
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);



    }

    @Override
    public void reloadAndFire(double velocity, double theta, double gravity) {
        if(!isFlying){
            shot.setGravity(gravity);
            shot.setTheta(theta);
            shot.setVelocity(velocity);
            reloadAndFire = true;
        }
    }

    public void reset(){
        isFlying = false;
        reloadAndFire = false;
        shot.setX(0);
        shot.setY(0);
        count = 0;
        startX = 150;
        startY = 1200;
    }

    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    public boolean doPause() {
        return false;
    }

    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    public boolean doQuit() {
        return false;
    }

    /**
     * reverse the ball's direction when the screen is tapped
     */
    public void onTouch(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            goBackwards = !goBackwards;
        }
    }



}//class TextAnimator

