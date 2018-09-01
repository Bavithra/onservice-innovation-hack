package com.example.bavithrathangaraj.oneserviceinnovationhackathon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.Item;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.SingletonRequestQueue;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomRecyclerView> implements Filterable {
    private List<Item> itemList;
    private List<Item> mFilteredList;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Button giveButton;



    public RecyclerViewAdapter( List<Item> itemList) {
        this.itemList = itemList;
        mFilteredList=itemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.CustomRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard, null);
        CustomRecyclerView rcv = new CustomRecyclerView(layoutView);
        return rcv;
    }

    @Override
    public int getItemCount() {
        return this.mFilteredList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.CustomRecyclerView holder, int position) {
        Item myData = mFilteredList.get(position);
        holder.txtLabel.setText(myData.getName());
        if (myData.getPic() != null) {
            try{
                byte [] encodeByte= Base64.decode(myData.getPic(),Base64.DEFAULT);
                Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                holder.avatar.setImageBitmap(bitmap);
                if (myData.getType().equalsIgnoreCase("giveaway")) {

                    // show give away view
                    holder.giveout.setVisibility(View.VISIBLE);
                    holder.lookingfor.setVisibility(View.GONE);
                } else {
                    holder.giveout.setVisibility(View.GONE);
                    holder.lookingfor.setVisibility(View.VISIBLE);
                }
            }catch(Exception e){
                e.getMessage();
            }
        }
    }



    public class CustomRecyclerView extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtLabel;
        TextView giveout;
        TextView lookingfor;
        ImageView avatar;

        CustomRecyclerView(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txtLabel = itemView.findViewById(R.id.description);
            txtLabel = itemView.findViewById(R.id.giving_out);
            txtLabel = itemView.findViewById(R.id.looking_for);
            avatar = itemView.findViewById(R.id.product);
            giveButton = itemView.findViewById(R.id.button1);
        }

        @Override
        public void onClick(View v) {
                giveButton = v.findViewById(R.id.button1);
                giveButton.setText("Requested");

            //go through each item if you have few items within recycler view
            if(getLayoutPosition()==0){
                //Do whatever you want here


            }else if(getLayoutPosition()==1){
                //Do whatever you want here

            }else if(getLayoutPosition()==2){

            }else if(getLayoutPosition()==3){

            }else if(getLayoutPosition()==4){

            }else if(getLayoutPosition()==5){

            }
        }
    }

    @Override
    public  Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = itemList;
                } else {

                    ArrayList<Item> filteredList = new ArrayList<>();

                    for (Item item : itemList) {

                        if (item.getName().toLowerCase().contains(charString) || item.getName().toLowerCase().contains(charString) || item.getName().toLowerCase().contains(charString)) {

                            filteredList.add(item);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }






}
