package com.dashwood.myspring.model.datax.croe;

/**
 *
 */
public interface DataxEnginService {

    static final String DATAX_PATH = "D:\\datax\\bin\\datax.py";

    /**
     * 执行datax
     * @param id
     * @return DataXJobCounter dataX统计结果
     */
    DataXJobCounter execute(String id) ;

    void executeJar(String jobJson, String jobId);


}
