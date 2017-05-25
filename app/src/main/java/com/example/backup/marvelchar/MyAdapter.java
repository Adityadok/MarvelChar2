package com.example.backup.marvelchar;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Backup on 5/24/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

  private  List<ListItem> listItems;

    private  Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
           final ListItem listItem= listItems.get(position);
        holder.description.setText(listItem.getDescp());
        holder.name.setText(listItem.getName());
       Picasso.with(context)
               .load(listItem.getUrl())        //Displaying Images Using Picasso Library
               .into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"you clicked "+ listItem.getName(),Toast.LENGTH_LONG).show();
                Intent intent= new Intent(context,SecondActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle p1= new Bundle();
                intent.putExtra("name",listItem.getName());
                intent.putExtra("modified",listItem.getModified());
                intent.putExtra("id",listItem.getId());
                intent.putExtra("resourceURI",listItem.getResourceURI());

                context.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        public  ImageView imageView;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            description=(TextView)itemView.findViewById(R.id.description);
            imageView=(ImageView) itemView.findViewById(R.id.imageView);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);


        }
    }
}
