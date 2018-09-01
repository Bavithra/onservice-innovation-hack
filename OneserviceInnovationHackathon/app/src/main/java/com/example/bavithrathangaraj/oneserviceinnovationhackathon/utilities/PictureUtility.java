package com.example.bavithrathangaraj.oneserviceinnovationhackathon.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import java.io.File;


public class PictureUtility {

    /****************************************************/
    // Constants
    /****************************************************/
    private static final String TAG = "PictureUtility";
    public static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;
    public static final String FILE_NAME_CAMERA = "temp.jpg";
    public static final String FILE_NAME_GALLERY = "temp.png";

    /****************************************************/
    // Public Static Methods

    /****************************************************/

    /**
     * Method to open the gallery for allowing the User to choose an image. After the User has chosen a picture, the "startActivityForResult" will be called.
     *
     * @param fragment The fragment from which the method is being called.
     */
    public static void startGalleryChooser(Fragment fragment) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        fragment.startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                GALLERY_IMAGE_REQUEST);
    }

    /**
     * Method to start the camera. After the User has clicked the picture, the "startActivityForResult" will be called.
     *
     * @param activity The fragment from which the method is being called.
     */
    public static void startCamera(Activity activity) {
        if (PermissionUtils.requestPermission(activity, CAMERA_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getCameraFileUri());
            activity.startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        } else {
            //TODO - Handle the scenario where the user denies the permission to his photos/camera.
        }
    }

    /**
     * Method to get the image uri that was taken using the camera.
     *
     * @return The image uri of the picture that was taken using the camera.
     */
    public static Uri getCameraFileUri() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return Uri.fromFile(new File(dir, FILE_NAME_CAMERA));
    }
}

