package com.ascalonic.vigr;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by HP on 12-11-2017.
 */



public class AccessManager{

    AppCompatActivity act;  SharedPreferences sharedPref;

    public AccessManager(AppCompatActivity act,String pref_file)
    {
        this.act=act;
        sharedPref = act.getBaseContext().getSharedPreferences(pref_file, act.getBaseContext().MODE_PRIVATE);
    }

    public void setAccessToken(String token)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("ACCESS_TOKEN",token);
        editor.commit();
    }

    public String getAccessToken()
    {
        String ret = sharedPref.getString("ACCESS_TOKEN","102e164eb646802e91ff1d37a60353d1cdabf537");
        return ret;
    }

    public void setPhone(String phone)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("PHONE_NUM",phone);
        editor.commit();
    }

    public String getPhone()
    {
        String ret = sharedPref.getString("PHONE_NUM","9400346491");
        return ret;
    }


}
