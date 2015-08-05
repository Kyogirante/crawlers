package example.com.demo;

import android.app.Application;

import java.util.List;

import example.com.demo.Utils.AppConfig;

/**
 * Created by wang on 2015/7/28.
 */
public class MyApplication extends Application{
    private static MyApplication instance;

    public static MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        List<String> data = AppConfig.getTabData();
        if(data.isEmpty()){
            data.add("娱乐");
            data.add("热点");
            data.add("社会");
            data.add("政治");
            data.add("头条");
            data.add("体育");
            data.add("科技");
            data.add("游戏");
            data.add("数码");
            data.add("手机");
            data.add("NBA");
            data.add("足球");
            data.add("时尚");
            AppConfig.setTabData(data);
        }
    }
}
