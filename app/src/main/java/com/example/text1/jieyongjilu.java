package com.example.text1;

/**
 * Created by hasee on 2017/6/6.
 */

public class jieyongjilu {
    private String date;
    private String time;
    private String classroom;
    private String phone;
    public jieyongjilu(String date,String time,String classroom,String phone){
        this.date=date;
        this.time=time;
        this.classroom=classroom;
        this.phone=phone;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getPhone() {
        return phone;
    }
}
