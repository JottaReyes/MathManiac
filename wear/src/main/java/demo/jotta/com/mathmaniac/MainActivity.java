package demo.jotta.com.mathmaniac;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {


    private TextView mNumber;
    private ProgressBar mProgressBar;
    private TextView mOperation;
    private Button btn1;
    private Button btn5;
    private Button btn10;
    private Button btn20;
    private int i;
    public  int max;
    int sumatoria=0;
    private CountDownTimer mCountDownTimer;
    private Context mContext;


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
                btn1 = (Button) stub.findViewById(R.id.btn_1);
                btn5 = (Button) stub.findViewById(R.id.btn_5);
                btn10 = (Button) stub.findViewById(R.id.btn_10);
                btn20 = (Button) stub.findViewById(R.id.btn_20);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int cont = operation(v);
                        sumatoria = cont;
                        mOperation.setText(String.valueOf(sumatoria));
                    }
                });

                mNumber.setText(factory());

                mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
                mProgressBar.setProgress(i);
                max = 10000;
                mCountDownTimer=new CountDownTimer(max,1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                        if(mNumber.getText().toString() == String.valueOf(sumatoria)){
                            Toast.makeText(mContext, "You Win!!", Toast.LENGTH_LONG).show();
                        }else {
                            onFinish();
                        }
                        i++;
                        mProgressBar.setProgress(i);

                    }

                    @Override
                    public void onFinish() {

                        Toast.makeText(mContext, "You Loose!!", Toast.LENGTH_LONG).show();

                        i++;
                        mProgressBar.setProgress(i);
                    }
                };
                mCountDownTimer.start();

            }
        });
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
        int operation=0;

        switch (view.getId()){
            case R.id.btn_1:
                sum = 1;
                operation = sum + operation;
                return operation;
            case R.id.btn_5:
                sum = 5;
                operation = sum + operation;
                return operation;
            case R.id.btn_10:
                sum = operation;
                operation = sum + operation;
                return operation;
            case R.id.btn_20:
                sum = 20;
                operation = sum + operation;
                return operation;
        }
        mOperation.setText(operation);
        return sum;
    }
}
