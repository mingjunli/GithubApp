package com.anly.githubapp.ui.module.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.anly.githubapp.R;
import com.anly.githubapp.data.model.Repo;
import com.anly.githubapp.di.component.MainComponent;
import com.anly.githubapp.presenter.main.SearchPresenter;
import com.anly.githubapp.ui.base.LceFragment;
import com.anly.githubapp.ui.module.account.LoginActivity;
import com.anly.githubapp.ui.module.main.adapter.RepoListRecyclerAdapter;
import com.anly.githubapp.ui.widget.RxClickableView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by mingjun on 16/7/19.
 */
public class SearchFragment extends LceFragment<ArrayList<Repo>> {

    @BindView(R.id.language_spinner)
    Spinner mLanguageSpinner;
    @BindView(R.id.search_key)
    MaterialEditText mSearchKeyText;
    @BindView(R.id.repo_list)
    RecyclerView mRepoListView;
    @BindView(R.id.search_toolbar)
    Toolbar mSearchToolbar;

    private RepoListRecyclerAdapter mAdapter;

    @Inject
    SearchPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);

        mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void initViews() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setSupportActionBar(mSearchToolbar);
        setHasOptionsMenu(true);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLanguageSpinner.setAdapter(adapter);

        mAdapter = new RepoListRecyclerAdapter(null);
        mRepoListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRepoListView.setAdapter(mAdapter);
    }

    @NonNull
    @Override
    public String getLoadingMessage() {
        return getString(R.string.load_searching);
    }

    @Override
    public void showContent(ArrayList<Repo> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                String key = mSearchKeyText.getText().toString();
                String lang = (String) mLanguageSpinner.getSelectedItem();
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(lang)) {
                    mPresenter.searchRepo(key, lang);
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
