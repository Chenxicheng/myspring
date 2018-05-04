package com.dashwood.myspring.model.datax.croe.impl;

import com.dashwood.myspring.model.datax.croe.JobJsonGenerator;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service("jobJsonGenerator")
public class JobJsonGeneratorImpl implements JobJsonGenerator {
    @Override
    public void generateJsonFile(String fileName, String readJson, String writeJson, String settingJson) throws IOException {
        String jobJSON = JOB_JSON;
        jobJSON = jobJSON.replace("${datax.readJson}", readJson)
                            .replace("${datax.writeJson}", writeJson)
                            .replace("${datax.settingJson}", settingJson);
        File jsonFile = new File(JOB_JSON_PATH+fileName+".json");
        Files.write(jobJSON.getBytes(Charsets.UTF_8), jsonFile);
    }

    @Override
    public boolean verificateJsonFileExit(String fileName) {
        File file = new File(JOB_JSON_PATH+fileName+".json");
        return file.exists();
    }

    @Override
    public void deleteJsonFile(String fileName) {
        File file = new File(JOB_JSON_PATH+fileName+".json");
        if (file.exists()) {
            file.delete();
        }
    }


}
