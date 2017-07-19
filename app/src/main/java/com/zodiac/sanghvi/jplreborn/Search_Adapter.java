package com.zodiac.sanghvi.jplreborn;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


/**
 * Created by Sanghvi on 6/11/17.
 */

public class Search_Adapter extends RecyclerView.Adapter<Search_Adapter.SearchView_Holder> {
    private LayoutInflater inflater;
    private List<Player> data = Collections.emptyList();
    private Communicator communicator;

    public Search_Adapter(Context context, List<Player> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    public void setCommunicator(Communicator communicator)
    {
        this.communicator=communicator;
    }

    @Override
    public SearchView_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.layout_rv_search, parent, false);
        SearchView_Holder holder = new SearchView_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchView_Holder holder, int position) {
        Player current = data.get(position);
        holder.TeamName.setText(current.TeamName);
        holder.PName.setText(current.Name);
        holder.PImg.setImageBitmap(current.Img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class SearchView_Holder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView PName, TeamName;
        ImageView PImg;

        public SearchView_Holder(View v)
        {
            super(v);
            itemView.setOnClickListener(this);

            PName = (TextView) v.findViewById(R.id.PName);
            TeamName = (TextView) v.findViewById(R.id.TeamName);
            PImg = (ImageView) v.findViewById(R.id.PImg);
        }

        @Override
        public void onClick(View v)
        {
            if(communicator!=null)
            communicator.ItemClicked(getAdapterPosition());
        }
    }

    public interface Communicator
    {
       void ItemClicked(int position);
    }

}
