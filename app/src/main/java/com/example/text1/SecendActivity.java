package com.example.text1;

import android.support.v7.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class SecendActivity extends AppCompatActivity {
    final String DB_PATH="/data/data/com.example.text1/databases";
    final String DB_NAME="yonghuxinxi.db3";
    private  List<shangping> shangpingList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secend_layout);
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

                XCRoundImageView xcRoundImageView=(XCRoundImageView)findViewById(R.id.title_layout2).findViewById(R.id.titlepic1);
                xcRoundImageView.setImageBitmap(bitmap);
                String name=cursor.getString(cursor.getColumnIndex("name"));
                TextView textView=(TextView)findViewById(R.id.title_layout2).findViewById(R.id.titlename);
                textView.setText(name);
            }
            cursor.moveToNext();
        }
        TextView xiaoyuanfencai=(TextView)findViewById(R.id.diceng_layout2).findViewById(R.id.xiaoyuanfengcai);
        TextView jiaoshiliyong=(TextView)findViewById(R.id.diceng_layout2).findViewById(R.id.jiaoshiliyong);
        TextView ershoushichang=(TextView)findViewById(R.id.diceng_layout2).findViewById(R.id.ershoushichang);
        TextView gerenxinxi=(TextView)findViewById(R.id.diceng_layout2).findViewById(R.id.gerenxingxi);
        xiaoyuanfencai.setBackgroundResource(R.drawable.beijing);
        jiaoshiliyong.setBackgroundResource(R.drawable.beijing);
        ershoushichang.setBackgroundResource(R.drawable.titlegreen);
        gerenxinxi.setBackgroundResource(R.drawable.beijing);
        xiaoyuanfencai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecendActivity.this,firstactivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        jiaoshiliyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecendActivity.this,classroomActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        ershoushichang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecendActivity.this,SecendActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        gerenxinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecendActivity.this,yonghuActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        final TextView[] textViews=new TextView[7];
        textViews[0]=(TextView)findViewById(R.id.bicycle);
        textViews[1]=(TextView)findViewById(R.id.tiyu);
        textViews[2]=(TextView)findViewById(R.id.yifu);
        textViews[3]=(TextView)findViewById(R.id.jineng);
        textViews[4]=(TextView)findViewById(R.id.dianzi);
        textViews[5]=(TextView)findViewById(R.id.riyong);
        textViews[6]=(TextView)findViewById(R.id.huazhuang);
        final  ListView listView=(ListView)findViewById(R.id.list_view1);
        for(int i=0;i<7;i++){
            if(i==0){
                textViews[i].setTextColor(0xff5bfd90);
                textViews[i].setBackgroundResource(R.drawable.biankuang10);
                textViews[i].getBackground().setAlpha(180);
            }else{
                textViews[i].setTextColor(0xff000000);
                textViews[i].setBackgroundResource(R.drawable.mulu1);
                textViews[i].getBackground().setAlpha(180);
            }
        }
        shangpingList.clear();
        showwuping("自行车");
        textViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<7;i++){
                    if(i==0){
                        textViews[i].setTextColor(0xff5bfd90);
                        textViews[i].setBackgroundResource(R.drawable.biankuang10);
                        textViews[i].getBackground().setAlpha(180);
                    }else{
                        textViews[i].setTextColor(0xff000000);
                        textViews[i].setBackgroundResource(R.drawable.mulu1);
                        textViews[i].getBackground().setAlpha(180);
                    }
                }
                shangpingList.clear();
                showwuping("自行车");
            }
        });
        textViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<7;i++){
                    if(i==1){
                        textViews[i].setTextColor(0xff5bfd90);
                        textViews[i].setBackgroundResource(R.drawable.biankuang10);
                        textViews[i].getBackground().setAlpha(180);
                    }else{
                        textViews[i].setTextColor(0xff000000);
                        textViews[i].setBackgroundResource(R.drawable.mulu1);
                        textViews[i].getBackground().setAlpha(180);
                    }
                }
                shangpingList.clear();
                showwuping("体育用品");
            }
        });
        textViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<7;i++){
                    if(i==2){
                        textViews[i].setTextColor(0xff5bfd90);
                        textViews[i].setBackgroundResource(R.drawable.biankuang10);
                        textViews[i].getBackground().setAlpha(180);
                    }else{
                        textViews[i].setTextColor(0xff000000);
                        textViews[i].setBackgroundResource(R.drawable.mulu1);
                        textViews[i].getBackground().setAlpha(180);
                    }
                }

                shangpingList.clear();
                showwuping("服装");
            }
        });
        textViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<7;i++){
                    if(i==3){
                        textViews[i].setTextColor(0xff5bfd90);
                        textViews[i].setBackgroundResource(R.drawable.biankuang10);
                        textViews[i].getBackground().setAlpha(180);
                    }else{
                        textViews[i].setTextColor(0xff000000);
                        textViews[i].setBackgroundResource(R.drawable.mulu1);
                        textViews[i].getBackground().setAlpha(180);
                    }
                }

                shangpingList.clear();
                showwuping("技能服务");
            }
        });
        textViews[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<7;i++){
                    if(i==4){
                        textViews[i].setTextColor(0xff5bfd90);
                        textViews[i].setBackgroundResource(R.drawable.biankuang10);
                        textViews[i].getBackground().setAlpha(180);
                    }else{
                        textViews[i].setTextColor(0xff000000);
                        textViews[i].setBackgroundResource(R.drawable.mulu1);
                        textViews[i].getBackground().setAlpha(180);
                    }
                }

                shangpingList.clear();
                showwuping("电子产品");
            }
        });
        textViews[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<7;i++){
                    if(i==5){
                        textViews[i].setTextColor(0xff5bfd90);
                        textViews[i].setBackgroundResource(R.drawable.biankuang10);
                        textViews[i].getBackground().setAlpha(180);
                    }else{
                        textViews[i].setTextColor(0xff000000);
                        textViews[i].setBackgroundResource(R.drawable.mulu1);
                        textViews[i].getBackground().setAlpha(180);
                    }
                }

                shangpingList.clear();
                showwuping("日用品");
            }
        });
        textViews[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<7;i++){
                    if(i==6){
                        textViews[i].setTextColor(0xff5bfd90);
                        textViews[i].setBackgroundResource(R.drawable.biankuang10);
                        textViews[i].getBackground().setAlpha(180);
                    }else{
                        textViews[i].setTextColor(0xff000000);
                        textViews[i].setBackgroundResource(R.drawable.mulu1);
                        textViews[i].getBackground().setAlpha(180);
                    }
                }
                shangpingList.clear();
                showwuping("个人美妆");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public  void onItemClick(AdapterView<?>parent, View view,int position,long id){
                shangping shangping=shangpingList.get(position);
                byte[] zhaopianid=shangping.getZhaopianid();
                Float price=shangping.getPrice();
                String shangpingjieshao=shangping.getShangpingjieshao();

                Intent intent=new Intent(SecendActivity.this,xiangqingactivity.class);
                intent.putExtra("zhaopianid",zhaopianid);
                intent.putExtra("price",price);
                intent.putExtra("shangpingjieshao",shangpingjieshao);
                intent.putExtra("id",shangping.getId());
                startActivity(intent);
            }
        });
        Button maichu=(Button)findViewById(R.id.maichu);
        maichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SecendActivity.this,tianjiashangpingActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
    private void showwuping(String string){
        SQLiteDatabase db= SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
        Cursor cursor=db.query("shangpin",null,"zhonglei=?",new String[]{string},null,null,null,null);
        Cursor cursor1=db.query("yonghu",null,null,null,null,null,null,null);
        int length1=cursor1.getCount();
        int length=cursor.getCount();
        Log.d("SecendActivity",Integer.toString(length));
        if(length==0){
            ListView listView = (ListView) findViewById(R.id.list_view1);
            shangpingAdapter adapter = new shangpingAdapter(SecendActivity.this, R.layout.pic_text, shangpingList);
            listView.setAdapter(adapter); 
        }else {
            cursor.moveToFirst();
            for (int i = 0; i < length; i++) {
                String phone=cursor.getString(cursor.getColumnIndex("phone"));
                cursor1.moveToFirst();
                for (int j = 0; j < length1; j++) {
                    String phone1 = cursor1.getString(cursor1.getColumnIndex("phone"));
                    if (phone1.equals(phone)) {
                        break;
                    }
                    cursor1.moveToNext();
                }
                String name=cursor1.getString(cursor1.getColumnIndex("name"));
                String shangpinname=cursor.getString(cursor.getColumnIndex("name"));
                byte[] touxiang = cursor1.getBlob(cursor1.getColumnIndex("pic"));
                float price = Float.parseFloat(cursor.getString(cursor.getColumnIndex("price")));
                byte[] shangpingtupian = cursor.getBlob(cursor.getColumnIndex("pic1"));
                String jieshao = cursor.getString(cursor.getColumnIndex("jieshao"));
                String id=cursor.getString(cursor.getColumnIndex("id"));
                shangping shangping = new shangping(touxiang,name,shangpinname, price, shangpingtupian, jieshao,id);
                shangpingList.add(shangping);
                cursor.moveToNext();
            }
            ListView listView = (ListView) findViewById(R.id.list_view1);
            shangpingAdapter adapter = new shangpingAdapter(SecendActivity.this, R.layout.pic_text, shangpingList);
            listView.setAdapter(adapter);
        }
    }
}
