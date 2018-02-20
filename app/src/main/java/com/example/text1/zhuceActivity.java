package com.example.text1;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class zhuceActivity extends AppCompatActivity implements View.OnClickListener{
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuce_layout);
        final Intent intent=getIntent();
        if(intent.getByteArrayExtra("bitmap")!=null) {
             bitmap = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("bitmap"), 0, intent.getByteArrayExtra("bitmap").length);
            ImageView imageView = (ImageView) findViewById(R.id.takeorchoose);
            imageView.setImageBitmap(bitmap);
            SharedPreferences pref=getSharedPreferences("zhuce",MODE_PRIVATE);
            String name=pref.getString("name","");
            String phone=pref.getString("phone","");
            String password=pref.getString("password","");
            final EditText bianji1=(EditText)findViewById(R.id.bianji1);
            final EditText bianji2=(EditText)findViewById(R.id.bianji2);
            final EditText bianji3=(EditText)findViewById(R.id.bianji3);
            bianji1.setText(name);
            bianji2.setText(phone);
            bianji3.setText(password);
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
        final EditText nicheng=(EditText) findViewById(R.id.nicheng);
        final EditText bianji1=(EditText)findViewById(R.id.bianji1);
        final EditText dianhua=(EditText) findViewById(R.id.dianhua);
         final EditText bianji2=(EditText)findViewById(R.id.bianji2);
        final EditText mima=(EditText) findViewById(R.id.mima1);
        final EditText bianji3=(EditText)findViewById(R.id.bianji3);
        nicheng.setFocusable(false);
        dianhua.setFocusable(false);
        mima.setFocusable(false);
        bianji1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                nicheng.setText("昵称");
                if(hasFocus){
                    nicheng.setBackgroundResource(R.drawable.xiahuaxian1);
                }else
                {
                    nicheng.setBackgroundResource(R.drawable.xiahuaxian2);
                }
            }
        });
        bianji2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                dianhua.setText("+86");
                if(hasFocus){
                    dianhua.setBackgroundResource(R.drawable.xiahuaxian1);
                }else
                {
                   dianhua.setBackgroundResource(R.drawable.xiahuaxian2);
                }
            }
        });
        bianji3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mima.setText("密码");
                if(hasFocus){
                   mima.setBackgroundResource(R.drawable.xiahuaxian1);
                }else
                {
                   mima.setBackgroundResource(R.drawable.xiahuaxian2);
                }
            }
        });
        Button button=(Button)findViewById(R.id.zhuce1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=bianji1.getText().toString();
                String number=bianji2.getText().toString();
                String password=bianji3.getText().toString();
                if(name.length()==0){
                    AlertDialog.Builder builder=new AlertDialog.Builder(zhuceActivity.this);
                    builder.setMessage("请输入昵称");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                    bianji1.setText(name);
                    bianji2.setText("");
                    bianji3.setText("");
                }else{
                    if(number.length()!=11){
                        AlertDialog.Builder builder=new AlertDialog.Builder(zhuceActivity.this);
                        builder.setMessage("手机号应为11位");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                        bianji2.setText("");
                        bianji3.setText("");
                    }else{
                        if(password.length()<7){
                            AlertDialog.Builder builder=new AlertDialog.Builder(zhuceActivity.this);
                            builder.setMessage("密码应大于7位");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();
                            bianji3.setText("");
                        }else {
                            SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
                            ContentValues values=new ContentValues();
                            Cursor cursor=db.query("yonghu",null,null,null,null,null,null);
                            cursor.moveToFirst();
                            int length=cursor.getCount();
                            int i,j;
                            j=0;
                            for(i=0;i<length;i++){
                                if(number.equals(cursor.getString(cursor.getColumnIndex("phone")))){
                                    j=1;break;
                                }
                                cursor.moveToNext();
                            }
                            if(j==1){
                                AlertDialog.Builder builder=new AlertDialog.Builder(zhuceActivity.this);
                                builder.setMessage("手机号已被注册");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.show();
                                bianji2.setText("");
                                bianji3.setText("");
                            }else {
                                if (intent.getByteArrayExtra("bitmap") == null) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(zhuceActivity.this);
                                    builder.setMessage("请放入您喜欢的头像哦");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    builder.show();
                                } else {
                                    values.put("name", name);
                                    values.put("password", password);
                                    values.put("phone", number);
                                    values.put("pic",intent.getByteArrayExtra("bitmap"));
                                    db.insert("yonghu", null, values);
                                    values.clear();
                                    Intent intent = new Intent(zhuceActivity.this, loginactivity.class);
                                    intent.putExtra("phone",number);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }
                }
            }
        });
    }
    public void show(View view){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.choose_layout, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto1);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto1);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
          //       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        SharedPreferences.Editor editor=getSharedPreferences("zhuce",MODE_PRIVATE).edit();
        final EditText bianji1=(EditText)findViewById(R.id.bianji1);
        final EditText bianji2=(EditText)findViewById(R.id.bianji2);
        final EditText bianji3=(EditText)findViewById(R.id.bianji3);
        editor.putString("name",bianji1.getText().toString());
        editor.putString("phone",bianji2.getText().toString());
        editor.putString("password",bianji3.getText().toString());
        editor.apply();
        dialog.show();//显示对话框
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.takePhoto1:
                Toast.makeText(zhuceActivity.this,"暂不可用",Toast.LENGTH_SHORT).show();
                /*Intent intent=new Intent(zhuceActivity.this,paiceActivity.class);
                int i=0;
                intent.putExtra("choose",i);
                startActivity(intent);
                finish();
                break;*/
                break;
            case R.id.choosePhoto1:
                Intent intent=new Intent(zhuceActivity.this,paiceActivity.class);
                 intent=new Intent(zhuceActivity.this,paiceActivity.class);
                int i=1;
                intent.putExtra("choose",i);
                startActivity(intent);
                finish();
                break;
        }
        dialog.dismiss();
    }
}


