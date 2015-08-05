package example.com.demo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import example.com.demo.MyApplication;

/**
 * Created by wang on 2015/7/27.
 */
public class AppConfig {
    private static final String CONFIG_TAB_DATA = "config_tab_data";

    private static final String TAB_DATA = "tab_data";
    private static final String getFileName(){
        return CONFIG_TAB_DATA;
    }

    public static List<String> getTabData(){
        return getArrayList(getFileName(),TAB_DATA);
    }

    public static void setTabData(List<String> value){
        commitArrayList(getFileName(),TAB_DATA,value);
    }

    private static void commitArrayList(String fileName, String key, List<String> value){
        MyApplication context = MyApplication.getInstance();
        SharedPreferences.Editor edit = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        edit.clear().commit();
        edit.putInt(key, value.size());
        for(int i = 0; i < value.size(); i ++){
            edit.putString(key+"_" + i, value.get(i));
        }
        edit.commit();
    }

    private static List<String> getArrayList(String fileName, String key){
        MyApplication context = MyApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        int size = sp.getInt(key, 0);
        List<String> list = new ArrayList<>();
        for(int i = 0; i < size; i++){
            list.add(sp.getString(key+"_" +i,""));
        }
        return list;
    }
}
