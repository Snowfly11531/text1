package com.example.text1;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class yonghuActivity extends AppCompatActivity {
    final String DB_PATH="/data/data/com.example.text1/databases";
    final String DB_NAME="yonghuxinxi.db3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yonghu_layout);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
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
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        String phone=sharedPreferences.getString("name","");
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
        Cursor cursor=db.query("yonghu",null,null,null,null,null,null);
        int length=cursor.getCount();
        cursor.moveToFirst();
        for(int j=0;j<length;j++){
            if(cursor.getString(cursor.getColumnIndex("phone")).equals(phone)){
                byte[] bitmapbyte=cursor.getBlob(cursor.getColumnIndex("pic"));
                Bitmap bitmap= BitmapFactory.decodeByteArray(bitmapbyte,0,bitmapbyte.length);
                ImageView yonghu_touxiang=(ImageView)findViewById(R.id.yonghu_touxiang);
                XCRoundImageView xcRoundImageView=(XCRoundImageView)findViewById(R.id.title_layout3).findViewById(R.id.titlepic1);
                xcRoundImageView.setImageBitmap(bitmap);
                yonghu_touxiang.setImageBitmap(bitmap);

                String name=cursor.getString(cursor.getColumnIndex("name"));
                TextView textView=(TextView)findViewById(R.id.title_layout3).findViewById(R.id.titlename);
                TextView yonghu_name=(TextView)findViewById(R.id.yonghu_name);
                yonghu_name.setText(name);
                textView.setText(name);
                TextView yonghu_phone=(TextView)findViewById(R.id.yonghu_phone);
                yonghu_phone.setText("Tel:"+cursor.getString(cursor.getColumnIndex("phone")));
                break;
            }
            cursor.moveToNext();
        }
        TextView xiaoyuanfencai=(TextView)findViewById(R.id.diceng_layout3).findViewById(R.id.xiaoyuanfengcai);
        TextView jiaoshiliyong=(TextView)findViewById(R.id.diceng_layout3).findViewById(R.id.jiaoshiliyong);
        TextView ershoushichang=(TextView)findViewById(R.id.diceng_layout3).findViewById(R.id.ershoushichang);
        TextView gerenxinxi=(TextView)findViewById(R.id.diceng_layout3).findViewById(R.id.gerenxingxi);
        xiaoyuanfencai.setBackgroundResource(R.drawable.beijing);
        jiaoshiliyong.setBackgroundResource(R.drawable.beijing);
        ershoushichang.setBackgroundResource(R.drawable.beijing);
        gerenxinxi.setBackgroundResource(R.drawable.titlegreen);
        xiaoyuanfencai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(yonghuActivity.this,firstactivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        jiaoshiliyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(yonghuActivity.this,classroomActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        ershoushichang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(yonghuActivity.this,SecendActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        gerenxinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(yonghuActivity.this,yonghuActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        Cursor cursor1= db.query("shangpin",null,"phone=?",new String[]{phone},null,null,null,null);
        int length1=cursor1.getCount();
        List<shangping1> shangping1List=new ArrayList<>();
        cursor1.moveToFirst();
        for(int i=0;i<length1;i++){
            byte[] zhaopian=cursor1.getBlob(cursor1.getColumnIndex("pic1"));
            String name=cursor1.getString(cursor1.getColumnIndex("name"));
            float price=Float.parseFloat(cursor1.getString(cursor1.getColumnIndex("price")));
            String id=cursor1.getString(cursor1.getColumnIndex("id"));
            shangping1 shangping1=new shangping1(zhaopian,price,name,id);
            shangping1List.add(shangping1);
            cursor1.moveToNext();
        }
        ListViewforScrollView listViewforScrollView=(ListViewforScrollView)findViewById(R.id.ershou_list);
        ershoujiluAdapter adapter=new ershoujiluAdapter(yonghuActivity.this,R.layout.ershoujilu,shangping1List);
        listViewforScrollView.setAdapter(adapter);
        Cursor cursor2=db.query("jieyong",null,"name=?",new String[]{phone},null,null,null,null);
        int length2=cursor2.getCount();
        List<jieyongjilu> jieyongjilus=new ArrayList<>();
        cursor2.moveToFirst();
        for(int i=0;i<length2;i++){
            String date=cursor2.getString(cursor2.getColumnIndex("date"));
            String time=cursor2.getString(cursor2.getColumnIndex("time"));
            String classroom=cursor2.getString(cursor2.getColumnIndex("classroom"));
            String phone1=cursor2.getString(cursor2.getColumnIndex("name"));
            jieyongjilu jieyongjilu=new jieyongjilu(date,time,classroom,phone1);
            jieyongjilus.add(jieyongjilu);
            cursor2.moveToNext();
        }
        ListViewforScrollView listViewforScrollView1=(ListViewforScrollView)findViewById(R.id.jieyong_list);
        jieyongjiluAdapter adapter1=new jieyongjiluAdapter(yonghuActivity.this,R.layout.jieyongjilu,jieyongjilus);
        listViewforScrollView1.setAdapter(adapter1);
    }
}
