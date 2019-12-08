package com.dongdaozhu.test.Service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.dongdaozhu.test.GPSUtils;
import com.dongdaozhu.test.Http.MovieEntity;
import com.dongdaozhu.test.Http.MovieUtils;
import com.dongdaozhu.test.Http.XiayuCallBack;
import com.dongdaozhu.test.Mail.SendMailUtil;
import com.xdandroid.hellodaemon.AbsWorkService;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

/**
 * Created by Administrator on 2019/12/8.
 */

public class MyService extends AbsWorkService {
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;

    @SuppressLint("WrongConstant")
    @Override
    public Boolean shouldStopService(Intent intent, int flags, int startId) {


        return (intent.getFlags()==998?false:null);
    }

    @Override
    public void startWork(Intent intent, int flags, int startId) {

        sharedPreferences = getSharedPreferences("test", getApplication().MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Log.e("---",sharedPreferences.getInt("et_time",1)+"");

        Log.e("---",sharedPreferences.getString("et_email","10086@qq.com")+"");

      Timer timer =new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                Gps();//查询经纬度

            }
        }, 0, sharedPreferences.getInt("et_time",5)*1000*60);
    }


    //查询经纬度
    private void Gps() {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {


                GPSUtils.getInstance(getApplication()).getLngAndLat(new GPSUtils.OnLocationResultListener() {
                    @Override
                    public void onLocationResult(Location location) {
                        Log.e("----getLongitude", String.valueOf(location.getLongitude()));
                        Log.e("----getLatitude", String.valueOf(location.getLatitude()));

                        http(location);//走接口网络查询对应的经纬度 是什么地址
                    }

                    @Override
                    public void OnLocationChange(Location location) {


                    }
                });
            }
        });
    }

    //走接口网络查询对应的经纬度 是什么地址
    private void http(Location location) {

        MovieUtils.getAdd(new XiayuCallBack<MovieEntity>(MovieEntity.class,getApplication(),false) {
            @Override
            public void myError(Call call, Exception e, int id) {

                Log.e("---e",e.getMessage());
                Toast.makeText(getApplication(),"请检查您的网络",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(MovieEntity response, int id) {

                Log.e("---成功",response.toString());

                Toast.makeText(getApplication(),response.getResult().get(0).getAddress(),Toast.LENGTH_SHORT).show();

                //查询成功在发送邮件

                SendMailUtil.send(sharedPreferences.getString("et_email","523815974@qq.com"),response.getResult().get(0).getAddress());

            }
        }, String.valueOf(location.getLongitude()),String.valueOf(location.getLatitude()) );
    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {


    }

    @Override
    public Boolean isWorkRunning(Intent intent, int flags, int startId) {
        return null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent, Void alwaysNull) {
        return null;
    }

    @Override
    public void onServiceKilled(Intent rootIntent) {

    }
}
