package com.connotation_vibes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
    // Splash activity - opening screen
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();
        background = findViewById(R.id.background);
        // Fade-in animation handling
        // Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_screen_animation);
        // background.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Once the delay is over, the next will happen:
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}
