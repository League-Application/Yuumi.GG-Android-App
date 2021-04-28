package com.LeagueApplication.YummiGG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class SummonersInfoActivity extends AppCompatActivity {

    private final String TAG = "SummonersInfoActivity";

    TextView firstSummonerName, secondSummonerName, firstSummonerRankedSolo, secondSummonerRankedSolo,
            firstSummonerLP, secondSummonerLP, firstSummonerLevel, secondSummonerLevel;
    ImageView firstSummonerIcon, secondSummonerIcon;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summonersinfo);

        firstSummonerName = findViewById(R.id.tvFirstSummonerName);
        firstSummonerRankedSolo = findViewById(R.id.tvFirstSummonerRankedSolo);
        firstSummonerLP = findViewById(R.id.tvFirstSummonerLP);
        firstSummonerLevel = findViewById(R.id.tvFirstSummonerLevelInt);
        firstSummonerIcon= findViewById(R.id.ivFirstSummonerIcon);

        secondSummonerName = findViewById(R.id.tvSecondSummonerName);
        secondSummonerRankedSolo = findViewById(R.id.tvSecondSummonerRankedSolo);
        secondSummonerLP = findViewById(R.id.tvSecondSummonerLP);
        secondSummonerLevel = findViewById(R.id.tvSecondSummonerLevelInt);
        secondSummonerIcon = findViewById(R.id.ivSecondSummonerIcon);


        setupOrianna();
        //String[] summoners = new String[2];     //Unwrapping user input summoner names and adding them to a list //TODO add better handling for multiple summoners


        String firstSummoner = Parcels.unwrap(getIntent().getParcelableExtra("firstSummoner"));
        String secondSummoner = Parcels.unwrap(getIntent().getParcelableExtra("secondSummoner"));


        getSummonerName(firstSummoner, firstSummonerName);
        getSummonerName(secondSummoner, secondSummonerName);
        getSummonerLevel(firstSummoner, firstSummonerLevel);
        getSummonerLevel(secondSummoner, secondSummonerLevel);
        getSummonerRankedSolo(firstSummoner, firstSummonerRankedSolo, firstSummonerLP);
        getSummonerRankedSolo(secondSummoner, secondSummonerRankedSolo, secondSummonerLP);
        getSummonerIcon(firstSummoner,firstSummonerIcon);
        getSummonerIcon(secondSummoner, secondSummonerIcon);
    }

    private void setupOrianna() {
        Orianna.setRiotAPIKey(BuildConfig.API_KEY);
        Orianna.setDefaultRegion(Region.NORTH_AMERICA);
    }

    private void getSummonerIcon(String summoner, ImageView etSummonericon) {
        OriannaHandler ori = new OriannaHandler(summoner);
        ori.start();
        try {
            ori.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String imageUri = ori.summonerIconUrl;
        String [] new_URI = imageUri.split(":",2);
        String final_url="https:"+ new_URI[1];                   // needs https to work
        Picasso.with(this).load(final_url).into(etSummonericon);
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
        Log.i(TAG, String.valueOf(ori.summonerLevel));
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
        //Log.i(TAG, String.valueOf(ori.summonerRankedSolo.getLeaguePoints()));
        try { //attempt to grab rank
            etSummonerRank.setText(String.valueOf(ori.summonerRankedSolo.getTier()).substring(0, 1) + String.valueOf(ori.summonerRankedSolo.getTier()).substring(1).toLowerCase() + " " + ori.summonerRankedSolo.getDivision());
            etSummonerLP.setText(ori.summonerRankedSolo.getLeaguePoints() + " LP");
        }
        catch (Exception e) { // if it can't grab a rank, it must be unranked
            Log.e(TAG, "Must be unranked...");
            etSummonerRank.setText("Unranked");
            etSummonerLP.setText("0 LP");
        }
    }

    @Override
    public void onBackPressed() { //override back button
        startActivity(new Intent(this, MainActivity.class)); //restart main method fresh
    }


}
