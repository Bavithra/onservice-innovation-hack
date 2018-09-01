package com.example.bavithrathangaraj.oneserviceinnovationhackathon.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bavithrathangaraj.oneserviceinnovationhackathon.R;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerCallback;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.VolleyHelper;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.Item;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.User;
import com.google.gson.Gson;

import org.json.JSONObject;

public class NotificationRecyclerView extends RecyclerView.Adapter<NotificationRecyclerView.CustomRecyclerView> {

    String[] text;
    String[] botton;

    Context context;
    public NotificationRecyclerView(String[] text, String[] button, Context context) {
        this.text = text;
        this.botton = button;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, null);
        NotificationRecyclerView.CustomRecyclerView rcv = new NotificationRecyclerView.CustomRecyclerView(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerView holder, int position) {
        for (int i = 0; i < position; i++) {
            holder.textView.setText(text[i]);
            holder.buttonView.setText(botton[i]);
        }
    }

    @Override
    public int getItemCount() {
        return text.length;
    }

    public class CustomRecyclerView extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        Button buttonView;

        CustomRecyclerView(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = itemView.findViewById(R.id.notification_text);
            buttonView = itemView.findViewById(R.id.notification_button);;
        }

        @Override
        public void onClick(View v) {
            buttonView = v.findViewById(R.id.notification_button);
            buttonView.setText("Review");
            Gson gson = new Gson();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String json = prefs.getString("item", "");
            Item obj = gson.fromJson(json, Item.class);
            VolleyHelper.putItemDetails(obj.getId(),context);
        }
    }
}
