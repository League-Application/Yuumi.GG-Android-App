package com.LeagueApplication.YummiGG;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;

import org.parceler.Parcels;

public class SummonerInfoActivity extends AppCompatActivity {

    private final String TAG = "SummonerInfoActivity";

    TextView summonerName, summonerRankedSolo, summonerLP, summonerLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summonerinfo);

        summonerName = findViewById(R.id.tvSummonerName);
        summonerRankedSolo = findViewById(R.id.tvSummonerRank);
        summonerLP = findViewById(R.id.tvSummonerLP);
        summonerLevel = findViewById(R.id.tvSummonerLevelInt);

        setupOrianna();
        //String[] summoners = new String[2];     //Unwrapping user input summoner names and adding them to a list //TODO add better handling for multiple summoners

        String summoner = Parcels.unwrap(getIntent().getParcelableExtra("Summoner"));

        getSummonerName(summoner, summonerName);
        getSummonerLevel(summoner, summonerLevel);
        getSummonerRankedSolo(summoner, summonerRankedSolo, summonerLP);
    }

    private void setupOrianna() {
        Orianna.setRiotAPIKey(BuildConfig.API_KEY);
        Orianna.setDefaultRegion(Region.NORTH_AMERICA);
    }

    private void getSummonerName(String summoner, TextView etSummonerName) {
        OriannaHandler ori = new OriannaHandler(summoner);
        ori.start();
        try {
            ori.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        etSummonerName.setText(ori.summonerName);
    }

    private void getSummonerLevel(String summoner, TextView etSummonerLevel) {
        OriannaHandler ori = new OriannaHandler(summoner);
        ori.start();
        try {
            ori.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        etSummonerLevel.setText(String.valueOf(ori.summonerLevel));
    }

    private void getSummonerRankedSolo(String summoner, TextView etSummonerRank, TextView etSummonerLP) {
        OriannaHandler ori = new OriannaHandler(summoner);
        ori.start();
        try {
            ori.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, String.valueOf(ori.summonerRankedSolo.getLeaguePoints()));
        etSummonerRank.setText(String.valueOf(ori.summonerRankedSolo.getTier()).substring(0, 1) + String.valueOf(ori.summonerRankedSolo.getTier()).substring(1).toLowerCase() + " " + ori.summonerRankedSolo.getDivision());
        etSummonerLP.setText(ori.summonerRankedSolo.getLeaguePoints() + " LP");
    }

}
