package com.example.text1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by hasee on 2017/5/12.
 */

public class shangpingAdapter extends ArrayAdapter<shangping> {
    private int resourceId;
    private Context context;
    private LayoutInflater inflater;
    public shangpingAdapter(Context context, int textViewResourceId, List<shangping>objects){
        super(context,textViewResourceId,objects);
        this.context=context;
        resourceId=textViewResourceId;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final shangping shangping=getItem(position);
        View view;
        final ViewHolder viewHolder;
        if(convertView==null) {
            view = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.zhaopian = (XCRoundImageView) view.findViewById(R.id.zhaopian);
            viewHolder.shangpingtupian = (ImageView) view.findViewById(R.id.shangpingzhaopian);
            viewHolder.name=(TextView)view.findViewById(R.id.maijianame) ;
            viewHolder.shangpinname=(TextView)view.findViewById(R.id.wupinming);
            viewHolder.price = (TextView) view.findViewById(R.id.price);
            viewHolder.shangpingjieshao=(TextView)view.findViewById(R.id.shangpingjieshao);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        byte[] bimmaptype=shangping.getShangpingtupianid();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bimmaptype, 0, bimmaptype.length);
        viewHolder.shangpingtupian.setImageBitmap(bitmap);
        ImageViewUtil.matchAll(context,viewHolder.shangpingtupian);
        bimmaptype=shangping.getZhaopianid();
        bitmap = BitmapFactory.decodeByteArray(bimmaptype, 0, bimmaptype.length);
        viewHolder.zhaopian.setImageBitmap(bitmap);
        viewHolder.price.setText(Float.toString(shangping.getPrice()));
        viewHolder.name.setText(shangping.getname());
        viewHolder.shangpinname.setText("物品名："+shangping.getShangpinname());
        viewHolder.shangpingjieshao.setText(shangping.getShangpingjieshao());
        viewHolder.zhaopian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] bimmaptype=shangping.getZhaopianid();
                Intent intent=new Intent(context,touxiangacyivity.class);
                intent.putExtra("id",bimmaptype);
                context.startActivity(intent);
            }
        });
        return view;
    }
    class ViewHolder{
        XCRoundImageView zhaopian;
        ImageView shangpingtupian;
        TextView name;
        TextView shangpinname;
        TextView price;
        TextView shangpingjieshao;
    }
}
