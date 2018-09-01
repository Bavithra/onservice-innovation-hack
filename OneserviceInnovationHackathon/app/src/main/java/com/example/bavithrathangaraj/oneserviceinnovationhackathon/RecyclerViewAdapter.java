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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.Item;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.SingletonRequestQueue;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomRecyclerView> {
    private List<Item> itemList;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public RecyclerViewAdapter( List<Item> itemList) {
        this.itemList = itemList;
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
        return this.itemList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.CustomRecyclerView holder, int position) {
        Item myData = itemList.get(position);
        holder.txtLabel.setText(myData.getDetails());
        if (myData.getPic() != null) {
            try{
                byte [] encodeByte= Base64.decode(myData.getPic(),Base64.DEFAULT);
                Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                holder.avatar.setImageBitmap(bitmap);
            }catch(Exception e){
                e.getMessage();
            }
        }
    }

    public class CustomRecyclerView extends RecyclerView.ViewHolder {
        TextView txtLabel;
        ImageView avatar;

        CustomRecyclerView(View itemView) {
            super(itemView);
            txtLabel = itemView.findViewById(R.id.description);
            avatar = itemView.findViewById(R.id.product);
        }
    }
}
