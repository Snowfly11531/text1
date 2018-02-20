package com.example.text1;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import static android.R.id.list;

public class classroomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classroom_layout);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        TextView xiaoyuanfencai=(TextView)findViewById(R.id.diceng_layout1).findViewById(R.id.xiaoyuanfengcai);
        TextView jiaoshiliyong=(TextView)findViewById(R.id.diceng_layout1).findViewById(R.id.jiaoshiliyong);
        TextView ershoushichang=(TextView)findViewById(R.id.diceng_layout1).findViewById(R.id.ershoushichang);
        TextView gerenxinxi=(TextView)findViewById(R.id.diceng_layout1).findViewById(R.id.gerenxingxi);
        xiaoyuanfencai.setBackgroundResource(R.drawable.beijing);
        jiaoshiliyong.setBackgroundResource(R.drawable.titlegreen);
        ershoushichang.setBackgroundResource(R.drawable.beijing);
        gerenxinxi.setBackgroundResource(R.drawable.beijing);
        xiaoyuanfencai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(classroomActivity.this,firstactivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        jiaoshiliyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(classroomActivity.this,classroomActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        ershoushichang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(classroomActivity.this,SecendActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        gerenxinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(classroomActivity.this,yonghuActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
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

                XCRoundImageView xcRoundImageView=(XCRoundImageView)findViewById(R.id.title_layout1).findViewById(R.id.titlepic1);
                xcRoundImageView.setImageBitmap(bitmap);
                String name=cursor.getString(cursor.getColumnIndex("name"));
                TextView textView=(TextView)findViewById(R.id.title_layout1).findViewById(R.id.titlename);
                textView.setText(name);
            }
            cursor.moveToNext();
        }
        String[] use=getResources().getStringArray(R.array.use);
        final Spinner classuse=(Spinner)findViewById(R.id.classuse);
        final Spinner day=(Spinner)findViewById(R.id.day);
        final Spinner kechenglist=(Spinner)findViewById(R.id.kecheng);
        classuse.getBackground().setAlpha(150);
        day.getBackground().setAlpha(150);
        ArrayAdapter<String> classadpter=new ArrayAdapter<String>(classroomActivity.this,android.R.layout.simple_spinner_item,use);
        classadpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classuse.setAdapter(classadpter);
        classuse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  classuse=parent.getSelectedItem().toString();
                if(classuse.equals("空教室查询")){
                    String[] date =new String[7];
                    for(int i=0;i<7;i++){
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(new Date());
                        cal.add(Calendar.DAY_OF_MONTH, i);
                        String   tomorrow = Integer.toString(cal.get(cal.DATE));
                        String   tomorrow1 = Integer.toString(cal.get(cal.MONTH)+1);
                        cal.clear();
                        cal.setTime(new Date());
                        cal.add(Calendar.DAY_OF_MONTH, i-1);
                        String   week=Integer.toString(cal.get(cal.DAY_OF_WEEK));
                        date[i]=tomorrow1+"月"+tomorrow+"日"+" 星期"+week;
                    }
                    ArrayAdapter<String> classadpter=new ArrayAdapter<String>(classroomActivity.this,android.R.layout.simple_spinner_item,date);
                    classadpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    day.setAdapter(classadpter);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    String   week=Integer.toString(cal.get(cal.DAY_OF_WEEK));
                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
                    Cursor cursor = db.query("kebiao", null, null, null, null, null, null);
                    cursor.moveToFirst();
                    int length = cursor.getCount();
                    int i;
                    String[] yilou = new String[22];
                    int j = 0;
                    for (i = 0; i < length; i++) {
                        String classroom = cursor.getString(cursor.getColumnIndex("classroom"));
                        String weizhi = classroom.substring(0, 1);
                        AlertDialog.Builder builder = new AlertDialog.Builder(classroomActivity.this);
                        String kecheng=kechenglist.getSelectedItem().toString();
                        String mulu = week + kecheng.substring(1,3);
                        String bianhao = cursor.getString(cursor.getColumnIndex(mulu));
                        if (weizhi.equals("1") && bianhao.equals("true")) {
                            yilou[j] = classroom;
                            j++;
                        }
                        cursor.moveToNext();

                        List<DoubleString> doubleStrings = initdouble(yilou);
                        ListView jiaoshihao = (ListView) findViewById(R.id.jiaoshihao);
                        jiaoshihao.setDivider(null);
                        StringAdapter stringAdapter = new StringAdapter(classroomActivity.this, R.layout.string_layout, doubleStrings,0,mulu,day.getSelectedItem().toString());
                        jiaoshihao.setAdapter(stringAdapter);
                    }
                }if(classuse.equals("空教室借用")){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(classroomActivity.this);
                    dialog.setMessage("只能预约两天之后的哦 : ）");
                    dialog.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                    String[] date =new String[7];
                    for(int i=2;i<9;i++){
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(new Date());
                        cal.add(Calendar.DAY_OF_MONTH, i);
                        String   tomorrow = Integer.toString(cal.get(cal.DATE));
                        String   tomorrow1 = Integer.toString(cal.get(cal.MONTH)+1);
                        cal.clear();
                        cal.setTime(new Date());
                        cal.add(Calendar.DAY_OF_MONTH, i-1);
                        String   week=Integer.toString(cal.get(cal.DAY_OF_WEEK));
                        date[i-2]=tomorrow1+"月"+tomorrow+"日"+" 星期"+week;
                    }
                    ArrayAdapter<String> classadpter=new ArrayAdapter<String>(classroomActivity.this,android.R.layout.simple_spinner_item,date);
                    classadpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    day.setAdapter(classadpter);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    String   week=Integer.toString(cal.get(cal.DAY_OF_WEEK));
                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
                    Cursor cursor = db.query("kebiao", null, null, null, null, null, null);
                    cursor.moveToFirst();
                    int length = cursor.getCount();
                    int i;
                    String[] yilou = new String[22];
                    int j = 0;
                    for (i = 0; i < length; i++) {
                        String classroom = cursor.getString(cursor.getColumnIndex("classroom"));
                        String weizhi = classroom.substring(0, 1);
                        AlertDialog.Builder builder = new AlertDialog.Builder(classroomActivity.this);
                        String mulu = week + "01";
                        String bianhao = cursor.getString(cursor.getColumnIndex(mulu));
                        if (weizhi.equals("1") && bianhao.equals("true")) {
                            yilou[j] = classroom;
                            j++;
                        }
                        cursor.moveToNext();

                        List<DoubleString> doubleStrings = initdouble(yilou);
                        ListView jiaoshihao = (ListView) findViewById(R.id.jiaoshihao);
                        jiaoshihao.setDivider(null);
                        StringAdapter stringAdapter = new StringAdapter(classroomActivity.this, R.layout.string_layout, doubleStrings,0,mulu,day.getSelectedItem().toString());
                        jiaoshihao.setAdapter(stringAdapter);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String week=day.getSelectedItem().toString();
                String kecheng=kechenglist.getSelectedItem().toString();
                String usechoose=classuse.getSelectedItem().toString();
                int choose=1;
                if(usechoose.equals("空教室借用")){
                    choose=0;
                }
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
                Cursor cursor = db.query("kebiao", null, null, null, null, null, null);
                cursor.moveToFirst();
                int length = cursor.getCount();
                int i;
                String[] yilou = new String[22];
                int j = 0;
                for (i = 0; i < length; i++) {
                    String classroom = cursor.getString(cursor.getColumnIndex("classroom"));
                    String weizhi = classroom.substring(0, 1);
                    AlertDialog.Builder builder = new AlertDialog.Builder(classroomActivity.this);
                    String mulu = week.substring(week.length()-1,week.length()) + kecheng.substring(1, 3);
                    String bianhao = cursor.getString(cursor.getColumnIndex(mulu));
                    if (weizhi.equals("1") && bianhao.equals("true")) {
                        yilou[j] = classroom;
                        j++;
                    }
                    cursor.moveToNext();

                    List<DoubleString> doubleStrings = initdouble(yilou);
                    ListView jiaoshihao = (ListView) findViewById(R.id.jiaoshihao);
                    jiaoshihao.setDivider(null);
                    StringAdapter stringAdapter = new StringAdapter(classroomActivity.this, R.layout.string_layout, doubleStrings,choose,mulu,day.getSelectedItem().toString());
                    jiaoshihao.setAdapter(stringAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] kecheng={"第01节课 8:00-8:45","第02节课 8:50-9:35","第03节课 9:55-10:40","第04节课 10:45-11:30","第05节课 11:35-12:15",
                "第06节课 14:00-14:45","第07节课 14:50-15:35","第08节课 15:55-16:40","第09节课 16:45-17:30","第10节课 18:30-19:15",
                "第11节课 19:20-20:05","第12节课 20:10-20:55"};
        classadpter=new ArrayAdapter<String>(classroomActivity.this,android.R.layout.simple_spinner_item,kecheng);
        classadpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kechenglist.getBackground().setAlpha(150);
        kechenglist.setAdapter(classadpter);
        kechenglist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String week=day.getSelectedItem().toString();
                String kecheng=kechenglist.getSelectedItem().toString();
                String usechoose=classuse.getSelectedItem().toString();
                int choose=1;
                if(usechoose.equals("空教室借用")){
                    choose=0;
                }
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
                Cursor cursor = db.query("kebiao", null, null, null, null, null, null);
                cursor.moveToFirst();
                int length = cursor.getCount();
                int i;
                String[] yilou = new String[22];
                int j = 0;
                for (i = 0; i < length; i++) {
                    String classroom = cursor.getString(cursor.getColumnIndex("classroom"));
                    String weizhi = classroom.substring(0, 1);
                    AlertDialog.Builder builder = new AlertDialog.Builder(classroomActivity.this);
                    String mulu = week.substring(week.length()-1,week.length()) + kecheng.substring(1, 3);
                    String bianhao = cursor.getString(cursor.getColumnIndex(mulu));
                    if (weizhi.equals("1") && bianhao.equals("true")) {
                        yilou[j] = classroom;
                        j++;
                    }
                    cursor.moveToNext();

                    List<DoubleString> doubleStrings = initdouble(yilou);
                    ListView jiaoshihao = (ListView) findViewById(R.id.jiaoshihao);
                    jiaoshihao.setDivider(null);
                    StringAdapter stringAdapter = new StringAdapter(classroomActivity.this, R.layout.string_layout, doubleStrings,choose,mulu,day.getSelectedItem().toString());
                    jiaoshihao.setAdapter(stringAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final List<String> loucheng=new ArrayList<>();
        String[] ceng={"第一层","第二层","第三层","第四层","第五层","第六层","第七层","第八层","第九层"};
        for(int i=0;i<9;i++){
            loucheng.add(ceng[i]);
        }
        ArrayAdapter<String> louchengadpter=new ArrayAdapter<String>(classroomActivity.this,R.layout.loucheng,loucheng);
        ListView louchenglist=(ListView)findViewById(R.id.loucheng);
        louchenglist.setAdapter(louchengadpter);
        louchenglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String jilou=loucheng.get(position);
                String loushu="";
                String usechoose=classuse.getSelectedItem().toString();
                int choose=1;
                if(usechoose.equals("空教室借用")){
                    choose=0;
                }
                if(jilou.equals("第一层"))loushu="1";if(jilou.equals("第二层"))loushu="2";if(jilou.equals("第三层"))loushu="3";
                if(jilou.equals("第四层"))loushu="4";if(jilou.equals("第五层"))loushu="5";if(jilou.equals("第六层"))loushu="6";
                if(jilou.equals("第七层"))loushu="7";if(jilou.equals("第八层"))loushu="8";if(jilou.equals("第九层"))loushu="9";
                String xingqi=day.getSelectedItem().toString();
                String xingqishu=xingqi.substring(xingqi.length()-1,xingqi.length());
                String ke=kechenglist.getSelectedItem().toString();
                String keshu=ke.substring(1,3);
                String mulu=xingqishu+keshu;
                SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(DB_PATH+DB_NAME,null);
                Cursor cursor=db.query("kebiao",null,null,null,null,null,null);
                cursor.moveToFirst();
                int length=cursor.getCount();
                 int i;
                if(loushu.equals("1")){
                    String[] yilou=new String[22];
                    int j=0;
                    for(i=0;i<length;i++){
                        String classroom=cursor.getString(cursor.getColumnIndex("classroom"));
                        String weizhi=classroom.substring(0,1);
                        AlertDialog.Builder builder=new AlertDialog.Builder(classroomActivity.this);
                        String bianhao=cursor.getString(cursor.getColumnIndex(mulu));
                        if(weizhi.equals("1")&&bianhao.equals("true")){
                            yilou[j]=classroom;
                            j++;
                        }
                        cursor.moveToNext();
                    }
                    List<DoubleString> doubleStrings=initdouble(yilou);
                    ListView jiaoshihao=(ListView)findViewById(R.id.jiaoshihao);
                    jiaoshihao.setDivider(null);
                    StringAdapter stringAdapter=new StringAdapter(classroomActivity.this,R.layout.string_layout,doubleStrings,choose,mulu,day.getSelectedItem().toString());
                    jiaoshihao.setAdapter(stringAdapter);
                }
                if(loushu.equals("2")){
                    String[] yilou=new String[22];
                    int j=0;
                    for(i=0;i<length;i++){
                        String classroom=cursor.getString(cursor.getColumnIndex("classroom"));
                        String weizhi=classroom.substring(0,1);
                        AlertDialog.Builder builder=new AlertDialog.Builder(classroomActivity.this);

                        String bianhao=cursor.getString(cursor.getColumnIndex(mulu));
                        if(weizhi.equals("2")&&bianhao.equals("true")){
                            yilou[j]=classroom;
                            j++;
                        }
                        cursor.moveToNext();
                    }
                    List<DoubleString> doubleStrings=initdouble(yilou);
                    ListView jiaoshihao=(ListView)findViewById(R.id.jiaoshihao);
                    jiaoshihao.setDivider(null);
                    StringAdapter stringAdapter=new StringAdapter(classroomActivity.this,R.layout.string_layout,doubleStrings,choose,mulu,day.getSelectedItem().toString());
                    jiaoshihao.setAdapter(stringAdapter);
                }
                if(loushu.equals("3")){
                    String[] yilou=new String[22];
                    int j=0;
                    for(i=0;i<length;i++){
                        String classroom=cursor.getString(cursor.getColumnIndex("classroom"));
                        String weizhi=classroom.substring(0,1);
                        AlertDialog.Builder builder=new AlertDialog.Builder(classroomActivity.this);

                        String bianhao=cursor.getString(cursor.getColumnIndex(mulu));
                        if(weizhi.equals("3")&&bianhao.equals("true")){
                            yilou[j]=classroom;
                            j++;
                        }
                        cursor.moveToNext();
                    }
                    List<DoubleString> doubleStrings=initdouble(yilou);
                    ListView jiaoshihao=(ListView)findViewById(R.id.jiaoshihao);
                    jiaoshihao.setDivider(null);
                    StringAdapter stringAdapter=new StringAdapter(classroomActivity.this,R.layout.string_layout,doubleStrings,choose,mulu,day.getSelectedItem().toString());
                    jiaoshihao.setAdapter(stringAdapter);
                }
                if(loushu.equals("4")){
                    String[] yilou=new String[22];
                    int j=0;
                    for(i=0;i<length;i++){
                        String classroom=cursor.getString(cursor.getColumnIndex("classroom"));
                        String weizhi=classroom.substring(0,1);
                        AlertDialog.Builder builder=new AlertDialog.Builder(classroomActivity.this);

                        String bianhao=cursor.getString(cursor.getColumnIndex(mulu));
                        if(weizhi.equals("4")&&bianhao.equals("true")){
                            yilou[j]=classroom;
                            j++;
                        }
                        cursor.moveToNext();
                    }
                    List<DoubleString> doubleStrings=initdouble(yilou);
                    ListView jiaoshihao=(ListView)findViewById(R.id.jiaoshihao);
                    jiaoshihao.setDivider(null);
                    StringAdapter stringAdapter=new StringAdapter(classroomActivity.this,R.layout.string_layout,doubleStrings,choose,mulu,day.getSelectedItem().toString());
                    jiaoshihao.setAdapter(stringAdapter);
                }
                if(loushu.equals("5")){
                    String[] yilou=new String[22];
                    int j=0;
                    for(i=0;i<length;i++){
                        String classroom=cursor.getString(cursor.getColumnIndex("classroom"));
                        String weizhi=classroom.substring(0,1);
                        AlertDialog.Builder builder=new AlertDialog.Builder(classroomActivity.this);

                        String bianhao=cursor.getString(cursor.getColumnIndex(mulu));
                        if(weizhi.equals("5")&&bianhao.equals("true")){
                            yilou[j]=classroom;
                            j++;
                        }
                        cursor.moveToNext();
                    }
                    List<DoubleString> doubleStrings=initdouble(yilou);
                    ListView jiaoshihao=(ListView)findViewById(R.id.jiaoshihao);
                    jiaoshihao.setDivider(null);
                    StringAdapter stringAdapter=new StringAdapter(classroomActivity.this,R.layout.string_layout,doubleStrings,choose,mulu,day.getSelectedItem().toString());
                    jiaoshihao.setAdapter(stringAdapter);
                }
                if(loushu.equals("6")){
                    String[] yilou=new String[22];
                    int j=0;
                    for(i=0;i<length;i++){
                        String classroom=cursor.getString(cursor.getColumnIndex("classroom"));
                        String weizhi=classroom.substring(0,1);
                        AlertDialog.Builder builder=new AlertDialog.Builder(classroomActivity.this);

                        String bianhao=cursor.getString(cursor.getColumnIndex(mulu));
                        if(weizhi.equals("6")&&bianhao.equals("true")){
                            yilou[j]=classroom;
                            j++;
                        }
                        cursor.moveToNext();
                    }
                    List<DoubleString> doubleStrings=initdouble(yilou);
                    ListView jiaoshihao=(ListView)findViewById(R.id.jiaoshihao);
                    jiaoshihao.setDivider(null);
                    StringAdapter stringAdapter=new StringAdapter(classroomActivity.this,R.layout.string_layout,doubleStrings,choose,mulu,day.getSelectedItem().toString());
                    jiaoshihao.setAdapter(stringAdapter);
                }
                if(loushu.equals("7")){
                    String[] yilou=new String[22];
                    int j=0;
                    for(i=0;i<length;i++){
                        String classroom=cursor.getString(cursor.getColumnIndex("classroom"));
                        String weizhi=classroom.substring(0,1);
                        AlertDialog.Builder builder=new AlertDialog.Builder(classroomActivity.this);

                        String bianhao=cursor.getString(cursor.getColumnIndex(mulu));
                        if(weizhi.equals("7")&&bianhao.equals("true")){
                            yilou[j]=classroom;
                            j++;
                        }
                        cursor.moveToNext();
                    }
                    List<DoubleString> doubleStrings=initdouble(yilou);
                    ListView jiaoshihao=(ListView)findViewById(R.id.jiaoshihao);
                    jiaoshihao.setDivider(null);
                    StringAdapter stringAdapter=new StringAdapter(classroomActivity.this,R.layout.string_layout,doubleStrings,choose,mulu,day.getSelectedItem().toString());
                    jiaoshihao.setAdapter(stringAdapter);
                }
                if(loushu.equals("8")){
                    String[] yilou=new String[22];
                    int j=0;
                    for(i=0;i<length;i++){
                        String classroom=cursor.getString(cursor.getColumnIndex("classroom"));
                        String weizhi=classroom.substring(0,1);

                        String bianhao=cursor.getString(cursor.getColumnIndex(mulu));
                        if(weizhi.equals("8")&&bianhao.equals("true")){
                            yilou[j]=classroom;
                            j++;
                        }
                        cursor.moveToNext();
                    }
                    List<DoubleString> doubleStrings=initdouble(yilou);
                    ListView jiaoshihao=(ListView)findViewById(R.id.jiaoshihao);
                    jiaoshihao.setDivider(null);
                    StringAdapter stringAdapter=new StringAdapter(classroomActivity.this,R.layout.string_layout,doubleStrings,choose,mulu,day.getSelectedItem().toString());
                    jiaoshihao.setAdapter(stringAdapter);
                }
                if(loushu.equals("9")){
                    String[] yilou=new String[22];
                    int j=0;
                    for(i=0;i<length;i++){
                        String classroom=cursor.getString(cursor.getColumnIndex("classroom"));
                        String weizhi=classroom.substring(0,1);

                        String bianhao=cursor.getString(cursor.getColumnIndex(mulu));
                        if(weizhi.equals("9")&&bianhao.equals("true")){
                            yilou[j]=classroom;
                            j++;
                        }
                        cursor.moveToNext();
                    }
                    List<DoubleString> doubleStrings=initdouble(yilou);
                    ListView jiaoshihao=(ListView)findViewById(R.id.jiaoshihao);
                    jiaoshihao.setDivider(null);
                    StringAdapter stringAdapter=new StringAdapter(classroomActivity.this,R.layout.string_layout,doubleStrings,choose,mulu,day.getSelectedItem().toString());
                    jiaoshihao.setAdapter(stringAdapter);
                }
            }
        });
    }

    private List<DoubleString> initdouble(String[] strings){
        final List<DoubleString> doubleStrings=new ArrayList<>();
        int length=strings.length;
        int i=0;
        if(length % 2 != 0)i=1;
        if(i==0){
            for(int j=0;j<length;j=j+2){
                DoubleString doubleString=new DoubleString(strings[j],strings[j+1]);
                doubleStrings.add(doubleString);
            }
        }
        if(i==1){
            for(int j=0;j<length-1;j=j+2){
                DoubleString doubleString= new DoubleString(strings[j],strings[j+1]);
                doubleStrings.add(doubleString);
            }
            DoubleString doubleString= new DoubleString(strings[length-1],"");
            doubleStrings.add(doubleString);
        }
        return doubleStrings;
    }
}
