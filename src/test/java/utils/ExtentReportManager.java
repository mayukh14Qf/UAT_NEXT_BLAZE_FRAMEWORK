package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExtentReportManager implements ITestListener
{
		public ExtentSparkReporter sparkReporter;
		public ExtentReports extent;
		public ExtentTest test;

		
		String repName;
		
		public void onStart(ITestContext testContext)
		{		
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
			repName="Test-Report-"+timeStamp+".html";
			
			System.out.println(repName);
					
			sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName);//specify location of the report
					
			sparkReporter.config().setDocumentTitle("UAT Test Next Automation Report"); // Title of report
			sparkReporter.config().setReportName("TestNext Functional Testing"); // name of the report
			sparkReporter.config().setTheme(Theme.DARK);
					
			extent=new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Application", "TestNext");
			extent.setSystemInfo("Module", "Admin");
			extent.setSystemInfo("Sub Module", "Author test");
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
			extent.setSystemInfo("Environemnt","QA");
			extent.setSystemInfo("Operating System","windows");

			String browser=testContext.getCurrentXmlTest().getParameter("browser");
			extent.setSystemInfo("Browser",browser);

			List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
			if (!includedGroups.isEmpty()){
				extent.setSystemInfo("Groups",includedGroups.toString());
			}
		}


//		public void onTestSuccess(ITestResult result) {
//			test = extent.createTest(result.getTestClass().getName());
//			test.assignCategory(result.getMethod().getGroups());
//			test.log(Status.PASS, result.getName() + " Got Successfully Executed");
//
//			// Method-only execution time
//			long methodDuration = result.getEndMillis() - result.getStartMillis();
//			test.getModel().setStartTime(new Date(result.getStartMillis()));
//			test.getModel().setEndTime(new Date(result.getEndMillis()));
//
//			System.out.println("Method Execution Time: " + methodDuration + " ms");
//
//			// Full lifecycle (suite start to now)
//			long fullDuration = System.currentTimeMillis() - result.getTestContext().getStartDate().getTime();
//			System.out.println("Total Execution Time (like TestNG panel): " + fullDuration + " ms");
//		}

		public void onTestSuccess(ITestResult result) {
			test = extent.createTest(result.getTestClass().getName());
			test.assignCategory(result.getMethod().getGroups());
			test.log(Status.PASS, result.getName() + " Got Successfully Executed");

			// Exact TestNG duration
			long duration = result.getEndMillis() - result.getStartMillis();
			test.getModel().setStartTime(new Date(result.getStartMillis()));
			test.getModel().setEndTime(new Date(result.getEndMillis()));

			// Log timing info in ExtentReports
			String timeMsg = "Execution Time: " + duration + " ms (" + (duration / 1000.0) + " sec)";
			test.log(Status.INFO, timeMsg);

			System.out.println("Execution Time (matching TestNG): " + duration + " ms");
		}



		public void onTestFailure(ITestResult result)
		{
			test=extent.createTest(result.getTestClass().getName());
			test.assignCategory(result.getMethod().getGroups());

			test.log(Status.FAIL,result.getName()+ " : Test Failed");
			test.log(Status.INFO, result.getThrowable().getMessage());
			
			try
			{
			String screenshotPath=new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(screenshotPath);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public void onTestSkipped(ITestResult result)
		{
			test=extent.createTest(result.getTestClass().getName()); //test=extent.createTest(result.getTestContext().getName());
			//test.createNode(result.getName());
			test.assignCategory(result.getMethod().getGroups());
			test.log(Status.SKIP,result.getName()+ " test Skipped");
			test.log(Status.INFO, result.getThrowable().getMessage());
		}
		
		public void onFinish(ITestContext context)
		{
//			// Class execution time
//			long classStart = context.getStartDate().getTime();
//			long classEnd = context.getEndDate().getTime();
//			long classDuration = classEnd - classStart;
//
//			System.out.println("Total execution time for class " + context.getName() + ": " + classDuration + " ms (" + (classDuration / 1000.0) + " sec)");
//
//			// Optionally, log in ExtentReports
//			test = extent.createTest("Class Execution Summary: " + context.getName());
//			test.log(Status.INFO, "Total execution time: " + classDuration + " ms (" + (classDuration / 1000.0) + " sec)");

			// Flush the report
			extent.flush();

			// Automatically open report
			String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
			File extentReport = new File(pathOfExtentReport);
			try {
				Desktop.getDesktop().browse(extentReport.toURI());
			} catch (Exception e) {
				e.printStackTrace();
			}
//			try {
//			URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
//
//
//
//			 ImageHtmlEmail email = new ImageHtmlEmail();
//			  email.setDataSourceResolver(new DataSourceUrlResolver(url));
//			  email.setHostName("smtp.googlemail.com");
//			  email.setSmtpPort(465);
//			  email.setAuthenticator(new DefaultAuthenticator("mayukhjit@gmail.com", "mayukh123A!"));
//			  email.setSSLOnConnect(true);
//			  email.setFrom("mayukhjit@gmail.com");   //Sender
//			  email.setSubject("Test Results");
//			  email.setMsg("Please find Attached Report....");
//			  email.addTo("mayukhc55@gmail.com");   //Receiver
//			  email.attach(url, "extent report", "please check report...");
//			  email.send();   // send the email
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
			}
	}
