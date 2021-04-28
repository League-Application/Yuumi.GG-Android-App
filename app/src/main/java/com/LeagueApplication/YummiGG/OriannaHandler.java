package com.LeagueApplication.YummiGG;

import android.util.Log;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.league.LeagueEntry;
import com.merakianalytics.orianna.types.core.match.Match;
import com.merakianalytics.orianna.types.core.match.MatchHistory;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
import com.merakianalytics.orianna.types.core.staticdata.Champions;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

import java.util.ArrayList;
import java.util.List;

public class OriannaHandler extends Thread{


    private String TAG = "OriannaHandler";
    public Summoner summoner;
    private String summmonerNameInput;
    private boolean needMatchHistory;
    private int numberOfMatches, championIndex;

    String summonerName, summonerIconUrl;
    int summonerLevel;
    LeagueEntry summonerRankedSolo;
    MatchHistory matchHistory;
    List<Match> matches = new ArrayList<>();

    public OriannaHandler(){
        //required for MatchAdapter reference
    }


    public OriannaHandler(String summmonerNameInput, boolean needMatchHistory){
        this.summmonerNameInput = summmonerNameInput;
        this.needMatchHistory = needMatchHistory;
    }

    public OriannaHandler(String summmonerNameInput, int numberOfMatches){
        this.summmonerNameInput = summmonerNameInput;
        this.numberOfMatches = numberOfMatches;
        this.needMatchHistory = true;
    }
    public OriannaHandler(int championIndex){
        this.championIndex = championIndex;
    }


    @Override
    public void run() {
        summoner = Orianna.summonerNamed(summmonerNameInput).get();
        summonerName = summoner.getName();
        summonerLevel = summoner.getLevel();
        summonerRankedSolo = summoner.getLeaguePosition(Queue.RANKED_SOLO);
        summonerIconUrl = summoner.getProfileIcon().getImage().getURL();

        if(needMatchHistory == true){
            matchHistory = MatchHistory.forSummoner(summoner).withQueues(Queue.RANKED_SOLO).get();

            for(int i = 0; i < numberOfMatches; i++) {
                //Log.i(TAG, "Match " + i + matchHistory.get(i));
                matches.add(matchHistory.get(i));
            }
            Log.i(TAG, String.valueOf(matches));
        }
    }
}
