package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AddTestcasePage;
import pageObjects.AuthorTestCasePage;
import testBase.BaseClass;

public class TC003_AddTestCase extends BaseClass {
    @Test
    public void verifyTestCaseCreation(){
        logger.info("****** Starting the Log in Test Case *****************");
        try {
            login();

            AuthorTestCasePage authorTestCasePage=new AuthorTestCasePage(getDriver());
           authorTestCasePage.clickAuthorTestcase();
            authorTestCasePage.selectEpic("Epic Mohit");
            authorTestCasePage.selectFeature("Feature Mohit");
            authorTestCasePage.clickRequirement("RQ-469");

            authorTestCasePage.clickAddTestcase();

            AddTestcasePage addTestcasePage= new AddTestcasePage(getDriver());
            addTestcasePage.setTestCaseName("Tc-08");
            addTestcasePage.setDescription("As a tester I want to test so that..");
            addTestcasePage.selectPriority("Low");
            addTestcasePage.selectQaUser("Mayukhjit Chakraborty");
            addTestcasePage.clickSave();
            String testCaseId=addTestcasePage.getTestcaseId("Tc-08");
            System.out.println(testCaseId);
            Assert.assertFalse(testCaseId.isEmpty());
        }
        catch (Exception | AssertionError e){
            e.printStackTrace();
            logger.error("Test case failed ...");
            Assert.fail();
        }
        logger.info("************ Test Case Finished *************************");
    }

}
