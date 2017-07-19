package com.zodiac.sanghvi.jplreborn;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SplashActivty extends Activity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash_screen);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        databaseReference.child("Matches").child("JD Vs Sultan").child("Choose").setValue("Batting");
        Log.d("Sayam","Worked");

        if(isNetworkAvailable())
        {
            new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    ImageView imageView = (ImageView) findViewById(R.id.SplashScreen);
                    int width = imageView.getWidth() / 2;
                    int height = imageView.getHeight() / 2;
                    float finalRadius = (float) Math.hypot(width, height);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        Animator anim = ViewAnimationUtils.createCircularReveal(imageView, width, height, 0, finalRadius);
                        anim.start();
                    }
                    Intent i = new Intent(SplashActivty.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }.start();
        }else
        {
            AlertDialog.Builder Internet_Check=new AlertDialog.Builder(this);
            Internet_Check.setMessage("Please Connect to INTERNET to Access our Database");
            Internet_Check.setCancelable(false);
            Internet_Check.setNeutralButton("Sure", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                 recreate();
                }
            });
            AlertDialog alertdialog=Internet_Check.create();
            alertdialog.show();
        }
    }
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

