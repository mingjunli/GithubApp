package com.anly.githubapp.ui.module.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.anly.githubapp.R;
import com.anly.githubapp.data.pref.AccountPref;
import com.anly.githubapp.data.pref.AppPref;
import com.anly.githubapp.ui.module.account.LoginActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by mingjun on 16/8/9.
 */
public class IntroduceActivity extends AppIntro {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, IntroduceActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(
                "Code is everything",
                "Read the fucking source code.",
                R.drawable.ic_github,
                getResources().getColor(R.color.md_lime_500),
                getResources().getColor(R.color.md_pink_900),
                getResources().getColor(R.color.md_pink_400)
        ));

        addSlide(AppIntroFragment.newInstance(
                "Let's coding",
                "Talk is cheap, show me the code",
                R.drawable.ic_github,
                getResources().getColor(R.color.md_yellow_700),
                getResources().getColor(R.color.md_pink_900),
                getResources().getColor(R.color.md_pink_400)
        ));
        addSlide(AppIntroFragment.newInstance(
                "Well, well",
                "Find the best code on the Github.",
                R.drawable.ic_github,
                getResources().getColor(R.color.md_teal_A100),
                getResources().getColor(R.color.md_pink_900),
                getResources().getColor(R.color.md_pink_400)
        ));

        showSkipButton(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        AppPref.setAlreadyRun(this);

        if (AccountPref.isLogon(this)) {
            MainActivity.launch(this);
        }
        else {
            LoginActivity.launch(this);
        }
    }
}
