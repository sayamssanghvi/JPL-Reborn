package com.zodiac.sanghvi.jplreborn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Menu_Option extends AppCompatActivity{

    private ImageView Vote, MostRuns, MostWickets, SearchPlayer;

    private int CheckLayout = 1;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Menu_Option.this, Audience_First.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_option);

        Vote = (ImageView) findViewById(R.id.Vote);
        MostRuns = (ImageView) findViewById(R.id.MostRuns);
        SearchPlayer = (ImageView) findViewById(R.id.SearchPlayer);
        MostWickets = (ImageView) findViewById(R.id.MostWickets);


        Vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLayout = 1;
                Intent i = new Intent(Menu_Option.this, FeaturesActivity.class);
                i.putExtra("CheckLayout", CheckLayout);
                startActivity(i);
            }
        });

        MostRuns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLayout = 2;
                Intent i = new Intent(Menu_Option.this, FeaturesActivity.class);
                i.putExtra("CheckLayout", CheckLayout);
                startActivity(i);

            }
        });

        SearchPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLayout = 4;
                Intent i = new Intent(Menu_Option.this, SearchActivity.class);
                i.putExtra("CheckLayout", CheckLayout);
                startActivity(i);
            }
        });

        MostWickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLayout = 3;
                Intent i = new Intent(Menu_Option.this, FeaturesActivity.class);
                i.putExtra("CheckLayout", CheckLayout);
                startActivity(i);
            }
        });
    }
}