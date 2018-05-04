package com.dashwood.myspring.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.function.Supplier;

public class TestRunnable implements Supplier<JSONObject>{
    private String i;

    public TestRunnable(String i) {
        super();
        this.i = i;
    }

    public TestRunnable() {
        super();
    }

    public JSONObject get() {
        System.out.println(i);
        JSONObject json = new JSONObject();
        try {
//            System.out.println(i);
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (i.endsWith("2")) {
            try {
                throw new Exception("test");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(60000L);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                json.put("a"+i, e.getMessage());
                return json;
            }
        }

        json.put("a"+i, i);
        System.out.println(json);
        return json;
    }


}
