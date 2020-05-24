package com.aaron.studylive.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.aaron.studylive.R;
import com.aaron.studylive.bean.LoginData;
import com.aaron.studylive.database.StudentDBhelper;
import com.aaron.studylive.database.StudentInfo;
import com.aaron.studylive.utils.ActivityCollector;
import com.aaron.studylive.utils.DateUtil;
import com.aaron.studylive.utils.HttpUrl;
import com.aaron.studylive.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginContentActivity extends AppCompatActivity{
    private static final String TAG = "LoginContentActivity";

    private EditText et_student_user,et_student_passwd;
    private TextView tv_forgotpasswd;
    private Button btn_login,btn_regist;
    private ToggleButton toggleBtnPw;

    private StudentDBhelper sDB; // 声明一个用户数据库帮助器对象

    private LoginData loginData = new LoginData();
    private int isUser = 0;  //0 :不存在用户；1：存在用户
    private static final String ImgUrl = "http://course-api.zzu.gdatacloud.com:890/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
        ActivityCollector.addActivity(this);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //事件注册以及点击的实现
        initViewAndClick();


    }

        //事件注册以及点击的实现
    private void initViewAndClick() {
        et_student_user = findViewById(R.id.et_student_user);
        et_student_passwd = findViewById(R.id.et_student_passwd);
        tv_forgotpasswd = findViewById(R.id.tv_forgotpasswd);
        toggleBtnPw = findViewById(R.id.togglePwd);

        btn_login = findViewById(R.id.btn_login);
        btn_regist = findViewById(R.id.btn_regist);

        btn_login.setOnClickListener(new LoginOnClickListener());
        btn_regist.setOnClickListener(new RegistClickListener());
        tv_forgotpasswd.setOnClickListener(new ForgetPasswd());

        //监听密码框是否获取到焦点
        et_student_user.setOnFocusChangeListener(new UserFocusListener());
        et_student_passwd.setOnFocusChangeListener(new PasswdFocusListener());
        toggleBtnPw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    et_student_passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    et_student_passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获得用户数据库帮助器的一个实例
        sDB = StudentDBhelper.getInstance(this, 2);
        // 恢复页面，则打开数据库连接
        sDB.openWriteLink();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 暂停页面，则关闭数据库连接
        sDB.closeLink();
    }

    //注册界面的点击按钮
    class RegistClickListener implements View.OnClickListener{
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_regist:
                    new AlertDialog.Builder(getParent())
                            .setTitle("请选择注册类型").setMessage("请正确选择您的身份，点击进行注册！")
                            .setPositiveButton("教师", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(LoginContentActivity.this,"请移步到网页注册",Toast.LENGTH_SHORT).show();
//                                    Intent intent1 = new Intent();
//                                    intent1.setClass(LoginContentActivity.this, TeacherRegistActivity.class);
//                                    startActivity(intent1);
//                                    finish();
                                }
                            })
                            .setNeutralButton("学生", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setClass(LoginContentActivity.this, StudentRegistActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).create().show();
            }
        }
    }


    private void sendRequestWithOkHttp(String username,String password ) {
        new Thread(new Runnable() {
            public void run() {
                JSONObject object = new JSONObject();
                try {
                    object.put("password", password);
                    object.put("rememberMe", "false");
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
                            .url(HttpUrl.getInstance().getLoginUrl()).post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();

                    String responseData = response.body().string();
                    JSONObject data = new JSONObject(responseData);
                    int code = data.getInt("code");
                    if (code == 0){
                        //获取sessionid并保存到SharedPreferences：
                        Headers headers =response.headers(); //response为okhttp请求后的响应
                        List cookies = headers.values("Set-Cookie");
                        String session = (String) cookies.get(0);
                        String sessionid = session.substring(0,session.indexOf(";"));
                        SharedPreferences share = LoginContentActivity.this.getSharedPreferences("Session",MODE_PRIVATE);
                        SharedPreferences.Editor edit = share.edit();//编辑文件
                        edit.putString("sessionid",sessionid);
                        edit.commit();
                        parseJSONWithJSONObject(responseData);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    //接收的方法
    //拿返回的key值接收即可
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject object = new JSONObject(jsonData).getJSONObject("content");

//            if (object.getString("img").contains("data:image/png;base64,")){
//                String userId = object.getString("img");
//                String userIdJiequ = userId.substring(22);
//                L.d("登录界面获取的照片信息 = "+userIdJiequ);
//                Bitmap bm = ImageToBase64.stringtoBitmap(userIdJiequ);
//                L.d(" 位图是不是 = "+bm);
//                Drawable drawable = new BitmapDrawable(bm);
//                L.d("  drawable "+drawable);
//                loginData.setImg(drawable); //多一个照片信息
//            }else {
//
//            }

            //设置登录信息，方便个人信息界面调用
            loginData.setsId(object.getInt("id"));
            loginData.setUsername(object.getString("username"));
            loginData.setEmail(object.getString("email"));
            loginData.setRoleId(object.getInt("roleId"));
            loginData.setName(object.getString("name"));

            loginData.setPhone(object.getString("phone"));
            loginData.setUpdate_time(object.getString("updateTime"));
            loginData.setCreate_time(object.getString("createTime"));


            //END
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //查询用户名是否存在
    private int JudgeByNameAndPasswd(String username,String password){
        JSONObject object = new JSONObject();
        try {
            object.put("password", password);
            object.put("rememberMe", "false");
            object.put("username", username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaType type = MediaType.parse("application/json;charset=UTF-8");
        RequestBody requestBody = RequestBody.create(type, "" + object.toString());
        L.d("run 真正该运行");

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    // 指定访问的服务器地址
                    .url(HttpUrl.getInstance().getLoginUrl()).post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject data = new JSONObject(responseData);
            int code = data.getInt("code");
            if (code == 0){
                isUser = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isUser;
    }

    //点击登录进入到主界面
    class LoginOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            if(v.getId()==R.id.btn_login){
                //获取输入框中的手机号、密码
                String username = et_student_user.getText().toString().trim();
                String passwd = et_student_passwd.getText().toString().trim();
                JudgeByNameAndPasswd(username,passwd);
//                StudentInfo byPhoneInfo = sDB.queryByPhone(phone);
//                L.d("1.登录对比手机号byPhoneInfo.phone::"+byPhoneInfo.phone);
                //判断user和password
                if(username.equals("")){
                    Toast.makeText(LoginContentActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                } else if (passwd.equals("")){
                    Toast.makeText(LoginContentActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                } else if (username.equals(loginData.getUsername())){ //对比数据库的用户名
                    //对比数据库的用户名密码
                    //如果比对失败，提示注册
                    if (isUser == 0){

                        //实现记住密码功能
                        // 创建一个用户信息实体类 存储到数据库
                        StudentInfo info = new StudentInfo();
                        info.name = loginData.getName();
                        info.username = username;
                        info.password = passwd;
                        info.phone = loginData.getPhone();
                        info.roleId = loginData.getRoleId();
                        info.email = loginData.getEmail();
                        info.create_time = loginData.getCreate_time();
                        info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
                        // 往用户数据库添加登录成功的用户信息（包含手机号码、密码、登录时间）
                        sDB.insert(info);

                        Intent intent = new Intent(LoginContentActivity.this, FlyMainActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(LoginContentActivity.this,"密码不正确",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    Toast.makeText(LoginContentActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }


    // 焦点变更事件的处理方法，hasFocus表示当前控件是否获得焦点。
    private class PasswdFocusListener implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            // 判断是否是密码编辑框发生焦点变化
            // 用户已输入手机号码，且密码框获得焦点
            if ( hasFocus) {
                // 根据手机号码到数据库中查询用户记录
                et_student_passwd.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String username = et_student_user.getText().toString();
                        String passwd = et_student_passwd.getText().toString().trim();
                        if (s.length()>=6){
                            btn_login.setEnabled(true);//设置按钮可点击
                            sendRequestWithOkHttp(username,s.toString());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        }
    }

    // 焦点变更事件的处理方法，hasFocus表示当前控件是否获得焦点。
    private class UserFocusListener implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            // 判断是否是密码编辑框发生焦点变化
            // 用户已输入手机号码，且密码框获得焦点
            if ( hasFocus) {
                et_student_user.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String username = et_student_user.getText().toString();
                        String passwd = et_student_passwd.getText().toString().trim();
                        if (s.length()>=6){
                            btn_login.setEnabled(true);//设置按钮可点击
                            sendRequestWithOkHttp(s.toString(),passwd);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

            }
        }
    }

    //忘记密码的点击
    private class ForgetPasswd implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginContentActivity.this, ForgetPasswdActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
