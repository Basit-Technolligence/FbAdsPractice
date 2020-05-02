package com.example.fbadspractice;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<String> str;
    Activity activity;
    NativeAd nativeAd;
    List<View> clickableViews = new ArrayList<>();
    NativeAdLayout nativeAdLayout;
    AdView bannerAd;
    int adCheck3=3;
    int adCheck5=5;
    int v=0;
    int showingPosition=1;
    int ads=1,previousShowingPosition=0;


    public Adapter(ArrayList<String> str, Activity activity, NativeAd nativeAd, NativeAdLayout nativeAdLayout, AdView bannerAd){
        this.str=str;
        this.nativeAdLayout=nativeAdLayout;
        this.activity=activity;
        this.nativeAd=nativeAd;
        this.bannerAd=bannerAd;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == 1){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_ad_layout, parent, false);
                return new NativeAdditionalHolder(view);

        }else if(viewType == 2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_ad_layout, parent, false);
            return new BannerAdditionalHolder(view);
        } else {
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        if(getItemViewType(position)==1){
            if(nativeAd.isAdLoaded()){
            nativeAd.unregisterView();
            NativeAdditionalHolder new_holder = (NativeAdditionalHolder) holder;
            new_holder.nativeAdTitle.setText(nativeAd.getAdvertiserName());
            new_holder.nativeAdBody.setText(nativeAd.getAdBodyText());
            new_holder.nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
            new_holder.nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            new_holder.nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
            new_holder.sponsoredLabel.setText(nativeAd.getSponsoredTranslation());
            clickableViews.add(new_holder.nativeAdTitle);
            clickableViews.add(new_holder.nativeAdCallToAction);
            nativeAd.registerViewForInteraction(
                    new_holder.adView,
                    new_holder.nativeAdMedia,
                    new_holder.nativeAdIcon,
                    clickableViews);
        }}
        else if(getItemViewType(position)==2){
            if(previousShowingPosition<showingPosition){
                showingPosition=showingPosition+1;
            }else{
                showingPosition=showingPosition-1;
            }
            BannerAdditionalHolder banner_new_holder = (BannerAdditionalHolder) holder;
            if(bannerAd.getParent() != null) {
                ((ViewGroup)bannerAd.getParent()).removeView(bannerAd); // <- fix
//                banner_new_holder.adContainer.removeAllViews();
            }
            banner_new_holder.adContainer.addView(bannerAd);
        }
        else{

            if(position>5 && nativeAd.isAdLoaded()){
                holder.textView.setText(str.get(position-showingPosition));
                if(position-showingPosition==str.size()-1){
                    previousShowingPosition=showingPosition;
                    showingPosition=showingPosition-1;
                }
            }else{
                previousShowingPosition=0;
                showingPosition=1;
                holder.textView.setText(str.get(position));

            }
        }

    }

    @Override
    public int getItemCount() {
        if(nativeAd.isAdLoaded()){
            if(str.size()<=5){
                v=str.size()+1;
                return  v;
            }else{
                v=(str.size()-5)/8;
                int x=str.size()+1+v;
                return  x;
            }
        }else{
                return str.size();
        }

    }
    @Override
    public int getItemViewType(int position) {
        if (nativeAd.isAdLoaded()) {
            if (str.size() <= 5) {
                if (position == str.size()) {
                    return 1;
                } else {
                    return 0;
                }

        }
            else {
                if (position == 5) {
                    return 1;
                } else if(position==14 || position == 23 || position == 32 || position == 41 || position == 50){
                    return 2;
                }
                else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.textView);
        }
    }



    private class NativeAdditionalHolder extends ViewHolder {
        AdIconView nativeAdIcon ;
        TextView nativeAdTitle ;
        MediaView nativeAdMedia;
        TextView nativeAdSocialContext ;
        TextView nativeAdBody ;
        TextView sponsoredLabel ;
        Button nativeAdCallToAction ;
        private LinearLayout adView;
        public NativeAdditionalHolder(View view) {
            super(view);
            LayoutInflater inflater = LayoutInflater.from(activity);
            // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
            adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false);
            nativeAdLayout.addView(adView);

            nativeAdIcon = view.findViewById(R.id.native_ad_icon);
            nativeAdTitle = view.findViewById(R.id.native_ad_title);
            nativeAdMedia = view.findViewById(R.id.native_ad_media);
            nativeAdSocialContext = view.findViewById(R.id.native_ad_social_context);
            nativeAdBody = view.findViewById(R.id.native_ad_body);
            sponsoredLabel = view.findViewById(R.id.native_ad_sponsored_label);
            nativeAdCallToAction = view.findViewById(R.id.native_ad_call_to_action);

        }
    }

    private class BannerAdditionalHolder extends ViewHolder {
        LinearLayout adContainer;

        public BannerAdditionalHolder(View view) {
            super(view);
            adContainer= (LinearLayout) view.findViewById(R.id.banner_ad_container);


        }
    }
}
