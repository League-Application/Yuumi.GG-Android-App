package com.LeagueApplication.YummiGG.Models;

import android.os.Build;

import com.LeagueApplication.YummiGG.BuildConfig;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

public class SummonerObject {
    ApiConfig config = new ApiConfig().setKey(BuildConfig.API_KEY);
    RiotApi api = new RiotApi(config);

    private static final long serialVersionUID = -8213488199644701555L;

    private String name;
    private String summonerID;
    private String accountID;
    private String puuid;
    private int summonerLevel;
    private int profileIconID;
    private net.rithms.riot.api.endpoints.summoner.dto.Summoner summoner;
    private String imageURL;

    public SummonerObject() { // default constructor
    }

    public SummonerObject(String username) throws RiotApiException {
        summoner = api.getSummonerByName(Platform.NA, username);
        name = summoner.getName();
        summonerID = summoner.getId();
        accountID = summoner.getAccountId();
        puuid = summoner.getPuuid();
        summonerLevel = summoner.getSummonerLevel();
        profileIconID = summoner.getProfileIconId();
//        imageURL = profileIconID.getImage().getURL();
    }
    public String getName() {
        return name;
    }

    public String getSummonerID() {
        return summonerID;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getPuuid() {
        return puuid;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public int getProfileIconID() {
        return profileIconID;
    }

    public net.rithms.riot.api.endpoints.summoner.dto.Summoner getSummoner() {
        return summoner;
    }
}