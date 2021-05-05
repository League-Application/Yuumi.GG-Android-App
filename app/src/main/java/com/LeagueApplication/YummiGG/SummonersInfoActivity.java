package com.LeagueApplication.YummiGG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.match.Match;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

public class SummonersInfoActivity extends AppCompatActivity {

    private final String TAG = "SummonersInfoActivity";

    TextView firstSummonerName, secondSummonerName, firstSummonerRankedSolo, secondSummonerRankedSolo,
            firstSummonerLP, secondSummonerLP, firstSummonerLevel, secondSummonerLevel, tvMatchKDAFirst,
            tvMatchKDASecond, tvMatchResultFirst, tvMatchResultSecond,
            tvScoreFirst, tvScoreSecond, tvBetterFirst, tvBetterSecond;

    ImageView firstSummonerIcon, secondSummonerIcon, ivChampionIconFirst, ivChampionIconSecond;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summonersinfo);

        firstSummonerName = findViewById(R.id.tvFirstSummonerName);
        firstSummonerRankedSolo = findViewById(R.id.tvFirstSummonerRankedSolo);
        firstSummonerLP = findViewById(R.id.tvFirstSummonerLP);
        firstSummonerLevel = findViewById(R.id.tvFirstSummonerLevelInt);
        firstSummonerIcon = findViewById(R.id.ivFirstSummonerIcon);
        tvMatchKDAFirst = findViewById(R.id.tvMatchKDAFirst);
        tvMatchResultFirst = findViewById(R.id.tvMatchResultFirst);
        ivChampionIconFirst = findViewById(R.id.ivChampionIconFirst);
        tvScoreFirst = findViewById(R.id.tvScoreFirst);
        tvBetterFirst = findViewById(R.id.tvBetterFirst);

        secondSummonerName = findViewById(R.id.tvSecondSummonerName);
        secondSummonerRankedSolo = findViewById(R.id.tvSecondSummonerRankedSolo);
        secondSummonerLP = findViewById(R.id.tvSecondSummonerLP);
        secondSummonerLevel = findViewById(R.id.tvSecondSummonerLevelInt);
        secondSummonerIcon = findViewById(R.id.ivSecondSummonerIcon);
        tvMatchKDASecond = findViewById(R.id.tvMatchKDASecond);
        tvMatchResultSecond = findViewById(R.id.tvMatchResultSecond);
        ivChampionIconSecond = findViewById(R.id.ivChampionIconSecond);
        tvScoreSecond = findViewById(R.id.tvScoreSecond);
        tvBetterSecond = findViewById(R.id.tvBetterSecond);

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
        getSummonerIcon(firstSummoner, firstSummonerIcon);
        getSummonerIcon(secondSummoner, secondSummonerIcon);

        Match firstBest = bestMatch(firstSummonerName.getText().toString());
        int kills, deaths, assists;
        kills = firstBest.getParticipants().get(0).getStats().getKills();
        deaths = firstBest.getParticipants().get(0).getStats().getDeaths();
        assists = firstBest.getParticipants().get(0).getStats().getAssists();
        final int FIRST = (int) calculateKDA(kills, deaths, assists);
        tvScoreFirst.setText(String.valueOf(FIRST));
        tvMatchKDAFirst.setText(String.valueOf(kills) + " / " + String.valueOf(deaths) + " / " + String.valueOf(assists));
        setMatchImage(firstBest, 0);
        if (firstBest.getParticipants().get(0).getStats().isWinner()){
            tvMatchResultFirst.setTextColor(Color.parseColor("#1F81CF"));
            tvMatchResultFirst.setText("Victory");
        }
        else if(firstBest.isRemake()){
            tvMatchResultFirst.setTextColor(Color.parseColor("#9A000000"));
            tvMatchResultFirst.setText("Remake");
        }
        else{
            tvMatchResultFirst.setTextColor(Color.parseColor("#FFCF1F42"));
            tvMatchResultFirst.setText("Defeat");
        }

        Match secondBest = bestMatch(secondSummonerName.getText().toString());
        kills = secondBest.getParticipants().get(0).getStats().getKills();
        deaths = secondBest.getParticipants().get(0).getStats().getDeaths();
        assists = secondBest.getParticipants().get(0).getStats().getAssists();
        final int SECOND = (int) calculateKDA(kills, deaths, assists);
        tvScoreSecond.setText(String.valueOf(SECOND));
        tvMatchKDASecond.setText(String.valueOf(kills) + " / " + String.valueOf(deaths) + " / " + String.valueOf(assists));
        setMatchImage(secondBest, 1);
        if (secondBest.getParticipants().get(0).getStats().isWinner()){
            tvMatchResultSecond.setTextColor(Color.parseColor("#1F81CF"));
            tvMatchResultSecond.setText("Victory");
        }
        else if(secondBest.isRemake()){
            tvMatchResultSecond.setTextColor(Color.parseColor("#9A000000"));
            tvMatchResultSecond.setText("Remake");
        }
        else {
            tvMatchResultSecond.setTextColor(Color.parseColor("#FFCF1F42"));
            tvMatchResultSecond.setText("Defeat");
        }

        if (FIRST > SECOND) {
            tvBetterFirst.setText("Better Player");
            tvBetterSecond.setText("");
        }
        else {
            tvBetterSecond.setText("Better Player");
            tvBetterFirst.setText("");
        }

    }

    private void setupOrianna() {
        Orianna.setRiotAPIKey(BuildConfig.API_KEY);
        Orianna.setDefaultRegion(Region.NORTH_AMERICA);
    }

    private void setMatchImage(Match match, int summoner) {
        final String[] matchChampionIcon = new String[1];
        final String[] matchSummonersIcon = new String[2];
        Thread thread = new Thread(){

            public void run(){
                String originalChampionIconURL = match.getParticipants().get(0).getChampion().getImage().getURL();
                matchChampionIcon[0] =  originalChampionIconURL.substring(0,4) + "s" + originalChampionIconURL.substring(4);
                //Log.i(TAG,matchChampionIcon[0]);

                String originalSummonerDURL = match.getParticipants().get(0).getSummonerSpellD().getImage().getURL();
                String originalSummonerFURL = match.getParticipants().get(0).getSummonerSpellF().getImage().getURL();

                //Summoner Spell D is index 0, Summoner Spell F is index 1
                matchSummonersIcon[0] = originalSummonerDURL.substring(0,4) + "s" + originalSummonerDURL.substring(4);
                matchSummonersIcon[1] = originalSummonerFURL.substring(0,4) + "s" + originalSummonerFURL.substring(4);
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (summoner == 0)
            Glide.with(getApplicationContext()).load(matchChampionIcon[0]).into(ivChampionIconFirst);
        else
            Glide.with(getApplicationContext()).load(matchChampionIcon[0]).into(ivChampionIconSecond);

    }

    private void getSummonerIcon(String summoner, ImageView etSummonericon) {
        OriannaHandler ori = new OriannaHandler(summoner, false);
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
        Log.i(TAG, String.valueOf(ori.summonerLevel));
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
        OriannaHandler ori = new OriannaHandler(summoner, 20);
        ori.start();
        try {
            ori.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Matches List: " + String.valueOf(ori.matches));
        return ori.matches;
    }

    private Match bestMatch(String summoner) {
        int k, d, a;
        double min = -1;
        Match out = null;
        //calculate kda method
        for (Match match: getMatches(summoner)) {
            k = match.getParticipants().get(0).getStats().getKills();
            d = match.getParticipants().get(0).getStats().getDeaths();
            a = match.getParticipants().get(0).getStats().getAssists();
            double check = calculateKDA(k, d, a);
            if (check >= min) {
                min = calculateKDA(k, d, a);
                out = match;
            }
        }
        return out;
    }
    private double calculateKDA(int k, int d, int a) {
        //kda logic
        if(d == 0){
            return (k + a);
        }
        return (k + a) / d;

    }

    @Override
    public void onBackPressed() { //override back button
        startActivity(new Intent(this, MainActivity.class)); //restart main method fresh
    }

}