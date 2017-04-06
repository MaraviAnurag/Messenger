package org.aakashresearchlabs.messenger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HARSHIT on 20-Mar-17.
 */

public class AdapterRecyclerGroup extends ArrayAdapter<String>{
    Context context;
    int layoutResourceId;
    List<String> data = new ArrayList<>();

    public AdapterRecyclerGroup(Context context, int layoutResourceId, List<String> data) {
        super(context,layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    public View getView(int position,View convertView,ViewGroup parent)
    {
        View row = convertView;
        GroupHolder holder = null;
        if(row == null)
 {
 LayoutInflater inflater = ((Activity)context).getLayoutInflater();
 row = inflater.inflate(layoutResourceId, parent, false);

 holder = new GroupHolder();
 holder.txtTitle = (TextView)row.findViewById(R.id.GroupName);
holder.cardView=(CardView)row.findViewById(R.id.groupcardview);
 row.setTag(holder);
 }
 else
 {
 holder = (GroupHolder)row.getTag();
 }
 final String title = data.get(position);
 holder.txtTitle.setText(title);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,chatRoom.class);
                intent.putExtra("user_name","hul");
                intent.putExtra("room_name",title);
                context.startActivity(intent);
            }
        });
 return row;

    }
    static class GroupHolder
 {
TextView txtTitle;
     CardView cardView;
}
}
