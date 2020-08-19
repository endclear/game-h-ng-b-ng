package com.example.catchtheball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel,startLabel;
    private ImageView box,orange,black,pink,green;

    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    private int boxY;
    private  int orangeX;
    private int orangeY;
    private int pinkX;
    private int pinkY;
    private int blackX;
    private int blackY;
    private int greenX;
    private int greenY;
    private int score;

    // Speed
    private int boxSpeed;
    private int orangeSpeed;
    private int pinkSpeed;
    private int blackSpeed;
    private  int greenSpeed;

    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private SoundPlayer sound;

    private boolean action_flg= false;
    private boolean start_flg = false;

   /* private Button pause;
    private boolean pause_flg = false;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // pause = (Button)findViewById(R.id.pause_id);

        sound = new SoundPlayer(this);

        scoreLabel = (TextView)findViewById(R.id.score_label);
        startLabel = (TextView)findViewById(R.id.start_label);
        box = (ImageView)findViewById(R.id.box);
        orange = (ImageView)findViewById(R.id.orange);
        black = (ImageView)findViewById(R.id.black);
        pink = (ImageView)findViewById(R.id.pink);
        green = (ImageView)findViewById(R.id.green);

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenHeight=size.y;
        screenWidth = size.x;

        orange.setX(-80);
        orange.setY(-80);
        black.setX(-80);
        black.setY(-80);
        pink.setX(-80);
        pink.setY(-80);
        green.setX(-80);
        green.setY(-80);

        boxSpeed = Math.round(screenWidth / 60);
        orangeSpeed = Math.round(screenWidth / 55);
        pinkSpeed = Math.round(screenWidth / 36);
        blackSpeed = Math.round(screenWidth / 45);
        greenSpeed = Math.round(screenWidth/40);


        scoreLabel.setText("Score : 0");

    }

    public void changePos(){
        hitCheck();

        //orange
        orangeX -= orangeSpeed;
        if (orangeX<0){
            orangeX = screenWidth + 15;
            orangeY = (int)Math.floor(Math.random()*(frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        //black
        blackX -= blackSpeed;
        if (blackX<0){
            blackX = screenWidth + 10;
            blackY = (int)Math.floor(Math.random()*(frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        //pink
        pinkX -= pinkSpeed;
        if (pinkX<0){
            pinkX = screenWidth + 5000;
            pinkY = (int)Math.floor(Math.random()*(frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);

        //green
        greenX -= greenSpeed;
        if (greenX<0){
            greenX = screenWidth + 15;
            greenY = (int)Math.floor(Math.random()*(frameHeight - green.getHeight()));
        }
        green.setX(greenX);
        green.setY(greenY);



        // Move Box
        if (action_flg == true) {

            boxY -= boxSpeed;

        } else {

            boxY += boxSpeed;
        }


        if (boxY < 0) boxY = 0;

        if (boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;

        box.setY(boxY);
        if(score<0)
            score = 0;
        scoreLabel.setText("Score : "+score);

    }

   /* public void hitcheck2(){
        //orange
        int orangeCenterX = orangeX + orange.getWidth()/2;
        int orangeCenterY = orangeY + orange.getHeight()/2;

        //0<= orangeCenterX <= boxWidth
        //boxY <= orangeCenterY <= boxY + boxHeight
        if(0<=orangeCenterX && orangeCenterX <= boxSize && boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize){
            orangeX = -10;
            score+= 15;
            sound.playHitSound0();
        }
    }*/

    public void hitCheck(){

        //orange
        int orangeCenterX = orangeX + orange.getWidth()/2;
        int orangeCenterY = orangeY + orange.getHeight()/2;

        //0<= orangeCenterX <= boxWidth
        //boxY <= orangeCenterY <= boxY + boxHeight
        if(0<=orangeCenterX && orangeCenterX <= boxSize && boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize){
            orangeX = -10;
            score+= 15;
            sound.playHitSound0();
        }

        //pink
        int pinkCenterX = pinkX + pink.getWidth()/2;
        int pinkCenterY = pinkY + pink.getHeight()/2;


        if(0<=pinkCenterX && pinkCenterX <= boxSize && boxY <= pinkCenterY && pinkCenterY <= boxY + boxSize){
            pinkX = -10;
            score+= 30;
            sound.playHitSound0();
        }

        //green
        int greenCenterX = greenX + green.getWidth()/2;
        int greenCenterY = greenY + green.getHeight()/2;


        if(0<=greenCenterX && greenCenterX <= boxSize && boxY <= greenCenterY && greenCenterY <= boxY + boxSize){
            greenX = -10;
            score -=5;
            sound.playHit2Sound0();
        }

        //black
        int blackCenterX = blackX + black.getWidth()/2;
        int blackCenterY = blackY + black.getHeight()/2;


        if(0<=blackCenterX && blackCenterX <= boxSize && boxY <= blackCenterY && blackCenterY <= boxY + boxSize){
            timer.cancel();
            timer = null;
            sound.playOverSound0();

            //show ket qua

            Intent intent = new Intent(getApplicationContext(), result.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
        }
    }

    public boolean onTouchEvent(MotionEvent me){
        if(start_flg == false){
            start_flg = true;

            FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            boxY = (int)box.getY();
            boxSize = box.getHeight();


            startLabel.setVisibility(View.GONE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0,25);
        }
        if (me.getAction()==MotionEvent.ACTION_DOWN){
            action_flg=true;
        }
        else if (me.getAction()==MotionEvent.ACTION_UP){
            action_flg=false;
        }

        return true;
    }

    //nút pause
    /*
    public void pauseAction(View view){


        if (pause_flg == false) {

            pause_flg = true;

            // stop
            timer.cancel();
            timer = null;

            // Change Text
            pause.setText("START");


        } else {

            pause_flg = false;

            // Change  Text.
            pause.setText("PAUSE");

            //Start
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 30);

        }
    }*/

    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
        if (event.getAction()== KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
