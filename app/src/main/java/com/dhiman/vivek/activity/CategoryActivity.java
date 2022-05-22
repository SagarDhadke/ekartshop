package com.dhiman.vivek.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhiman.vivek.R;
import com.dhiman.vivek.adapter.CategoryAdapter;
import com.dhiman.vivek.helper.Constant;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.IUnityBannerListener;
import com.unity3d.services.banners.UnityBanners;

public class CategoryActivity extends AppCompatActivity {

    TextView txtnodata;
    RecyclerView categoryrecycleview;
    public Toolbar toolbar;
    private String GameID = "4273153";
    private boolean testMode = false;
    private String bannerAdPlacement = "vc";
    private String interstitialAdPlacement = "n";

    private Button showInterstitialBtn, showBannerBtn;
    private AdView mAdView;

//    @Override
//    public void onBackPressed() {
////        UnityAds.load(interstitialAdPlacement);
////        DisplayInterstitialAd();
//    UnityBanners.loadBanner(CategoryActivity.this, bannerAdPlacement);
//
//   }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        UnityAds.initialize(CategoryActivity.this, GameID, testMode);
        IUnityAdsListener unityAdsListener = new IUnityAdsListener() {
            @Override
            public void onUnityAdsReady(String s) {
//                Toast.makeText(CategoryActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsStart(String s) {
              //  Toast.makeText(CategoryActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
             //   Toast.makeText(CategoryActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
                Toast.makeText(CategoryActivity.this, unityAdsError.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        UnityAds.setListener(unityAdsListener);

        if (UnityAds.isInitialized()) {
            UnityAds.load(interstitialAdPlacement);
            UnityBanners.loadBanner(CategoryActivity.this, bannerAdPlacement);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DisplayInterstitialAd();
                }
            }, 5000);
        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    UnityAds.load(interstitialAdPlacement);


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            DisplayInterstitialAd();
                            UnityBanners.loadBanner(CategoryActivity.this, bannerAdPlacement);
                        }
                    }, 5000);
                }
            }, 5000);
        }


        IUnityBannerListener iUnityBannerListener = new IUnityBannerListener() {
            @Override
            public void onUnityBannerLoaded(String s, View view) {
                ((ViewGroup) findViewById(R.id.bannerAdLayout)).removeView(view);
                ((ViewGroup) findViewById(R.id.bannerAdLayout)).addView(view);

            }

            @Override
            public void onUnityBannerUnloaded(String s) {

            }

            @Override
            public void onUnityBannerShow(String s) {
                UnityBanners.loadBanner(CategoryActivity.this, bannerAdPlacement);
            }

            @Override
            public void onUnityBannerClick(String s) {

            }

            @Override
            public void onUnityBannerHide(String s) {

            }

            @Override
            public void onUnityBannerError(String s) {

            }
        };

        UnityBanners.setBannerListener(iUnityBannerListener);

//        showInterstitialBtn = findViewById(R.id.showInterstitialAdBtn);
//       showBannerBtn = findViewById(R.id.showBannerAdBtn);
        UnityAds.load(interstitialAdPlacement);
        DisplayInterstitialAd();
//        showInterstitialBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UnityAds.load(interstitialAdPlacement);
//                DisplayInterstitialAd();
//
//            }
//        });


//
//        showBannerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UnityBanners.loadBanner(CategoryActivity.this, bannerAdPlacement);
//            }
//        });

    }


    private void DisplayInterstitialAd() {
        if (UnityAds.isReady(interstitialAdPlacement)) {
            UnityAds.show(CategoryActivity.this, interstitialAdPlacement);
        }



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        try {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.shopbycategory));

            txtnodata = findViewById(R.id.txtnodata);
            categoryrecycleview = findViewById(R.id.categoryrecycleview);
            categoryrecycleview.setLayoutManager(new GridLayoutManager(CategoryActivity.this, Constant.GRIDCOLUMN));
            if (MainActivity.categoryArrayList != null)
                if (MainActivity.categoryArrayList.size() == 0)
                    txtnodata.setVisibility(View.VISIBLE);
                else
                    categoryrecycleview.setAdapter(new CategoryAdapter(CategoryActivity.this, MainActivity.categoryArrayList, R.layout.lyt_category_main, "cate"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
