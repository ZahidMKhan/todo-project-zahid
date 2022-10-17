package com.todo.util;

import com.todo.base.BasePage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class TestUtil extends BasePage {

    static String screenshotFolder = "test-screenshots", screenshotPrefix = "FAILED_";

    public static void takeScreenshot(String testMethod) {

        String filePath;
        File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            filePath = screenshotFolder + File.separator + screenshotPrefix + testMethod + ".png";
            FileUtils.copyFile(screenshotFile, new File(filePath));
            log.info("*** screenshot saved ***");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getScreenshot(String testMethod) {

        byte[] imageBytes= null;

        if(testMethod!=null) {
            String sImagePath = screenshotFolder + File.separator + "FAILED_" + testMethod + ".png";
            try {
                imageBytes = IOUtils.toByteArray(new FileInputStream(sImagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "data:image/png;base64,"+ Base64.getEncoder().encodeToString(imageBytes);
        }
        else return null;
    }
}
