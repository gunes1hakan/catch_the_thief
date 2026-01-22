package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.gridlayout.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView textViewTime;
    TextView textViewScore;
    ImageView[] imageViews;
    Runnable runnable;

    Handler handler;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        score=0;

        GridLayout grid=findViewById(R.id.gridLayout);      //Stored the ImageViews located within the GridLayout into an array
        int n=grid.getChildCount();
        imageViews=new ImageView[n];
        for(int i=0; i<n; i++){
            View child = grid.getChildAt(i);
            imageViews[i] = (ImageView) child;
            imageViews[i].setOnClickListener(this::increaseScore);
        }
        hideImages();

        new CountDownTimer(10000,1000){     //Implemented the countdown timer logic

            @Override
            public void onFinish() {
                handler.removeCallbacks(runnable);
                for(ImageView i:imageViews){
                    i.setVisibility(View.INVISIBLE);
                }
                gameOver();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                textViewTime.setText("Time: "+millisUntilFinished/1000);
            }
        }.start();
        textViewTime=findViewById(R.id.textViewTime);
        textViewScore=findViewById(R.id.textViewScore);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void increaseScore(View view){       //onclick method of all imageViews
        score++;
        textViewScore.setText("Score: "+score);
    }

    public void hideImages(){       //Created a motion effect by successively activating the thief image in changing grid positions.
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                for(ImageView i:imageViews){
                    i.setVisibility(View.INVISIBLE);
                }
                Random random = new Random();
                int randomNumber = random.nextInt(16);
                imageViews[randomNumber].setVisibility(View.VISIBLE);
                handler.postDelayed(runnable,500);
            }
        };
        handler.post(runnable);

    }

    public void gameOver(){     //The method executed at game over
        saveTop3(score);
        showTop3OnOverlay(score);
    }

    public void saveTop3(int newScore){     //Saves the top 3 high scores
        SharedPreferences sharedPreferences=getSharedPreferences("com.example.myapplication", Context.MODE_PRIVATE);
        int[] a={
                sharedPreferences.getInt("best1",0),
                sharedPreferences.getInt("best2",0),
                sharedPreferences.getInt("best3",0),
                newScore
        };
        Arrays.sort(a);
        sharedPreferences.edit().
                          putInt("best1",a[3]).
                          putInt("best2",a[2]).
                          putInt("best3",a[1]).
                          apply();
    }

    public void showTop3OnOverlay(int newScore){        //Enabled the display of the top 3 high scores on the screen
        SharedPreferences sp=getSharedPreferences("com.example.myapplication",Context.MODE_PRIVATE);

        ((TextView) findViewById(R.id.best1)).setText("1) "+sp.getInt("best1",0));
        ((TextView) findViewById(R.id.best2)).setText("2) "+sp.getInt("best2",0));
        ((TextView) findViewById(R.id.best3)).setText("3) "+sp.getInt("best3",0));
        ((TextView) findViewById(R.id.yourScore)).setText("Your Score: "+newScore);

        findViewById(R.id.gameOverOverlay).setVisibility(View.VISIBLE);
    }

    public void restartGame(View view){     //Defined the onClick event handler for the restart button to reset game state
        findViewById(R.id.gameOverOverlay).setVisibility(View.GONE);
        Intent intent=getIntent();
        finish();
        startActivity(intent);
    }

}