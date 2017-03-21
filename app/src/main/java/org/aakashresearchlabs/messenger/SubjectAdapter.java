package org.aakashresearchlabs.messenger;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kevin on 3/21/2017.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder>{





    private Context context;
    List<SubjectClass> subjectList;

    public SubjectAdapter(List<SubjectClass> subjectList, Context context){
        super();
        //Getting all the articles
        this.subjectList = subjectList;
        this.context = context;
    }

    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflating the article_view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dummy_card, parent, false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(SubjectAdapter.ViewHolder holder, int position) {

        final SubjectClass currentSubject=subjectList.get(position);

        holder.subjectName.setText(currentSubject.getSubjectName());
        holder.fileName.setText(currentSubject.getFileName());
        //On clicking the view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //click stuff
            }
        });

    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Defining viewHolder
        TextView subjectName,fileName;

        public ViewHolder(View itemView) {
            //finding the required views by id
            super(itemView);
            subjectName=(TextView)itemView.findViewById(R.id.subjectName);
            fileName=(TextView)itemView.findViewById(R.id.fileName);
        }

    }


}