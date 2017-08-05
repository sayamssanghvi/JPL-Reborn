package com.zodiac.sanghvi.jplreborn;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Sanghvi on 7/13/17.
 */

public class Player_Adapter extends RecyclerView.Adapter<Player_Adapter.Player_ViewHolder>
{
    private LayoutInflater inflater;
    private ArrayList<String> data=new ArrayList<>();
    private Player_Comm player_comm;

    public Player_Adapter(Context context,ArrayList<String> data)
    {
        this.data=data;
        this.inflater=LayoutInflater.from(context);
    }

    public void setItemClicked(Player_Comm player_comm)
    {
        this.player_comm=player_comm;
    }

    @Override
    public Player_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v=inflater.inflate(R.layout.layout_rv_player,parent,false);
        return new Player_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Player_ViewHolder holder, int position)
    {
        String current=data.get(position);
        holder.PName.setText(current);
    }

    @Override
    public int getItemCount()
    {
            return data.size();
    }

    class Player_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        TextView PName;

        Player_ViewHolder(View v)
        {
            super(v);
            PName= (TextView) v.findViewById(R.id.Player_Name);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            player_comm.ItemClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            player_comm.ItemLongClicked(getAdapterPosition());
            return true;
        }
    }

    public interface Player_Comm
    {
        void ItemClicked(int position);
        void ItemLongClicked(int position);
    }
}
