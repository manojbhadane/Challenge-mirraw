package com.manojbhadane.challenge.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.manojbhadane.challenge.R;
import com.manojbhadane.challenge.app.App;
import com.manojbhadane.challenge.utils.Utils;

import butterknife.ButterKnife;

/**
 * Created by manoj.bhadane on 16-12-2017.
 */
public abstract class BaseActivity extends AppCompatActivity
{
    public Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        init();
        Utils.setFont((ViewGroup) findViewById(R.id.parent), App.getUbuntuTypeFace());
    }

    public abstract void init();

    public abstract int getLayoutResId();

    public void setToolbar()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    public Toolbar getToolbar()
    {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    public void showToolbarBackBtn(boolean isShown)
    {
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShown);
            getSupportActionBar().setDisplayShowHomeEnabled(isShown);
        }
    }

    public void setToolbarTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }
}
