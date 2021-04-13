package com.LeagueApplication.YummiGG;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class OriannaHandler extends Thread{

    private String summmonerNameInput;
    String summonerName;
    int summonerLevel;

    public OriannaHandler(String summmonerNameInput){
        this.summmonerNameInput = summmonerNameInput;
    }

    @Override
    public void run() {
        Summoner summoner = Orianna.summonerNamed(summmonerNameInput).get();
        summonerName = summoner.getName();
        summonerLevel = summoner.getLevel();
    }

}
