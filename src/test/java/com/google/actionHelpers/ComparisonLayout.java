package com.google.actionHelpers;

import com.codeborne.selenide.Selenide;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import com.google.dataProvider.DateProvider;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.Assert;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ComparisonLayout {

    public void assertLayout(String testName, String width, String height, String browserName) {

        String expectedFileName = testName + "_" + width + "_" + height + ".png";
        String expectedScreensFolder = "src/test/resources/expectedScreens/" + browserName+ "/";

        File actualScreenShot = Selenide.screenshot(OutputType.FILE);
        File expectedScreenshot = new File(expectedScreensFolder + expectedFileName);

        if (!expectedScreenshot.exists()) {

            if (actualScreenShot != null) {
                actualScreenShot.renameTo(expectedScreenshot);
            }

            try {
                Allure.addAttachment("EXPECTED SCREENSHOT", FileUtils.openInputStream(expectedScreenshot));
            } catch (IOException e) {
                throw new RuntimeException("Can't added an expected screenshot");
            }

            Allure.step("EXPECTED SCREENSHOT WAS ABSENT TO COMPARING. CURRENT SCREENSHOT WAS ADDED TO " + "'" +
                    "expectedScreens/" + browserName + "/" + "'" + " FOLDER. IF YOU WANT TO COMPARE LAYOUT THEN RERUN TEST");
        } else {

            ImageComparisonResult result = getImageComparisonResult(expectedScreensFolder, expectedFileName, testName,
                                                                    actualScreenShot, expectedScreenshot, width,
                                                                    height, browserName);

            Assert.assertEquals(result.getImageComparisonState(), ImageComparisonState.MATCH);
        }
    }

    private static ImageComparisonResult getImageComparisonResult(String expectedScreensFolder, String expectedFileName,
                                                                  String testName, File actualScreenShot,
                                                                  File expectedScreenshot, String width, String height,
                                                                  String browserName) {

        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expectedScreensFolder + expectedFileName);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualScreenShot.toPath().toString());

        File diffImage = new File("reports/screenshots/diffs/" + browserName + "/" + testName + "_" + width
                         + "_" + height + "_" + DateProvider.currentDate() + "_" + DateProvider.currentTime() + ".png");

        ImageComparison imageComparison = new ImageComparison(expectedImage, actualImage, diffImage);
        ImageComparisonResult result = imageComparison.compareImages();

        if (!result.getImageComparisonState().equals(ImageComparisonState.MATCH)) {
            Allure.step("LAYOUT ISN'T MATCHED");
            try {
                Allure.attachment("ACTUAL SCREEN", FileUtils.openInputStream(actualScreenShot));
                Allure.addAttachment("EXPECTED SCREEN", FileUtils.openInputStream(expectedScreenshot));
                Allure.addAttachment("DIFF", FileUtils.openInputStream(diffImage));
            } catch (IOException e) {
                throw new RuntimeException("Can't added actual, expected or diff screens");
            }
        } else {
            Allure.step("LAYOUT IS MATCHED");
        }
        return result;
    }
}