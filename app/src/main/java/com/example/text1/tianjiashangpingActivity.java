package com.example.text1;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.Manifest.permission;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Target;

public class tianjiashangpingActivity extends AppCompatActivity implements View.OnClickListener{
    private Dialog dialog;
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Uri imageUri;
    private static final int TAKE_PHOTO = 1;
   private static final int CHOOSE_PHOTO = 2;
    private static int i;
    private final String DB_PATH="/data/data/com.example.text1/databases";
    private final String DB_NAME="yonghuxinxi.db3";
    private static final Bitmap[] bitmaps=new Bitmap[6];
    byte[] bitmapbyte;
    private int kongzhi=0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){

            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try {
                        if(kongzhi==1){kongzhi=0;LinearLayout llGroup=(LinearLayout)findViewById(R.id.linearlayout);llGroup.removeAllViews();}
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        Bitmap bitmap1=yashuo(bitmap);
                        Bitmap bitmap2=ImageCrop(bitmap1);
                        LinearLayout llGroup=(LinearLayout)findViewById(R.id.linearlayout);
                        ImageView imageview =new ImageView(this);
                        imageview.setLayoutParams(new LayoutParams(llGroup.getHeight(),llGroup.getHeight()));
                        imageview.setPadding(10,0,10,0);
                        imageview.setImageBitmap(bitmap2);
                        llGroup.addView(imageview);
                        i=i+1;
                        bitmaps[i]=bitmap1;

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tianjiashangping_layout);
        LinearLayout llGroup=(LinearLayout)findViewById(R.id.linearlayout);
        TextView textView=new TextView(this);
        textView.setText("点击“+”添加照片");
        textView.setTextSize(20);
        llGroup.addView(textView);
        kongzhi=1;
        i=0;
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

        Spinner zhonglei=(Spinner)findViewById(R.id.zhonglei) ;
        String[] use={"自行车","体育用品","服装","技能服务","电子产品","日用品","个人美妆"};
        ArrayAdapter<String> classadpter=new ArrayAdapter<String>(tianjiashangpingActivity.this,android.R.layout.simple_spinner_item,use);
        classadpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zhonglei.setAdapter(classadpter);
        TextView clearall=(TextView)findViewById(R.id.clearall);
        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=0;
                for(int j=1;j<6;j++){
                    bitmaps[j]=null;
                }
                LinearLayout llGroup=(LinearLayout)findViewById(R.id.linearlayout);llGroup.removeAllViews();
                TextView textView=new TextView(tianjiashangpingActivity.this);
                textView.setText("点击“+”添加照片");
                textView.setTextSize(20);
                llGroup.addView(textView);
                kongzhi=1;
            }
        });
        Button fabu=(Button)findViewById(R.id.fabu);
        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
                String phone=pref.getString("name","");
                EditText editText=(EditText)findViewById(R.id.wupinname);
                String name=editText.getText().toString();
                if(name.equals("")){Toast.makeText(tianjiashangpingActivity.this,"请写入物品名",Toast.LENGTH_SHORT).show();}else {
                    Spinner spinner = (Spinner) findViewById(R.id.zhonglei);
                    String zhonglei = spinner.getSelectedItem().toString();
                    editText = (EditText) findViewById(R.id.wupinmiaoshu);
                    if (editText.getText().toString().length() < 10) {
                        Toast.makeText(tianjiashangpingActivity.this, "物品描述字数过少", Toast.LENGTH_SHORT).show();
                    } else {
                        String jieshao = editText.getText().toString();
                        editText = (EditText) findViewById(R.id.nidingprice);
                        String price = editText.getText().toString();
                        if(price.equals("")){Toast.makeText(tianjiashangpingActivity.this,"请输入价格",Toast.LENGTH_SHORT).show();}else {
                            if(bitmaps[1]==null){Toast.makeText(tianjiashangpingActivity.this,"请加入至少一张图片",Toast.LENGTH_SHORT).show();}else {
                                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
                                ContentValues values = new ContentValues();
                                Cursor cursor = db.query("shangpin", null, null, null, null, null, null);
                                if (cursor.getCount() == 0) {
                                    values.put("phone", phone);
                                    values.put("name", name);
                                    values.put("zhonglei", zhonglei);
                                    values.put("jieshao", jieshao);
                                    values.put("price", price);
                                    values.put("id", "1");
                                    Log.d("a", "a");
                                    for (int j = 1; j < 6; j++) {
                                        if (bitmaps[j] == null) {
                                            break;
                                        }
                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        bitmaps[j].compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                        bitmapbyte = byteArrayOutputStream.toByteArray();
                                        if (bitmapbyte != null) Log.d("a", "a");
                                        values.put("pic" + Integer.toString(j), bitmapbyte);
                                    }
                                } else {
                                    cursor.moveToLast();
                                    String id = cursor.getString(cursor.getColumnIndex("id"));
                                    values.put("id", Integer.toString(Integer.parseInt(id) + 1));
                                    values.put("phone", phone);
                                    values.put("name", name);
                                    values.put("zhonglei", zhonglei);
                                    values.put("jieshao", jieshao);
                                    values.put("price", price);
                                    Log.d("a", "a");
                                    for (int j = 1; j < 6; j++) {
                                        if (bitmaps[j] == null) {
                                            break;
                                        }
                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        bitmaps[j].compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                        bitmapbyte = byteArrayOutputStream.toByteArray();
                                        if (bitmapbyte != null) Log.d("a", "a");
                                        values.put("pic" + Integer.toString(j), bitmapbyte);
                                    }
                                }
                                db.insert("shangpin", null, values);
                                values.clear();
                                Toast.makeText(tianjiashangpingActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(tianjiashangpingActivity.this, SecendActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }
    public void show1(View view){
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
        if(i>4){Toast.makeText(tianjiashangpingActivity.this,"最多只能添加五张图片哦",Toast.LENGTH_SHORT).show();}else {
            dialog.show();//显示对话框
        }
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.takePhoto1:
                if(ContextCompat.checkSelfPermission(tianjiashangpingActivity.this, permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(tianjiashangpingActivity.this,new String[]{permission.CAMERA},1);
                }
                 else{
                    File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
                    try{
                        if(outputImage.exists()){
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    if(Build.VERSION.SDK_INT>=24){
                        imageUri= FileProvider.getUriForFile(tianjiashangpingActivity.this,"com.example.text1.fileprovider",outputImage);
                    }else{
                        imageUri=Uri.fromFile(outputImage);
                    }
                    takephoto();
                }
                break;
            case R.id.choosePhoto1:
                if(ContextCompat.checkSelfPermission(tianjiashangpingActivity.this, permission.WRITE_EXTERNAL_STORAGE )!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(tianjiashangpingActivity.this,new String[]{permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbum();
                }
                break;
        }
        dialog.dismiss();
    }
    private void takephoto(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }
    private void openAlbum(){
        Intent intent =new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this,"you denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    private void handleImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }
    private void handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        String imagepath=getImagePath(uri,null);
        displayImage(imagepath);
    }
    private String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath){
        if(imagePath!=null){
            Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
            Bitmap bitmap1=yashuo(bitmap);
            Bitmap bitmap2=ImageCrop(bitmap1);
            LinearLayout llGroup=(LinearLayout)findViewById(R.id.linearlayout);
            ImageView imageview =new ImageView(this);
            imageview.setLayoutParams(new LayoutParams(llGroup.getHeight(),llGroup.getHeight()));
            if(kongzhi==1){kongzhi=0; llGroup=(LinearLayout)findViewById(R.id.linearlayout);llGroup.removeAllViews();}
            imageview.setPadding(10,0,10,0);

            imageview.setImageBitmap(bitmap2);
            llGroup.addView(imageview);
            i=i+1;
            bitmaps[i]=bitmap1;
        }
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
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(tianjiashangpingActivity.this,SecendActivity.class);
        startActivity(intent);
        finish();
    }
}
