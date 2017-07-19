package com.zodiac.sanghvi.jplreborn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zodiac.sanghvi.jplreborn.R;

public class PlayerActivity extends AppCompatActivity{

    String PName;
    TextView PlayerName;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_player);

        PlayerName= (TextView) findViewById(R.id.PlayerName);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Player");

        PName=getIntent().getStringExtra("PName");
        PlayerName.setText(databaseReference.getKey());

    }

}
