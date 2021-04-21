package com.LeagueApplication.YummiGG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "FirstpageActivity";                         // tag for Log
    private EditText etFirstSummonerName, etSecondSummonerName;  // text inputs
    private Button btnCompareSummoners;
    private ImageButton btnSearchFirstSummoner, btnSearchSecondSummoner;
    public List<String> summoners =  new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstSummonerName = findViewById(R.id.etFirstSummonerName);             // reference variables to xml elements
        etSecondSummonerName = findViewById(R.id.etSecondSummonerName);

        btnSearchFirstSummoner = findViewById(R.id.imgBtnSearchFirstSummoner);

        btnSearchFirstSummoner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSummoner(etFirstSummonerName.getText().toString());
            }
        });

        btnSearchSecondSummoner = findViewById(R.id.imgBtnSearchSecondSummoner);

        btnSearchSecondSummoner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSummoner(etSecondSummonerName.getText().toString());
            }
        });

        btnCompareSummoners = findViewById(R.id.btnCompareSummoners);

        btnCompareSummoners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                summoners.add(etFirstSummonerName.getText().toString());        //TODO add better summoners handling
                summoners.add(etSecondSummonerName.getText().toString());
                Log.i(TAG,etFirstSummonerName.getText().toString());
                Log.i(TAG,etSecondSummonerName.getText().toString());
                searchSummoners(summoners);
            }
        });
    }

    private void searchSummoner(String summoner) {
        Log.i(TAG, "Attempting to search a summoner ");

        Intent i = new Intent(this, SummonerInfoActivity.class);
        i.putExtra("Summoner", Parcels.wrap(summoner));
        startActivity(i);
    }

    private void searchSummoners(List<String> summoners) {
        Log.i(TAG, "Attempting to search summoners ");


        Intent i = new Intent(this, SummonersInfoActivity.class);
        i.putExtra("firstSummoner", Parcels.wrap(summoners.get(0)));
        i.putExtra("secondSummoner", Parcels.wrap(summoners.get(1)));
        startActivity(i);
    }
}
