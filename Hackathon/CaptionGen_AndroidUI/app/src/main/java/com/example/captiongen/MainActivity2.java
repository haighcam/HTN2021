package com.example.captiongen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity {
    ImageView il;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        il = (ImageView)findViewById(R.id.imageView);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int resid = bundle.getInt("resId");
            il.setImageResource(resid);
        }
    }
}