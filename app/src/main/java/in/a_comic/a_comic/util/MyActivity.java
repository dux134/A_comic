package in.a_comic.a_comic.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import in.a_comic.a_comic.SplashScreen;


public class MyActivity extends AppCompatActivity {

    public InterstitialAd mInterstitialAd;
    public static Intent intent;
    private PrefManager prefManager;
    private boolean string;

    public void loadAdvertisement() {
        prefManager = new PrefManager(MyActivity.this);
        mInterstitialAd = newInterstitialAd();

        loadInterstitial();
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getAdId());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //mNextLevelButton.setEnabled(true);
                Toast.makeText(getApplicationContext(),"loaded",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                goToNextLevel();
                if(string)
                    startActivity(intent);
            }
        });
        return interstitialAd;
    }

    private void goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

    private String getAdId() {
        if(SplashScreen.addCount == 1) {
            return prefManager.getAdmobIdDux();
        } else {
            return prefManager.getAdmobId();
        }
//        return "";
    }

    public void showMyAdd(Intent i,boolean newIntent) {
        intent = i;
        string = newIntent;
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd.isLoaded()) {
//                    Toast.makeText(getApplicationContext(), "loaded " + Dashboard.addCount, Toast.LENGTH_SHORT).show();
                    if (SplashScreen.addCount == 0)
                        SplashScreen.addCount = 1;
                    else
                        SplashScreen.addCount = 0;
                    mInterstitialAd.show();
                }


            }
        },1000);

    }

//    private fun loadRewardedVideoAd() {
//        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
//                AdRequest.Builder().build());
//    }
}
