package com.dashwood.myspring.model.datax.croe;

import java.io.IOException;

/**
 * 生产datax执行job的json文件
 */
public interface JobJsonGenerator {
    /**
     * json路径
     */
    static final String JOB_JSON_PATH = "D:\\datax\\job\\";
    /**
     * json模板
     */
    static final String JOB_JSON = "{"
            +"\"job\": {"
            +"\"content\": ["
            + "{"
            +  "\"reader\": ${datax.readJson},"
            +  "\"writer\": ${datax.writeJson}"
            + "}"
            + "],"
            +  "\"setting\": ${datax.settingJson} "
            +"}}";
    /**
     * 生成json配置文件
     * @param fileName 文件名称
     * @param readJson readJson配置
     * @param writeJson writeJson配置
     * @param settingJson datax设置配置
     */
    void generateJsonFile (String fileName,String readJson, String writeJson, String settingJson) throws IOException;

    /**
     * 校验是否文件存在
     * @param fileName 文件名称
     * @return
     */
    boolean verificateJsonFileExit (String fileName);

    /**
     * 删除文件
     * @param fileName
     */
    void deleteJsonFile (String fileName);

}
