package com.example.tacocloud;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomePageBrowserTest {

    //@LocalServerPort
    //private int port;

    @Autowired
    Environment environment;

    private static HtmlUnitDriver browser;

    private static final Logger logger = LoggerFactory.getLogger(HomePageBrowserTest.class);

    @BeforeClass
    public static void setup() {
        browser = new HtmlUnitDriver();
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void teardown() {
        browser.quit();
    }

    @Test
    public void testHomepage() {
        String homePage = "http://localhost:" + environment.getProperty("local.server.port");
        logger.info("URI: {}", homePage);
        browser.get(homePage);

        Assert.assertEquals("Taco Cloud"
                ,browser.getTitle());
        Assert.assertEquals("Welcome to..."
                ,browser.findElementByTagName("h1").getText());
        Assert.assertEquals(homePage + "/images/TacoCloud.png"
                ,browser.findElementByTagName("img").getAttribute("src"));
    }
}
