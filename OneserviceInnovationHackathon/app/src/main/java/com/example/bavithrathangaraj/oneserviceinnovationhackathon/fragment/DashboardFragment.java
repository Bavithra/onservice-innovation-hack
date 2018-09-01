package com.example.bavithrathangaraj.oneserviceinnovationhackathon.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bavithrathangaraj.oneserviceinnovationhackathon.R;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.base.BaseFragment;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.utilities.ImageRecogniserUtility;
import com.example.bavithrathangaraj.oneserviceinnovationhackathon.utilities.PictureUtility;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;



public class DashboardFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.camera)
    ImageView mCamera;


    /****************************************************/
    // Abstract Method Implementation

    /****************************************************/


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, rootView);
        this.mCamera.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.camera:
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                PictureUtility.startCamera(this);
//                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://news.pg.com/"));
//                startActivity(myIntent);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == PictureUtility.CAMERA_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            ImageRecogniserUtility imageRecogniserUtility = new ImageRecogniserUtility();
            Log.d("check","Inside camer");

            imageRecogniserUtility.recogniseTextInImage(PictureUtility.getCameraFileUri(), getActivity().getContentResolver(), new ImageRecogniserUtility.OnImageRecognition() {
                @Override
                public void onSuccess(ArrayList<String> texts) {
                    Toast.makeText(getContext(), "The object is " + texts.get(0) + "", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getContext(), "Fail to process the image", Toast.LENGTH_LONG).show();

                }

            });

        } else {//TODO - This needs to be checked properly.
            //Toast message for failure
            Toast.makeText(getContext(), "Fail to process the image", Toast.LENGTH_LONG).show();
        }
    }
}
