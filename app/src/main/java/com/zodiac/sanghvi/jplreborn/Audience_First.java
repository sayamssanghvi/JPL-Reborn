package com.zodiac.sanghvi.jplreborn;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Audience_First extends AppCompatActivity
{

    private ImageButton Menu;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_first);

        Menu= (ImageButton) findViewById(R.id.Menu);

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getBaseContext(),Menu_Option.class);
                startActivity(i);
            }
        });
    }
}
