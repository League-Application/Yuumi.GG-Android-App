package com.LeagueApplication.YummiGG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.match.Match;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class SummonerInfoActivity extends AppCompatActivity {

    private final String TAG = "SummonerInfoActivity";

    TextView summonerName, summonerRankedSolo, summonerLP, summonerLevel;
    ImageView summonerIcon;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summonerinfo);
        RecyclerView rvMatches = findViewById(R.id.rvMatches);

        summonerName = findViewById(R.id.tvSummonerName);
        summonerRankedSolo = findViewById(R.id.tvSummonerRankedSolo);
        summonerLP = findViewById(R.id.tvSummonerLP);
        summonerLevel = findViewById(R.id.tvSummonerLevelInt);
        summonerIcon = findViewById(R.id.ivSummonerIcon);

        setupOrianna();


        String summoner = Parcels.unwrap(getIntent().getParcelableExtra("Summoner"));

        getSummonerName(summoner, summonerName);
        getSummonerLevel(summoner, summonerLevel);
        getSummonerRankedSolo(summoner, summonerRankedSolo, summonerLP);
        getSummonerIcon(summoner, summonerIcon);
        //getMatches(summoner);
        //Log.i(TAG, String.valueOf(matches));


        //Creating the match adapter
        final MatchAdapter matchAdapter = new MatchAdapter(this, getMatches(summoner));
        //Setting the adapter on the recyclerview
        rvMatches.setAdapter(matchAdapter);
        //Setting a layout manager on the recyclerview
        rvMatches.setLayoutManager(new LinearLayoutManager(this));
        matchAdapter.notifyDataSetChanged();
    }

    private void setupOrianna() {
        Orianna.setRiotAPIKey(BuildConfig.API_KEY);
        Orianna.setDefaultRegion(Region.NORTH_AMERICA);
    }

    private void getSummonerIcon(String summoner, ImageView ivSummonerIcon) {
        OriannaHandler ori = new OriannaHandler(summoner, false);
        ori.start();
        try {
            ori.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String imageURL = ori.summonerIconUrl;
        String [] new_URL = imageURL.split(":",2);
        String final_URL ="https:"+ new_URL[1]; // needs https to work
        Picasso.with(this).load(final_URL).into(ivSummonerIcon);
    }
    private void getSummonerName(String summoner, TextView etSummonerName) {
        OriannaHandler ori = new OriannaHandler(summoner, false);
        ori.start();
        try {
            ori.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        etSummonerName.setText(ori.summonerName);
    }

    private void getSummonerLevel(String summoner, TextView etSummonerLevel) {
        OriannaHandler ori = new OriannaHandler(summoner, false);
        ori.start();
        try {
            ori.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        etSummonerLevel.setText(String.valueOf(ori.summonerLevel));
    }

    private void getSummonerRankedSolo(String summoner, TextView etSummonerRank, TextView etSummonerLP) {
        OriannaHandler ori = new OriannaHandler(summoner, false);
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

    private List<Match> getMatches(String summoner) {
        OriannaHandler ori = new OriannaHandler(summoner, 10);
        ori.start();
        try {
            ori.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Matches List: " + String.valueOf(ori.matches));
        return ori.matches;
    }

    @Override
    public void onBackPressed() { //override back button
        startActivity(new Intent(this, MainActivity.class)); //restart main method fresh
    }

}
