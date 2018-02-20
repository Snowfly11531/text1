package com.example.text1;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.ItemizedOverlay;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.OverlayItem;
import com.tianditu.android.maps.overlay.MarkerOverlay;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static com.tianditu.engine.TiandituSoftParam.mContext;

public class firstactivity extends baseactivity  {
    private MyDatabaseHelper dbhelper;
    private int i=1;
    private Bitmap bitmap;
    public static MapView mMapView=null;
    private OverItemT mOverlay=null;
    public static View mPopView=null;
    public static TextView mTxt=null;
    public static ImageView jianzhuwu=null;
    public static TextView jiaowulou=null;
    final String DB_PATH="/data/data/com.example.text1/databases";
    final String DB_NAME="yonghuxinxi.db3";
    private String username;
    private static String[] name={"教五楼始建于xxxx年xx月xx日，主要功能是balabalabalabalakkkkkkk" +
            "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n\n\n","逸夫楼始建于xxxx年xx月xx日，主要功能是balabalabalabala",
            "体育馆始建于xxxx年xx月xx日，主要功能是balabalabalabala"};
    private static  int[] jianzhuwuid={R.drawable.jiaowulou,R.drawable.yifulou,R.drawable.tiyuguan};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        if(ContextCompat.checkSelfPermission(firstactivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(firstactivity.this,new String[]{Manifest.permission.CAMERA},1);
        }
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

                XCRoundImageView xcRoundImageView=(XCRoundImageView)findViewById(R.id.title_layout).findViewById(R.id.titlepic1);
                xcRoundImageView.setImageBitmap(bitmap);
                String name=cursor.getString(cursor.getColumnIndex("name"));
                TextView textView=(TextView)findViewById(R.id.title_layout).findViewById(R.id.titlename);
                textView.setText(name);
            }
            cursor.moveToNext();
        }
        TextView xiaoyuanfencai=(TextView)findViewById(R.id.diceng_layout).findViewById(R.id.xiaoyuanfengcai);
        TextView jiaoshiliyong=(TextView)findViewById(R.id.diceng_layout).findViewById(R.id.jiaoshiliyong);
        TextView ershoushichang=(TextView)findViewById(R.id.diceng_layout).findViewById(R.id.ershoushichang);
        TextView gerenxinxi=(TextView)findViewById(R.id.diceng_layout).findViewById(R.id.gerenxingxi);
        xiaoyuanfencai.setBackgroundResource(R.drawable.titlegreen);
        jiaoshiliyong.setBackgroundResource(R.drawable.beijing);
        ershoushichang.setBackgroundResource(R.drawable.beijing);
        gerenxinxi.setBackgroundResource(R.drawable.beijing);
        xiaoyuanfencai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(firstactivity.this,firstactivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        jiaoshiliyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(firstactivity.this,classroomActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        ershoushichang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(firstactivity.this,SecendActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        gerenxinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(firstactivity.this,yonghuActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        mMapView = (MapView) findViewById(R.id.main_mapview);
        MapController mMapController = mMapView.getController();
        GeoPoint point = new GeoPoint(32080950,118810510);
        mMapController.setCenter(point);
        mMapController.setZoom(25);
        mMapView.setMapType(1);
        //
        List<Overlay> list = mMapView.getOverlays();
        MyLocationOverlay myLocation = new MyLocationOverlay(this, mMapView);
        myLocation.enableMyLocation();
        list.add(myLocation);
        Drawable marker = getResources().getDrawable(R.drawable.poi_xml);
        mOverlay = new OverItemT(marker);
        list.add(mOverlay);
        //
        mPopView = super.getLayoutInflater().inflate(R.layout.popview, null);
        mTxt = (TextView) mPopView.findViewById(R.id.text1);
        jianzhuwu=(ImageView)mPopView.findViewById(R.id.jianzhuwu);
        jiaowulou=(TextView)mPopView.findViewById(R.id.dianji);
        mMapView.addView(mPopView, new MapView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, null,
                MapView.LayoutParams.BOTTOM));
        mPopView.setVisibility(View.GONE);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

    }
    public class OverItemT extends ItemizedOverlay<OverlayItem> implements
            Overlay.Snappable {
        private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
        double[][] points = new double[][] { { 32.08069,118.81054 },
                { 32.08095,118.81184 }, { 32.08227,118.80982 },
                };
        int size = points.length;

        public OverItemT(Drawable marker) {
            super(boundCenterBottom(marker));
            for (int i = 0; i < size; i++) {
                OverlayItem item = new OverlayItem(
                        new GeoPoint((int) (points[i][0] * 1E6),
                                (int) (points[i][1] * 1E6)), "P" + i, "point"
                        + i);
                item.setMarker(marker);
                mGeoList.add(item);
            }
            populate(); // createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法

        }

        @Override
        protected OverlayItem createItem(int i) {
            return mGeoList.get(i);
        }

        @Override
        public int size() {
            return mGeoList.size();
        }

        // 处理当点击事件
        @Override
        protected boolean onTap(int i) {
            if (i == -1)
                return false;
            GeoPoint pt = mGeoList.get(i).getPoint();
            firstactivity.mMapView.updateViewLayout(
                    firstactivity.mPopView, new MapView.LayoutParams(
                            MapView.LayoutParams.WRAP_CONTENT,
                            MapView.LayoutParams.WRAP_CONTENT, pt,
                            MapView.LayoutParams.BOTTOM_CENTER));
            firstactivity.mPopView.setVisibility(View.VISIBLE);
            firstactivity.mTxt.setText(name[i]);
            firstactivity.jianzhuwu.setImageResource(jianzhuwuid[i]);
            if(i==0) {
                firstactivity.jiaowulou.setText("点击这里查空教室");
                firstactivity.jiaowulou.setBackgroundResource(R.drawable.biankuang11);
                firstactivity.jiaowulou.setTextSize(20);
                firstactivity.jiaowulou.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(firstactivity.this, classroomActivity.class);
                        intent.putExtra("yonghuming",username);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                });
            }else {
                firstactivity.jiaowulou.setText("");
                firstactivity.jiaowulou.setTextSize(1);
                firstactivity.jiaowulou.setBackgroundResource(R.drawable.poiresult_sel);
                firstactivity.jiaowulou.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            return true;
        }
        @Override
        public boolean onTap(GeoPoint p, MapView mapView) {
            firstactivity.mPopView.setVisibility(View.GONE);
            return super.onTap(p, mapView);
        }
    }
}
