package org.yansou.ci.storage.merge;

/**
 * Created by Administrator on 2017/6/12.
 */
public class ProjectVector {
    //第一级地址
    private String a1;
    //第二级地址
    private String a2;
    //第三级地址
    private String a3;
    //功率
    private String mw1;
    //甲方
    private String party_a;
    //引用的对象
    private Object quote;

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getMw1() {
        return mw1;
    }

    public void setMw1(String mw1) {
        this.mw1 = mw1;
    }

    public String getParty_a() {
        return party_a;
    }

    public void setParty_a(String party_a) {
        this.party_a = party_a;
    }


    public Object getQuote() {
        return quote;
    }

    public void setQuote(Object quote) {
        this.quote = quote;
    }


}
