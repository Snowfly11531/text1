package com.example.text1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class touxiangacyivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tou);
        ImageView touxiang=(ImageView)findViewById(R.id.touxiang);
        Intent intent=getIntent();
        byte[] id=intent.getByteArrayExtra("id");
         Bitmap bitmap = BitmapFactory.decodeByteArray(id, 0, id.length);
        touxiang.setImageBitmap(bitmap);
        ImageView touxiang1=(ImageView)findViewById(R.id.touxiang);
        touxiang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
