package com.anly.githubapp.ui.module.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.anly.githubapp.R;
import com.anly.githubapp.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mingjun on 16/8/15.
 */
public class SettingsActivity extends BaseActivity {

    @BindView(R.id.night_mode_toggle)
    ToggleButton nightModeToggle;
    @BindView(R.id.current_cache)
    TextView currentCache;
    @BindView(R.id.current_version)
    TextView currentVersion;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        setTitle(R.string.settings);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.night_mode, R.id.clear_cache, R.id.upgrade, R.id.about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.night_mode:
                // TODO
                break;

            case R.id.clear_cache:
                // TODO
                break;

            case R.id.upgrade:
                // TODO
                break;

            case R.id.about:
                AboutActivity.launch(this);
                break;
        }
    }
}
