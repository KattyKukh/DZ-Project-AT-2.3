package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
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
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSubmitValidData() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79998887766");
        driver.findElement(By.cssSelector("[data-test-id = agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id = order-success]")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldAlertForValidateInputInFieldName() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Ivan Ivanov");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79998887766");
        driver.findElement(By.cssSelector("[data-test-id = agreement]")).click();
        driver.findElement(By.className("button")).click();
        boolean actual = driver.findElement(By.cssSelector("[data-test-id = name].input_invalid .input__sub")).isDisplayed();
        assertTrue(actual);
    }

    @Test
    void shouldTextAlertForValidateInputName() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Ivan Ivanov");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79998887766");
        driver.findElement(By.cssSelector("[data-test-id = agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id = name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTextAlertForEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).clear();
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79998887766");
        driver.findElement(By.cssSelector("[data-test-id = agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id = name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldAlertForValidateInputInFieldPhone() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+799988877");
        driver.findElement(By.cssSelector("[data-test-id = agreement]")).click();
        driver.findElement(By.className("button")).click();
        boolean actual = driver.findElement(By.cssSelector("[data-test-id = phone].input_invalid .input__sub")).isDisplayed();
        assertTrue(actual);
    }

    @Test
    void shouldTextAlertForValidateInputPhone() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+7999#887766");
        driver.findElement(By.cssSelector("[data-test-id = agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id = phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTextAlertForEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).clear();
        driver.findElement(By.cssSelector("[data-test-id = agreement]")).click();
        driver.findElement(By.className("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id = phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldAlertForValidateCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+79998887766");
        driver.findElement(By.className("button")).click();
        boolean actual = driver.findElement(By.cssSelector("[data-test-id = agreement].input_invalid .checkbox__text")).isDisplayed();
        assertTrue(actual);
    }
}
