package com.google.tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Allure;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public abstract class BaseTest {
    private String baseUrl = "https://github.com/OlehVelmyk";
    private String testName, browserType, width, height;

    public String getTestName() {
        return testName;
    }

    public String getBrowserType() {
        return browserType;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        Configuration.reportsFolder = "";
        Configuration.downloadsFolder = "";
        Configuration.browser = browser;
        setBrowserType(browser);
    }

    @BeforeMethod
    @Parameters({"width", "height"})
    protected void goToPage(@Optional("430") Integer width, @Optional("932") Integer height) {
        Allure.step("GO TO " + baseUrl);
        setWidth(width.toString());
        setHeight(height.toString());
        Dimension dimension = new Dimension(width, height);

        open(baseUrl);
        getWebDriver().manage().window().setSize(dimension);

        String currentUrl = getWebDriver().getCurrentUrl();
        Assert.assertEquals(currentUrl, baseUrl);
    }

    @BeforeMethod
    public void getTestName(ITestResult result){
        String name = result.getMethod().getMethodName();
        setTestName(name);
    }

    protected  void updateTestCaseName(String testName){
        Allure.getLifecycle().updateTestCase(result -> {
            result.setName(testName);
        });
    }
}
