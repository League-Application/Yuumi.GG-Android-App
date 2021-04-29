package com.LeagueApplication.YummiGG;

import android.content.Intent;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.core.league.LeagueEntry;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

import static androidx.core.content.ContextCompat.startActivity;

public class OriannaHandler extends Thread{

    private String summmonerNameInput;
    String summonerName, summonerIconUrl;
    int summonerLevel;
    LeagueEntry summonerRankedSolo;

    public OriannaHandler(String summmonerNameInput){
        this.summmonerNameInput = summmonerNameInput;
    }

    @Override
    public void run() {
        try {
            Summoner summoner = Orianna.summonerNamed(summmonerNameInput).get();
            summonerName = summoner.getName();
            summonerLevel = summoner.getLevel();
            summonerRankedSolo = summoner.getLeaguePosition(Queue.RANKED_SOLO);
            summonerIconUrl = summoner.getProfileIcon().getImage().getURL();
        } catch (Exception e) {
            System.out.println("Not good");
        }
    }

}
