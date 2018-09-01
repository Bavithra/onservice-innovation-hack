package com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class VolleyHelper {
    private static final String BASE_URL = "http://10.0.1.65:3000/api/goods";

    public static void POSTStringAndJSONRequest(final Context context) {

        RequestQueue queue = SingletonRequestQueue.getInstance(context).getRequestQueue();

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

    public static String convertBitmapToString(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream); //compress to which format you want.
        byte[] byte_arr = stream.toByteArray();
        String imageStr = Base64.encodeToString(byte_arr, 1);
        return imageStr;
    }
}

