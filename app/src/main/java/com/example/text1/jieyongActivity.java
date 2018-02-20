package com.example.text1;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class jieyongActivity extends AppCompatActivity {
    private String jieyongren1=null;
    private String phone1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jieyong_layout);
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
        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        String phone=sharedPreferences.getString("name","");
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
        Cursor cursor=db.query("yonghu",null,null,null,null,null,null);
        int length=cursor.getCount();
        cursor.moveToFirst();
        for(int j=0;j<length;j++){
            if(cursor.getString(cursor.getColumnIndex("phone")).equals(phone)){
                jieyongren1=cursor.getString(cursor.getColumnIndex("name"));
                phone1=cursor.getString(cursor.getColumnIndex("phone"));
            }
            cursor.moveToNext();
        }
       final String jieyongriqi1=intent.getStringExtra("jieyongriqi");
       final String jieyongshijianduan1=intent.getStringExtra("jieyongshijianduan");
       final String jieyongjiaoshi1=intent.getStringExtra("jieyongjiaoshi");
        TextView  jieyongren =(TextView)findViewById(R.id.jieyongren);
        TextView  jieyongriqi =(TextView)findViewById(R.id.jieyongriqi);
        TextView  jieyongshijianduan =(TextView)findViewById(R.id.jieyongshijianduan);
        TextView jieyongjiaoshi  =(TextView)findViewById(R.id.jieyongjiaoshi);
        final EditText jieyongyuanying  =(EditText) findViewById(R.id.jieyongyuanying);
        Button  jieyongbutton   =(Button)findViewById(R.id.jieyongbutton);
        jieyongjiaoshi.setText("借用教室    ："+jieyongjiaoshi1);
        jieyongriqi.setText("借用日期    ："+jieyongriqi1);
        jieyongshijianduan.setText("借用时间段：第"+jieyongshijianduan1.substring(1,3)+"节课");
        jieyongren.setText("借用人        ："+jieyongren1);
        String[] a=new String[10];
        String[] b=new String[10];
        jieyongbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jieyongyuanying.length()<10){
                    Toast.makeText(jieyongActivity.this,"借用原因字数不得小于10",Toast.LENGTH_SHORT).show();
                    jieyongyuanying.setText("");
                }
                else {
                    SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
                    ContentValues values=new ContentValues();
                    Cursor cursor=db.query("yonghu",null,null,null,null,null,null);
                    values.put("name",phone1);
                    values.put("date",jieyongriqi1);
                    values.put("time",jieyongshijianduan1.substring(1,3));
                    values.put("classroom",jieyongjiaoshi1);
                    values.put("reason",jieyongyuanying.getText().toString());
                    db.insert("jieyong",null,values);
                    Intent intent1=new Intent(jieyongActivity.this,classroomActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(jieyongActivity.this,classroomActivity.class);
        startActivity(intent);
        finish();
    }
}
