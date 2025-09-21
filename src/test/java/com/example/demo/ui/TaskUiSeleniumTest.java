package com.example.demo.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskUiSeleniumTest {
    @LocalServerPort int port;
    private WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new", "--no-sandbox", "--disable-gpu");
        driver = new ChromeDriver(opts);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    void tearDown() { if (driver != null) driver.quit(); }

    @Test
    void addTaskViaUi() {
        String base = "http://localhost:" + port + "/";
        driver.get(base);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("addBtn")));

        WebElement input = driver.findElement(By.id("taskInput"));
        input.sendKeys("Selenium Task");
        WebElement btn = driver.findElement(By.id("addBtn"));
        btn.click();

        // Wait until the task appears in the tasks list
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("tasksList"), "Selenium Task"));
        WebElement tasksList = driver.findElement(By.id("tasksList"));
        assertTrue(tasksList.getText().contains("Selenium Task"));
    }
}
