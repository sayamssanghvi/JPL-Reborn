package com.zodiac.sanghvi.jplreborn;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sanghvi on 7/13/17.
 */

public class Player_Adapter extends RecyclerView.Adapter<Player_Adapter.Player_ViewHolder>
{
    private LayoutInflater inflater;
    private List<String> data= Collections.emptyList();
    private Player_Comm player_comm;

    public Player_Adapter(Context context,List<String> data)
    {
        this.data=data;
        this.inflater=LayoutInflater.from(context);
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

    public void setItemClicked(Player_Comm player_comm)
    {
        this.player_comm=player_comm;
    }

    @Override
    public int getItemCount()
    {
            return data.size();
    }

    class Player_ViewHolder extends RecyclerView.ViewHolder
    {
        TextView PName;

        public Player_ViewHolder(View itemView)
        {
            super(itemView);
            PName= (TextView) itemView.findViewById(R.id.PlayerName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    player_comm.ItemClicked(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v)
                {
                    player_comm.ItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }

    public interface Player_Comm
    {
        void ItemClicked(int position);
        void ItemLongClicked(int position);
    }
}
