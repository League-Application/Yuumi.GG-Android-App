package com.LeagueApplication.YummiGG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";                         // tag for Log
    private EditText etFirstSummonerName, etSecondSummonerName;  // text inputs
    private Button btnSubmit;                                      // submit button
    public List<String> summoners =  new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstSummonerName = findViewById(R.id.etFirstSummonerName);             // reference variables to xml elements
        etSecondSummonerName = findViewById(R.id.etSecondSummonerName);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                summoners.add(etFirstSummonerName.getText().toString());        //TODO add better summoners handling
                summoners.add(etSecondSummonerName.getText().toString());
                searchSummoners(summoners);
            }
        });
    }

    private void searchSummoners(List<String> summoners) {
        Log.i(TAG, "Attempting to search summoners ");

        Intent i = new Intent(this, InfoActivity.class);
        i.putExtra("firstSummoner", Parcels.wrap(summoners.get(0)));
        i.putExtra("secondSummoner", Parcels.wrap(summoners.get(1)));
        startActivity(i);
    }
}
