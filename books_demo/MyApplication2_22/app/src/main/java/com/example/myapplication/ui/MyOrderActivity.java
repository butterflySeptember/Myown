package com.example.myapplication.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.SetUtils;
import com.example.myapplication.adapter.BookOrderAdapter;
import com.example.myapplication.bean.BookOrder;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * 我 的订单
 */
public class MyOrderActivity extends AppCompatActivity {
    private BookOrderAdapter adapter;
    private ArrayList<BookOrder> mDate=new ArrayList<>();
    private ListView mList;
    private TextView mTvState;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        intview();
        initdate();
        intievnet();
    }
    private void intievnet() {
        adapter.setOnDelLitner(new BookOrderAdapter.OnDelLitner() {
            @Override
            public void del(final int pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyOrderActivity.this);
                builder.setMessage("确认要删除该订单？");
                builder.setTitle("提示");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {   @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();}
                });
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {   @Override
                public void onClick(DialogInterface dialog, int which) {
                    BookOrder book = mDate.get(pos);
                    int delete = book.delete();
                    if(delete==0){
                        Toast.makeText(getApplicationContext(),"删除失败",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                        initdate();
                    }
                    dialog.dismiss();
                }
                });
                builder.create().show();
            }
        });
    }

    private void initdate() {
        List<BookOrder> bookList = LitePal.where("userid = ?", SetUtils.GetId(getApplicationContext())).order("id").find(BookOrder.class);
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
        adapter = new BookOrderAdapter(getApplicationContext(),mDate);
        mList.setAdapter(adapter);
    }

    public void back(View view) {
        finish();
    }
}
