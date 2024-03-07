package com.google.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class GitHubProfileOverviewPage extends BasePage {
    private final SelenideElement repositoriesTab  = $(By.cssSelector(".user-profile-nav > nav:nth-child(1) > a:nth-child(2)"));
    GitHubProfileRepositoriesPage repositoriesPage = new GitHubProfileRepositoriesPage();

    public void clickRepositoriesTab() {
        actionClickElement("CLICK ON REPOSITORIES TAB", repositoriesTab);
        repositoriesPage.getRepositoriesList().shouldBe(appear);
        sleep(2000);
    }
}
