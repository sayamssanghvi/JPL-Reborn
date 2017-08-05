package com.zodiac.sanghvi.jplreborn;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sanghvi on 6/26/17.
 */

public class Match_Adapter extends RecyclerView.Adapter<Match_Adapter.Match_ViewHolder>
{
    private List<Match> data= Collections.emptyList();
    private LayoutInflater inflater;
    private Match_Comm match_comm;
    private Context context;

    public Match_Adapter(Context context,List<Match> data )
    {
        this.inflater=LayoutInflater.from(context);
        this.data=data;
        this.context=context;
    }

    @Override
    public Match_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
       View v=inflater.inflate(R.layout.layout_rv_match,parent,false);
       Match_ViewHolder holder=new Match_ViewHolder(v);
       return holder;
    }

    public void setMatch_Comm(Match_Comm match_Comm)
    {
        this.match_comm = match_Comm;
    }

    @Override
    public void onBindViewHolder(Match_ViewHolder holder, int position)
    {
        Match current=data.get(position);
        holder.Team1.setText(current.Team1);
        holder.Team2.setText(current.Team2);
        Picasso.with(context).load(current.Img_Team1).resize(90,120).into(holder.Img_Team1);
        Picasso.with(context).load(current.Img_Team2).resize(90,120).into(holder.Img_Team2);
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    class Match_ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView Img_Team1,Img_Team2;
        TextView Team1,Team2;

        public Match_ViewHolder(View itemView)
        {
            super(itemView);

            Img_Team1= (ImageView) itemView.findViewById(R.id.Img_Team1);
            Img_Team2= (ImageView) itemView.findViewById(R.id.Img_Team2);
            Team1= (TextView) itemView.findViewById(R.id.Team1);
            Team2= (TextView) itemView.findViewById(R.id.Team2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(match_comm!=null)
                    match_comm.ItemClicked(getAdapterPosition());
                }
            });
        }
    }

    interface Match_Comm
    {
        void ItemClicked(int position);
    }
}
