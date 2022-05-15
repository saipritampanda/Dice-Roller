package com.nightxstudio.diceroller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Calendar;

public class ConnectWithUs extends AppCompatActivity {

    ImageButton instagramButton;
    ImageButton twitterButton;
    TextView year;

    private AdView mAdView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_with_us);

        instagramButton = findViewById(R.id.instagramButton);
        twitterButton = findViewById(R.id.twitterButton);
        year = findViewById(R.id.year);

        instagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.instagram.com/saipritampanda/");
            }
        });

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://twitter.com/SaiPritamPanda1");
            }
        });

        int currentYear = Calendar. getInstance(). get(Calendar. YEAR);
        year.setText("© " + currentYear + " DiceRoller ");


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

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW , uri));
    }
}