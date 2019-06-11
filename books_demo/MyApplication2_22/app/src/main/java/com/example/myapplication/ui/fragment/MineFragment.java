package com.example.myapplication.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.utils.SetUtils;
import com.example.myapplication.ui.LoginActivity;
import com.example.myapplication.ui.MyOrderActivity;

/**
 * 我的页面
 */
public class MineFragment extends Fragment{

    private TextView mTvId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_mine,null);
        initview(layout);
        initdate();
        return layout;
    }

    private void initdate() {
        mTvId.setText("您好:"+ SetUtils.GetUserName(getActivity()));
    }

    private void initview(View layout) {
        mTvId = (TextView)layout.findViewById(R.id.tv_id);
        layout.findViewById(R.id.rtl_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyOrderActivity.class));
            }
        });
        layout.findViewById(R.id.rtl_exist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确认要退出？");
                builder.setTitle("提示");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {   @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();}
                });
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {   @Override
                public void onClick(DialogInterface dialog, int which) {
                    SetUtils.saveIsLogin(getActivity(),false);
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                    dialog.dismiss();
                }
                });
                builder.create().show();
            }
        });
    }
}
