package com.dashwood.myspring.util;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

public class CmMAS {

    @Test
    public void test() {
        Map<String,String> map = Maps.newHashMap();
        map.put("a","99.12");

        double a = new Double(map.get("a"));

        System.out.println(a);
    }


}
