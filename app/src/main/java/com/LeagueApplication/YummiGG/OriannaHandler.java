package com.LeagueApplication.YummiGG;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.league.LeagueEntry;
<<<<<<< HEAD
import com.merakianalytics.orianna.types.core.staticdata.ReforgedRunes;
=======
import com.merakianalytics.orianna.types.core.staticdata.ProfileIcon;
import com.merakianalytics.orianna.types.core.staticdata.ReforgedRunes;
import com.merakianalytics.orianna.types.core.staticdata.SummonerSpells;
>>>>>>> e924a0ffe444a4b31d168267442f23583b8e9033
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import com.merakianalytics.orianna.types.dto.staticdata.ReforgedRune;

public class OriannaHandler extends Thread{

    private String summmonerNameInput;
    String summonerName;
    int summonerLevel;
    LeagueEntry summonerRankedSolo;
    String  summonerIconUrl;
    public OriannaHandler(String summmonerNameInput){
        this.summmonerNameInput = summmonerNameInput;
    }

    @Override
    public void run() {
        Summoner summoner = Orianna.summonerNamed(summmonerNameInput).get();
        summonerName = summoner.getName();
        summonerLevel = summoner.getLevel();
        summonerRankedSolo = summoner.getLeaguePosition(Queue.RANKED_SOLO);
        summonerIconUrl = summoner.getProfileIcon().getImage().getURL();

    }

}
