    package com.zodiac.sanghvi.jplreborn;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Admin_ScoreCard extends AppCompatActivity implements com.zodiac.sanghvi.jplreborn.Player_Adapter.Player_Comm, com.zodiac.sanghvi.jplreborn.Frag_ScoreCard.Frag_Comm {

    TextView Overs,Vs,Batting,Bowling,BatsMen1,BatsMen2,Bowler,Choose;
    ArrayList<String> data;
    String team1,team2,Selected;
    EditText Runs,Wickets;
    Context context=this;

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    Frag_ScoreCard Frag_ScoreCard;

    ValueEventListener valueEventListener;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_score_card);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();


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
        data=new ArrayList<>();

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();

        team1=getIntent().getStringExtra("Team1");
        team2=getIntent().getStringExtra("Team2");

        Batting.setText(Bat_Bowl.Batting);
        Bowling.setText(Bat_Bowl.Bowling);

        Vs.setText(team1+"  "+"Vs"+"  "+team2);

        Choose.setText(Bat_Bowl.Toss +" "+"Won the toss and Choose to"+" "+ Bat_Bowl.Choose);

        BatsMen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {BatBowl(Bat_Bowl.Batting,"BastMen1");}
        });

        BatsMen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {BatBowl(Bat_Bowl.Batting,"BastMen2");
            }
        });

        Bowler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BatBowl(Bat_Bowl.Bowling,"Bowler");
            }
        });

    }

    public void BatBowl(String Calling,String Who)
    {
        getData(Calling);
        Frag_ScoreCard=new Frag_ScoreCard();
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("Data",data);
        bundle.putString("Who",Who);
        Frag_ScoreCard.setArguments(bundle);
        fragmentTransaction.add(R.id.ScoreCard,Frag_ScoreCard);
        fragmentTransaction.commit();
    }

    private ArrayList<String> getData(final String Calling)
    {
        final ArrayList<String> Players= new ArrayList<>();

        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot Snapshot:dataSnapshot.child("Teams").child(Calling).getChildren())
                {
                    Players.add(Snapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };databaseReference.addValueEventListener(valueEventListener);

        for (int i=0;i<Players.size();i++)
        {
            String Names=Players.get(i);
            data.add(Names);
        }
        Players.clear();
        return data;
    }

    @Override
    public void ItemClicked(int position)
    {
        String current=data.get(position);
        Selected=current;
    }

    @Override
    public void ItemLongClicked(int position)
    {
        String current=data.get(position);
        Selected=current;
        Dialog Score=new Dialog(context);
        Score.setContentView(R.layout.layout_dialog_admin_scorecard_setscore);
    }

    @Override
    public void ItemUsed(String current,String Who,String Clicked)
    {

        if (Clicked.equals("Clicked"))
        {
           switch (Who)
           {
               case "BatsMen1":
                   BatsMen1.setText(current);
                   databaseReference.child("Matches").child(team1+"Vs"+team2).child("BatsMen1").setValue(current);
                   break;
               case "BatsMen2":
                   BatsMen2.setText(current);
                   databaseReference.child("Matches").child(team1+"Vs"+team2).child("BatsMen2").setValue(current);
                   break;
               case "Bowler":
                   Bowler.setText(current);
                   databaseReference.child("Matches").child(team1+"Vs"+team2).child("Bowler").setValue(current);
                   break;
               default:

           }
        }else
        {

        }
    }
}
