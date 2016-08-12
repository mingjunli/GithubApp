package com.anly.githubapp.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anly.githubapp.R;
import com.anly.githubapp.common.util.ImageLoader;
import com.anly.githubapp.data.model.Repo;
import com.flyco.labelview.LabelView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mingjun on 16/8/12.
 */
public class RepoItemView extends FrameLayout {

    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.desc)
    TextView mDesc;
    @BindView(R.id.image)
    ImageView mUserIcon;
    @BindView(R.id.owner)
    TextView mOwner;
    @BindView(R.id.update_time)
    TextView mUpdateTime;
    @BindView(R.id.star)
    TextView mStarCount;
    @BindView(R.id.star_view)
    LinearLayout mStarView;
    @BindView(R.id.label_view)
    LabelView mLabelView;

    public RepoItemView(Context context) {
        super(context);
        init();
    }

    public RepoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RepoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_repo, this);
        ButterKnife.bind(this);
    }

    public void setRepo(Repo repo) {
        mName.setText(repo.getName());
        mDesc.setText(repo.getDescription());

        ImageLoader.load(getContext(), repo.getOwner().getAvatar_url(), mUserIcon);
        mOwner.setText(repo.getOwner().getLogin());

        if (!TextUtils.isEmpty(repo.getLanguage())) {
            mLabelView.setVisibility(VISIBLE);
            mLabelView.setText(repo.getLanguage());
        }
        else {
            mLabelView.setVisibility(GONE);
        }

        mStarCount.setText(String.valueOf(repo.getStargazers_count()));
    }

    @OnClick(R.id.star_view)
    public void onClick() {
    }
}
