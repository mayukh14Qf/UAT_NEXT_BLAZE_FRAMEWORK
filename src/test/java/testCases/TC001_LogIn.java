package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseClass;

public class TC001_LogIn extends BaseClass {
    @Test
    public void verifyLogin(){
        logger.info("****** Starting the Log in Test Case *****************");
        try {
            login();
        }
        catch (Exception | AssertionError e){
            logger.error("Test case failed ...");
            Assert.fail();
        }
        logger.info("************ Test Case Finished *************************");
    }
}
