package com.example.text1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by hasee on 2017/6/6.
 */

public class ershoujiluAdapter extends ArrayAdapter<shangping1> {
    private int resourceId;
    private Context context;
    private LayoutInflater inflater;
    final String DB_PATH="/data/data/com.example.text1/databases";
    final String DB_NAME="yonghuxinxi.db3";
    public ershoujiluAdapter(Context context, int textViewResourceId, List<shangping1> objects){
        super(context,textViewResourceId,objects);
        this.context=context;
        resourceId=textViewResourceId;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    class ViewHolder{
        ImageView zhaopian;
        TextView wupinname;
        TextView price;
        Button xiajia;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final shangping1 shangping = getItem(position);
        View view;
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
        final ershoujiluAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = inflater.inflate(resourceId, parent, false);
            viewHolder = new ershoujiluAdapter.ViewHolder();
            viewHolder.zhaopian = (ImageView) view.findViewById(R.id.ershou_image);
            viewHolder.wupinname = (TextView) view.findViewById(R.id.ershou_name);
            viewHolder.price = (TextView) view.findViewById(R.id.ershou_price);
            viewHolder.xiajia = (Button) view.findViewById(R.id.ershou_xiajia);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ershoujiluAdapter.ViewHolder) view.getTag();
        }
        byte[] bitmaptype=shangping.getZhaopian();
        Bitmap bitmap= BitmapFactory.decodeByteArray(bitmaptype,0,bitmaptype.length);
        Bitmap bitmap1=yashuo(bitmap);
        Bitmap bitmap2=ImageCrop(bitmap1);
        viewHolder.zhaopian.setImageBitmap(bitmap2);
        viewHolder.wupinname.setText(shangping.getWupingname());
        viewHolder.price.setText(Float.toString(shangping.getPrice()));
        viewHolder.xiajia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("确认要下架这件商品吗？");
                builder.setCancelable(false);
                builder.setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
                        db.delete("shangpin","id=?",new String[]{shangping.getId()});
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
    private Bitmap yashuo(Bitmap bitmap){
        int size=100;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,85,out);
        float zoom =(float)Math.sqrt(size*1024/(float)out.toByteArray().length);
        Matrix matrix = new Matrix();
        matrix.setScale(zoom,zoom);
        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        out.reset();
        result.compress(Bitmap.CompressFormat.JPEG,85,out);
        while(out.toByteArray().length>size*1024) {
            System.out.println(out.toByteArray().length);
            matrix.setScale(0.9f, 0.9f);
            result = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);
            out.reset();
            result.compress(Bitmap.CompressFormat.JPEG, 85, out);
        }
        return result;
    }
    public static Bitmap ImageCrop(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

        int retX = w > h ? (w - h) / 2 : 0;//基于原图，取正方形左上角x坐标
        int retY = w > h ? 0 : (h - w) / 2;

        //下面这句是关键
        return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
    }
}
