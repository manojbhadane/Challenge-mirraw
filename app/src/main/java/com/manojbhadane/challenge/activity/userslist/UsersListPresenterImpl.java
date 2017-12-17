package com.manojbhadane.challenge.activity.userslist;

import com.manojbhadane.challenge.api.JsonRequest;
import com.manojbhadane.challenge.app.App;
import com.manojbhadane.challenge.app.Constant;
import com.manojbhadane.challenge.listener.ApiResponceListener;
import com.manojbhadane.challenge.model.ResponceModel;
import com.manojbhadane.challenge.model.UserModel;
import com.manojbhadane.challenge.utils.ConnectionManager;

import java.util.ArrayList;

/**
 * Created by manoj.bhadane on 16-12-2017.
 */
public class UsersListPresenterImpl implements UsersListPresenter
{
    private UsersListView mView;
    private boolean mLoading = true;
    private int mLastKnownPage = 1, mTotalPages = 1;
    private int mVisibleThreshold = 3, mPreviousTotal = 0;

    public UsersListPresenterImpl(UsersListView view)
    {
        mView = view;
    }

    @Override
    public void getData()
    {
        if (ConnectionManager.getInstance(App.getInstance()).isConnectingToInternet())
        {
            mView.showRetryOptions(false);
            if (mLastKnownPage <= mTotalPages)
            {
                JsonRequest.getInstance().makeRequest(Constant.URL + mLastKnownPage, ResponceModel.class, new ApiResponceListener()
                {
                    @Override
                    public void onResponce(Object aClass)
                    {

                        ArrayList<UserModel> arrayList = new ArrayList<>();
                        ResponceModel model = (ResponceModel) aClass;
                        arrayList.addAll(model.getData());
                        if (mLastKnownPage != mTotalPages)
                            arrayList.add(null);

                        mTotalPages = model.getTotal_pages();
                        mLastKnownPage = model.getPage() + 1;

                        mView.setList(arrayList);
                        mView.showProgress(false);
                    }
                });
            }
        } else
        {
            mView.showProgress(false);
            mView.showRetryOptions(true);
        }
    }

    @Override public void loadMore(int totalitemcount, int lastvisiableitem)
    {
        if (mLoading)
            if (totalitemcount > mPreviousTotal)
            {
                mLoading = false;
                mPreviousTotal = totalitemcount;
            }

        if (!mLoading && totalitemcount <= (lastvisiableitem + mVisibleThreshold))
            if (!mLoading && totalitemcount <= (lastvisiableitem + 1))
            {
                getData();
                mLoading = true;
            }
    }
}
