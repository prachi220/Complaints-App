package com.developers.prasakshi.iitdcomplaints;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.net.CookieManager;
import java.net.CookiePolicy;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by sarvesh_c on 18-02-2016.
 */
public class CookieManage extends Application {
    CookieManager cookieManager;

    @Override
    public void onCreate(){
        cookieManager = new CookieManager();
        cookieManager.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        super.onCreate();
    }


}
