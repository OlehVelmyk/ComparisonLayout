package com.google.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class GitHubProfileRepositoriesPage extends BasePage {
    private final SelenideElement repositoriesList = $("#user-private-profile-frame");

    public SelenideElement getRepositoriesList() {
        return repositoriesList;
    }
}