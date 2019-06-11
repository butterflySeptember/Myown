package com.example.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.SetUtils;
import com.example.myapplication.bean.Book;
import com.example.myapplication.bean.BookOrder;

/**
 * 下单
 */

//创建一个新的类MakeOrderActivity，定义三个字段名mEtPhone、mEtAddress、mBook

public class MakeOrderActivity extends AppCompatActivity {

    private Book mBook;
    private EditText mEtPhone;
    private EditText mEtAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        intview();
        intidate();
        intivetn();
    }

    private void intivetn() {

    }

    private void intidate() {
        mBook = (Book) getIntent().getSerializableExtra("data");
    }

    private void intview() {
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtAddress = (EditText) findViewById(R.id.et_address);
    }

    public void back(View view) {
        finish();
    }

    /**
     * 确认下单
     * @param view
     */
    //定义makeOrder()方法，判断用户的输入地址信息和手机信息是否为空；
    // 判断用户有没有输入，trim()--头尾空白被滤掉，如果有缺省，则系统提示信息“请输入完整”

    public void makeOrder(View view) {
        if(mEtAddress.getText().toString().trim().equals("")||mEtPhone.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"请输入完整",Toast.LENGTH_SHORT).show();
        }else {

            //反之，定义一个新的订单，设置用户的id，将String字符类型数据转换为Integer整型数据，取得用户id
            // 并且用getApplicationContext()方法取得上下文。
            //设置订单信息的id、姓名、价格、地址、电话，逐一对应数据库的字段内容。
            //如果保存成功，系统提示信息为“下单成功”，并且显示
            //反之保存失败，则提示信息为“下单失败”，并且显示
            BookOrder order = new BookOrder();
            order.setUserid(Integer.parseInt(SetUtils.GetId(getApplicationContext())));
            order.setBook_id(mBook.getId());
            order.setBookName(mBook.getName());
            order.setBookPrice(mBook.getPrice());
            order.setAddress(mEtAddress.getText().toString().trim());
            order.setPhone(mEtPhone.getText().toString().trim());
            boolean save = order.save();
            if(save){
                Toast.makeText(getApplicationContext(),"下单成功",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"下单失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
