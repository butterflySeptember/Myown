package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.SetUtils;
import com.example.myapplication.bean.User;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * description: 用户登录
 */
public class RegistActivity extends FragmentActivity {

    private EditText mEtPassword;
    private EditText mEtUsername;
    private EditText mEtPassword1;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initview();
        initdate();
    }

    /**
     * 初始化数据
     */
    private void initdate() {
        if(SetUtils.IsLogin(getApplicationContext())){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    private void initview() {
        mEtUsername = (EditText) findViewById(R.id.et_name);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtPassword1 = (EditText) findViewById(R.id.et_password1);
    }

    /**
     * 注册
     * @param view
     */
    public void regist(View view) {
        if(mEtUsername.getText().toString().trim().equals("")||mEtPassword1.getText().toString().trim().equals("")||mEtPassword.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"请填写完整",Toast.LENGTH_SHORT).show();
        }else if(!mEtPassword1.getText().toString().trim().equals(mEtPassword.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
        } else {
            List<User> users = LitePal.where("username = ?", mEtUsername.getText().toString().trim()).find(User.class);
            if(users.size()==0){
                User user = new User();
                user.setUsername(mEtUsername.getText().toString().trim());
                user.setPassword(mEtPassword.getText().toString().trim());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                user.setCreatTime(df.format(new Date()));
                user.setType(1);
                boolean save = user.save();
                if(save){
                    Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(),"该用户名已注册过",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 退出
     * @param view
     */
    public void back(View view) {
        finish();
    }
}
