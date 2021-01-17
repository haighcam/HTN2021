package com.htn2021.CaptionGen;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class TTS implements OnInitListener {
    private TextToSpeech mTTS;
    private Boolean mReady = false;
    private String Id = "asdges";
    private int count = 0;

    public TTS(Context context) {
        mTTS = new TextToSpeech(context, this);
    }

    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mReady = true;
        }
    }

    public void speak(String text) {
        if (mReady) {
            mTTS.speak(text, TextToSpeech.QUEUE_ADD, null, Id + Integer.toString(count));
            count++;
        }
    }
}
