package com.dashwood.myspring.util;

import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class DateTimeUtils {
    private static final ZoneId ZONEID = ZoneId.systemDefault();
    public static final SimpleDateFormat BASE_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat BASE_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_Y_M = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat DATE_Y = new SimpleDateFormat("yyyy");

    public static final String TIME_TYPE_YEAR = "y";

    public static final String TIME_TYPE_MONTH = "M";

    public static final String TIME_TYPE_DAY = "d";

    public static final String TIME_TYPE_HOUR = "H";

    public static final String TIME_TYPE_MINUTE = "m";
    public static final String TIME_TYPE_SECOND = "s";

    private List<String> timeList = Lists.newArrayList();

    public static DateTimeUtils newInstance() {
        return new DateTimeUtils();
    }

//    private List<String> betweenOfDate(LocalDateTime t1, LocalDateTime t2, SimpleDateFormat sdf, String timeType) {
//        timeList.add(dtf.format(t1));
//        int i = 1;
//        while (true) {
//            LocalDateTime plusDate = t1.plusDays(i);
//            switch (timeType) {
//                case TIME_TYPE_YEAR:
//                    t1.plusYears(i);
//                    break;
//                case TIME_TYPE_MONTH:
//                    t1.plusMonths(i);
//                    break;
//                case TIME_TYPE_DAY:
//                    t1.plusDays(i);
//                    break;
//                case TIME_TYPE_HOUR:
//                    t1.plusHours(i);
//                    break;
//                case TIME_TYPE_MINUTE:
//                    t1.plusYears(i);
//                    break;
//                case TIME_TYPE_SECOND:
//                    t1.plusSeconds(i);
//                    break;
//            }
//            String plusDateFormat = dtf.format(plusDate);
//            System.out.println(plusDateFormat);
//            timeList.add(plusDateFormat);
//            if (t2.equals(plusDate)) {
//                break;
//            }
//            i++;
//        }
//        return timeList;
//    }

    public List<String> between(String startTime, String endTime, SimpleDateFormat sdf, String timeType) throws ParseException {
        LocalDateTime t1 = LocalDateTime.ofInstant(sdf.parse(startTime).toInstant(), ZONEID);
        LocalDateTime t2 = LocalDateTime.ofInstant(sdf.parse(endTime).toInstant(), ZONEID);
        timeList.add(startTime);
        int i = 1;
        while (true) {
            LocalDateTime plusDate = null;
            switch (timeType) {
                case TIME_TYPE_YEAR:
                    plusDate = t1.plusYears(i);
                    break;
                case TIME_TYPE_MONTH:
                    plusDate = t1.plusMonths(i);
                    break;
                case TIME_TYPE_DAY:
                    plusDate = t1.plusDays(i);
                    break;
                case TIME_TYPE_HOUR:
                    plusDate = t1.plusHours(i);
                    break;
                case TIME_TYPE_MINUTE:
                    plusDate = t1.plusYears(i);
                    break;
                case TIME_TYPE_SECOND:
                    plusDate = t1.plusSeconds(i);
                    break;
            }

            String plusDateFormat = sdf.format(Date.from(plusDate.atZone(ZONEID).toInstant()));
            timeList.add(plusDateFormat);
            if (t2.equals(plusDate)) {
                break;
            }
            i++;
        }
        return timeList;
    }

    public static List<String> betweenByDays(String startDate, String endDate) throws ParseException {
        return newInstance().between(startDate,endDate,BASE_DATE, TIME_TYPE_DAY);
    }

    public static List<String> betweenByMonths(String startDate, String endDate) throws ParseException {
        return newInstance().between(startDate,endDate,DATE_Y_M, TIME_TYPE_MONTH);
    }

    public static List<String> betweenByYears(String startDate, String endDate) throws ParseException {
        return newInstance().between(startDate,endDate,DATE_Y, TIME_TYPE_DAY);
    }

}
