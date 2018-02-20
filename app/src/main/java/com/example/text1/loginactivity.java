package com.example.text1;


import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by hasee on 2017/5/24.
 */

public class loginactivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        Intent intent=getIntent();
        String phone=intent.getStringExtra("phone");
        if(phone!=null){
            EditText accountEdit=(EditText)findViewById(R.id.yonghuming);
            accountEdit.setText(phone);
        }
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
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)actionBar.hide();
        final EditText accountEdit=(EditText)findViewById(R.id.yonghuming);
        final EditText passwordEdit=(EditText)findViewById(R.id.mima);
        final CheckBox remember_pass=(CheckBox)findViewById(R.id.remember_pass);
        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        String account=pref.getString("name","");
        String password=pref.getString("password","");
        Boolean remember=pref.getBoolean("remember_pass",false);
        if(remember&&phone==null) {
            accountEdit.setText(account);
            passwordEdit.setText(password);
            remember_pass.setChecked(true );
        }
        final Button login=(Button)findViewById(R.id.denglu);
        login.getBackground().setAlpha(150);
        Button register=(Button)findViewById(R.id.zhuce);
        register.getBackground().setAlpha(150);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginactivity.this,zhuceActivity.class);
                startActivity(intent);
            }
        });
       final XCRoundImageView imageView=(XCRoundImageView)findViewById(R.id.xiaohui);
        accountEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
                    Cursor cursor = db.query("yonghu", null, null, null, null, null, null);
                    cursor.moveToFirst();
                    String account = accountEdit.getText().toString();
                    int length = cursor.getCount();
                    int j=0;
                    for (int i = 0; i < length; i++) {
                        if (account.equals(cursor.getString(cursor.getColumnIndex("phone")))) {
                            byte[] bimmaptype;
                            bimmaptype = cursor.getBlob(cursor.getColumnIndex("pic"));
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bimmaptype, 0, bimmaptype.length);
                            imageView.setImageBitmap(bitmap);
                            j=1;
                            break;
                        }
                        cursor.moveToNext();
                    }
                    if(j==0){
                        imageView.setImageResource(R.drawable.xiaohui);
                    }
                } else {
                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
                    Cursor cursor = db.query("yonghu", null, null, null, null, null, null);
                    cursor.moveToFirst();
                    String account = accountEdit.getText().toString();
                    int  length = cursor.getCount();
                    int j=0;
                    for (int i = 0; i < length; i++) {
                        if (account.equals(cursor.getString(cursor.getColumnIndex("phone")))) {
                            byte[] bimmaptype;
                            bimmaptype = cursor.getBlob(cursor.getColumnIndex("pic"));
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bimmaptype, 0, bimmaptype.length);
                            imageView.setImageBitmap(bitmap);
                            j=1;
                            break;
                        }
                        cursor.moveToNext();
                    }
                    if(j==0){
                        imageView.setImageResource(R.drawable.xiaohui);
                    }
                }
                }

        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account=accountEdit.getText().toString();
                String password=passwordEdit.getText().toString();

                SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
                Cursor cursor=db.query("yonghu",null,null,null,null,null,null);
                cursor.moveToFirst();
                int length=cursor.getCount();
                int i,j;
                j=0;
                for(i=0;i<length;i++) {
                    if (account.equals(cursor.getString(cursor.getColumnIndex("phone"))) && password.equals(cursor.getString(cursor.getColumnIndex("password")))) {
                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                        if (remember_pass.isChecked()) {
                            editor.putString("name", account);
                            editor.putString("password", password);
                            editor.putBoolean("remember_pass", true);
                        } else {
                            editor.putString("name", account);
                            editor.putString("password", password);
                            editor.putBoolean("remember_pass",false);
                        }

                        editor.apply();
                        Intent intent = new Intent(loginactivity.this, firstactivity.class);
                        intent.putExtra("bitmap",cursor.getBlob(cursor.getColumnIndex("pic")));
                        intent.putExtra("yonghuming",cursor.getString(cursor.getColumnIndex("name")));
                        startActivity(intent);
                        finish();
                        j=1;break;
                    }
                    cursor.moveToNext();
                }
                if(j==0){
                    Toast.makeText(loginactivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
