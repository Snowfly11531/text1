package com.example.text1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by hasee on 2017/6/6.
 */

public class jieyongjiluAdapter extends ArrayAdapter <jieyongjilu>{
    private int resourceId;
    private Context context;
    private LayoutInflater inflater;
    final String DB_PATH="/data/data/com.example.text1/databases";
    final String DB_NAME="yonghuxinxi.db3";
    public jieyongjiluAdapter(Context context, int textViewResourceId, List<jieyongjilu> objects){
        super(context,textViewResourceId,objects);
        this.context=context;
        resourceId=textViewResourceId;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    class ViewHolder{
        TextView time;
        TextView classroom;
        Button quxiao;
    }
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final jieyongjilu shangping = getItem(position);
        View view;
        if ((new File(DB_PATH + DB_NAME)).exists() == false) {
            File f = new File(DB_PATH);
            if (!f.exists()) {
                f.mkdir();
            }
            try {
                InputStream is = getContext().getAssets().open("yonghuxinxi.db3");
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final jieyongjiluAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = inflater.inflate(resourceId, parent, false);
            viewHolder = new jieyongjiluAdapter.ViewHolder();
            viewHolder.time = (TextView) view.findViewById(R.id.jieyong_time);
            viewHolder.classroom = (TextView) view.findViewById(R.id.jieyong_class);
            viewHolder.quxiao=(Button)view.findViewById(R.id.jieyong_quxiao);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (jieyongjiluAdapter.ViewHolder) view.getTag();
        }
        viewHolder.time.setText("借用时间："+shangping.getDate()+" 第"+shangping.getTime()+"节课");
        viewHolder.classroom.setText("借用教室：50"+shangping.getClassroom());
        viewHolder.quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("确认要取消预约吗？");
                builder.setCancelable(false);
                builder.setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
                        db.delete("jieyong","name=? and date=? and classroom=? and time=?",new String[]{shangping.getPhone(),shangping.getDate(),shangping.getClassroom(),shangping.getTime()});
                        Intent intent=new Intent(context,yonghuActivity.class);
                        context.startActivity(intent);
                        Activity activity=(Activity)context;
                        activity.finish();
                        activity.overridePendingTransition(0, 0);
                    }
                });
                builder.setPositiveButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        return view;
    }
}
