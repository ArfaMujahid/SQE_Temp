package com.jtspringproject.JtSpringProject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class UserLoginTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        // Set up the WebDriver (in this example, using ChromeDriver)
        System.setProperty("webdriver.chrome.driver", "D:\\semester 5\\SQE\\E-commerce-project-springBoot-master2\\JtProject\\src\\test\\java\\com\\jtspringproject\\JtSpringProject\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        // Navigate to the login page
        driver.get("http://localhost:8080/");
    }

    @Test
    public void testUserLogin() {
        // Find the username and password input fields and the login button
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));

        // Enter valid credentials
        usernameInput.sendKeys("admin");
        passwordInput.sendKeys("123");

        // Click the login button
        loginButton.click();

        // Assuming you have a message element to verify successful login
        WebElement messageElement = driver.findElement(By.cssSelector("h3[style='color:red;']"));
        String actualMessage = messageElement.getText();

        // Assert that the login was successful
        assertEquals("Expected success message", "Expected success message", actualMessage);
    }

    @After
    public void tearDown() {
        // Close the browser after the test
        driver.quit();
    }
}
