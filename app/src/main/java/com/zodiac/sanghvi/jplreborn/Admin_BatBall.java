package com.zodiac.sanghvi.jplreborn;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Admin_BatBall extends AppCompatActivity
{
    ImageButton Img_Team1,Img_Team2;
    String team1,team2,Batting_St,Toss;
    TextView Team1,Team2;
    Bat_Bowl bat_bowl;
    final Context context=this;
    Button Start,Batting,Bowling;
    boolean Check=false;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_batball);

        team1=getIntent().getStringExtra("Team1");
        team2=getIntent().getStringExtra("Team2");

        Team1= (TextView) findViewById(R.id.team1);
        Team2= (TextView) findViewById(R.id.team2);
        Img_Team1= (ImageButton) findViewById(R.id.img_team1);
        Img_Team2= (ImageButton) findViewById(R.id.img_team2);
        Start= (Button) findViewById(R.id.Start);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String img1=dataSnapshot.child("Teams").child(team1).child("Img").getValue(String.class);
                String img2=dataSnapshot.child("Teams").child(team2).child("Img").getValue(String.class);
                Picasso.with(getBaseContext()).load(img1).into(Img_Team1);
                Picasso.with(getBaseContext()).load(img2).into(Img_Team2);
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);

        Start.setClickable(false);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Matches").child(team1+"Vs"+team2).child("View").setValue(true);
                Intent i=new Intent(getBaseContext(), Admin_ScoreCard.class);
                i.putExtra("Team1",team1);
                i.putExtra("Team2",team2);
                startActivity(i);
            }
        });

        Img_Team1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {if(!Check) {CallDialog(team1);}
            }
        });

        Img_Team2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {if(!Check) {CallDialog(team2);}}
        });

        Team1.setText(team1);
        Team2.setText(team2);

    }

    public void CallDialog(final String Team)
    {
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_admin_batball);
        dialog.setTitle("Choose To");


        Batting= (Button) dialog.findViewById(R.id.Batting);
        Bowling= (Button) dialog.findViewById(R.id.Bowling);

        Batting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start.setClickable(true);
                if (Team.equals(team1))
                {
                    bat_bowl.Batting = team1;
                    bat_bowl.Bowling = team2;
                    Log.d("Sayam",""+bat_bowl.Batting);
                    Log.d("Sayam",""+bat_bowl.Bowling);
                }else
                {
                    bat_bowl.Batting = team2;
                    bat_bowl.Bowling = team1;
                    Log.d("Sayam",""+bat_bowl.Batting);
                    Log.d("Sayam",""+bat_bowl.Bowling);
                }
                bat_bowl.Toss=Team;
                bat_bowl.Choose="Batting";
                Start.setBackgroundResource(R.color.colorPrimary);
                databaseReference.child("Matches").child(team1+"Vs"+team2).child("Toss").setValue(Team);
                databaseReference.child("Matches").child(team1+"Vs"+team2).child("Choose").setValue("Batting");
                Check = true;
                dialog.dismiss();
            }
        });

        Bowling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Start.setClickable(true);
                if (Team.equals(team1))
                {
                    bat_bowl.Batting = team2;
                    bat_bowl.Bowling = team1;
                    Log.d("Sayam",""+bat_bowl.Batting);
                    Log.d("Sayam",""+bat_bowl.Bowling);
                }else
                {
                    bat_bowl.Batting = team1;
                    bat_bowl.Bowling = team2;
                    Log.d("Sayam",""+bat_bowl.Batting);
                    Log.d("Sayam",""+bat_bowl.Bowling);
                }
                bat_bowl.Toss=Team;
                bat_bowl.Choose="Bowling";
                Start.setBackgroundResource(R.color.ButtonColour);
                databaseReference.child("Matches").child(team1+"Vs"+team2).child("Toss").setValue(Team);
                databaseReference.child("Matches").child(team1+"Vs"+team2).child("Choose").setValue("Bowling");
                Check = true;
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }
}