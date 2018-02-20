package com.example.text1;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/5/24.
 */

public class ActivityCollector {//Acticity管理器
    public static List<Activity> activities=new ArrayList<>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishall() {//关闭所有活动
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
