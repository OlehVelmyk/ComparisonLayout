package com.google.tests;

import com.google.actionHelpers.ComparisonLayout;
import com.google.pages.GitHubProfileOverviewPage;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.NORMAL;

public class N_0002_CheckLayoutRepositoriesProfilePageTest extends BaseTest {

    @Test
    @Description("This test attempts to check page layout. " + "Fails if any error happens." +
            "\n\nIf the expected screen is absent we save the current as an expected screenshot " +
            "to 'expectedScreen' folder")
    @Severity(NORMAL)
    @Owner("Oleh Velmyk")
    @Tag("NewUI")
    @Link(name = "Website", url = "https://github.com/OlehVelmyk")
    @Issue("AUTH-002")
    @TmsLink("TMS-002")
    @Epic("Web interface")
    @Feature("Essential features")
    @Story("Layout")
    public void checkLayoutRepositoriesProfilePage() {
        updateTestCaseName("Check Layout Repositories Profile Page");

        GitHubProfileOverviewPage page = new GitHubProfileOverviewPage();
        ComparisonLayout assertScreen = new ComparisonLayout();

        page.clickRepositoriesTab();
        assertScreen.assertLayout(getTestName(), getWidth(), getHeight(), getBrowserType());
    }
}
