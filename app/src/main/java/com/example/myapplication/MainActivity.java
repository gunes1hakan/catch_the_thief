package com.example.myapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.gridlayout.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView textViewTime;
    TextView textViewScore;
    ImageView[] imageViews;
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

    public void hideImages(){       //Set the visibility of the ImageViews to invisible
        for(ImageView i:imageViews){
            i.setVisibility(View.INVISIBLE);
        }
    }

}