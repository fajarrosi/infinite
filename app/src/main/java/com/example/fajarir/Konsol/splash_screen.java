package com.example.fajarir.Konsol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fajarir.Konsol.data.PrefManager;

/**
 * Created by WIN 8 on 17/08/2017.
 */

public class splash_screen extends AppCompatActivity {
    private TextView txtTitle, txtTitle1;
    private ImageView imgLogo;
    private Animation slideUp, fadeOut;
    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        setContentView(R.layout.activity_splash_screen);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle1 = (TextView) findViewById(R.id.txtTitle1);
        imgLogo = (ImageView) findViewById(R.id.imageLogo);
        slideUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideup_anim);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        imgLogo.setVisibility(View.VISIBLE);
        imgLogo.startAnimation(slideUp);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.startAnimation(slideUp);
        txtTitle1.setVisibility(View.VISIBLE);
        txtTitle.startAnimation(slideUp);

        slideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txtTitle.startAnimation(fadeOut);
                txtTitle1.startAnimation(fadeOut);
                imgLogo.startAnimation(fadeOut);
                Intent intent = new Intent(splash_screen.this,slider_intro.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

//        startActivity(new Intent(splash_screen.this,slider_intro.class));
//        finish();
//        btnGetStarted.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//
//            }
//        });
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(this,slider_intro.class));
        finish();
    }
}
