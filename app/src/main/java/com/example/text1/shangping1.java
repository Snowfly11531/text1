package com.example.text1;

/**
 * Created by hasee on 2017/6/6.
 */

public class shangping1 {
    private byte[] zhaopian;
    private float price;
    private String wupingname;
    private String id;
    public shangping1(byte[] zhaopian,float price,String wupingname,String id){
        this.zhaopian=zhaopian;
        this.price=price;
        this.wupingname=wupingname;
        this.id=id;
    }
    public byte[] getZhaopian(){
        return zhaopian;
    }
    public float getPrice(){
        return price;
    }

    public String getWupingname() {
        return wupingname;
    }

    public String getId() {
        return id;
    }
}
