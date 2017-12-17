package com.manojbhadane.challenge.activity.userslist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.manojbhadane.challenge.R;
import com.manojbhadane.challenge.activity.base.BaseActivity;
import com.manojbhadane.challenge.adapter.UsersListAdapter;
import com.manojbhadane.challenge.app.App;
import com.manojbhadane.challenge.listener.OnItemClickListener;
import com.manojbhadane.challenge.model.UserModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by manoj.bhadane on 16-12-2017.
 */
public class UsersListActivity extends BaseActivity implements UsersListView, OnItemClickListener
{
    @BindView(R.id.prgbar) ProgressBar mPrgbar;
    @BindView(R.id.txt_error) TextView mTxtError;
    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.lay_no_internet) RelativeLayout mLayRetry;

    private UsersListAdapter mAdapter;
    private UsersListPresenter mPresenter;
    private ArrayList<UserModel> mUsersList;
    private LinearLayoutManager mLayoutManager;

    @Override
    public int getLayoutResId()
    {
        return R.layout.activity_users_list;
    }

    @Override
    public void init()
    {
        setToolbar();
        setToolbarTitle(getString(R.string.title_users_list));

        mUsersList = new ArrayList<>();
        mPresenter = new UsersListPresenterImpl(this);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new UsersListAdapter(mUsersList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        loadData();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                mPresenter.loadMore(mLayoutManager.getItemCount(), mLayoutManager.findLastVisibleItemPosition());
            }
        });
    }

    @OnClick(R.id.btn_retry)
    public void loadData()
    {
        showProgress(true);
        mPresenter.getData();
    }

    @Override public void showRetryOptions(boolean shouldShow)
    {
        mLayRetry.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(shouldShow ? View.GONE : View.VISIBLE);
    }


    @Override public void showProgress(boolean shouldShow)
    {
        mPrgbar.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setList(ArrayList<UserModel> arrayList)
    {
        if (mUsersList.size() > 0 && mUsersList.get(mUsersList.size() - 1) == null)
            mUsersList.remove(mUsersList.size() - 1);
        if (arrayList.size() == 0)
            mTxtError.setText(getString(R.string.error_no_users));
        mUsersList.addAll(arrayList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int posistion)
    {

    }

    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_users_list, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        ((TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTypeface(App.getUbuntuTypeFace());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                if (!searchView.isIconified()) searchView.setIconified(true);
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                mAdapter.getFilter().filter(s.toString().trim());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
