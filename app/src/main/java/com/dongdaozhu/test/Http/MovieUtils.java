package com.dongdaozhu.test.Http;

import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by Administrator on 2019/9/10.
 */

public class MovieUtils {
    public static void getAdd(XiayuCallBack<MovieEntity> callBack, String Longitude, String Latitude ) {
        OkHttpUtils.get()
                .url("http://m.gpsspg.com/apis/geo/?lat="+Latitude+"&lng="+Longitude+"&type=0")
                .build()
                .execute(callBack);
    }
}

