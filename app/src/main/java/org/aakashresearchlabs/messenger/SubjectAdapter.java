package org.aakashresearchlabs.messenger;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public void onBindViewHolder(final SubjectAdapter.ViewHolder holder, int position) {

        final SubjectClass currentSubject=subjectList.get(position);

        holder.subjectName.setText(currentSubject.getSubjectName());
        holder.fileName.setText(currentSubject.getFileName());
        holder.fileThumbNail.setMaxHeight(holder.fileThumbNail.getWidth());
        String extn=getExtension(currentSubject.getFileName());
        if(extn.contains("jpg")||extn.contains("jpeg"))
            holder.fileThumbNail.setImageResource(R.drawable.jpg_icon);
        else if(extn.contains("pdf"))
            holder.fileThumbNail.setImageResource(R.drawable.pdf_icon);
        else if(extn.contains("doc"))
            holder.fileThumbNail.setImageResource(R.drawable.doc_icon);
        else if(extn.contains("png"))
            holder.fileThumbNail.setImageResource(R.drawable.png_icon);
        else if(extn.contains("txt"))
            holder.fileThumbNail.setImageResource(R.drawable.txt_icon);
        else if(extn.contains("ppt"))
            holder.fileThumbNail.setImageResource(R.drawable.ppt_icon);
        else
            holder.fileThumbNail.setImageResource(R.drawable.unknown_icon);


        holder.downloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,currentSubject.getFileURL(), Toast.LENGTH_SHORT).show();

            }
        });


//         //On clicking the view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context,currentSubject.getFileURL(), Toast.LENGTH_SHORT).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentSubject.getFileURL()));
                context.startActivity(browserIntent);
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
        ImageView fileThumbNail,downloadFile;

        public ViewHolder(View itemView) {
            //finding the required views by id
            super(itemView);
            subjectName=(TextView)itemView.findViewById(R.id.subjectName);
            fileName=(TextView)itemView.findViewById(R.id.fileName);
            fileThumbNail=(ImageView)itemView.findViewById(R.id.fileThumbNail);
            downloadFile=(ImageView)itemView.findViewById(R.id.downloadFile);
        }

    }
    public String getExtension(String s)
    {
        String extn="";
        int startPostn=s.lastIndexOf('.');
        extn=s.substring(startPostn+1,s.length());
        return extn;
    }


}
