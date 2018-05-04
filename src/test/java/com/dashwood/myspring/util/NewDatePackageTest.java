package com.dashwood.myspring.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.mockito.cglib.core.TinyBitSet;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;

public class NewDatePackageTest {

    @Test
    public void testClock() {
        Clock clock = Clock.systemUTC();
        System.out.println("获取当前时刻："+clock.instant());

        System.out.println(clock.millis());


    }

    @Test
    /**
     * Instant 表示时刻，不直接对应年月日信息，需要通过时区转换
     */
    public void testInstant() {
        Instant now = Instant.now();

        System.out.println();
    }

    @Test
    public void test2() {
//        Instant now = Instant.now();
        List<String> timeList = Lists.newArrayList();
        String s1 = "2017-09-12";
        String s2 = "2017-10-12";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(s1, dtf);
        LocalDate end = LocalDate.parse(s2, dtf);
        timeList.add(dtf.format(start));
        int i = 1;
        while (true) {
            LocalDate plusDate = start.plusDays(i);
            String plusDateFormat = dtf.format(plusDate);
            System.out.println(plusDateFormat);
            timeList.add(plusDateFormat);
            if (end.equals(plusDate)) {
                break;
            }
            i++;
        }


        System.out.println(timeList);
    }

    @Test
    public void testArray() {
        String s1 = "2017-09";
//        String s2 = "2017-10-12";
        DateTimeFormatter DATEFORMATTER1 = DateTimeFormatter.ofPattern("yyyy");
        DateTimeFormatter dtf = new DateTimeFormatterBuilder().append(DATEFORMATTER1)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
        System.out.println(dtf.toString());
//        LocalDate ld = LocalDate.parse(s1, dtf);
        LocalDateTime ldt = LocalDateTime.parse(s1, dtf);
        System.out.println(dtf.format(ldt));

    }

    @Test
    public void test3 () {
        Instant timestamp = new Date().toInstant();

        LocalDateTime ldt = LocalDateTime.ofInstant(timestamp, ZoneId.of("Asia/Shanghai"));

        System.out.println(ldt);
    }

    @Test
    public void testr () throws ParseException {


//        System.out.println(DateTimeUtils.newInstance().between("2017-09-12","2017-10-12", DateTimeUtils.BASE_DATE, DateTimeUtils.TIME_TYPE_DAY));

        System.out.println(DateTimeUtils.betweenByMonths("2017-03","2017-10"));
    }
}
