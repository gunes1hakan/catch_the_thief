package com.example.myapplication;

import android.content.DialogInterface;
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


}