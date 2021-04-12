package com.LeagueApplication.YummiGG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.LeagueApplication.YummiGG.Models.SummonerObject;
import net.rithms.riot.api.RiotApiException;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity.java";                         // tag for Log
    private EditText input_firstSummonerName, input_secondSummonerName;  // text inputs
    private Button btn_submit;                                      // submit button
    private SummonerObject firstSummoner, secondSummoner;                // two 'friends' summoners (as objects)
    private SummonerObject[] duo = new SummonerObject[2];           // array of size 2 to store summoners for easy access

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_firstSummonerName = findViewById(R.id.input_firstSummonerName);             // reference variables to xml elements
        input_secondSummonerName = findViewById(R.id.input_secondSummonerName);
        btn_submit = findViewById(R.id.btnSubmit);

        btn_submit.setOnClickListener(v -> {                                    //submit button
//            Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();
            try {
                duo = new setSummoners().execute().get();                       // runs setSummoners class below
                Log.i(TAG, duo[0].getName());
                SummonerStorage.getInstance().set(duo);
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            } catch (ExecutionException e) {                                    // required catch block
                e.printStackTrace();
            } catch (InterruptedException e) {                                  // required catch block
                e.printStackTrace();
            }
//            Log.i(TAG, "Summoners: " +                                    // log summoners
//                    duo[0].getName() + ", " +
//                    duo[1].getName());

//            Toast.makeText(this, "Got Summoners: "  +
//                    duo[0].getName() + ", " +
//                    duo[1].getName(), Toast.LENGTH_SHORT).show();
        });
    }

    private class setSummoners extends AsyncTask<Void, Void, SummonerObject[]> {
        @Override

        protected SummonerObject[] doInBackground(Void... voids) {
            try {
                firstSummoner = new SummonerObject(input_firstSummonerName.getText().toString());          // grab users from input
                secondSummoner = new SummonerObject(input_secondSummonerName.getText().toString());    // TODO: add handling for no inputs
                duo[0] = firstSummoner;  duo[1] = secondSummoner;                                        // set users into array
            } catch (RiotApiException e) {
                Log.e(TAG, "Encountered an error...");
                e.printStackTrace();
            }
            return duo;                                                                             // return array
        }
        @Override
        protected void onPostExecute(SummonerObject[] aVoid) {
            Log.i(TAG, "Grabbed " + duo[0].getName() + " and " + duo[1].getName());
            super.onPostExecute(aVoid);
        }
    }


}