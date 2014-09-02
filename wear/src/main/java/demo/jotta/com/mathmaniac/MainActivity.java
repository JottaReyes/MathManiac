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
    private ProgressBar mProgressBar;
    private TextView mOperation;
    private RelativeLayout mRelativeLayout;

    private TextView mTimer;
    private ImageView mLife1;
    private ImageView mLife2;
    private ImageView mLife3;
    private int lives = 3;
    private int secondsCounter = 10;
    int operation=0;
    public  int max = 10000;
    private CountDownTimer mCountDownTimer;
    private Context mContext = this;
    private static final String  PATH_ROBOTO_BOLD = "fonts/Roboto-Bold.ttf";
    private static int BOLD_ID = 1;

    Typeface typeRobotoBold = Typeface.createFromAsset(getAssets(), PATH_ROBOTO_BOLD);


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

                mNumber.setTypeface(typeRobotoBold);
                mOperation.setTypeface(typeRobotoBold);

                //Initialize Game
                mNumber.setText(factory());
                livesCounterRefresher(lives);
                gameTimer(max);

            }
        });
    }



    public void gameTimer(int max){
        secondsCounter = max/1000;
        mCountDownTimer = new CountDownTimer(max,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                secondsCounter--;
                mTimer.setText(Integer.toString(secondsCounter));
            }

            @Override
            public void onFinish() {
                if(lives >= 0){
                    lifeCounter(lives);
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

        return String.valueOf(generatedNumber);

    }


    public int operation(View view){

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

        if(operation == Integer.parseInt(mOperation.getText().toString())){
            gameTimer(6000);
        }else{
            operation = 0;
            mOperation.setText(Integer.toString(operation));
            mNumber.setText(factory());
        }

        return sum;
    }

    private int lifeCounter(int livesCount){
        if(lives == 0){
            lives = -1;
            max = 1;
            gameTimer(max);
        }else{
            livesCount --;
            lives = livesCount;
            livesCounterRefresher(lives);
            mNumber.setText(factory());
            max = 4000;

            gameTimer(max);
        }
        return lives;
    }

    private void livesCounterRefresher(int livesCount){
        if(livesCount == 2){
            mLife3.setVisibility(View.INVISIBLE);
        }else if(livesCount == 1){
            mLife2.setVisibility(View.INVISIBLE);
        }else if(livesCount == 0){
            mLife1.setVisibility(View.INVISIBLE);
        }
    }


}
