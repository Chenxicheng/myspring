package com.dashwood.myspring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);
    public static void main(String[] args) {
        int status = 0;
        if (status == 0) {
            logger.info("status: {}", status);
        } else {
            logger.info("status: {}", status);
        }
        logger.info("end!");

    }



}
