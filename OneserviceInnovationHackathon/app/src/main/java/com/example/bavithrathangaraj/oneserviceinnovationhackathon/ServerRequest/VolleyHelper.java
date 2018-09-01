package com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest;

import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.R;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerCallback;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.Item;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolleyHelper {
    private static final String BASE_URL = "http://192.168.1.169:3000/api/goods/Bob";

    public static void POSTStringAndJSONRequest(final Context context, Item item,Bitmap icon) {

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

        //Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog);
        String image = convertBitmapToString(icon);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", item.getName());
            jsonObject.put("details", item.getDetails());
            jsonObject.put("location", item.getLocation());
            jsonObject.put("pic", image);
            jsonObject.put("datePosted", item.getDatePosted());
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

    public static void putItemDetails(final String itemId, Context context) {

        StringRequest putRequest = new StringRequest(Request.Method.PUT, BASE_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("name", itemId);

                return params;
            }

        };

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

    public static void requestLoginDetails(final Context context, final ServerCallback callback) {
        RequestQueue queue = com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.SingletonRequestQueue.getInstance(context).getRequestQueue();
        final List<User> userList = new ArrayList<>();

        JsonArrayRequest req = new JsonArrayRequest("http://192.168.1.169:3000/api/users/Bob",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                User item = new User();
                                item.setId(String.valueOf(jsonObject.get("_id")));
                                item.setName(String.valueOf(jsonObject.get("name")));
                                item.setGender(String.valueOf(jsonObject.get("gender")));
                                item.setKampong(String.valueOf(jsonObject.get("kampong")));
                                item.setTotalRating(String.valueOf(jsonObject.get("totalRatings")));
                                userList.add(item);
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor prefsEditor = prefs.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(item);
                                prefsEditor.putString("user", json);
                                prefsEditor.apply();
                                callback.onSuccess(jsonObject);
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

    }
}

