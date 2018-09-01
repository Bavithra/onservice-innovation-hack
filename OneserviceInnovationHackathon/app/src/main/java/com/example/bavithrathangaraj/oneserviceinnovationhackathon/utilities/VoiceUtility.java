package com.example.bavithrathangaraj.oneserviceinnovationhackathon.utilities;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;

/**
 * @author Bavithra Thangaraj on 9/1/2016.
 */
public class VoiceUtility {

    public static void getVoiceInput(Fragment fragment) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
        fragment.startActivityForResult(intent, 1010);
    }
}
