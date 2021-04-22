package com.LeagueApplication.YummiGG;

import android.util.Log;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.core.league.LeagueEntry;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class OriannaHandler extends Thread{

    private String TAG = "OriannaHandler.java";
    private String summmonerNameInput;
    String summonerName, summonerIconUrl;
    int summonerLevel;
    LeagueEntry summonerRankedSolo;

    public OriannaHandler(String summmonerNameInput){
        this.summmonerNameInput = summmonerNameInput;
    }

    @Override
    public void run() {
        Summoner summoner = Orianna.summonerNamed(summmonerNameInput).get();
        summonerName = summoner.getName();
        summonerLevel = summoner.getLevel();
        try {
            summonerRankedSolo = summoner.getLeaguePosition(Queue.RANKED_SOLO);
        }
        catch (Exception e) {
            Log.e(TAG, "Must be unranked");
            summonerRankedSolo = null;
        }
        summonerIconUrl = summoner.getProfileIcon().getImage().getURL();

    }

}
