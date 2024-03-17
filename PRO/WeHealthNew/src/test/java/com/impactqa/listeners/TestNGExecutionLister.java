package com.impactqa.listeners;
import org.apache.commons.io.FileUtils;
import org.testng.IExecutionListener;
import java.io.File;

public class TestNGExecutionLister implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        try {

            File file = new File(System.getProperty("user.dir")+"/allure-results");

            if(file.exists())
                FileUtils.deleteDirectory(file);
            System.out.println("Allure result deleted..");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onExecutionFinish() {
        System.out.println();
    }
}
