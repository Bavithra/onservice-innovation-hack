package com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.R;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class VolleyHelper {
    private static final String BASE_URL = "http://192.168.1.169:3000/api/goods";

    public static void POSTStringAndJSONRequest(final Context context) {

        RequestQueue queue = com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.SingletonRequestQueue.getInstance(context).getRequestQueue();

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(context, "No network available", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        };

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog);
        String image = convertBitmapToString(icon);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "bavithra");
            jsonObject.put("details", "bavithra is great");
            jsonObject.put("location", "bavithra is at OSIC");
            jsonObject.put("tags", "tech");
            jsonObject.put("pic", image);
            jsonObject.put("datePosted", "09/10/2018");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(BASE_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.wtf(response.toString());
                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();

            }
        }, errorListener) {

            @Override
            public int getMethod() {
                return Method.POST;
            }

            @Override
            public Priority getPriority() {
                return Priority.NORMAL;
            }

        };

        queue.add(jsonObjectRequest);

    }

    public static List<Item> GETStringAndJSONRequest(final Context context) {
        RequestQueue queue = com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.SingletonRequestQueue.getInstance(context).getRequestQueue();
        final List<Item> itemList = new ArrayList<>();

        JsonArrayRequest req = new JsonArrayRequest(BASE_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Item item = new Item();
                                item.setName(String.valueOf(jsonObject.get("name")));
                                if (jsonObject.get("pic") != null) {
                                    item.setName(String.valueOf(jsonObject.get("pic")));
                                }
                                itemList.add(item);
                            } catch (JSONException e) {
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 30000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(req);
        return itemList;
    }

        public static String convertBitmapToString(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream); //compress to which format you want.
        byte[] byte_arr = stream.toByteArray();
        String imageStr = Base64.encodeToString(byte_arr, 1);
        return imageStr;
    }
}

