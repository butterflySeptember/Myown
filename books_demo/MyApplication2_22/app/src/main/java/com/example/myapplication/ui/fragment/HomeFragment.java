package com.example.myapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BookAdapter;
import com.example.myapplication.bean.Book;
import com.example.myapplication.ui.MakeOrderActivity;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */

public class HomeFragment extends Fragment {
    private ArrayList<Book> mDate=new ArrayList<>();
    private ListView mList;
    private BookAdapter adapter;
    private TextView mTvState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_home,null);
        initview(layout);
        initdate();
        initevent();
        return layout;
    }
    private void initevent() {
        adapter.setOnBuyLitner(new BookAdapter.OnBuyLitner() {
            @Override
            public void buy(int pos) {
                startActivity(new Intent(getActivity(), MakeOrderActivity.class)
                .putExtra("data",mDate.get(pos)));
            }
        });
    }

    public void initdate() {
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

    public void initview(View layout) {
        mTvState = (TextView) layout.findViewById(R.id.tv_state);
        mList = (ListView) layout.findViewById(R.id.list);
        adapter = new BookAdapter(getActivity(),mDate);
        mList.setAdapter(adapter);
    }
}
