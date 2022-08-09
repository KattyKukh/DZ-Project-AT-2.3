package ru.netology.web;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class OrderCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "/driver/linux/chromedriver.exe");
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
    @Test
    void shouldOpenSite() {
        driver.get("http://localhost:9999/");
    }
    @Test
    void shouldSubmitValidData() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79998887766");
        driver.findElement(By.cssSelector("[data-test-id = argeement]")).click();
        driver.findElement(By.className("button")).click();
        boolean actual = driver.findElement(By.cssSelector("[data-test-id = order-success]")).getText().contains("Ваша заявка успешно отправлена");
        assertTrue(actual);
    }

    @Test
    void shouldColorAlertForValidateInputInFieldName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Ivan Ivanov");
        driver.findElement(By.className("button")).click();
        String expected = "rgba(255, 92, 92, 1) !important";
        String actual = driver.findElement(By.cssSelector(".input_invalid [data-test-id = name] ")).getCssValue("color");
        assertEquals(expected, actual);
    }

    @Test
    void shouldTextAlertForValidateInputName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Ivan Ivanov");
        driver.findElement(By.className("button")).click();
        boolean actual = driver.findElement(By.cssSelector("[data-test-id = name] .input__sub")).getText().contains("неверно");
        assertTrue(actual);
    }

    @Test
    void shouldTextAlertForEmptyName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).clear();
        driver.findElement(By.className("button")).click();
        boolean actual = driver.findElement(By.cssSelector("[data-test-id = name] .input__sub")).getText().contains("Поле обязательно для заполнения");
        assertTrue(actual);
    }

    @Test
    void shouldColorAlertForValidateInputInFieldPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+799988877");
        driver.findElement(By.className("button")).click();
        String expected = "rgba(255, 92, 92, 1) !important";
        String actual = driver.findElement(By.cssSelector(".input_invalid [data-test-id = phone]")).getCssValue("color");
        assertEquals(expected, actual);
    }

    @Test
    void shouldTextAlertForValidateInputPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+799988877");
        driver.findElement(By.className("button")).click();
        boolean actual = driver.findElement(By.cssSelector("[data-test-id = phone] .input__sub")).getText().contains("неверно");
        assertTrue(actual);
    }

    @Test
    void shouldTextAlertForEmptyPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).clear();
        driver.findElement(By.className("button")).click();
        boolean actual = driver.findElement(By.cssSelector("[data-test-id = phone] .input__sub")).getText().contains("Поле обязательно для заполнения");
        assertTrue(actual);
    }

    @Test
    void shouldValidateCheckBox() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79998887766");
        driver.findElement(By.cssSelector("[data-test-id = argeement]")).clear();
        driver.findElement(By.className("button")).click();
        String expected = "rgba(255, 92, 92, 1) !important";
        String actual = driver.findElement(By.cssSelector(".input_invalid [data-test-id = argeement]")).getCssValue("color");
        assertEquals(expected, actual);
    }
}
