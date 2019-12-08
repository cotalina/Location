package com.dongdaozhu.test;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.dongdaozhu.test.Service.MyService;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class MainActivity extends AppCompatActivity {

    private EditText et_time;
    private EditText et_email;
    private Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_time=findViewById(R.id.et_time);
        et_email=findViewById(R.id.et_email);
        enter=findViewById(R.id.bt_enter);
        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();



        et_time.setText(sharedPreferences.getInt("et_time",1)+"");
        et_email.setText(sharedPreferences.getString("et_email","523815974@qq.com")+"");
        //6.0原生 要权限 要完之后开启Service
        Permission();



        enter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {


                if(!isInteger(et_time.getText().toString())){
                    Toast.makeText(MainActivity.this,"时间必须为整数",Toast.LENGTH_LONG).show();

                    return;
                }

                if(et_time.getText().toString().length()==0||et_email.length()==0){
                    Toast.makeText(MainActivity.this,"不得为空",Toast.LENGTH_LONG).show();

                    return;}


                Toast.makeText(MainActivity.this,"设置完成",Toast.LENGTH_LONG).show();


                //获取编辑器
                editor.putInt("et_time", Integer.parseInt(et_time.getText().toString()));
                editor.putString("et_email", et_email.getText().toString());
                editor.commit();//提交修改

                startService(new Intent(MainActivity.this, MyService.class).setFlags(998));
            }
        });

    }


    private void Permission() {


        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "定位", R.drawable.permission_ic_camera));
        permissonItems.add(new PermissionItem(Manifest.permission.ACCESS_COARSE_LOCATION, "定位", R.drawable.permission_ic_camera));

        HiPermission.create(this)
                .permissions(permissonItems)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                    }

                    @SuppressLint("WrongConstant")
                    @Override
                    public void onFinish() {
                        //授权通过
                        Log.e("---","授权通过");

                    }

                    @Override
                    public void onDeny(String permisson, int position) {


                    }

                    @Override
                    public void onGuarantee(String permisson, int position) {

                    }
                });
    }



    private boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


}