package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.DatabaseMetaData;

public class MainActivity extends AppCompatActivity {

    private Button buttonsql;
    private mysqlclass dphelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setOnClickListener();
    }

    private void setOnClickListener() {
         buttonsql.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dphelper.getWritableDatabase();
             }
         });
    }

    private void init() {
        buttonsql = (Button)findViewById(R.id.buttonsql);
        dphelper = new  mysqlclass(this,"BookStore.dp",null,1);
    }

}
