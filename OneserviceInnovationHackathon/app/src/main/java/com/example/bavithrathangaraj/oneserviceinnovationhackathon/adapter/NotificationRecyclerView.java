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
    TextView textView;
    Button buttonView;
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
        textView = layoutView.findViewById(R.id.notification_text);
        buttonView = layoutView.findViewById(R.id.notification_button);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerView holder, int position) {
        for (int i = 0; i < position; i++) {
            textView.setText(text[i]);
            buttonView.setText(botton[i]);
        }

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyHelper.requestLoginDetails(context, new ServerCallback() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                Gson gson = new Gson();
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                String json = prefs.getString("user", "");
                                User obj = gson.fromJson(json, User.class);
                                Item item = new Item();
                                item.setName(obj.getName());
                                item.setDetails("Item details here");
                                item.setDatePosted("01/09/2019");
                                item.setLocation("Clementi");
                                item.setStatus("not_available");
                                Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.cat);
                                VolleyHelper.POSTStringAndJSONRequest(context, item, icon);

                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return text.length;
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
