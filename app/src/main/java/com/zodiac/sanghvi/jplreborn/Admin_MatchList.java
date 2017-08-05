package com.zodiac.sanghvi.jplreborn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin_MatchList extends AppCompatActivity implements Match_Adapter.Match_Comm
{

    String[] Team1,Team2,Img_Team1,Img_Team2;
    List<Match> data= new ArrayList<>();
    boolean isRvOpen=false;
    TextView MainText;
    ImageView Expand;

    RelativeLayout Day1,Day2,Day3,Day4,Day5,Day6,Day7;
    Match_Adapter Match_adapter;
    RecyclerView RecyclerView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_matchlist);


        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Dates");

        Day1= (RelativeLayout) findViewById(R.id.Day_1);
        Day2= (RelativeLayout) findViewById(R.id.Day_2);
        Day3= (RelativeLayout) findViewById(R.id.Day_3);
        Day4= (RelativeLayout) findViewById(R.id.Day_4);
        Day5= (RelativeLayout) findViewById(R.id.Day_5);
        Day6= (RelativeLayout) findViewById(R.id.Day_6);
        Day7= (RelativeLayout) findViewById(R.id.Day_7);

        MainText= (TextView) findViewById(R.id.MainTextView);

        Expand= (ImageView) findViewById(R.id.Expand_Img);

        RecyclerView= (android.support.v7.widget.RecyclerView) findViewById(R.id.Match_Rv);

        Day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               CallRv(1);
            }
        });

        Day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             MainText.setText("02/07/2017");
             CallRv(2);
            }
        });

        Day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainText.setText("09/07/2017");
                CallRv(3);
            }
        });

        Day4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainText.setText("16/07/2017");
                CallRv(4);
            }
        });

        Day5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainText.setText("25/07/2017");
                CallRv(5);
            }
        });

    }

    public void SetRv(int Day)
    {
        Match_adapter=new Match_Adapter(getBaseContext(),getData(Day));
        Match_adapter.setMatch_Comm(this);
        RecyclerView.setVisibility(View.VISIBLE);
        RecyclerView.setAdapter(Match_adapter);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        isRvOpen=true;
    }

    public void CallRv(int Day)
    {
        if(!isRvOpen)
        {
            SetRv(Day);
            Expand.setImageResource(R.drawable.expand_less);
            Day2.setVisibility(View.INVISIBLE);
            Day3.setVisibility(View.INVISIBLE);
            Day4.setVisibility(View.INVISIBLE);
            Day5.setVisibility(View.INVISIBLE);
        }else
        {
            Expand.setImageResource(R.drawable.expand_more);
            RecyclerView.setVisibility(View.GONE);
            data.clear();
            MainText.setText("Semi-Finals");
            Day2.setVisibility(View.VISIBLE);
            Day3.setVisibility(View.VISIBLE);
            Day4.setVisibility(View.VISIBLE);
            Day5.setVisibility(View.VISIBLE);
            isRvOpen=false;
        }
    }

    public List<Match> getData(int Day)
    {
        if(Day==1)
        {
            String[] TeamA={"Pigeon","RCB","Mangal","Sonu","Jeet","Sultan"};
            String[] TeamB={"CSK","RSS","Bherav","Orbit","GB","JD"};
            String[] Img_TeamA={"http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg"};
            String[] Img_TeamB={"https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg"};
            Team1=TeamA;
            Team2=TeamB;
            Img_Team1=Img_TeamA;
            Img_Team2=Img_TeamB;
        }else if(Day==2)
        {
            String[] TeamA={"Sonu","Mangal","Sultan","Pigeon","Bherav","JD"};
            String[] TeamB={"RCB","GB","RSS","Jeet","CSK","Orbit"};
            String[] Img_TeamA={"http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg"};
            String[] Img_TeamB={"https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg"};
            Team1=TeamA;
            Team2=TeamB;
            Img_Team1=Img_TeamA;
            Img_Team2=Img_TeamB;
        }else if(Day==3)
        {
            String[] TeamA={"Mangal","Orbit","GB","RCB","Pigeon","RSS"};
            String[] TeamB={"Jeet","Sultan","CSK","JD","Bherav","Sonu"};
            String[] Img_TeamA={"http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg"};
            String[] Img_TeamB={"https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg"};
            Team1=TeamA;
            Team2=TeamB;
            Img_Team1=Img_TeamA;
            Img_Team2=Img_TeamB;
        }else if(Day==4)
        {
            String[] TeamA={"JD","Pigeon","RCB","Mangal","Sonu","Jeet"};
            String[] TeamB={"RSS","GB","Orbit","CSK","Sultan","Bherav"};
            String[] Img_TeamA={"http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg"};
            String[] Img_TeamB={"https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg"};
            Team1=TeamA;
            Team2=TeamB;
            Img_Team1=Img_TeamA;
            Img_Team2=Img_TeamB;
        }else if(Day==5)
        {
            String[] TeamA={"Sonu","Bherav","Sultan","Jeet","Orbit","Pigeon"};
            String[] TeamB={"JD","GB","RCB","CSK","RSS","Mangal"};
            String[] Img_TeamA={"http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg"};
            String[] Img_TeamB={"https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg","https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/1200px-Tom_Holland_by_Gage_Skidmore.jpg","http://www.joblo.com/newsimages1/robert-downey-jr-iron-man.jpg"};
            Team1=TeamA;
            Team2=TeamB;
            Img_Team1=Img_TeamA;
            Img_Team2=Img_TeamB;
        }

        for(int i=0;i<6;i++)
        {
            Match current=new Match();
            current.Team1=Team1[i];
            current.Team2=Team2[i];
            current.Img_Team1=Img_Team1[i];
            current.Img_Team2=Img_Team2[i];
            data.add(current);
        }
        return data;
    }

    @Override
    public void ItemClicked(int position)
    {
        String Team1=data.get(position).Team1;
        String Team2=data.get(position).Team2;
        Intent i=new Intent(this,Admin_BatBall.class);
        i.putExtra("Team1",Team1);
        i.putExtra("Team2",Team2);
        startActivity(i);
    }
}
