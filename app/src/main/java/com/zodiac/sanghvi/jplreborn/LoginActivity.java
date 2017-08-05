package com.zodiac.sanghvi.jplreborn;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button Audience,Admin;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Audience= (Button) findViewById(R.id.Audience);
        Admin= (Button) findViewById(R.id.Admin);

        Audience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(),Audience_First.class);
                startActivity(i);
            }
        });

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(),Admin_MatchList.class);
                startActivity(i);
            }
        });
    }

    private void Dialog_Call()
    {
        Dialog dialog=new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_dialog_login_password);

        dialog.show();
    }
}
