package com.htn2021.CaptionGen;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class TTS implements OnInitListener {
    private TextToSpeech mTTS;
    private Boolean mReady = false;

    public TTS(Context context) {
        mTTS = new TextToSpeech(context, this);
    }

    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mReady = true;
        }
    }
}
