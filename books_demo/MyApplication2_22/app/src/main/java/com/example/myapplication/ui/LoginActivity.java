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

import java.util.List;

/**
 * description: 用户登录
 */

//创建一个新的类，命名为LoginActivity，继承FagmentActivity类的方法。
// 定义两个字段，分别为mEtPassword和mEtUsername，用来实现用户的登录。
public class LoginActivity extends FragmentActivity {

    private EditText mEtPassword;
    private EditText mEtUsername;

    //用onCreate()方法创建上下文，显示activity_login的布局，并且创建两个方法，分别为   initview()和initdate();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LitePal.initialize(this);
        initview();
        initdate();
    }

    /**
     * 初始化数据
     */
    //提供一个新的用户接口，当类型为2时，本地数据库的会找到用户的类
    //如果用户的大小为0，会定义一个新的用户设置它的账号密码为admin,admin，设置字段类型为2，保存信息。
    private void initdate() {
        List<User> users = LitePal.where("type = ?", "2").find(User.class);
        if(users.size()==0){
            User user = new User();
            user.setUsername("admin");
            user.setPassword("admin");
            user.setType(2);
            user.save();
        }
        //如果设置工具时，已经登录成功了，那么用getApplicationContext()方法获取到上下文。
        // 如果登陆成功为管理员，用intent()传递消息则设置管理员的活动类型，开启一个新的Activity

        if(SetUtils.IsLogin(getApplicationContext())){
            if(SetUtils.IsManager(getApplicationContext())){
                startActivity(new Intent(getApplicationContext(),ManagerActivity.class));
            }else {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
            finish();
        }
    }

    private void initview() {
        mEtUsername = (EditText) findViewById(R.id.et_name);
        mEtPassword = (EditText) findViewById(R.id.et_password);
    }

    /**
     * 登录
     * @param view
     */
    public void login(View view) {
        if(mEtUsername.getText().toString().trim().equals("")||mEtPassword.getText().toString().trim().equals("")){
        }else {
            User users = LitePal.where("username = ? and password=?", mEtUsername.getText().toString().trim(),mEtPassword.getText().toString().trim()).findFirst(User.class);
            if(users!=null){
                Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                SetUtils.saveId(getApplicationContext(),users.getId()+"");
                SetUtils.saveIsLogin(getApplicationContext(),true);
                SetUtils.saveUserName(getApplicationContext(),users.getUsername());
                if(users.getType()==1){
                    SetUtils.saveIsManager(getApplicationContext(),false);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else if(users.getType()==2){
                    SetUtils.saveIsManager(getApplicationContext(),true);
                    startActivity(new Intent(getApplicationContext(),ManagerActivity.class));
                }
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"请输入正确的用户名或密码",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 注册
     * @param view
     */
    public void regist(View view) {
        startActivity(new Intent(getApplicationContext(),RegistActivity.class));
    }
}
