package demo.jotta.com.mathmaniac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {


    private TextView mNumber;
    private TextView mOperation;
    private RelativeLayout mRelativeLayout;

    private TextView mTimer;
    private ImageView mLife1;
    private ImageView mLife2;
    private ImageView mLife3;
    private boolean isHeartOneVisible = true;
    private boolean isHeartTwoVisible = true;
    private boolean isHeartThreeVisible = true;
    private int lives = 3;
    private long secondsCounter;
    int operation=0;
    private int extraLives=0;
    long extraSeconds = 0;
    public  int maxTime = 20000;
    private CountDownTimer mCountDownTimer;
    private Context mContext = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mNumber = (TextView) stub.findViewById(R.id.textViewNumber);
                mOperation = (TextView) stub.findViewById(R.id.textViewOperation);
                mRelativeLayout = (RelativeLayout) stub.findViewById(R.id.gameBoard);
                mTimer = (TextView) stub.findViewById(R.id.textViewTimer);
                mLife1 = (ImageView) stub.findViewById(R.id.img_life1);
                mLife2 = (ImageView) stub.findViewById(R.id.img_life2);
                mLife3 = (ImageView) stub.findViewById(R.id.img_life3);

//
                //Initialize Game
                mNumber.setText(factory());
                livesCounterRefresher(lives);
                gameTimer(maxTime);

            }
        });
    }



    public void gameTimer(long max){

        mCountDownTimer = new CountDownTimer(max,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                    secondsCounter = millisUntilFinished/1000;
                    extraSeconds = secondsCounter;
                    mTimer.setText("Remaining: " + Long.toString(secondsCounter));
            }

            @Override
            public void onFinish() {
                if(lives >= 0){
                    lifeCounter(lives);
                    gameTimer(5000);
                }else if (lives<0){
                    Toast.makeText(mContext, "You Lose!!" +" " + ("\ud83d\ude1e"), Toast.LENGTH_SHORT).show();
                    mRelativeLayout.removeAllViews();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }

            }
        };
        mCountDownTimer.start();

    }

    public String factory(){

        Random random = new Random();
        int Low = 1;
        int High = 100;
        int generatedNumber = random.nextInt(High-Low) + Low;

        mOperation.setText(String.valueOf(0));

        return String.valueOf(generatedNumber);


    }


    public void operation(View view){

        int sum =0;

        switch (view.getId()){
            case R.id.btn_1:
                sum = 1;
                mOperation.setText(Integer.toString(operation));
                break;
            case R.id.btn_5:
                sum = 5;
                mOperation.setText(Integer.toString(operation));
                break;
            case R.id.btn_10:
                sum = 10;
                mOperation.setText(Integer.toString(operation));
                break;
            case R.id.btn_20:
                sum = 20;
                mOperation.setText(Integer.toString(operation));
                break;
        }

        operation += sum;
        mOperation.setText(Integer.toString(operation));

        String actualValue = mNumber.getText().toString();

        if(actualValue.toString().equals(mNumber.getText().toString())){
            if(operation == Integer.parseInt(mNumber.getText().toString())){
                extraLives++;

                if(extraLives >5){
                    if(lives <=3){
                        lives++;
                        rewardLives();
                        extraLives = 0;
                    }
                }

                mOperation.setText(String.valueOf(0));
                operation = 0;
                mNumber.setText(factory());
                long extraTime = (extraSeconds * 1000) + 5000;
                gameTimer(extraTime);
            }else if (operation > Integer.parseInt(mNumber.getText().toString())){
                operation = 0;
                mOperation.setText(Integer.toString(operation));
                mNumber.setText(factory());
            }
        }else{
            operation =0;
            mOperation.setText(Integer.toString(operation));
            mNumber.setText(factory());

        }
    }

    private int lifeCounter(int livesCount){
        if(lives == 0){
            lives = -1;
            maxTime = 1;
            gameTimer(maxTime);
        }else{
            livesCount --;
            lives = livesCount;
            livesCounterRefresher(lives);
            mNumber.setText(factory());
        }
        return lives;
    }

    private void livesCounterRefresher(int livesCount){
        if(livesCount == 2){
            mLife3.setVisibility(View.INVISIBLE);
            isHeartThreeVisible = false;
        }else if(livesCount == 1){
            mLife2.setVisibility(View.INVISIBLE);
            isHeartTwoVisible = false;
        }else if(livesCount == 0){
            mLife1.setVisibility(View.INVISIBLE);
            isHeartOneVisible = false;
        }
    }

    private void rewardLives(){
        lives++;

        if(!isHeartThreeVisible && isHeartTwoVisible && isHeartOneVisible){
            mLife3.setVisibility(View.VISIBLE);
            lives++;
            isHeartThreeVisible = true;
        }else if(!isHeartTwoVisible && isHeartOneVisible){
            mLife2.setVisibility(View.VISIBLE);
            lives++;
            isHeartTwoVisible = true;
        }else if(!isHeartOneVisible && !isHeartTwoVisible && !isHeartThreeVisible){
            mLife1.setVisibility(View.VISIBLE);
            lives++;
            isHeartOneVisible = true;
        }
    }


}
