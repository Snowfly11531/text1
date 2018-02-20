package com.example.text1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by hasee on 2017/5/29.
 */

public class StringAdapter extends ArrayAdapter <DoubleString>{
    private int resourceId;
    private Context context;
    private LayoutInflater inflater;
    private int i;
    private String mulu;
    private String riqi;
    public StringAdapter(Context context, int textViewResourceId, List<DoubleString> objects,int i,String mulu,String riqi){
        super(context,textViewResourceId,objects);
        this.context=context;
        resourceId=textViewResourceId;
        this.i=i;
        this.mulu=mulu;
        this.riqi=riqi;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    class ViewHolder{
        TextView string1;
        TextView string2;
    }
    public View getView(int position, final View convertView, final ViewGroup parent) {
        final String DB_PATH="/data/data/com.example.text1/databases";
        final String DB_NAME="yonghuxinxi.db3";
        if((new File(DB_PATH+DB_NAME)).exists()==false){
            File f=new File(DB_PATH);
            if(!f.exists()){
                f.mkdir();
            }
            try{
                InputStream is=getContext().getAssets().open("yonghuxinxi.db3");
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

        final DoubleString doubleString=getItem(position);
        View view;
        final StringAdapter.ViewHolder viewHolder;
        if(convertView==null) {
            view = inflater.inflate(resourceId, parent, false);
            viewHolder = new StringAdapter.ViewHolder();
            viewHolder.string1 = (TextView) view.findViewById(R.id.string1);
            viewHolder.string1.getBackground().setAlpha(50);
            viewHolder.string2 = (TextView) view.findViewById(R.id.string2);
            viewHolder.string2.getBackground().setAlpha(50);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(StringAdapter.ViewHolder)view.getTag();
        }
        viewHolder.string1.setText(doubleString.getString1());
        viewHolder.string2.setText(doubleString.getString2());
        if(i==0) {
            SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
            Cursor cursor=db.query("jieyong",null,null,null,null,null,null);
            cursor.moveToFirst();
            int length=cursor.getCount();
             int a1=0;
             int a2=0;
            for(int i=0;i<length;i++) {
                if (doubleString.getString1() != null) {
                    if (doubleString.getString1().equals(cursor.getString(cursor.getColumnIndex("classroom"))) && mulu.substring(1, 3).equals(cursor.getString(cursor.getColumnIndex("time")))
                            && riqi.equals(cursor.getString(cursor.getColumnIndex("date")))) {
                        viewHolder.string1.setTextColor(Color.rgb(255, 0, 0));
                        viewHolder.string1.setText(doubleString.getString1()+"(借出)");
                        a1=1;
                        break;
                    }
                    else {viewHolder.string1.setTextColor(Color.rgb(80,80,80));}
                    cursor.moveToNext();
                }
            }
            cursor.moveToFirst();
            for (int i=0;i<length;i++){
                if(doubleString.getString2()!=null) {
                    if (doubleString.getString2().equals(cursor.getString(cursor.getColumnIndex("classroom"))) && mulu.substring(1, 3).equals(cursor.getString(cursor.getColumnIndex("time")))
                            && riqi.equals(cursor.getString(cursor.getColumnIndex("date")))) {
                        viewHolder.string2.setTextColor(Color.rgb(255, 0, 0));
                        viewHolder.string2.setText(doubleString.getString2()+"(借出)");
                        a2=1;
                        break;
                    }
                    else {viewHolder.string2.setTextColor(Color.rgb(80,80,80));}
                    cursor.moveToNext();
                }
            }
            if(doubleString.getString1()==null){
                viewHolder.string1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }else {
                if (a1 == 1) {
                    viewHolder.string1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                              Toast.makeText(context,"该教室已借出",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    viewHolder.string1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, jieyongActivity.class);
                            intent.putExtra("jieyongjiaoshi", doubleString.getString1());
                            intent.putExtra("jieyongriqi", riqi);
                            intent.putExtra("jieyongshijianduan", mulu);
                            context.startActivity(intent);
                            Activity activity = (Activity) context;
                            activity.finish();
                        }
                    });
                }
            }
            if(doubleString.getString2()==null){
                viewHolder.string2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }else {
                if (a2 == 1) {
                    viewHolder.string2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context,"该教室已借出",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    viewHolder.string2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, jieyongActivity.class);
                            intent.putExtra("jieyongjiaoshi", doubleString.getString2());
                            intent.putExtra("jieyongriqi", riqi);
                            intent.putExtra("jieyongshijianduan", mulu);
                            context.startActivity(intent);
                            Activity activity = (Activity) context;
                            activity.finish();

                        }
                    });
                }
            }
        }else{
            SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
            Cursor cursor=db.query("jieyong",null,null,null,null,null,null);
            cursor.moveToFirst();
            int length=cursor.getCount();
            for(int i=0;i<length;i++) {
                if (doubleString.getString1() != null) {
                    if (doubleString.getString1().equals(cursor.getString(cursor.getColumnIndex("classroom"))) && mulu.substring(1, 3).equals(cursor.getString(cursor.getColumnIndex("time")))
                            && riqi.equals(cursor.getString(cursor.getColumnIndex("date")))) {
                        viewHolder.string1.setTextColor(Color.rgb(255, 0, 0));
                        viewHolder.string1.setText(doubleString.getString1()+"(借出)");
                        break;
                    }
                    else {viewHolder.string1.setTextColor(Color.rgb(80,80,80));}
                    cursor.moveToNext();
                }
            }
            cursor.moveToFirst();
            for (int i=0;i<length;i++){
                if(doubleString.getString2()!=null) {
                    if (doubleString.getString2().equals(cursor.getString(cursor.getColumnIndex("classroom"))) && mulu.substring(1, 3).equals(cursor.getString(cursor.getColumnIndex("time")))
                            && riqi.equals(cursor.getString(cursor.getColumnIndex("date")))) {
                        viewHolder.string2.setTextColor(Color.rgb(255, 0, 0));
                        viewHolder.string2.setText(doubleString.getString2()+"(借出)");
                        break;
                    }
                    else {viewHolder.string2.setTextColor(Color.rgb(80,80,80));}
                    cursor.moveToNext();
                }
            }

            viewHolder.string1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"查询界面不可预约",Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.string2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"查询界面不可预约",Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }
}
