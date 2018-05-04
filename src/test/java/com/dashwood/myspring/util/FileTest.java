package com.dashwood.myspring.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileTest {
    @Test
    public void test1() {
        System.out.println(f1());
    }

    public static String f1() {
        String a = "cxc";
        try {

            try {
                throw new Exception("aaaaaaa");
            } catch (Exception e) {
                a = "fff";
                return e.getMessage();
            }
        } catch (Exception e) {
            System.out.println("-----");
        } finally {
            System.out.println(a);
        }
        return "11111";
    }

    @Test
    public void  test2() {
        String s = "2017-12-26 14:43:01.163 [job-0] INFO  StandAloneJobContainerCommunicator - Total 6 records, 21213 bytes | Speed 2.07KB/s, 0 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 0.000s |  All Task WaitReaderTime 0.000s | Percentage 100.00%";
        s = s.substring(s.indexOf("records,")+8, s.indexOf("bytes")).trim();
        System.out.println(s);
    }

    @Test
    public void  test3() {
        String line = "任务启动时刻                    : 2017-12-28 14:37:26";
//        line.substring(line.indexOf(":")+1, line.length()).trim()

        System.out.println(line.matches("(任务启动时刻)"));
    }

    @Test
    public void test5() throws IOException {
        InputStreamReader isr = null;
        BufferedReader br = null;
        String line = null;
        Process process = Runtime.getRuntime().exec("java -cp D:\\workspace\\myspring\\target\\classes com.dashwood.myspring.util.TestMain");
        isr = new InputStreamReader(process.getInputStream());
        br = new BufferedReader(isr);
        BufferedReader reader = br;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            //readExecueOutputLineStatus(line, dataxJobStatus);
        }
    }


}
