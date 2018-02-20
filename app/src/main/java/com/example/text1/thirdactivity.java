package com.example.text1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class thirdactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_latout);
        Button button = (Button) findViewById(R.id.button_6);
        final EditText edittext = (EditText) findViewById(R.id.eidttext2);
        String inputText=load();
        edittext.setText(inputText);
        edittext.setSelection(inputText.length());
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) actionbar.hide();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String string = edittext.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("data_return", string);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
    public String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while ((line=reader.readLine())!=null){
                content.append(line);
            };
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}

