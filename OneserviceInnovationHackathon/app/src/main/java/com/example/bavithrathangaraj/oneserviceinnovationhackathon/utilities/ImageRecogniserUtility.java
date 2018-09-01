package com.example.bavithrathangaraj.oneserviceinnovationhackathon.utilities;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Deepak on 6/5/16.
 */
public class ImageRecogniserUtility {

    /********************************************************/
    // Constants
    /********************************************************/

    //private static final String CLOUD_VISION_API_KEY = "AIzaSyBKIDNgAtXdHES6R97Rz6IOl4GAi_o47b4";
    private static final String CLOUD_VISION_API_KEY = "AIzaSyD8pY_5FdsYb1ucxZwmD4A4vx8AAjJf6kA";//New key - 12/11/2017
    private static final String STR_TEXT_DETECTION = "LABEL_DETECTION";
    private static final String TAG = "ImageRecogniserUtility";
    private static final int MAXIMUM_NUMBER_OF_RESULTS = 10;
    private static Bitmap bitmapImage = null;

    /********************************************************/
    // Public Interfaces
    /********************************************************/

    /**
     * Interface which will help notify the user whether we were successfully able to get text from image or not.
     */
    public interface OnImageRecognition {
        /**
         * On successful information retrieval the user will be notified.
         * @param texts The list of texts that were identified in the image.
         */
        void onSuccess(HashMap<String,String> texts);

        /**
         * Called if we get an error while retrieving information from the server.
         * @param error The error message.
         */
        void onError(String error);
    }

    /********************************************************/
    // Instance Variables
    /********************************************************/

    /**
     * The list that will be returned to the user.
     */
    private HashMap<String,String> arrTexts;

    /**
     * The maximum number of results that needs to be returned by the server.
     */
    private int maximumNumberOfResults;

    /**
     * The listener to notify the user about the progress
     */
    private OnImageRecognition listener;

    /********************************************************/
    // Public Methods
    /********************************************************/

    /**
     * Method to recognise text in image. This will run async task & return the control via the listener.
     * It is a good idea to show a Progress Dialog while the async tasks is running.
     * @param imageURI The URI of the image from which the text needs to be detected.
     * @param cr The content resolver.
     * @return An arraylist of strings that were identified in the image.
     */
    public void recogniseTextInImage(Uri imageURI, ContentResolver cr, OnImageRecognition listener) {

        if (imageURI != null) {
            try {
                // Initialize the array list
                arrTexts = new HashMap<>();

                // Store the maximum number of text integer for later use
                maximumNumberOfResults = MAXIMUM_NUMBER_OF_RESULTS;

                // Store the listener
                this.listener = listener;

                // scale the image to 800px to save on bandwidth
                Bitmap bitmap = scaleBitmapDown(MediaStore.Images.Media.getBitmap(cr, imageURI), 1200);
                Log.e(TAG, "calling cloud vision");
                bitmapImage=bitmap;
                callCloudVision(bitmap);

            } catch (IOException e) {
                Log.e(TAG, "Incorrect image format received");
                e.printStackTrace();
                listener.onError("Incorrect image format received.");
            }
        } else {
            listener.onError("Null image URI passed.");
        }
    }

    /********************************************************/
    // Private Methods
    /********************************************************/

    /**
     * Call the cloud vision for getting information about the image.
     * This will also scale the image before sending it to the server.
     * @param bitmap The bitmap that needs to be sent to the server.
     * @throws IOException
     */
    private void callCloudVision(final Bitmap bitmap) throws IOException {

        // Do the real work in an async task, because we need to use the network anyway
        new AsyncTask<Object, Void, HashMap<String,String>>() {
            @Override
            protected HashMap<String,String> doInBackground(Object... params) {
                try {
                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(new
                            VisionRequestInitializer(CLOUD_VISION_API_KEY));
                    Vision vision = builder.build();

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                            new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                        // Add the image
                        Image base64EncodedImage = new Image();
                        // Convert the bitmap to a JPEG
                        // Just in case it's a format that Android understands but Cloud Vision
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                        byte[] imageBytes = byteArrayOutputStream.toByteArray();

                        // Base64 encode the JPEG
                        base64EncodedImage.encodeContent(imageBytes);
                        annotateImageRequest.setImage(base64EncodedImage);

                        // add the features we want
                        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                            Feature labelDetection = new Feature();
                            labelDetection.setType("LABEL_DETECTION");
                            labelDetection.setMaxResults(maximumNumberOfResults);
                            add(labelDetection);
                        }});

                        // Add the list of one thing to the request
                        add(annotateImageRequest);
                    }});

                    Vision.Images.Annotate annotateRequest =
                            vision.images().annotate(batchAnnotateImagesRequest);
                    // Due to a bug: requests to Vision API containing large images fail when GZipped.
                    annotateRequest.setDisableGZipContent(true);
                    // The request is starting here

                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    // Populate the
                    return convertResponseToString(response);

                } catch (GoogleJsonResponseException e) {
                    Log.d("apiWrong","IncorrectAPI");
                    listener.onError(e.getMessage());
                } catch (IOException e) {
                    listener.onError(e.getMessage());
                }
                // Return the empty array.
                return arrTexts;
            }

            protected void onPostExecute(HashMap<String,String> result) {
                listener.onSuccess(result);
            }
        }.execute();
    }

    /**
     * Scales the bitmap image before sending it to Google for processing.
     * @param bitmap The bitmap that needs to be processed.
     * @param maxDimension The max dimension of the image after processing.
     * @return The scaled bitmap.
     */
    public Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    /**
     * Retrieve the arraylist of string from the response sent by the Google CloudVision.
     * @param response The response received from Google.
     * @return The list of strings in the response.
     */
    private HashMap<String,String> convertResponseToString(BatchAnnotateImagesResponse response) {

        List<EntityAnnotation> texts = response.getResponses().get(0).getLabelAnnotations();
        if (texts != null) {
            for (EntityAnnotation text : texts) {
                arrTexts.put("desc",text.getDescription());
                arrTexts.put("bitmap",BitMapToString(bitmapImage));
            }
        }
        return arrTexts;
    }


    //Bitmap to String
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
