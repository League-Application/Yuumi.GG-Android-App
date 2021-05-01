package com.LeagueApplication.YummiGG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LeagueApplication.YummiGG.R.color;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.merakianalytics.orianna.types.core.match.Match;
import com.merakianalytics.orianna.types.core.match.Participant;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.time.LocalDateTime;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder>{

    private String TAG= "MatchAdapter";
    Context context;
    List<Match> matches;
    double bestKDa = 0.0;
    Match bestMatch;

    public MatchAdapter(Context context, List<Match> matches) {
        this.context = context;
        this.matches = matches;
    }

    //Inflates a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View matchView = LayoutInflater.from(context).inflate(R.layout.item_match, parent, false);
        return new ViewHolder(matchView);
    }

    //Involves populating data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Get the match at the passed in position
        Match match = matches.get(position);
        //Bind the match data into the viewholder
        holder.bind(match);
    }

    //Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return matches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvMatchResult, tvMatchKDA, tvMatchType, tvMatchCreationTime;
        ImageView ivChampionIcon, ivSummonerSpellD, ivSummonerSpellF;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMatchResult = itemView.findViewById(R.id.tvMatchResult);
            tvMatchType = itemView.findViewById(R.id.tvMatchType);
            tvMatchKDA = itemView.findViewById(R.id.tvMatchKDA);
            tvMatchCreationTime = itemView.findViewById(R.id.tvMatchCreationTime);
            ivChampionIcon = itemView.findViewById(R.id.ivChampionIcon);
            ivSummonerSpellD = itemView.findViewById(R.id.ivSummonerSpellD);
            ivSummonerSpellF = itemView.findViewById(R.id.ivSummonerSpellF);
        }

        public void bind(Match match) {
            if(match.getQueue().name() == "RANKED_SOLO"){
                tvMatchType.setText("Ranked Solo");
            }

            //Win or Loss logic

            if(match.getParticipants().get(0).getStats().isWinner()){
                tvMatchResult.setTextColor(Color.parseColor("#1F81CF"));
                tvMatchResult.setText("Victory");
            }
            else if(match.isRemake()){
                tvMatchResult.setTextColor(Color.parseColor("#9A000000"));
                tvMatchResult.setText("Remake");
            }
            else{
                tvMatchResult.setTextColor(Color.parseColor("#FFCF1F42"));
                tvMatchResult.setText("Defeat");
            }
            String kills, deaths, assists;
            kills = String.valueOf(match.getParticipants().get(0).getStats().getKills());
            deaths = String.valueOf(match.getParticipants().get(0).getStats().getDeaths());
            assists = String.valueOf(match.getParticipants().get(0).getStats().getAssists());
            tvMatchKDA.setText(kills + " / " + deaths + " / " + assists);


            PrettyTime timeFormatter = new PrettyTime();
            tvMatchCreationTime.setText(timeFormatter.format(match.getCreationTime().toDate()));


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

            Glide.with(context).load(matchChampionIcon[0]).into(ivChampionIcon);
            Glide.with(context).load(matchSummonersIcon[0]).into(ivSummonerSpellD);
            Glide.with(context).load(matchSummonersIcon[1]).into(ivSummonerSpellF);
        }
    }
}
