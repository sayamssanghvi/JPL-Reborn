package com.zodiac.sanghvi.jplreborn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements com.zodiac.sanghvi.jplreborn.Search_Adapter.Communicator
{

    private EditText SearchBar;
    private RecyclerView Search_Rv;
    private Search_Adapter Search_Adapter;
    private Bitmap images;
    final List<Player> data=new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,Menu_Option.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        SearchBar= (EditText) findViewById(R.id.search_text);
        Search_Rv= (RecyclerView) findViewById(R.id.Search_RV);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Players");

        SearchBarFuntion();
}


    public List<Player> getData(List<Player> Player)

    {
     return Player;
    }

    public Bitmap DisplayPic(String S)
    {
        Picasso.with(getBaseContext()).load(S).resize(75,75).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
            {
               images=bitmap;

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {}

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable)
            {
                images= BitmapFactory.decodeResource(getResources(),R.drawable.preloading_image);
            }
        });

        return images;
    }

    public void SearchBarFuntion(){
        SearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                final String Search_Text = String.valueOf(s).toLowerCase();

                if (Search_Text.length() > 0 && !Search_Text.isEmpty()) {
                    valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            List<String> Names= new ArrayList<>();

                            for(DataSnapshot Snapshot:dataSnapshot.getChildren())
                                Names.add(Snapshot.getKey());

                            for (int i = 0; i < Names.size(); i++) {
                                Player current = new Player();
                                String Player_Name =  Names.get(i);
                                if (Player_Name != null && Player_Name.startsWith(Search_Text)) {
                                    Player_Stats Player_Stats = dataSnapshot.child(Player_Name).getValue(Player_Stats.class);
                                    Bitmap Img = DisplayPic(Player_Stats.getImg());
                                    current.Img = Img;
                                    current.Name = Player_Stats.getName();
                                    current.TeamName = Player_Stats.getTeamName();
                                    data.add(current);
                                    Search_Adapter = new Search_Adapter(getBaseContext(), getData(data));
                                    Search_Rv.setAdapter(Search_Adapter);
                                    Search_Adapter.setCommunicator(SearchActivity.this);
                                    Search_Rv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    databaseReference.addValueEventListener(valueEventListener);
                    data.clear();
                } else
                    data.clear();
            }

        });
    }

    @Override
    public void ItemClicked(int position)
    {
        List<Player> Player=getData(data);
        Player ItemClicked=Player.get(position);
        String Name=ItemClicked.Name.toLowerCase();
        Intent i=new Intent(getBaseContext(),PlayerActivity.class);
        i.putExtra("PName",Name);
        startActivity(i);
    }
}



