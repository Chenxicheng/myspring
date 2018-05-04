package com.dashwood.myspring.model.student.controller;

import com.alibaba.fastjson.JSONObject;
import com.dashwood.myspring.common.utils.DES;
import com.dashwood.myspring.common.utils.ResultJSON;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/student")
public class StudentController {
    private static Map<String, String> map = Maps.newHashMap();
    @RequestMapping(value = "hello")
    public String hello() {
        return "hello world";
    }

    @RequestMapping(value = "/cmSend", method= RequestMethod.POST)
    public String cmSend(@RequestBody JSONObject paramJSON) {
        JSONObject resultJSON = new JSONObject();
        try {
            JSONObject requsetJSON = new JSONObject();
            if (paramJSON.containsKey("jsonData")) {
                String code = paramJSON.getString("jsonData");
                String json = DES.decrypt(code);
                requsetJSON = JSONObject.parseObject(json);
                System.out.println(requsetJSON.toString());
                resultJSON.put("rspcod", "00");
                resultJSON.put("sendCnt", "100");
                resultJSON.put("successCnt", "100");
                resultJSON.put("failureCnt","0");
                resultJSON.put("rspmsg", "发送成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultJSON.put("rspcod", "01");
            resultJSON.put("rspmsg", "发送失败");
            return resultJSON.toString();
        }


        return resultJSON.toString();
    }

    @RequestMapping(value = "/ctSend", method= RequestMethod.POST)
    public String ctSend(@RequestBody JSONObject paramJSON) {
//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        JSONObject resultJSON = new JSONObject();
        try {
            JSONObject requsetJSON = new JSONObject();
            if (paramJSON.containsKey("jsonData")) {
                String code = paramJSON.getString("jsonData");
                String json = DES.decrypt(code);
                requsetJSON = JSONObject.parseObject(json);
                System.out.println(requsetJSON.toString());
                resultJSON.put("rspcod", "00");
                resultJSON.put("sendCnt", "100");
                resultJSON.put("successCnt", "100");
                resultJSON.put("failureCnt","0");
                resultJSON.put("rspmsg", "发送成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultJSON.put("rspcod", "01");
            resultJSON.put("rspmsg", "发送失败");
            return resultJSON.toString();
        }


        return resultJSON.toString();
    }

    @RequestMapping("/test")
    public String test(String i) {
        if (map.containsKey(i)) {
            System.out.println("1");
            return map.get(i);
        } else {
            System.out.println("2");
            map.put(i, "123456");
            return "123456";
        }
    }



}
