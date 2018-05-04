package com.dashwood.myspring.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CronTest {

    @Test
    public void test() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject resultJSON = new JSONObject();
        List<String> list = Lists.newArrayList();
        CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
        String type = "0";
        try {
            cronTriggerImpl.setCronExpression("0 */1 * * * ?");
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            if (type.equals("0")) {
                calendar.add(Calendar.HOUR_OF_DAY, 5);
            } else if (type.equals("1")) {
                calendar.add(Calendar.DAY_OF_YEAR, 5);
            } else if ("2".equals(type)) {
                calendar.add(Calendar.MONTH, 5);
            } else if ("3".equals(type)) {
                calendar.add(Calendar.MONTH, 2);
            } else if ("4".equals(type)) {
                calendar.add(Calendar.YEAR, 6);
            }
            //把统计的区间段设置为从现在到2年后的今天（主要是为了方法通用考虑，如那些1个月跑一次的任务，如果时间段设置的较短就不足20条)
            List<Date> dateList = TriggerUtils.computeFireTimesBetween(
                    cronTriggerImpl, null, now, calendar.getTime());//这个是重点，一行代码搞定~~
            System.out.println(dateList.size());
            for (int i=0; i<5; i++) {
                list.add(sdf.format(dateList.get(i)));
            }
            System.out.println(list);
        } catch (ParseException e) {
            e.printStackTrace();
        }//这里写要准备猜测的cron表达式
    }

    public void test2() {
        
    }
}
