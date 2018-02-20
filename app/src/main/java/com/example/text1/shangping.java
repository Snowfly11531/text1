package com.example.text1;

/**
 * Created by hasee on 2017/5/12.
 */

public class shangping {
    private byte[] zhaopianid;
    private float price;
    private byte[] shangpingtupianid;
    private String shangpingjieshao;
    private String id;
    private String name;
    private String shangpinname;
    public shangping(byte[] zhaopianid,String name,String shangpinname,float price,byte[] shangpingtupianid,String shangpingjieshao,String id){
        this.price=price;
        this.zhaopianid=zhaopianid;
        this.shangpingjieshao=shangpingjieshao;
        this.shangpingtupianid=shangpingtupianid;
        this.name=name;
        this.shangpinname=shangpinname;
        this.id=id;
    }
    public String getname() {
        return name;
    }
    public String getShangpinname(){return shangpinname;}
    public byte[] getZhaopianid(){
        return zhaopianid;
    }

    public byte[] getShangpingtupianid() {
        return shangpingtupianid;
    }

    public float getPrice() {
        return price;
    }

    public String getShangpingjieshao() {
        return shangpingjieshao;
    }
    public String getId(){
        return id;
    }
}
