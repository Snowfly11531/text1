package com.example.text1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class xiangqingactivity extends AppCompatActivity {
    private List<byte[]> pictue=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xiangqing);
        final String DB_PATH="/data/data/com.example.text1/databases";
        final String DB_NAME="yonghuxinxi.db3";
        if((new File(DB_PATH+DB_NAME)).exists()==false){
            File f=new File(DB_PATH);
            if(!f.exists()){
                f.mkdir();
            }
            try{
                InputStream is=getBaseContext().getAssets().open("yonghuxinxi.db3");
                OutputStream os=new FileOutputStream(DB_PATH+DB_NAME);
                byte[] buffer=new byte[1024];
                int length;
                while ((length=is.read(buffer))>0){
                    os.write(buffer,0,length);
                }
                os.flush();
                os.close();
                is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Intent intent=getIntent();
        String id1=intent.getStringExtra("id");
        byte[] zhaopianid=intent.getByteArrayExtra("zhaopianid");
        Bitmap bitmap = BitmapFactory.decodeByteArray(zhaopianid, 0, zhaopianid.length);
        Float price=intent.getFloatExtra("price",-1);
        String shangpingjieshao=intent.getStringExtra("shangpingjieshao");
        ImageView zhaopian=(ImageView)findViewById(R.id.zhaopian1);
        zhaopian.setImageBitmap(bitmap);
        TextView jieshao=(TextView)findViewById(R.id.shangpingjieshao1);
        jieshao.setText(shangpingjieshao);
        TextView jiege=(TextView)findViewById(R.id.price1);
        jiege.setText(Float.toString(price));
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
        Cursor cursor=db.query("shangpin",null,null,null,null,null,null);
        cursor.moveToFirst();
        int length=cursor.getCount();
        for(int i=0;i<length;i++){
            Log.d("haha",cursor.getString(cursor.getColumnIndex("id")));
            if(cursor.getString(cursor.getColumnIndex("id")).equals(id1)){
                Log.d("haha",cursor.getString(cursor.getColumnIndex("id")));
                for(int j=1;j<6;j++) {
                    if(cursor.getBlob(cursor.getColumnIndex("pic"+Integer.toString(j)))!=null) {
                        byte[] pic = cursor.getBlob(cursor.getColumnIndex("pic" + Integer.toString(j)));
                        pictue.add(pic);
                    }
                }
            }
            cursor.moveToNext();
        }
        pictureAdapter adapter=new pictureAdapter(xiangqingactivity.this,R.layout.putpicture,pictue);
        ListViewforScrollView listView=(ListViewforScrollView) findViewById(R.id.tupianlist);
        listView.setAdapter(adapter);
        ScrollView scrollView=(ScrollView)findViewById(R.id.scrollview1);
        scrollView.smoothScrollTo(0,0);
    }

}
