package com.todo.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.todo.base.BasePage;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExtentReporter extends BasePage implements IReporter {

    private ExtentReports extent;
    private ExtentTest test;
    private ExtentSparkReporter spark;

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        spark = new ExtentSparkReporter("test-reports" + File.separator + "test-execution-report.html");
        spark.config().setTheme(Theme.DARK);
        spark.config().thumbnailForBase64(true);
        spark.config().setDocumentTitle("Test Execution Report");

        extent = new ExtentReports();
        extent.setReportUsesManualConfiguration(true);  //this makes sure that the Started and Ended times on the summary page are correct
        extent.attachReporter(spark);

        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Host", System.getProperty("user.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();

            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();

                buildTestNodes(context.getPassedTests(), Status.PASS);
                buildTestNodes(context.getFailedTests(), Status.FAIL);
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
            }
        }

        extent.flush();
    }

    private void buildTestNodes(IResultMap tests, Status status) {

        if (tests.size() > 0) {

            for (ITestResult result : tests.getAllResults()) {

                test = extent.createTest(result.getMethod().getMethodName(),
                        result.getMethod().getDescription());

                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);

                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                    test.addScreenCaptureFromBase64String(TestUtil.getScreenshot(result.getName()));
                }
                else
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");

                test.getModel().setStartTime(getTime(result.getStartMillis()));
                test.getModel().setEndTime(getTime(result.getEndMillis()));
                test.getModel().getLogs().get(0).setTimestamp(getTime(result.getEndMillis()));
            }
        }
    }

    private Date getTime(long millis) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

}