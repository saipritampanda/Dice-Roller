package com.nightxstudio.diceroller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    ImageView errorImage;
    TextView score;
    TextView hint;
    Button roll;

    static boolean gameState = false;
    private AdView mAdView;

    public void onShakeImage() {
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        ImageView image;
        image = findViewById(R.id.errorImage);

        image.startAnimation(shake); // starts animation
    }

    private Timer shakeTimer;
    public void startTimer() {
        shakeTimer = new Timer(); // At this line a new Thread will be created
        shakeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                onShakeImage();
            }
        }, 1000,2000); // delay
    }

    void stopTimer() {
        shakeTimer.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.diceImage);
        errorImage = findViewById(R.id.errorImage);
        score = findViewById(R.id.scoreText);
        hint = findViewById(R.id.hint);
        roll = findViewById(R.id.diceRoll);

        final MediaPlayer mp = MediaPlayer.create(this , R.raw.diceshake);

        startTimer();

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gameState = true;
                stopTimer();
                hint.setVisibility(View.GONE);
                errorImage.setVisibility(View.GONE);

                Random rn = new Random();
                int num = rn.nextInt(6) + 1;
                score.setText(String.valueOf(num));

                mp.start();

                switch (num)
                {
                    case 1:
                        image.setImageResource(R.drawable.dice1);
                        break;
                    case 2:
                        image.setImageResource(R.drawable.dice2);
                        break;
                    case 3:
                        image.setImageResource(R.drawable.dice3);
                        break;
                    case 4:
                        image.setImageResource(R.drawable.dice4);
                        break;
                    case 5:
                        image.setImageResource(R.drawable.dice5);
                        break;
                    case 6:
                        image.setImageResource(R.drawable.dice6);
                        break;
                }

            }
        });

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable actionBackground = new ColorDrawable(Color.parseColor("#FF000000"));
        assert actionBar != null;
        actionBar.setBackgroundDrawable(actionBackground);


//  Ads Components starts from here:

        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-1785962158595876/9196767935");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu , menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.about) {
            Intent ConnectWithUsIntent = new Intent(MainActivity.this, ConnectWithUs.class);
            startActivity(ConnectWithUsIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder backPressedBuilder = new AlertDialog.Builder(this);
        backPressedBuilder.setTitle("Exit");
        backPressedBuilder.setMessage("Are you sure you want to exit ?");
        backPressedBuilder.setCancelable(false);
        backPressedBuilder.setIcon(R.mipmap.ic_launcher);
        backPressedBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        backPressedBuilder.setNegativeButton("No" , null);
        final AlertDialog backPressed = backPressedBuilder.create();
        backPressed.show();
    }
}