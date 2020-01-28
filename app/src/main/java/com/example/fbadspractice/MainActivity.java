package com.example.fbadspractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class MainActivity extends AppCompatActivity {
    private AdView adView;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadAds();

    }

    private void loadAds() {
        String bannerId="IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID";
        String interstitialId="YOUR_PLACEMENT_ID";
        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, bannerId,AdSize.BANNER_HEIGHT_50);
        interstitialAd = new InterstitialAd(this,interstitialId);
        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(MainActivity.this, "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });

        // Request an ad
        adView.loadAd();
        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
//                Log.e(TAG, "Interstitial ad displayed.");
                Toast.makeText(MainActivity.this, "displayed",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
//                Log.e(TAG, "Interstitial ad dismissed.");
                Toast.makeText(MainActivity.this, "dismissed",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
//                Log.e(TAG, "Interstitial ad failed to zload: " + adError.getErrorMessage());
                Toast.makeText(MainActivity.this, "error: "+ad+" --"+adError,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
//                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                Toast.makeText(MainActivity.this, "loaded",
                        Toast.LENGTH_LONG).show();
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
//                Log.d(TAG, "Interstitial ad clicked!");
                Toast.makeText(MainActivity.this, "clicked",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
//                Log.d(TAG, "Interstitial ad impression logged!");
                Toast.makeText(MainActivity.this, "logged",
                        Toast.LENGTH_LONG).show();
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        AdSettings.addTestDevice("d7b0cfda-84e8-409e-ad2f-9a5e4aae2664");
        interstitialAd.loadAd();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}
