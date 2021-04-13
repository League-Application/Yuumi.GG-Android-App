package com.LeagueApplication.YummiGG;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.ComparisonChain.start;

public class InfoActivity extends AppCompatActivity {

    private final String TAG = "InfoActivity";

    TextView firstSummonerName, secondSummonerName, firstSummonerRank, secondSummonerRank,
            firstSummonerLP, secondSummonerLP, firstSummonerLevel, secondSummonerLevel;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        firstSummonerName = findViewById(R.id.tvFirstSummonerName);
        firstSummonerRank = findViewById(R.id.tvFirstSummonerRank);
        firstSummonerLP = findViewById(R.id.tvFirstSummonerLP);
        firstSummonerLevel = findViewById(R.id.tvFirstSummonerLevelInt);

        secondSummonerName = findViewById(R.id.tvSecondSummonerName);
        secondSummonerRank = findViewById(R.id.tvSecondSummonerRank);
        secondSummonerLP = findViewById(R.id.tvSecondSummonerLP);
        secondSummonerLevel = findViewById(R.id.tvSecondSummonerLevelInt);

        setupOrianna();
        //String[] summoners = new String[2];     //Unwrapping user input summoner names and adding them to a list //TODO add better handling for multiple summoners

        String firstSummoner = Parcels.unwrap(getIntent().getParcelableExtra("firstSummoner"));
        String secondSummoner = Parcels.unwrap(getIntent().getParcelableExtra("secondSummoner"));

        getSummonerName(firstSummoner, firstSummonerName);
        getSummonerName(secondSummoner, secondSummonerName);
        getSummonerLevel(firstSummoner, firstSummonerLevel);
        getSummonerLevel(secondSummoner, secondSummonerLevel);
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
}
