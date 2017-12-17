package com.manojbhadane.challenge.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.manojbhadane.challenge.app.App;
import com.manojbhadane.challenge.app.Constant;
import com.manojbhadane.challenge.listener.ApiResponceListener;
import com.manojbhadane.challenge.utils.Utils;

import org.json.JSONObject;

/**
 * Created by manoj.bhadane on 16-12-2017.
 */
public class JsonRequest
{
    private Gson mGson;
    private static JsonRequest mInstance;

    private JsonRequest()
    {
        mGson = new Gson();
    }

    public static synchronized JsonRequest getInstance()
    {
        if (mInstance == null)
            mInstance = new JsonRequest();
        return mInstance;
    }

    public void makeRequest(String url, final Class<?> aClass, final ApiResponceListener listener)
    {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Object model = mGson.fromJson(response.toString(), aClass);
                listener.onResponce(model);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Utils.printLog(error.getMessage().toString());
            }
        });
        App.getInstance().addToRequestQueue(jsObjRequest);
    }
}
