package com.anly.githubapp.ui.module.repo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.anly.githubapp.R;
import com.anly.githubapp.data.net.response.Content;
import com.anly.githubapp.ui.base.BaseActivity;
import com.mukesh.MarkdownView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mingjun on 16/9/1.
 */
public class ReadmeActivity extends BaseActivity {

    private static final String EXTRA_README = "extra_readme";

    @BindView(R.id.readme_content)
    MarkdownView mReadmeContent;

    public static void launch(Context context, Content readme) {
        Intent intent = new Intent(context, ReadmeActivity.class);
        intent.putExtra(EXTRA_README, readme);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_readme);
        ButterKnife.bind(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
    }

    private void initViews() {
        Content readmeContent = getIntent().getParcelableExtra(EXTRA_README);
        mReadmeContent.setMarkDownText(readmeContent.content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
