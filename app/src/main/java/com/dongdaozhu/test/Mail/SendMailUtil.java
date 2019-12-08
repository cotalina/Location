package com.dongdaozhu.test.Mail;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import java.io.File;
import java.util.Date;

/**
 * Created by Administrator on 2019/9/11.
 */

public class SendMailUtil {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    //不知道邮箱的端口号可以看这篇文章  https://blog.csdn.net/zr_wb/article/details/52413674
    //qq
    private static final String SMTP_HOST = "smtp.qq.com";
    private static final String SMTP_PORT = "25";
    private static final String FROM_ADDRESS = "137396026@qq.com";
    private static  String SMTP_KEY = "srycadaausxfbibh";

    //    //163
    //  private static final String SMTP_HOST = "smtp.163.com";
  // private static final String SMTP_PORT = "465"; //或者465  994
  //   private static final String FROM_ADDRESS = "13276895502@163.com";
  // private static final String SMTP_KEY = "qq138689404333";
  ////    private static final String TO_ADD = "2584770373@qq.com";


    public static void send(final File file, String toAdd){
        final MailInfo mailInfo = creatMail(toAdd,"");
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendFileMail(mailInfo,file);
            }
        }).start();
    }


    public static void send(String toAdd,String msg){
        final MailInfo mailInfo = creatMail(toAdd,msg);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
            }
        }).start();
    }

    @NonNull
    private static MailInfo creatMail(String toAdd,String msg) {
        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(SMTP_HOST);
        mailInfo.setMailServerPort(SMTP_PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADDRESS); // 你的邮箱地址
        mailInfo.setPassword(SMTP_KEY);// 您的邮箱密码
        mailInfo.setFromAddress(FROM_ADDRESS); // 发送的邮
        mailInfo.setToAddress(toAdd); // 发到哪个邮件去
        mailInfo.setSubject("最新地址"+TimeUtil.formatDateTime(new Date()) ); // 邮件主题
        mailInfo.setContent(msg); // 邮件文本
        return mailInfo;
    }

}