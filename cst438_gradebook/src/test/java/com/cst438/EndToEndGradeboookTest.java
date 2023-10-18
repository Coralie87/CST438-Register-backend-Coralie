package com.cst438;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

public class EndToEndGradeboookTest {

    public static final String CHROME_DRIVER_FILE_LOCATION = "C:\\Users\\coral\\OneDrive\\Bureau\\chromedriver-win64 (1)\\chromedriver-win64/chromedriver.exe";

    public static final String URL = "http://localhost:3000";

    public static final String TEST_USER_EMAIL = "test@csumb.edu";

    public static final int TEST_COURSE_ID = 40442;

    public static final String TEST_SEMESTER = "2021 Fall";

    public static final int SLEEP_DURATION = 1000; // 1 second.

    @Test
    public void deleteAssignmentTest() throws Exception {

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {
            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);

            driver.findElement(By.linkText("Admin")).click();
            Thread.sleep(SLEEP_DURATION);

            driver.findElement(By.xpath("//tr[1]//button[text()='Edit']")).click();
            Thread.sleep(SLEEP_DURATION);

            String assignmentName = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

            driver.findElement(By.xpath("//button[text()='Delete']")).click();
            Thread.sleep(SLEEP_DURATION);

            assertThrows(NoSuchElementException.class, () -> {
                driver.findElement(By.xpath("//td[text()='" + assignmentName + "']"));
            });

        } catch (Exception ex) {
            throw ex;
        } finally {
            driver.quit();
        }
    }

    @Test
    public void addAssignmentTest() throws Exception {

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {
            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);

            driver.findElement(By.linkText("Admin")).click();
            Thread.sleep(SLEEP_DURATION);

            WebElement nameField = driver.findElement(By.xpath("//input[@placeholder='Name']"));

            nameField.clear();
            nameField.sendKeys("AssignmentTest");
            driver.findElement(By.xpath("//button[text()='Add']")).click();
            Thread.sleep(SLEEP_DURATION);

            assertNotNull(driver.findElement(By.xpath("//td[text()='AssignmentTest']")));

        } catch (Exception ex) {
            throw ex;
        } finally {
            driver.quit();
        }
    }

    @Test
    public void updateAssignmentTest() throws Exception {

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {
            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);

            driver.findElement(By.linkText("Admin")).click();
            Thread.sleep(SLEEP_DURATION);

            driver.findElement(By.xpath("//tr[1]//button[text()='Edit']")).click();
            Thread.sleep(SLEEP_DURATION);

            WebElement nameField = driver.findElement(By.id("editname"));

            nameField.clear();
            nameField.sendKeys("UpdatedAssignment");
            driver.findElement(By.xpath("//button[text()='Update']")).click();
            Thread.sleep(SLEEP_DURATION);

            assertNotNull(driver.findElement(By.xpath("//td[text()='UpdatedAssignment']")));

        } catch (Exception ex) {
            throw ex;
        } finally {
            driver.quit();
        }
    }
}
