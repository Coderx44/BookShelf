package com.mitadt.myapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class Api  {
    private static Api instance;
    private Context context;
    private RequestQueue requestQueue = Volley.newRequestQueue(context);
    String url ="abc";


    public <User> void request(User u){

    }
}