package com.htn2021.CaptionGen;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import java.util.LinkedList;
import java.util.Queue;

import java.util.Locale;

public class TTS implements OnInitListener {
    private TextToSpeech mTTS;
    private Boolean mReady = false;
    private Queue<String>  mQ;

    public TTS(Context context) {
        mQ = new LinkedList<>();
        mTTS = new TextToSpeech(context, this);
    }

    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mReady = true;
            Log.e("TTS", "loaded");
            mTTS.setLanguage(Locale.UK);
            while (true) {
                String val = mQ.poll();
                if (val == null) {
                    break;
                } else {
                    mTTS.speak(val, TextToSpeech.QUEUE_ADD, null);
                }
            }
        } else {
            Log.e("TTS", "not loaded");
        }
    }

    public void speak(String text) {
        if (mReady) {
            mTTS.speak(text, TextToSpeech.QUEUE_ADD, null);
            Log.e("TTS", "requested :" + text);
        }
        mQ.add(text);
        Log.e("TTS", "not ready :" + text);
    }
}
