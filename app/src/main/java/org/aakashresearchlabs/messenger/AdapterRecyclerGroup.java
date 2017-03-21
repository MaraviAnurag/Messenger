package org.aakashresearchlabs.messenger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Snehit Sagi on 20-Mar-17.
 */

public class AdapterRecyclerGroup extends RecyclerView.Adapter<AdapterRecyclerGroup.MyViewHolder> {

    private String[] GroupNameSet;
    Context groupContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView groupCardView;
        public TextView groupnametextview;

        public MyViewHolder(View v) {
            super(v);
            groupCardView = (CardView) v.findViewById(R.id.groupcardview);
            groupnametextview = (TextView) v.findViewById(R.id.GroupName);
        }
    }

    public AdapterRecyclerGroup(String[] myGroupNameSet, Context context) {
        GroupNameSet = myGroupNameSet;
        this.groupContext = context;
    }

    @Override
    public AdapterRecyclerGroup.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.groupcard, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
      holder.groupnametextview.setText(GroupNameSet[position]);
        holder.groupCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatRoomIntent =new Intent(groupContext,chatRoom.class);
                groupContext.startActivity(chatRoomIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return GroupNameSet.length;
    }

}
