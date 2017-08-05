package com.zodiac.sanghvi.jplreborn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class FeaturesActivity extends AppCompatActivity implements Search_Adapter.Communicator {

    private FirebaseDatabase firebase;
    private DatabaseReference databaseReference;
    private ValueEventListener valueeventlistener;
    private List<Player> data=new ArrayList<>();
    private RecyclerView Display_RV;
    private Search_Adapter Query_Adapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        int CheckLayout=getIntent().getIntExtra("CheckLayout",2);  //Vote=1,MostRuns=2,MostWickets=3

        if(CheckLayout==1)
        {
            //Vote
            setContentView(R.layout.layout_vote);
            Toast.makeText(this, "Vote Plz", Toast.LENGTH_SHORT).show();
        }
        else if(CheckLayout==2)
        {
            //Runs
            setContentView(R.layout.layout_most);

            TextView Title= (TextView) findViewById(R.id.Title);
            Title.setText("Top Ranking(Runs)");
            firebase=FirebaseDatabase.getInstance();
            databaseReference=firebase.getReference().child("Players");
            Display_RV= (RecyclerView) findViewById(R.id.Display_RV);

            Toast.makeText(this, "Runs Scored"+getBaseContext(), Toast.LENGTH_SHORT).show();

            Query MostRuns=databaseReference.orderByChild("Runs");
            valueeventlistener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    List<String> Names= new ArrayList<>();

                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        Names.add(snapshot.getKey());
                    }
                    for(int i=Names.size()-1;i>=0;i--)
                    {
                        Player current = new Player();
                        Player_Stats PStats = dataSnapshot.child(Names.get(i)).getValue(Player_Stats.class);
                        current.Img = PStats.getImg();
                        current.Name = PStats.getName();
                        current.TeamName = PStats.getTeamName();
                        data.add(current);
                        Query_Adapter = new Search_Adapter(getBaseContext(), getdata(data));
                        Display_RV.setAdapter(Query_Adapter);
                        Query_Adapter.setCommunicator(FeaturesActivity.this);
                        Display_RV.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            MostRuns.addValueEventListener(valueeventlistener);
        }
        else if(CheckLayout==3)
        {
            //Wickets
            setContentView(R.layout.layout_most);
            TextView Title= (TextView) findViewById(R.id.Title);
            Title.setText("Top Ranking(Wickets)");

            firebase=FirebaseDatabase.getInstance();
            databaseReference=firebase.getReference().child("Players");
            Display_RV= (RecyclerView) findViewById(R.id.Display_RV);

            Query MostWickets=databaseReference.orderByChild("Wickets");
            valueeventlistener = new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    List<String> Names= new ArrayList<>();

                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        Names.add(snapshot.getKey());
                    }
                    for(int i=Names.size()-1;i>=0;i--)
                    {
                        Player current = new Player();
                        Player_Stats PStats = dataSnapshot.child(Names.get(i)).getValue(Player_Stats.class);
                        current.Img =PStats.getImg();
                        current.Name = PStats.getName();
                        current.TeamName = PStats.getTeamName();
                        data.add(current);
                        Query_Adapter = new Search_Adapter(getBaseContext(), getdata(data));
                        Display_RV.setAdapter(Query_Adapter);
                        Query_Adapter.setCommunicator(FeaturesActivity.this);
                        Display_RV.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            MostWickets.addValueEventListener(valueeventlistener);
        }
        else if(CheckLayout==4)
        {
            //Valuable
            setContentView(R.layout.layout_features);
            Toast.makeText(this, "Valuable Player", Toast.LENGTH_SHORT).show();
        }
    }

    public List<Player> getdata(List<Player> data)
    {
        return data;
    }

    @Override
    public void ItemClicked(int position)
    {
        List<Player> Player=getdata(data);
        Player ItemClicked=Player.get(position);
        String Name=ItemClicked.Name.toLowerCase();
        Intent i=new Intent(getBaseContext(),PlayerActivity.class);
        i.putExtra("PName",Name);
        startActivity(i);
    }
}
