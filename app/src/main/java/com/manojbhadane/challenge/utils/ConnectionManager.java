package com.manojbhadane.challenge.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.manojbhadane.challenge.R;

/**
 * Created by manoj.bhadane on 16-12-2017.
 */
public class ConnectionManager
{
    private Context mContext;
    private static ConnectionManager mInstance;

    private ConnectionManager(Context context)
    {
        this.mContext = context;
    }

    public static synchronized ConnectionManager getInstance(Context context)
    {
        if (mInstance == null)
            mInstance = new ConnectionManager(context);
        return mInstance;
    }

    public boolean isConnectingToInternet()
    {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = cm.getAllNetworkInfo();
        if (info != null)
        {
            for (int i = 0; i < info.length; i++)
            {
                if (info[i].getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isConnectingToInternet(boolean showErrorMsg)
    {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = cm.getAllNetworkInfo();
        if (info != null)
        {
            for (int i = 0; i < info.length; i++)
            {
                if (info[i].getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        Toast.makeText(mContext, mContext.getResources().getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
        return false;
    }
}