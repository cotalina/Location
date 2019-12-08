# Location
# 自动定位跟踪，定时邮件发送，适合给老人小孩使用<br> <br> 

   Android5.0及以上的机型理论没有问题 (有问题反馈吧)<br> <br> 
     
 
          思路
          
          1.先获取手机定位网络权限（Gps经纬度）
          
          2.设置获取定位的间隔时间与接收邮箱（邮箱模块）

          2.开启service（APP杀掉依然可以定位获取信息）
          
          3.获取经纬度 逆向转换获取地址（抓了一个免费解析经纬度地址的接口）

          4.通过邮箱发送
          
          5.运行之前建议先开启自启动，不然会存在APP杀掉 service也跟着挂。
<br> <br> 

          思路
          
          1.Service 保活用了这个库 https://github.com/xingda920813/HelloDaemon
          
          2.demo网络请求图方便用了 okhttp utils https://github.com/hongyangAndroid/okhttputils
          
         

图片：<br> <br> 


 ![image](https://github.com/qq2068254/Location/blob/master/screenshots/1.jpg)
  ![image](https://github.com/qq2068254/Location/blob/master/screenshots/2.jpg)
   ![image](https://github.com/qq2068254/Location/blob/master/screenshots/3.jpg)
    ![image](https://github.com/qq2068254/Location/blob/master/screenshots/4.jpg)
