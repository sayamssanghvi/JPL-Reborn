package com.zodiac.sanghvi.jplreborn;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Admin_ScoreCard extends AppCompatActivity implements  com.zodiac.sanghvi.jplreborn.Frag_ScoreCard.Frag_Comm {

    TextView Overs,Vs,Batting,Bowling,BatsMen1,BatsMen2,Bowler,Choose;
    Button NextOver,NextInnings;
    private ArrayList<String> BatList,BowlList;
    List<Game_play> game_plays;
    EditText Runs,Wickets;
    boolean Innings=false;
    boolean[] MatchOver = {false};
    Context context=this;
    String team1,team2;
    int overs=1,Target;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;


    @Override
    public void onBackPressed()
    {
        SaveDataMethod();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_score_card);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        NextInnings= (Button) findViewById(R.id.NextInnings);
        BatsMen1= (TextView) findViewById(R.id.BatsMen1);
        BatsMen2= (TextView) findViewById(R.id.BatsMen2);
        Batting= (TextView) findViewById(R.id.Batting);
        Bowling= (TextView) findViewById(R.id.Bowling);
        NextOver= (Button) findViewById(R.id.NextOver);
        Wickets= (EditText) findViewById(R.id.Wickets);
        Bowler= (TextView) findViewById(R.id.Bowler);
        Choose= (TextView) findViewById(R.id.Choose);
        Overs= (TextView) findViewById(R.id.Overs);
        Runs= (EditText) findViewById(R.id.Runs);
        Vs= (TextView) findViewById(R.id.Vs);

        BatList=new ArrayList<>();
        BowlList=new ArrayList<>();
        game_plays=new ArrayList<>();
        team1=getIntent().getStringExtra("Team1");
        team2=getIntent().getStringExtra("Team2");
        Overs.setText(String.valueOf(overs));
        Batting.setText(Bat_Bowl.Batting);
        Bowling.setText(Bat_Bowl.Bowling);
        Vs.setText(team1+"  "+"Vs"+"  "+team2);
        Choose.setText(Bat_Bowl.Toss +" "+"Won the toss and Choose to"+" "+ Bat_Bowl.Choose);
        NextOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                NextOver();
            }
        });
        NextInnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                NextInnings();
            }
        });
        BatsMen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {Frag_Call(Bat_Bowl.Batting,"BatsMen1");}
        });
        BatsMen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Frag_Call(Bat_Bowl.Batting,"BatsMen2");
            }
        });
        Bowler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {Frag_Call(Bat_Bowl.Bowling,"Bowler");
            }
        });

    }

    @Override
    public void ItemUsed(final String current, final String Who, String Clicked)
    {
        NextOver.setVisibility(View.VISIBLE);
        NextInnings.setVisibility(View.VISIBLE);
        if (Clicked.equals("Clicked"))
        {
            switch (Who)
            {
                case "BatsMen1":
                    BatsMen1.setText(current);
                    databaseReference.child("Matches").child(team1+"Vs"+team2).child("CBatsMen1").setValue(current);
                    break;
                case "BatsMen2":
                    BatsMen2.setText(current);
                    databaseReference.child("Matches").child(team1+"Vs"+team2).child("CBatsMen2").setValue(current);
                    break;
                case "Bowler":
                    Bowler.setText(current);
                    databaseReference.child("Matches").child(team1+"Vs"+team2).child("CBowler").setValue(current);
                    break;
                default:

            }
        }else
        {
            final Dialog Score=new Dialog(context);
            Score.setContentView(R.layout.layout_dialog_admin_scorecard_setscore);
            Score.show();
            final EditText RunsWickets= (EditText) Score.findViewById(R.id.Score);
            Button Ok= (Button) Score.findViewById(R.id.Ok);
            Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(RunsWickets.getText()!=null || RunsWickets.getText().length()>0)
                    {
                        if (Who.equals("BatsMen1") || Who.equals("BatsMen2")) {
                            databaseReference.child("Matches").child(team1 + "Vs" + team2).child(Bat_Bowl.Batting).child(current).child("Runs").setValue(Integer.valueOf(String.valueOf(RunsWickets.getText())));
                            Score.dismiss();
                        }else if(Who.equals("Bowler")){
                            databaseReference.child("Matches").child(team1 + "Vs" + team2).child(Bat_Bowl.Bowling).child(current).child("Wickets").setValue(Integer.valueOf(String.valueOf(RunsWickets.getText())));
                            Score.dismiss();
                        }
                    }
                }
            });
        }
    }

    public void Frag_Call(String Calling,String Who)
    {
        NextInnings.setVisibility(View.INVISIBLE);
        NextOver.setVisibility(View.INVISIBLE);
        getData(Calling);
        Frag_ScoreCard Frag_ScoreCard=new Frag_ScoreCard();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Bundle bundle=new Bundle();
        if (Calling.equals(Bat_Bowl.Batting))
            bundle.putStringArrayList("Data",BatList);
        else
            bundle.putStringArrayList("Data",BowlList);
        bundle.putString("Who",Who);
        Frag_ScoreCard.setArguments(bundle);
        fragmentTransaction.add(R.id.ScoreCard,Frag_ScoreCard);
        fragmentTransaction.commit();
    }

    private void getData(final String Calling)
    {
        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(BatList.size()>0 )
                    BatList.clear();
                if(BowlList.size()>0)
                    BowlList.clear();
                List<String> Players=new ArrayList<>();
                for(DataSnapshot Snapshot:dataSnapshot.child("Teams").child(Calling).getChildren())
                {
                    Players.add(Snapshot.getKey());
                }

                for (int i=0;i<Players.size();i++)
                {
                    String Names=Players.get(i);
                    if (Calling.equals(Bat_Bowl.Bowling))
                        BowlList.add(Names);
                    else
                        BatList.add(Names);
                }
                Players.clear();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }

    public void NextInnings()
    {
        if(!Innings)
        {
            final Dialog Confirm = new Dialog(context);
            Confirm.setContentView(R.layout.layout_dialog_admin_scorecard_confirm);
            TextView Yes = (TextView) Confirm.findViewById(R.id.Yes);
            Confirm.show();
            Yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        databaseReference.child("Matches").child(team1 + "Vs" + team2).child("Target").setValue(Integer.valueOf(String.valueOf(Runs.getText())));
                        Target = Integer.valueOf(String.valueOf(Runs.getText()));
                        Bat_Bowl.Batting = String.valueOf(Bowling.getText());
                        Bat_Bowl.Bowling = String.valueOf(Batting.getText());
                        Batting.setText(Bat_Bowl.Batting);
                        Bowling.setText(Bat_Bowl.Bowling);
                        BatsMen1.setText("Choose A BatsMen");
                        BatsMen2.setText("Choose A BatsMen");
                        Bowler.setText("Choose A Bowler");
                        Overs.setText("1");
                        Runs.setText("");
                        Wickets.setText("");
                        Innings = true;
                        NextInnings.setText("Match Finished");
                        Confirm.dismiss();
                }
            });
        }else {
            overs=0;
            game_plays.clear();
            final Dialog Confirm = new Dialog(context);
            int WonBy;
            Confirm.setContentView(R.layout.layout_dialog_admin_scorecard_confirm);
            final TextView Message = (TextView) Confirm.findViewById(R.id.Message);
            TextView Yes = (TextView) Confirm.findViewById(R.id.Yes);
            Confirm.show();
            if (Integer.valueOf(String.valueOf(Runs.getText())) > Target) 
            {
                WonBy = 15 - Integer.valueOf(String.valueOf(Wickets));
                Bat_Bowl.Won=Bat_Bowl.Batting;
                Message.setText(Bat_Bowl.Batting + " " + "Won the Match By" + " " + WonBy);
            } else
            {
                WonBy=Target-Integer.valueOf(String.valueOf(Runs.getText()));
                Bat_Bowl.Won=Bat_Bowl.Bowling;
                Message.setText(Bat_Bowl.Bowling+" "+"Won the Match By"+" "+WonBy);
            }
            Yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    databaseReference.child("Matches").child(team1+"Vs"+team2).child("Verdict").setValue(Message.getText());
                    databaseReference.child("Matches").child(team1+"Vs"+team2).child("Won").setValue(Bat_Bowl.Won);
                    MatchOver[0]=true;
                    Confirm.dismiss();
                }
            });
        }
        if(MatchOver[0])
            finish();
    }

    public void NextOver()
    {
        if(Runs.length()>0 && Wickets.length()>0 && Runs!=null && overs<=21 && Integer.valueOf(String.valueOf(Wickets.getText()))<16)
        {
            for (int i=0;i<1;i++)
            {
                Game_play Game_Play=new Game_play();
                Game_Play.Wickets=Integer.valueOf(String.valueOf(Wickets.getText()));
                Game_Play.Runs=Integer.valueOf(String.valueOf(Runs.getText()));
                game_plays.add(Game_Play);
            }
            if(!Innings)
                databaseReference.child("Matches").child(team1+"Vs"+team2).child("1st Innings").child("Overs").setValue(game_plays);
            else
                databaseReference.child("Matches").child(team1+"Vs"+team2).child("2nd Innings").child("Overs").setValue(game_plays);

            databaseReference.child("Matches").child(team1+"Vs"+team2).child("COver").setValue(overs);
            databaseReference.child("Matches").child(team1+"Vs"+team2).child("CRuns").setValue(Integer.valueOf(String.valueOf(Runs.getText())));
            databaseReference.child("Matches").child(team1+"Vs"+team2).child("CWickets").setValue(Integer.valueOf(String.valueOf(Wickets.getText())));
            overs++;
            Overs.setText(String.valueOf(overs));

        }else
        {
            final Dialog Confirm=new Dialog(context);
            Confirm.setContentView(R.layout.layout_dialog_admin_scorecard_confirm);
            final TextView Message = (TextView) Confirm.findViewById(R.id.Message);
            TextView Yes = (TextView) Confirm.findViewById(R.id.Yes);
            Yes.setText("Sure");
            Confirm.setCancelable(false);
            Confirm.show();
            Message.setText("Please Check if Runs & Wickets are entered properly!!");
            Yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                 Confirm.dismiss();
                }
            });
        }
    }

    public void SaveDataMethod()
    {
        final Dialog Confirm=new Dialog(context);
        Confirm.setContentView(R.layout.layout_dialog_admin_scorecard_confirm);
        TextView Message= (TextView) Confirm.findViewById(R.id.Message);
        TextView Yes= (TextView) Confirm.findViewById(R.id.Yes);
        Message.setText("Are you sure you  want to QUIT the MATCH?");
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Confirm.dismiss();
                finish();
            }
        });
        Confirm.show();
    }
}
