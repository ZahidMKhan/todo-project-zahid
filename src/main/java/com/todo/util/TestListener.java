package com.todo.util;

import com.todo.base.BasePage;
import org.apache.commons.io.FileUtils;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;


public class TestListener extends BasePage implements ITestListener, IExecutionListener {


    public void onExecutionStart() {

        try {
            log.info("Cleaning up screenshots directory.");
            FileUtils.deleteDirectory(new File(TestUtil.screenshotFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestStart(ITestResult result) {

        log.info("Test '" + result.getMethod().getMethodName() + "' started");
    }

    /**
     * captures screenshot when a test fails
     */
    @Override
    public void onTestFailure(ITestResult result) {

        log.error("Test '" + result.getMethod().getMethodName() + "' Failed");
        TestUtil.takeScreenshot(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {

        log.info("Test '" + result.getMethod().getMethodName() + "' PASSED");
    }


}