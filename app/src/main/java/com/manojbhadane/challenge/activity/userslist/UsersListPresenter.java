package com.manojbhadane.challenge.activity.userslist;

/**
 * Created by manoj.bhadane on 16-12-2017.
 */
public interface UsersListPresenter
{
    public void getData();

    public void loadMore(int totalitemcount, int lastvisiableitem);
}
