    package com.zodiac.sanghvi.jplreborn;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin_ScoreCard extends AppCompatActivity implements com.zodiac.sanghvi.jplreborn.Player_Adapter.Player_Comm {

    TextView Overs,Vs,Batting,Bowling,BatsMen1,BatsMen2,Bowler,Choose;
    EditText Runs,Wickets;
    String team1,team2,Selected;
    Context context=this;
    List<String> Players;
    List<Player_Name> data=new ArrayList<>();

    RecyclerView Player_Rv;
    Player_Adapter Player_Adapter;
    Dialog dialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    Bat_Bowl bat_bowl;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_score_card);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        dialog=new Dialog(context);


        Vs= (TextView) findViewById(R.id.Vs);
        Batting= (TextView) findViewById(R.id.Batting);
        Bowling= (TextView) findViewById(R.id.Bowling);
        BatsMen1= (TextView) findViewById(R.id.BatsMen1);
        BatsMen2= (TextView) findViewById(R.id.BatsMen2);
        Bowler= (TextView) findViewById(R.id.Bowler);
        Choose= (TextView) findViewById(R.id.Choose);
        Overs= (TextView) findViewById(R.id.Overs);
        Wickets= (EditText) findViewById(R.id.Wickets);
        Runs= (EditText) findViewById(R.id.Runs);

        Players= new ArrayList<>();

        team1=getIntent().getStringExtra("Team1");
        team2=getIntent().getStringExtra("Team2");

        Batting.setText(Bat_Bowl.Batting);
        Bowling.setText(Bat_Bowl.Bowling);

        Vs.setText(team1+"  "+"Vs"+"  "+team2);

        Choose.setText(Bat_Bowl.Toss +" "+"Won the toss and Choose to"+" "+ Bat_Bowl.Choose);

            BatsMen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BatBowl(Bat_Bowl.Batting);
                Log.d("Sayam","Working");
                BatsMen1.setText(Selected);
            }
        });

        BatsMen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BatBowl(Bat_Bowl.Batting);
            }
        });

        Bowler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BatBowl(Bat_Bowl.Bowling);
            }
        });

    }

    public String BatBowl(String Calling)
    {
        if(!data.isEmpty())
        {
            data.clear();
        }
        dialog.setTitle("Select a Player");
        dialog.setContentView(R.layout.layout_dialog_admin_scorecard);
        dialog.setCancelable(true);
        Player_Rv= (RecyclerView) dialog.findViewById(R.id.Player_Rv);
        Player_Adapter=new Player_Adapter(getBaseContext(),getData(Calling));
        Player_Rv.setAdapter(Player_Adapter);
        Player_Rv.setLayoutManager(new LinearLayoutManager(this));
        Player_Adapter.setItemClicked(Admin_ScoreCard.this);
        dialog.show();
        if(Selected!=null)
        return Selected;
        else
            return null;
    }

    private List<Player_Name> getData(final String Calling)
    {
        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot Snapshot:dataSnapshot.child("Teams").child(Calling).getChildren())
                {
                    Players.add(Snapshot.getKey());
                    Log.d("Sayam","Size="+Players.size());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };databaseReference.addListenerForSingleValueEvent(valueEventListener);
        for (int i=0;i<Players.size();i++)
        {
            String Names=Players.get(i);
            Player_Name current=new Player_Name();
            current.Name=Names;
            data.add(current);
        }
        return data;
    }

    @Override
    public void ItemClicked(int position)
    {
        Player_Name current=data.get(position);
        Selected=current.Name;
        dialog.dismiss();
    }

    @Override
    public void ItemLongClicked(int position)
    {
        Player_Name current=data.get(position);
        Selected=current.Name;
        Dialog Score=new Dialog(context);
        Score.setContentView(R.layout.layout_dialog_setscore_admin_scorecard);
        EditText Scored= (EditText) dialog.findViewById(R.id.Score);
        dialog.dismiss();
    }
}
