package com.LeagueApplication.YummiGG;

import android.util.Log;

import com.LeagueApplication.YummiGG.Models.SummonerObject;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;

import okhttp3.Headers;

public class APIHandler {
    public static final String CONSUMER_KEY = "RGAPI-59197c22-0db6-442e-ae02-78ca10438dec";
    ApiConfig config = new ApiConfig().setKey(CONSUMER_KEY);
    RiotApi api = new RiotApi(config);

    SummonerObject[] summoners = SummonerStorage.getInstance().get();

    public static final String BASE_URL = "https://na1.api.riotgames.com/lol/"; // Change this, base API URL




    public void getRank(int player, JsonHttpResponseHandler handler) {
        String summonerID = summoners[player].getSummonerID();
        String apiUrl = BASE_URL + "league/v4/entries/by-summoner/" + summonerID;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(apiUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("APIHandler.java", "SUCCESS BTICH");
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

    }

    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}