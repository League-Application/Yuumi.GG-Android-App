package com.LeagueApplication.YummiGG;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.LeagueApplication.YummiGG.Models.SummonerObject;


public class InfoActivity extends AppCompatActivity {

    TextView firstSummonerName, firstSummonerRank, firstSummonerLP, firstSummonerLevel;
    TextView secondSummonerName, secondSummonerRank, secondSummonerLP, secondSummonerLevel;
    SummonerObject[] summoners = SummonerStorage.getInstance().get();
    public static final String TAG = "InfoActivity";

    static int player = 0;
    int firstSummoner = 0;
    int secondSummoner = 1;

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

        firstSummonerName.setText(summoners[0].getName());
        secondSummonerName.setText(summoners[1].getName());

        firstSummonerLevel.setText(summoners[0].getSummonerLevel()+"");
        secondSummonerLevel.setText(summoners[1].getSummonerLevel()+"");

//        getPlayerRank(firstSummoner);
//        getPlayerRank(secondSummoner);

//        Log.i(TAG, "first and second rank reached");
//        APIHandler handler = new APIHandler();
//        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Headers headers, JSON json) {
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
//
//            }
//        };
//        handler.getRank(1, jsonHttpResponseHandler);
    }

    private void getPlayerRank(int player) {

    }
}