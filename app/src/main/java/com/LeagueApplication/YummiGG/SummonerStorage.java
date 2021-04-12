package com.LeagueApplication.YummiGG;

import android.util.Log;

import com.LeagueApplication.YummiGG.Models.SummonerObject;

public class SummonerStorage {
    private SummonerObject[] summArr;
    public static final String TAG = "SummonerStorage";

    public SummonerObject[] get() {
        return summArr;
    }
    public void set(SummonerObject[] in) {
        Log.i(TAG, "Summoner array Set");
        this.summArr = in;

    }

    private static final SummonerStorage holder = new SummonerStorage();

    public static SummonerStorage getInstance() {
        return holder;
    }
}

// https://stackoverflow.com/a/4878259