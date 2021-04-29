package com.LeagueApplication.YummiGG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import org.apache.commons.io.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";                         // tag for Log
    private EditText etFirstSummonerName, etSecondSummonerName;  // text inputs
    private Button btnCompareSummoners, btnClear;
    private ImageButton btnSearchFirstSummoner, btnSearchSecondSummoner;
    private RecyclerView rvRecent;
    public List<String> summoners =  new ArrayList<String>();

    private List<String> recents;
    private recentsAdapter recentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadItems();

        rvRecent = findViewById(R.id.rvRecents);
        btnClear = findViewById(R.id.btnClear);



        etFirstSummonerName = findViewById(R.id.etFirstSummonerName);             // reference variables to xml elements
        etSecondSummonerName = findViewById(R.id.etSecondSummonerName);

        btnSearchFirstSummoner = findViewById(R.id.imgBtnSearchFirstSummoner);

        rvRecent.setLayoutManager(new LinearLayoutManager(this));

        recentsAdapter.OnClickListener onClickListener = position -> {
            String temp = recents.get(position);
            if (etFirstSummonerName.getText().toString().isEmpty()) {
                etFirstSummonerName.setText(temp, TextView.BufferType.EDITABLE);
                Log.i(TAG, "Plugin first");
            }
            else if (etSecondSummonerName.getText().toString().isEmpty()) {
                etSecondSummonerName.setText(temp, TextView.BufferType.EDITABLE);
                Log.i(TAG, "Plugin second");
            }
            else {
                Log.i(TAG, "Both full");
            }


        };
        recentsAdapter = new recentsAdapter(recents, onClickListener);
        rvRecent.setAdapter(recentsAdapter);

        btnClear.setOnClickListener(v -> {
            clearRecents();
        });

        btnSearchFirstSummoner.setOnClickListener(v -> {
            if (!etIsEmpty(etFirstSummonerName)) {
                if (!searchRecents(etFirstSummonerName.getText().toString())) {
                    notInList(etFirstSummonerName.getText().toString());
                } else {
                    inList(etFirstSummonerName.getText().toString());
                }
                saveItems();
                searchSummoner(etFirstSummonerName.getText().toString());
            }
            else {
                Toast.makeText(getApplicationContext(), "Please enter a name (1)" , Toast.LENGTH_SHORT).show();
            }
        });

        btnSearchSecondSummoner = findViewById(R.id.imgBtnSearchSecondSummoner);

        btnSearchSecondSummoner.setOnClickListener(v -> {
            if(!etIsEmpty(etSecondSummonerName)) {
                if (!searchRecents(etSecondSummonerName.getText().toString())) {
                    notInList(etSecondSummonerName.getText().toString());
                } else {
                    inList(etSecondSummonerName.getText().toString());
                }
                saveItems();
                searchSummoner(etSecondSummonerName.getText().toString());
            }
            else {
                Toast.makeText(getApplicationContext(), "Please enter a name (2)" , Toast.LENGTH_SHORT).show();
            }
        });

        btnCompareSummoners = findViewById(R.id.btnCompareSummoners);

        btnCompareSummoners.setOnClickListener(v -> {
            summoners.add(etFirstSummonerName.getText().toString());        //TODO add better summoners handling
            summoners.add(etSecondSummonerName.getText().toString());
            Log.i(TAG,etFirstSummonerName.getText().toString());
            Log.i(TAG,etSecondSummonerName.getText().toString());
            if (!etIsEmpty(etFirstSummonerName) && !etIsEmpty(etSecondSummonerName)) {
                if (!searchRecents(etFirstSummonerName.getText().toString())) {
                    notInList(etFirstSummonerName.getText().toString());
                } else {
                    inList(etFirstSummonerName.getText().toString());
                }

                if (!searchRecents(etSecondSummonerName.getText().toString())) {
                    notInList(etSecondSummonerName.getText().toString());
                } else {
                    inList(etSecondSummonerName.getText().toString());
                }
                saveItems();
                searchSummoners(summoners);
            }
            else {
                Toast.makeText(getApplicationContext(), "Please fill both fields." , Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void inList(String in) {
        int ini = recents.indexOf(in);
        shiftUp(in);
        recentsAdapter.notifyItemMoved(ini, 0);
        Toast.makeText(getApplicationContext(), in + " shifted", Toast.LENGTH_SHORT).show();
    }

    private void notInList(String in) {
        recents.add(in);
        recentsAdapter.notifyItemInserted(recents.size()-1);
        shiftUp(in);
        recentsAdapter.notifyItemMoved(recents.size()-1 , 0);
        Toast.makeText(getApplicationContext(), in + " added", Toast.LENGTH_SHORT).show();
    }

    private boolean etIsEmpty(EditText in) {
        return (in.getText().toString().isEmpty());
    }

    private void clearRecents() {
        recents.clear();
        recentsAdapter.notifyDataSetChanged();
    }

    private void shiftUp(String firstText) {
        int temp = recents.indexOf(firstText);
        if (temp >= 0) {
            recents.add(0, recents.remove(temp));
        }
    }

    private void searchSummoner(String summoner) {
        Log.i(TAG, "Attempting to search a summoner ");

        Intent i = new Intent(this, SummonerInfoActivity.class);
        i.putExtra("Summoner", Parcels.wrap(summoner));
        startActivity(i);
        finish();
    }

    private void searchSummoners(List<String> summoners) {
        Log.i(TAG, "Attempting to search summoners ");


        Intent i = new Intent(this, SummonersInfoActivity.class);
        i.putExtra("firstSummoner", Parcels.wrap(summoners.get(0)));
        i.putExtra("secondSummoner", Parcels.wrap(summoners.get(1)));
        startActivity(i);
        finish();
    }

    boolean searchRecents(String summonerName) {
        Log.i(TAG, "searchRecents() called with parameter: " + summonerName);
        for (String b: recents) {
            if (b.equals(summonerName)) {
                return true;
            }
        }
        return false;
    }

    private File getDataFile() {
        Log.i(TAG, "getDataFile() called");
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems() {
        try {
            Log.i(TAG, "loadItems() called");
            Log.i(TAG, "Finding recents");

            recents = new ArrayList<>(FileUtils.readLines(getDataFile(), String.valueOf(Charset.defaultCharset())));
            for (String b : recents) {
                Log.i(TAG, b);
            }
        } catch (IOException e) {
            Log.e("MainActivity.java", "error reading items", e);
            recents = new ArrayList<>();
        }
    }

    //saves by writing into file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), recents);
            Log.i(TAG, "saveItems() called");
        } catch (IOException e) {
            Log.e("MainActivity.java", "error writing items", e);
        }
    }
}