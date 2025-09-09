package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AuthorTestCasePage;
import testBase.BaseClass;

public class TC002_FilterRequirement extends BaseClass {
    @Test
    public void verifyRequirementHeading(){
        logger.info("****** Starting the Log in Test Case *****************");
        try {
            login();
            AuthorTestCasePage authorTestCasePage=new AuthorTestCasePage(getDriver());
            authorTestCasePage.selectEpic("Epic Mohit");
            authorTestCasePage.selectFeature("Feature Mohit");
            authorTestCasePage.clickRequirement("RQ-469");
            String requirementHeader= authorTestCasePage.showRequirementHeader();
            Assert.assertEquals(requirementHeader,"Requirement Mohit");
            logger.info("Verification Successful");
        }
        catch (Exception | AssertionError e){
            logger.error("Test case failed ...");
            Assert.fail();
            e.printStackTrace();
        }
        logger.info("************ Test Case Finished *************************");
    }
}
