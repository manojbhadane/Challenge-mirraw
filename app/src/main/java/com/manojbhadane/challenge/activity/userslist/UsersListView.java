package com.manojbhadane.challenge.activity.userslist;

import com.manojbhadane.challenge.model.UserModel;

import java.util.ArrayList;

/**
 * Created by manoj.bhadane on 16-12-2017.
 */
public interface UsersListView
{
    public void setList(ArrayList<UserModel> arrayList);

    public void showRetryOptions(boolean shouldShow);

    public void showProgress(boolean shouldShow);

}
