package com.example.fbadspractice;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    // declare ads instances
    private AdView bannerAd;
    private InterstitialAd interstitialAd;
    private final String TAG = MainActivity.class.getSimpleName();
    private NativeAd nativeAd;
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;

    RecyclerView recyclerView;
    ArrayList<String> str=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView) findViewById(R.id.recycler);
//        recyclerView.setNestedScrollingEnabled(false);

        // Initialize the Audience Network SDK for ads
        AudienceNetworkAds.initialize(this);
       // loadAds();
       // loadNativeAd();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        str.add("1");
        str.add("2");
        str.add("3");
        str.add("4");
        str.add("5");
        str.add("6");
        str.add("7");
        str.add("8");
        str.add("9");
        str.add("10");
        str.add("11");
        str.add("12");
        str.add("13");
        str.add("14");
        str.add("15");
//        str.add("16");
//        str.add("17");
//        str.add("18");
//        str.add("19");
//        str.add("20");
//        str.add("21");
//        str.add("22");
//        str.add("23");
//        str.add("24");
//        str.add("25");
//        str.add("26");
//        str.add("27");
//        str.add("28");
//        str.add("29");
//        str.add("30");
//        str.add("31");
//        str.add("32");
//        str.add("33");
//        str.add("34");
        nativeAdLayout = findViewById(R.id.native_ad_container);
        AdSettings.addTestDevice("45fc3d5b-6fd5-4d8b-9b83-05816a55205a");
        nativeAd = new NativeAd(this, "YOUR_PLACEMENT_ID");
        nativeAd.loadAd();
        final String bannerId="IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID";
        bannerAd = new AdView(this, bannerId,AdSize.BANNER_HEIGHT_50);

        bannerAd.loadAd();

        recyclerView.setAdapter(new Adapter(str, MainActivity.this,nativeAd,nativeAdLayout,bannerAd));
        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
                // Toast.makeText(getApplicationContext(), ""+ad, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
                //Toast.makeText(getApplicationContext(), ""+adError, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                recyclerView.setAdapter(new Adapter(str, MainActivity.this,nativeAd,nativeAdLayout,bannerAd));

                // Inflate Native Ad into Container
              //  inflateAd(nativeAd);
                // Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
                // Toast.makeText(getApplicationContext(), ""+ad, Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        if(interstitialAd.isAdLoaded()){
            interstitialAd.show();
        }
        else{
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        if (bannerAd != null) {
            bannerAd.destroy();
        }
//        if (nativeAdView != null) {
//            nativeAdView.destroy();
//        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
    private void loadAds(){
        String bannerId="IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID";
        String interstitialId="YOUR_PLACEMENT_ID";
        bannerAd = new AdView(this, bannerId,AdSize.BANNER_HEIGHT_50);
        interstitialAd = new InterstitialAd(this,interstitialId);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(bannerAd);
        bannerAd.loadAd();

        AdSettings.addTestDevice("45fc3d5b-6fd5-4d8b-9b83-05816a55205a");
        interstitialAd.loadAd();

    }
    private void interstitialActions(){
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Toast.makeText(MainActivity.this, "displayed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Toast.makeText(MainActivity.this, "dismissed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Toast.makeText(MainActivity.this, "error: "+ad+" --"+adError, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Toast.makeText(MainActivity.this, "loaded", Toast.LENGTH_LONG).show();
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Toast.makeText(MainActivity.this, "logged", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void loadNativeAd() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeAd = new NativeAd(this, "YOUR_PLACEMENT_ID");

        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
               // Toast.makeText(getApplicationContext(), ""+ad, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
                //Toast.makeText(getApplicationContext(), ""+adError, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
               // Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
               // Toast.makeText(getApplicationContext(), ""+ad, Toast.LENGTH_LONG).show();

            }
        });
        AdSettings.addTestDevice("45fc3d5b-6fd5-4d8b-9b83-05816a55205a");
        // Request an ad
        nativeAd.loadAd();
    }
    private void inflateAd(NativeAd nativeAd) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(MainActivity.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);
    }
}
