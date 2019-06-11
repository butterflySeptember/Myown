package com.example.myapplication.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.BookManagerAdapter;
import com.example.myapplication.R;
import com.example.myapplication.bean.Book;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * 书籍管理
 */

//创建BookManagerActivity类，定义了mList、mTvState两个字段名，书籍管理的适配器。
// 并且定义了一个新的书籍时间管理实例对象，继承list接口。
public class BookManagerActivity extends AppCompatActivity {
    private ArrayList<Book> mDate=new ArrayList<>();
    private ListView mList;
    private BookManagerAdapter adapter;
    private TextView mTvState;

    //用onCreate()方法，创建显示上下文，以activity_book_manager.xml布局文件显示，并且定义三个方法intview()、intidate()、intivetn()。

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        intview();
        intidate();
        intivetn();
    }

    //定义删除书籍方法，intivetn()；adapter是配给设置OnDelLitner删除按钮的接口，并且重定义一个书籍信息管理适配器的接口，用OnDelLitener()显示
    //定义一个新的提示信息，当结果集缺省时，按钮没有指定该命令，显示取消；当删除执行时，结果集为0时，执行确认，反之则删除成功。

    private void intivetn() {
        /**
         * 删除书籍
         */
        adapter.setOnDelLitner(new BookManagerAdapter.OnDelLitner() {
            @Override
            public void del(final int pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookManagerActivity.this);
                builder.setMessage("确认要删除该书籍？");
                builder.setTitle("提示");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {   @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();}
                });
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {   @Override
                public void onClick(DialogInterface dialog, int which) {
                    Book book = mDate.get(pos);
                   // int delete = book.g();
                    //if(delete==0){
                     //   Toast.makeText(getApplicationContext(),"删除失败",Toast.LENGTH_SHORT).show();
                   // }else {
                        Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                        intidate();
                    //}
                    dialog.dismiss();
                }
                });
                builder.create().show();
            }
        });


        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(),AddBookActivity.class),11);
            }
        });
    }

    private void intidate() {
        List<Book> bookList = LitePal.findAll(Book.class);
        mDate.clear();
        if(bookList.size()==0){
            mList.setVisibility(View.GONE);
            mTvState.setVisibility(View.VISIBLE);
            mTvState.setText("暂无数据");
        }else {
            mDate.addAll(bookList);
            mList.setVisibility(View.VISIBLE);
            mTvState.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    private void intview() {
        mTvState = (TextView) findViewById(R.id.tv_state);
        mList = (ListView) findViewById(R.id.list);
        adapter = new BookManagerAdapter(getApplicationContext(),mDate);
        mList.setAdapter(adapter);
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            intidate();
        }
    }
}
