package com.htn2021.CaptionGen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    GlobalData.getInstance().loadModel(this);
    
    findViewById(R.id.button_image).setOnClickListener(v -> get_image(v));
    findViewById(R.id.button_camera).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CameraActivity.class)));
  }

  public void get_image(View v) {
    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
    photoPickerIntent.setType("image/*");
    startActivityForResult(photoPickerIntent, 1);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == 1) {
      Uri photoUri = data.getData();
      String currentImage = photoUri.toString();
      Intent i = new Intent(MainActivity.this, ImageActivity.class);
      i.putExtra("resId", currentImage);
      startActivity(i);
    }
  }


}
