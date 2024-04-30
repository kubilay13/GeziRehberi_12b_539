package com.example.gezirehberi_12b_539;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuInflater;
import android.view.View;
import com.example.gezirehberi_12b_539.databinding.ActivityLogoBinding;
import java.util.Timer;

public class Activity_Logo extends AppCompatActivity {

    private ActivityLogoBinding activityLogoBinding;
    ZamanSayaci timer;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogoBinding = ActivityLogoBinding.inflate(getLayoutInflater());
        View view = activityLogoBinding.getRoot();
        setContentView(view);
        timer = new ZamanSayaci(5000,1000);
        i=0;
        timer.start();
    }
    class ZamanSayaci extends CountDownTimer{
        public ZamanSayaci(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long l) {
            i++;
        }
        @Override
        public void onFinish() {
            Intent intent = new Intent(Activity_Logo.this,MainActivity.class);
            startActivity(intent);
        }
    }
}