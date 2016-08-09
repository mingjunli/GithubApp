package com.anly.githubapp.ui.module.main;

import com.anly.githubapp.R;
import com.anly.githubapp.data.pref.AccountPref;
import com.anly.githubapp.data.pref.AppPref;
import com.anly.githubapp.ui.module.account.LoginActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * Created by mingjun on 16/8/8.
 */
public class WelcomeActivity extends AwesomeSplash {

    @Override
    public void initSplash(ConfigSplash configSplash) {

        configSplash.setBackgroundColor(R.color.md_cyan_300);
        configSplash.setAnimCircularRevealDuration(800); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        configSplash.setLogoSplash(R.drawable.ic_github); //or any other drawable
        configSplash.setAnimLogoSplashDuration(800); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce);

        //Customize Title
        configSplash.setTitleSplash(getString(R.string.app_name));
        configSplash.setTitleTextColor(R.color.md_white_1000);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(1500);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
    }

    @Override
    public void animationsFinished() {
        if (AppPref.isFirstRunning(this)) {
            IntroduceActivity.launch(this);
        }
        else {
            if (AccountPref.isLogon(this)) {
                MainActivity.launch(this);
            }
            else {
                LoginActivity.launch(this);
            }
        }
        finish();
    }
}
