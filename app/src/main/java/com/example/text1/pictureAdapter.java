package com.example.text1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by hasee on 2017/5/14.
 */

public class pictureAdapter extends ArrayAdapter<byte[]> {
    private int resourcesid;
    private Context context;
    private byte[] pic;
    public pictureAdapter(Context context,int viewid,List<byte[]> objects){
        super(context,viewid,objects);
        this.context=context;
        resourcesid=viewid;
        this.pic=pic;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        byte[] integer=getItem(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(integer, 0, integer.length);
        View view= LayoutInflater.from(getContext()).inflate(resourcesid,parent,false);
        ImageView imageView=(ImageView)view.findViewById(R.id.putpicture1);
        imageView.setImageBitmap(bitmap);
        ImageViewUtil.matchAll(context,imageView);
        return view;
    }
}
