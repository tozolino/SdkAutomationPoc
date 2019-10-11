package demo;

import caps.DesiredCaps;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pom.CommonBasePage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class DemoIT extends DesiredCaps {

    private CommonBasePage cbp;


    @BeforeClass(alwaysRun = true)
    @Parameters(value = {"config", "environment", "os"})
    public void setUp(@Optional String configFile, @Optional String environment, @Optional String os, final ITestContext context) throws Exception {
        super.setUp(configFile, environment, os, context.getName());

        cbp = new CommonBasePage(driver);
    }

    @Test(description = "Verify Demo Test is executed", priority = 1, groups = {"integration"})
    public void Verify_DemoTest() {


        assertThat("Demo Test not executed properly!", true, is(true));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}

