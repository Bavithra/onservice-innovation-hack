package com.example.bavithrathangaraj.oneserviceinnovationhackathon.activity;

import android.app.Activity;
import android.content.Intent;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ItemLandingActivity;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.R;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerCallback;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.ServerRequest.VolleyHelper;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.base.BaseActivity;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.Item;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.model.User;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.utilities.ImageRecogniserUtility;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.utilities.PictureUtility;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateProductActivity extends AppCompatActivity implements View.OnClickListener{

    EditText description;
    EditText text;
    ImageView submit;
    Button home;
    Button givingout;
    Button perishable;
    private Bitmap icon;
    private String title;
    ImageView camera;
    String icon1;
    Bitmap map1;
    Item item = new Item();
    ImageView add;
    ImageView explore;
    ImageView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        description = (EditText) findViewById(R.id.description);
        text = (EditText) findViewById(R.id.title);
        submit = (ImageView) findViewById(R.id.submit);
        home = (Button) findViewById(R.id.home);
        camera = (ImageView) findViewById(R.id.camera);
        givingout = (Button) findViewById(R.id.givingout);
        perishable = (Button) findViewById(R.id.perishable);
        map1=null;
        title=null;
        submit.setOnClickListener(this);
        home.setOnClickListener(this);
        givingout.setOnClickListener(this);
        perishable.setOnClickListener(this);
        add = (ImageView) findViewById(R.id.addPage);
        explore = (ImageView) findViewById(R.id.explore);
        message = (ImageView) findViewById(R.id.message);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateProductActivity.class);
                startActivity(intent);
            }
        });
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ItemLandingActivity.class);
                startActivity(intent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        camera.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.givingout:
                item.setType("Giving out");
                item.setStatus("available");
                givingout.setBackground(getDrawable(R.drawable.button_clicked));
                givingout.setTextColor(Color.WHITE);
                break;
            case R.id.looking_for:
                item.setType("Looking for");
                item.setStatus("available");
                break;
            case R.id.home:
                item.setCategory("Home");
                home.setBackground(getDrawable(R.drawable.button_clicked));
                home.setTextColor(Color.WHITE);
                perishable.setBackground(getDrawable(R.drawable.button_border));
                perishable.setTextColor(getColor(R.color.colorPrimary));
                break;
            case R.id.lifestyle:
                item.setCategory("Lifestyle");
                break;
            case R.id.perishable:
                item.setCategory("Perishable");
                perishable.setBackground(getDrawable(R.drawable.button_clicked));
                perishable.setTextColor(Color.WHITE);
                home.setBackground(getDrawable(R.drawable.button_border));
                home.setTextColor(getColor(R.color.colorPrimary));
                break;
            case R.id.elctronics:
                item.setCategory("Electronics");
                break;
            case R.id.submit:
                if(title!=null)
                    item.setName(title);
                else
                item.setName(text.getText().toString());
                item.setDetails(description.getText().toString());
                item.setDatePosted("02/09/2019");
                item.setLocation("Clementi");

                if(icon1!=null)
                    VolleyHelper.POSTStringAndJSONRequest(getApplicationContext(), item, icon1);
                else {
                    Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.cat);
                    VolleyHelper.POSTStringAndJSONRequest(getApplicationContext(), item, BitMapToString(icon));
                }



                break;
            case R.id.camera:
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                PictureUtility.startCamera(this);
//                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://news.pg.com/"));
//                startActivity(myIntent);
                break;

        }
    }



    //Bitmap to String
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == PictureUtility.CAMERA_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            ImageRecogniserUtility imageRecogniserUtility = new ImageRecogniserUtility();
            Log.d("check","Inside camer");

            imageRecogniserUtility.recogniseTextInImage(PictureUtility.getCameraFileUri(), this.getContentResolver(), new ImageRecogniserUtility.OnImageRecognition() {
                @Override
                public void onSuccess(HashMap<String,String> texts) {
                    title = texts.get("desc");
                    text.setText(title.toUpperCase());
                    icon1=texts.get("bitmap");
                    Toast.makeText(getApplicationContext(), "The object is " + title + "", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getApplicationContext(), "Fail to process the image", Toast.LENGTH_LONG).show();

                }

            });

        } else {//TODO - This needs to be checked properly.
            //Toast message for failure
            Toast.makeText(getApplicationContext(), "Fail to process the image", Toast.LENGTH_LONG).show();
        }
    }
}
