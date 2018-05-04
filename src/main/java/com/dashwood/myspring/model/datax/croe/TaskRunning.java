package com.dashwood.myspring.model.datax.croe;

import com.dashwood.myspring.common.utils.SpringContextHolder;

public class TaskRunning implements Runnable {
    private String id;
    private DataxEnginService dataxEnginService;

    public TaskRunning(String id) {
        this.id = id;
        this.dataxEnginService = (DataxEnginService) SpringContextHolder.getBean("dataxEnginService");
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId());
       DataXJobCounter dataxJobStatus = dataxEnginService.execute(id);
       System.out.println(dataxJobStatus);
    }
}
