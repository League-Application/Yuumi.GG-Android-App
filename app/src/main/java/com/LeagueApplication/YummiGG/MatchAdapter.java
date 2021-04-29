package com.LeagueApplication.YummiGG;

import android.content.Context;
import android.graphics.Bitmap;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.merakianalytics.orianna.types.core.match.Match;
import com.merakianalytics.orianna.types.core.match.Participant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder>{

    private String TAG= "MatchAdapter";
    Context context;
    List<Match> matches;

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

        TextView tvMatchResult, tvMatchKDA, tvMatchType;
        ImageView ivChampionIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMatchResult = itemView.findViewById(R.id.tvMatchResult);
            tvMatchType = itemView.findViewById(R.id.tvMatchType);
            tvMatchKDA= itemView.findViewById(R.id.tvMatchKDA);
            ivChampionIcon= itemView.findViewById(R.id.ivChampionIcon);
        }

        public void bind(Match match) {
            //Log.i(TAG, String.valueOf(matches));
            tvMatchType.setText(match.getQueue().name());


            //Win or Loss logic

            if(match.getParticipants().get(0).getStats().isWinner()){
                tvMatchResult.setText("Victory");
            }
            else if(match.isRemake()){
                tvMatchResult.setText("Remake");
            }
            else{
                tvMatchResult.setText("Defeat");
            }
            String kills, deaths, assists;
            kills = String.valueOf(match.getParticipants().get(0).getStats().getKills());
            deaths = String.valueOf(match.getParticipants().get(0).getStats().getDeaths());
            assists = String.valueOf(match.getParticipants().get(0).getStats().getAssists());
            tvMatchKDA.setText(kills + " / " + deaths + " / " + assists);

            //Log.i(TAG, match.getParticipants().get(0).getChampion().getImage().getFull());

            final String[] matchChampionIcon = new String[1];
            Thread thread = new Thread(){

                public void run(){
                    String originalURL = match.getParticipants().get(0).getChampion().getImage().getURL();
                    String finalURL = originalURL.substring(0,4) + "s" + originalURL.substring(4);
                    matchChampionIcon[0] = finalURL;
                    Log.i(TAG,matchChampionIcon[0]);
                }
            };

            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Glide.with(context).load(matchChampionIcon[0]).into(ivChampionIcon);
        }
    }
}
