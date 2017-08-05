package com.zodiac.sanghvi.jplreborn;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Sanghvi on 7/20/17.
 */

public class Frag_ScoreCard extends Fragment implements com.zodiac.sanghvi.jplreborn.Player_Adapter.Player_Comm
{
    RecyclerView Player_Rv;
    Player_Adapter Player_Adapter;
    ArrayList<String> data=new ArrayList<>();
    String current,Who;
    Bundle getData=new Bundle();
    Frag_Comm frag_Comm;
    Context context;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context=context;
        frag_Comm= (Frag_Comm) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.layout_frag_admin_scorecard,container,false);
        getData=getArguments();
        Who=getData.getString("Who");
        data=getData.getStringArrayList("Data");
        TextView Which= (TextView) v.findViewById(R.id.Team_Name);
        Which.setText(getData.getString("Who"));
        Player_Rv= (RecyclerView) v.findViewById(R.id.Player_Rv);
        Player_Adapter = new Player_Adapter(context, data);
        Player_Rv.setAdapter(Player_Adapter);
        Player_Rv.setLayoutManager(new LinearLayoutManager(getContext()));
        Player_Adapter.setItemClicked(this);
        return v;
    }

    @Override
    public void ItemClicked(int position)
    {
        current=data.get(position);
        close("Clicked");
    }

    @Override
    public void ItemLongClicked(int position)
    {
        current=data.get(position);
        close("LongClicked");
    }

    public void close(String Clicked)
    {
        frag_Comm.ItemUsed(current,getData.getString("Who"),Clicked);
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).remove(this).commit();
    }

    interface Frag_Comm
    {
        void ItemUsed(String current,String BatBowl,String Clicked);
    }
}
