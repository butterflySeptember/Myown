package com.example.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.bean.Book;

/**
 * 下单
 */
//定义了三个编辑框的字段名mEtName、mEtPrice、mEtAuth；
// 创建onCreate方法使界面显示布局文件activity_add_book。
// 定义三个方法， intview();、intidate();、intivetn();
public class AddBookActivity extends AppCompatActivity {

    private EditText mEtName;
    private EditText mEtPrice;
    private EditText mEtAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        intview();
        intidate();
        intivetn();
    }

    private void intivetn() {

    }

    private void intidate() {
    }

    private void intview() {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPrice = (EditText) findViewById(R.id.et_price);
        mEtAuth = (EditText) findViewById(R.id.et_auth);
    }

    public void back(View view) {
        finish();
    }

    /**
     * 确认添加
     * @param view
     */
    //addComfirm()方法，创建一个新的书籍，使能创建书籍名称、作者、价格。字段类型为boolean类型。
    // 当添加成功时，成功保存书籍，并且设置结果集为1.反之则添加失败。
    
    public void addComfirm(View view) {
        if(mEtAuth.getText().toString().trim().equals("")||mEtName.getText().toString().trim().equals("")||mEtName.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"请输入完整",Toast.LENGTH_SHORT).show();
        }else {
            Book book = new Book();
            book.setName(mEtName.getText().toString().trim());
            book.setAuth(mEtAuth.getText().toString().trim());
            book.setPrice(mEtPrice.getText().toString().trim());
            boolean save = book.save();
            if(save){
                Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                setResult(1);
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
