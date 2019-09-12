package com.example.fuat.robinhood;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;

/**
 * Created by Fuat on 22/09/2017.
 */

public class GamePlayersLoader {

    private ArrayList<Player> players = new ArrayList<>();

    public GamePlayersLoader(Context context){

        Resources res = context.getResources();

/*        Player p = new Player(BitmapFactory.decodeResource(res, R.drawable.football));
        players.add(p);
         p = new Player(BitmapFactory.decodeResource(res, R.drawable.minion));
        p.setX(300);
        players.add(p);
         p = new Player(BitmapFactory.decodeResource(res, R.drawable.football));
        p.setX(600);
        players.add(p);*/
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }
}