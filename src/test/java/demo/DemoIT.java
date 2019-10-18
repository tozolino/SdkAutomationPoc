package demo;

import caps.DesiredCaps;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.testng.ITestContext;
import org.testng.annotations.*;
import pom.CommonBasePage;


public class DemoIT extends DesiredCaps {

    private CommonBasePage cbp;
    private ClientAndServer mockServer;
    private MockServerClient msc;


    @BeforeClass(alwaysRun = true)
    @Parameters(value = {"config", "environment", "os"})
    public void setUp(@Optional String configFile, @Optional String environment, @Optional String os, final ITestContext context) throws Exception {
        super.setUp(configFile, environment, os, context.getName());

        cbp = new CommonBasePage(driver);
    }

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);
//    @Before
//    public void startMockServer() {
//        mockServer = startClientAndServer(3000);
//    }

    @Test(description = "Verify Demo Test is executed", priority = 1, groups = {"integration"})
    public void Verify_DemoTest() {
//REST ASSURED TEST EXAMPLE
//        given().
//                when().
//                get("https://acc.consent-api.talpanetwork.com/").then().
//
//
//        assertThat().body("cookiePolicy.version", equalToIgnoringCase("1.1.0")).
//        and().
//        statusCode(200);
//WIREMOCK TEST EXAMPLE
//        stubFor(get(urlEqualTo("/some/thing"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withStatusMessage("Everything was just fine!")
//                        .withHeader("Content-Type", "text/plain")));
//
//
//
//        assertThat(testClient.get("/some/thing/else").statusCode(), is(200));
//
//        assertThat(testClient.get("/some/thing/else").statusCode(), is(404));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

//    @After
//    public void stopMockServer() {
//        mockServer.stop();
//    }
}

