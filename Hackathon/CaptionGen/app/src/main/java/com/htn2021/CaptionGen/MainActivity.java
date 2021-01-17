package com.htn2021.CaptionGen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    findViewById(R.id.button_image).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ImageActivity.class)));
    findViewById(R.id.button_camera).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CameraActivity.class)));
  }


}
