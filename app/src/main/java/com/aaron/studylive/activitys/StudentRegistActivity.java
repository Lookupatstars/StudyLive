package com.aaron.studylive.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.aaron.studylive.R;
import com.aaron.studylive.constant.GlobalVaries;
import com.aaron.studylive.database.StudentDBhelper;
import com.aaron.studylive.database.StudentInfo;
import com.aaron.studylive.utils.ActivityCollector;
import com.aaron.studylive.utils.DateUtil;
import com.aaron.studylive.utils.HttpUrl;
import com.aaron.studylive.utils.ImageToBase64;
import com.aaron.studylive.utils.L;

import org.json.JSONObject;

import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StudentRegistActivity extends AppCompatActivity {

    private Button btn_reg_back,btn_reg_check,btn_reg_reg;
    private EditText et_reg_phone,et_reg_email,et_reg_username,et_reg_passwd,et_reg_conpasswd,et_reg_name;
    private TextView tv_reg_pro;
    private ToggleButton tb_pw,tb_pw_conf;

    private StudentDBhelper sDB; // 声明一个用户数据库帮助器对象

    private String ImageResult = "";
    private int res = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        final GlobalVaries globalVaries = (GlobalVaries)this.getApplication(); // 验证码

        ActivityCollector.addActivity(this);


        //事件注册的类
        initView();
        //点击按钮的实现类
        intiClick();

        //注册按钮
        btn_reg_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btn_reg_reg){

                    String student_phone = et_reg_phone.getText().toString().trim();
                    String student_email = et_reg_email.getText().toString().trim();
                    String student_name = et_reg_name.getText().toString().trim();
                    String student_username = et_reg_username.getText().toString().trim();
                    String student_passwd = et_reg_passwd.getText().toString().trim();
                    String student_conpasswd = et_reg_conpasswd.getText().toString().trim();

                    //判断是否为空
                    //判断数据库中是否有重复的手机号，如果有重复的可以提示找回密码
                    if(student_phone.equals("")){
                        Toast.makeText(StudentRegistActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    } else if (student_phone.length()!= 11){
                        Toast.makeText(StudentRegistActivity.this,"手机号有误，请重新输入",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (student_email.equals("")){
                        Toast.makeText(StudentRegistActivity.this,"邮件不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!student_email.contains("@") || !student_email.contains(".")){
                        Toast.makeText(StudentRegistActivity.this,"邮件格式不正确",Toast.LENGTH_SHORT).show();
                        return;
                    } else if (student_name.equals("")){
                        Toast.makeText(StudentRegistActivity.this,"名称不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (student_username.equals("")){
                        Toast.makeText(StudentRegistActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    } else if (student_passwd.equals("")||student_conpasswd.equals("")){
                        Toast.makeText(StudentRegistActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (student_passwd.length()<6 || student_passwd.length()>16){
                        Toast.makeText(StudentRegistActivity.this,"密码长度不对",Toast.LENGTH_SHORT).show();
                        return;
                    }else if (!student_passwd.equals(student_conpasswd)){
                        Toast.makeText(StudentRegistActivity.this,"两次输入的密码不一致，请重新输入！",Toast.LENGTH_SHORT).show();
                        return;
                    }else {

                        //两次密码相同，注册
                        L.d("student_email = "+student_email + "student_name = "+
                                student_name + "student_phone = "+student_phone
                                + "student_username = "+student_username +"student_passwd = "+student_passwd);
//                        sendRegisterDataWithOkHttp(student_email,student_name,student_phone,student_username,student_passwd);

                        new Thread(new Runnable() {
                            public void run() {
                                JSONObject object = new JSONObject();

                                //把图片转化为base64格式的
                                ImageTranslate();
                                try {
                                    object.put("email",student_email);
                                    object.put("img","data:image/png;base64,"+ImageResult);  //图片默认为初始头像
                                    object.put("name",student_name);
                                    object.put("password", student_passwd);
                                    object.put("phone", student_phone);
                                    object.put("username", student_username);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                MediaType type = MediaType.parse("application/json;charset=UTF-8");
                                RequestBody requestBody = RequestBody.create(type, "" + object.toString());

                                try {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder()
                                            // 指定访问的服务器地址
                                            .url(HttpUrl.getInstance().getRegister()).post(requestBody)
                                            .build();
                                    Response response = client.newCall(request).execute();

                                    String responseData = response.body().string();

                                    JSONObject data = new JSONObject(responseData);
                                    L.d("data "+ data);
                                    int code = data.getInt("code");
                                    if (code == 0){
                                        L.d("第一次查看 res的值 "+code);
                                        //创建一个实体的类，把信息注册进入StunetInfo
                                        StudentInfo studentInfo = new StudentInfo();
                                        studentInfo.name =  student_name;
                                        studentInfo.username = student_username;
                                        studentInfo.password = student_passwd;
                                        studentInfo.phone = student_phone;
                                        studentInfo.email=  student_email;
                                        studentInfo.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
                                        studentInfo.roleId = 2;
                                        sDB.insert(studentInfo);

                                        Looper.prepare();
                                        Toast.makeText(getApplicationContext(), "注册成功，您已同意服务协议", Toast.LENGTH_SHORT).show();
                                        Looper.loop();

                                        //注册成功后直接登陆.
                                        Intent intent = new Intent(StudentRegistActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }else if (code == 3){
                                        Looper.prepare();
                                        Toast.makeText(getApplicationContext(),"邮箱或账号名已经被注册",Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }else {
                                        Looper.prepare();
                                        Toast.makeText(getApplicationContext(),"请再检查一遍",Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();
                    }

                }
            }
        });  //注册按钮

    }

    //把图片转化为base64格式的
    @SuppressLint("ResourceType")
    private void ImageTranslate(){
        InputStream is = getResources().openRawResource(R.drawable.default_header);
        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        ImageResult = ImageToBase64.bitmaptoString(mBitmap);
        L.d("看看xin的结果  = "+ImageResult);
    }

    //向服务器发送信息
    private void sendRegisterDataWithOkHttp(String email ,String name , String phone, String username,String password ) {
        new Thread(new Runnable() {
            public void run() {
                JSONObject object = new JSONObject();

                //把图片转化为base64格式的
                ImageTranslate();
                try {
                    object.put("email",email);
                    object.put("img","data:image/png;base64,"+ImageResult);  //图片默认为初始头像
                    object.put("name",name);
                    object.put("password", password);
                    object.put("phone", phone);
                    object.put("username", username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MediaType type = MediaType.parse("application/json;charset=UTF-8");
                RequestBody requestBody = RequestBody.create(type, "" + object.toString());

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            // 指定访问的服务器地址
                            .url(HttpUrl.getInstance().getRegister()).post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();

                    String responseData = response.body().string();

                    JSONObject data = new JSONObject(responseData);
                    L.d("data "+ data);
                    int code = data.getInt("code");
                    if (code == 0){
                        L.d("第一次查看 res的值 "+code);
                        //创建一个实体的类，把信息注册进入StunetInfo
                        StudentInfo studentInfo = new StudentInfo();
                        studentInfo.name =  name;
                        studentInfo.username = username;
                        studentInfo.password = password;
                        studentInfo.phone = phone;
                        studentInfo.email=  email;
                        studentInfo.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
                        studentInfo.roleId = 2;
                        sDB.insert(studentInfo);

                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "注册成功，您已同意服务协议", Toast.LENGTH_SHORT).show();
                        Looper.loop();

                        //注册成功后直接登陆.
                        Intent intent = new Intent(StudentRegistActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }else if (code == 3){
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(),"邮箱或账号名已经被注册",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(),"请再检查一遍",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    //事件注册的类
    private void initView(){
        //Button按钮的事件注册
        btn_reg_back = findViewById(R.id.btn_reg_back);
        btn_reg_reg = findViewById(R.id.btn_reg_reg);
        tv_reg_pro = findViewById(R.id.tv_reg_pro);

        //EditText的事件注册
        et_reg_phone = findViewById(R.id.et_reg_phone);
        et_reg_email = findViewById(R.id.et_reg_email);
        et_reg_name = findViewById(R.id.et_reg_name);
        et_reg_username = findViewById(R.id.et_reg_username);
        et_reg_passwd = findViewById(R.id.et_reg_passwd);
        et_reg_conpasswd = findViewById(R.id.et_reg_conpasswd);
        //眼镜
        tb_pw = findViewById(R.id.tb_reg_s_pw);
        tb_pw_conf = findViewById(R.id.tb_reg_s_pw_conf);

    }

    //点击按钮的实现类
    private void intiClick() {
        btn_reg_back.setOnClickListener(new BackLogin()); //返回按钮
        tv_reg_pro.setOnClickListener(new ProText());  //协议点击
        tb_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    et_reg_passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    et_reg_passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        tb_pw_conf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    et_reg_conpasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    et_reg_conpasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


    }


    //返回按钮
    private class BackLogin implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btn_reg_back){
                Intent intent = new Intent(StudentRegistActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    //点击协议
    private class ProText implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //点击服务协议的显示内容
            Toast.makeText(StudentRegistActivity.this,"您点击了服务条约",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器的实例
        sDB = StudentDBhelper.getInstance(this, 2);
        // 打开数据库帮助器的写连接
        sDB.openWriteLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 关闭数据库连接
        sDB.closeLink();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
