package com.example.myapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView textViewTime;
    TextView textViewScore;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        score=0;
        new CountDownTimer(10000,1000){     //Implemented the countdown timer logic

            @Override
            public void onFinish() {

            }

            @Override
            public void onTick(long millisUntilFinished) {
                textViewTime.setText("Time: "+millisUntilFinished/1000);
            }
        };
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

}