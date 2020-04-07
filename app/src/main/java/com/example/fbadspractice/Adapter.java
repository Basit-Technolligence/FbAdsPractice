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
    int adCheck3=3;
    int adCheck5=5;
    int v=0;

    public Adapter(ArrayList<String> str, Activity activity,NativeAd nativeAd,NativeAdLayout nativeAdLayout){
        this.str=str;
        this.nativeAdLayout=nativeAdLayout;
        this.activity=activity;
        this.nativeAd=nativeAd;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == 1){
            if(nativeAd.isAdLoaded()) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_ad_layout, parent, false);
                return new AdditionalHolder(view);
            }
            else{
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
                return new ViewHolder(view);
            }
        }else {
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        if(getItemViewType(position)==1){
            if(nativeAd.isAdLoaded()){
            nativeAd.unregisterView();
            AdditionalHolder new_holder = (AdditionalHolder) holder;
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
//            holder.textView.setText(str.get(position));
        }}
        else{
//            if(position!=0 && getItemViewType(position-1)==1){
//                holder.textView.setText(str.get(position-1));
//            }
            if(str.size()>position)
            {holder.textView.setText(str.get(position));
            adCheck3=3;
            adCheck5=5;}
            else{
                if(str.size()>5) {
                    if(adCheck5<str.size()){
                    holder.textView.setText(str.get(adCheck5));
                        adCheck5 = adCheck5 + 5;
                    }

                }else{
                    if(adCheck3<=str.size()){
                    holder.textView.setText(str.get(adCheck3));}
                    adCheck3=adCheck3+3;
            }}
        }

    }

    @Override
    public int getItemCount() {
//        int additionalContent = 0;
//        if (str.size() > 0 && LIST_AD_DELTA > 0 && str.size() > LIST_AD_DELTA) {
//            additionalContent = str.size() / LIST_AD_DELTA;
//        }
//        return str.size() + additionalContent;
        if(str.size()>5){
            v=Math.round(str.size()/5);
        }else{
            v=Math.round(str.size()/3);
        }
        return str.size()+v;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.textView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(str.size()>5){
            if (position!=0 && position % 5 == 0)
                return 1;
            else
                return 0;
        }else{
            if (position!=0 && position % 3 == 0)
                return 1;
            else
                return 0;
        }

    }


    private class AdditionalHolder extends ViewHolder {
        AdIconView nativeAdIcon ;
        TextView nativeAdTitle ;
        MediaView nativeAdMedia;
        TextView nativeAdSocialContext ;
        TextView nativeAdBody ;
        TextView sponsoredLabel ;
        Button nativeAdCallToAction ;
        private LinearLayout adView;
        public AdditionalHolder(View view) {
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
}
