package Selenium;

import com.google.common.collect.Lists;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.parse;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.*;

public class ControllerTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        final String chromeDriverPath = "/snap/chromium/1985/usr/lib/chromium-browser/chromedriver";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();

        driver.manage().window().setSize(new Dimension(1000, 1000));
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, SECONDS);
        wait = new WebDriverWait(driver,10);

        String URL = "http://localhost:8080/home";
        driver.get(URL);
    }

    @AfterClass
    public void finish() {
        driver.close();
    }

    @Test
    public void testLoginAndUpdateUser() throws InterruptedException {
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getTitle().contains("Вход и регистрация"));
        driver.findElement(By.xpath("//*[@name='user_login']")).sendKeys("doctor1");
        driver.findElement(By.xpath("//*[@name='user_psw']")).sendKeys("task2");
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Welcome, doctor1!"));
        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Вы успешно авторизованы!"));
        driver.findElement(By.xpath("//*[contains(text(), 'Личный кабинет') or @value='Личный кабинет']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Князев Владлен Изотович"));
        Assert.assertTrue(driver.getPageSource().contains("doctor1"));
        Assert.assertTrue(driver.getPageSource().contains("task2"));
        Assert.assertTrue(driver.getPageSource().contains("time7@gmail.com"));
        Assert.assertTrue(driver.getPageSource().contains("8 (566) 420-47-01"));
        driver.findElement(By.xpath("//*[@name='passwd']")).sendKeys("task1");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить пароль!') or @value='Обновить пароль!']")).click();
        Assert.assertFalse(driver.getPageSource().contains("task2"));
        Assert.assertTrue(driver.getPageSource().contains("task1"));
        driver.findElement(By.xpath("//*[@name='passwd']")).sendKeys("task2");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить пароль!') or @value='Обновить пароль!']")).click();

        driver.findElement(By.xpath("//*[@name='login']")).sendKeys("doctor2");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить логин!') or @value='Обновить логин!']")).click();
        Assert.assertFalse(driver.getPageSource().contains("doctor1"));
        Assert.assertTrue(driver.getPageSource().contains("doctor2"));
        driver.findElement(By.xpath("//*[@name='login']")).sendKeys("doctor1");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить логин!') or @value='Обновить логин!']")).click();

        driver.findElement(By.xpath("//*[@name='name']")).sendKeys("Иванов Иван");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить ФИО!') or @value='Обновить ФИО!']")).click();
        Assert.assertFalse(driver.getPageSource().contains("Князев Владлен Изотович"));
        Assert.assertTrue(driver.getPageSource().contains("Иванов Иван"));
        driver.findElement(By.xpath("//*[@name='name']")).sendKeys("Князев Владлен Изотович");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить ФИО!') or @value='Обновить ФИО!']")).click();

        driver.findElement(By.xpath("//*[@name='phone']")).sendKeys("55-55-55");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить телефон!') or @value='Обновить телефон!']")).click();
        Assert.assertFalse(driver.getPageSource().contains("8 (566) 420-47-01"));
        Assert.assertTrue(driver.getPageSource().contains("55-55-55"));
        driver.findElement(By.xpath("//*[@name='phone']")).sendKeys("8 (566) 420-47-01");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить телефон!') or @value='Обновить телефон!']")).click();

        driver.findElement(By.xpath("//*[@name='email']")).sendKeys("yy@mail.ru");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить email!') or @value='Обновить email!']")).click();
        Assert.assertFalse(driver.getPageSource().contains("time7@gmail.com"));
        Assert.assertTrue(driver.getPageSource().contains("yy@mail.ru"));
        driver.findElement(By.xpath("//*[@name='email']")).sendKeys("time7@gmail.com");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить email!') or @value='Обновить email!']")).click();

        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Выйти?') or @value='Выйти?']")).click();
    }

    @Test
    public void testLCreateUser() throws InterruptedException {
        driver.findElement(By.xpath("//*[contains(text(), 'Зарегистрироваться') or @value='Зарегистрироваться']")).click();
        Assert.assertTrue(driver.getTitle().contains("Вход и регистрация"));

        Random rand = new Random();
        String id = String.valueOf(rand.nextInt(999999));
        driver.findElement(By.xpath("//*[@name='login']")).sendKeys("test123" + id);
        driver.findElement(By.xpath("//*[@name='passwd']")).sendKeys("123h");
        driver.findElement(By.xpath("//*[@name='email']")).sendKeys("test123@mail.ru");
        driver.findElement(By.xpath("//*[@name='phone']")).sendKeys("123-123");
        driver.findElement(By.xpath("//*[@name='name']")).sendKeys("Тест Тестович Тестов");

        driver.findElement(By.xpath("//*[contains(text(), 'Зарегистрироваться') or @value='Зарегистрироваться']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Welcome, test123" + id + "!"));

        driver.findElement(By.xpath("//*[@name='user_login']")).sendKeys("test123" + id);
        driver.findElement(By.xpath("//*[@name='user_psw']")).sendKeys("123h");
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Welcome, test123" + id + "!"));

        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Вы успешно авторизованы!"));
        driver.findElement(By.xpath("//*[contains(text(), 'Личный кабинет') or @value='Личный кабинет']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Тест Тестович Тестов"));
        Assert.assertTrue(driver.getPageSource().contains("test123" + id));
        Assert.assertTrue(driver.getPageSource().contains("123h"));
        Assert.assertTrue(driver.getPageSource().contains("test123@mail.ru"));
        Assert.assertTrue(driver.getPageSource().contains("123-123"));

        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Выйти?') or @value='Выйти?']")).click();
    }

    @Test
    public void testLoginWrongPasswdUserNotFound() throws InterruptedException {
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getTitle().contains("Вход и регистрация"));
        driver.findElement(By.xpath("//*[@name='user_login']")).sendKeys("doctor1");
        driver.findElement(By.xpath("//*[@name='user_psw']")).sendKeys("task1");
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Wrong password!"));
        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Желаете войти в аккаунт?"));

        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getTitle().contains("Вход и регистрация"));
        driver.findElement(By.xpath("//*[@name='user_login']")).sendKeys("doctor2");
        driver.findElement(By.xpath("//*[@name='user_psw']")).sendKeys("task1");
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getPageSource().contains("User not found!"));
        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Желаете войти в аккаунт?"));
    }

    @Test
    public void testUnauthorisedSearchAndInfo() throws InterruptedException {
        driver.findElement(By.xpath("//*[@name='arr_city']")).sendKeys("Москва");
        driver.findElement(By.xpath("//*[@name='arr_st']")).sendKeys("Парк");
        driver.findElement(By.xpath("//*[contains(text(), 'Найти!') or @value='Найти!']")).click();

        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        List<WebElement> cols;
        Assert.assertEquals(rows.size(), 6);
        for (int i = 1; i < rows.size(); i++) {
            cols = rows.get(i).findElements(By.tagName("td"));
            Assert.assertEquals(cols.size(), 9);
            Assert.assertTrue(cols.get(5).getText().contains("Парк"));
            Assert.assertTrue(cols.get(6).getText().contains("Москва"));
        }
        driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(8) .btn")).click();
        Assert.assertTrue(driver.getPageSource().contains("Информация о маршруте"));
        rows = driver.findElements(By.tagName("tr"));
        Assert.assertEquals(rows.size(), 11);
        Assert.assertTrue(rows.get(1).findElements(By.tagName("td")).get(1).getText().contains("180"));
        Assert.assertTrue(rows.get(1).findElements(By.tagName("td")).get(2).getText().contains("РАО «Шубин»"));
        Assert.assertTrue(rows.get(3).findElements(By.tagName("td")).get(1).getText().contains("Университет"));
        Assert.assertTrue(rows.get(3).findElements(By.tagName("td")).get(2).getText().contains("Москва"));
        Assert.assertTrue(rows.get(5).findElements(By.tagName("td")).get(1).getText().contains("Парк"));
        Assert.assertTrue(rows.get(5).findElements(By.tagName("td")).get(2).getText().contains("Москва"));
        Assert.assertEquals(rows.get(7).findElements(By.tagName("td")).size(), 6);
        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();

        driver.findElement(By.xpath("//*[@name='arr_city']")).sendKeys("Москва");
        driver.findElement(By.xpath("//*[@name='arr_st']")).sendKeys("Парк");
        driver.findElement(By.xpath("//*[@name='dep_city']")).sendKeys("Москва");
        driver.findElement(By.xpath("//*[@name='dep_st']")).sendKeys("Аэропорт");
        driver.findElement(By.xpath("//*[contains(text(), 'Найти!') or @value='Найти!']")).click();

        rows = driver.findElements(By.tagName("tr"));
        Assert.assertEquals(rows.size(), 2);
        for (int i = 1; i < rows.size(); i++) {
            cols = rows.get(i).findElements(By.tagName("td"));
            Assert.assertEquals(cols.size(), 9);
            Assert.assertTrue(cols.get(5).getText().contains("Парк"));
            Assert.assertTrue(cols.get(6).getText().contains("Москва"));
            Assert.assertTrue(cols.get(3).getText().contains("Аэропорт"));
            Assert.assertTrue(cols.get(4).getText().contains("Москва"));
        }
        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();

        driver.findElement(By.id("arr_time_min")).sendKeys("01-01-002022T00:00");
        driver.findElement(By.id("arr_time_max")).sendKeys("01-01-002022T23:00");
        driver.findElement(By.xpath("//*[contains(text(), 'Найти!') or @value='Найти!']")).click();

        rows = driver.findElements(By.tagName("tr"));
        Assert.assertEquals(rows.size(), 2);
        driver.findElement(By.cssSelector(".btn:nth-child(3)")).click();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        rows = driver.findElements(By.tagName("tr"));
        Assert.assertEquals(rows.size(), 9);
        Assert.assertTrue(parse(rows.get(7).findElements(By.tagName("td")).get(4).getText(), formatter).isBefore(parse("2022-01-01T23:00", formatter)));
        Assert.assertTrue(parse(rows.get(7).findElements(By.tagName("td")).get(4).getText(), formatter).isAfter(parse("2022-01-01T00:00", formatter)));

        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();

        driver.findElement(By.xpath("//*[@name='dep_city']")).sendKeys("Саратов");
        driver.findElement(By.xpath("//*[@name='dep_st']")).sendKeys("Парк");
        driver.findElement(By.id("arr_time_min")).sendKeys("01-01-002022T00:00");
        driver.findElement(By.id("arr_time_max")).sendKeys("01-01-002022T23:00");

        driver.findElement(By.xpath("//*[contains(text(), 'Найти!') or @value='Найти!']")).click();

        rows = driver.findElements(By.tagName("tr"));
        Assert.assertEquals(rows.size(), 2);

        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();
    }

    @Test
    public void testCreateAndChangeRoute() throws InterruptedException {
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getTitle().contains("Вход и регистрация"));
        driver.findElement(By.xpath("//*[@name='user_login']")).sendKeys("join4");
        driver.findElement(By.xpath("//*[@name='user_psw']")).sendKeys("newspaper");
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Welcome, join4!"));
        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();

        driver.findElement(By.xpath("//*[contains(text(), 'Панель администратора') or @value='Панель администратора']")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Создать новый маршрут') or @value='Создать новый маршрут']")).click();

        driver.getPageSource().contains("This station and route added for example! Please delete it later!");

        driver.findElement(By.id("route_name")).sendKeys("777");
        driver.findElement(By.id("ch_route")).click();
        driver.findElement(By.id("company_name")).sendKeys("Тестовый перевозчик");
        driver.findElement(By.id("ch_comp")).click();
        driver.findElement(By.id("st_id")).sendKeys("4");
        driver.findElement(By.id("st_ind")).sendKeys("2");
        driver.findElement(By.id("dep_time_min")).sendKeys("01-01-002022T00:00");
        driver.findElement(By.id("arr_time_min")).sendKeys("01-01-002022T00:00");
        driver.findElement(By.cssSelector("input:nth-child(15)")).click();
        driver.findElement(By.id("add")).click();
        driver.findElement(By.id("st_ind")).sendKeys("1");
        driver.findElement(By.id("st_city")).sendKeys("Москва");
        driver.findElement(By.id("st_name")).sendKeys("Кладбище");
        driver.findElement(By.id("dep_time_min")).sendKeys("01-01-002022T00:00");
        driver.findElement(By.id("arr_time_min")).sendKeys("01-01-002022T00:00");
        driver.findElement(By.cssSelector("input:nth-child(13)")).click();
        driver.findElement(By.id("add")).click();
        driver.findElement(By.id("st_ind")).sendKeys("3");
        driver.findElement(By.id("st_id")).sendKeys("2");
        driver.findElement(By.id("dep_time_min")).sendKeys("01-01-002022T00:00");
        driver.findElement(By.id("arr_time_min")).sendKeys("01-01-002022T00:00");
        driver.findElement(By.cssSelector("input:nth-child(14)")).click();
        driver.findElement(By.id("add")).click();

        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        Assert.assertEquals(rows.size(), 10);
        Assert.assertTrue(rows.get(1).findElements(By.tagName("td")).get(1).getText().contains("777"));
        Assert.assertTrue(rows.get(1).findElements(By.tagName("td")).get(2).getText().contains("Тестовый перевозчик"));
        Assert.assertTrue(rows.get(3).findElements(By.tagName("td")).get(1).getText().contains("Кладбище"));
        Assert.assertTrue(rows.get(3).findElements(By.tagName("td")).get(2).getText().contains("Москва"));
        Assert.assertTrue(rows.get(5).findElements(By.tagName("td")).get(1).getText().contains("Вокзал"));
        Assert.assertTrue(rows.get(5).findElements(By.tagName("td")).get(2).getText().contains("Москва"));
        Assert.assertEquals(rows.get(7).findElements(By.tagName("td")).size(), 6);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        Assert.assertTrue(parse(rows.get(7).findElements(By.tagName("td")).get(4).getText(), formatter).equals(parse("2022-01-01T00:00", formatter)));
        Assert.assertTrue(parse(rows.get(8).findElements(By.tagName("td")).get(4).getText(), formatter).equals(parse("2022-01-01T00:00", formatter)));
        Assert.assertTrue(parse(rows.get(9).findElements(By.tagName("td")).get(4).getText(), formatter).equals(parse("2022-01-01T00:00", formatter)));
        Assert.assertEquals(rows.get(7).findElements(By.tagName("td")).get(0).getText(), "2");
        Assert.assertEquals(rows.get(8).findElements(By.tagName("td")).get(0).getText(), "1");
        Assert.assertEquals(rows.get(9).findElements(By.tagName("td")).get(0).getText(), "3");
        Assert.assertEquals(rows.get(7).findElements(By.tagName("td")).get(2).getText(), "Университет");
        Assert.assertEquals(rows.get(8).findElements(By.tagName("td")).get(2).getText(), "Кладбище");
        Assert.assertEquals(rows.get(9).findElements(By.tagName("td")).get(2).getText(), "Вокзал");
        Assert.assertEquals(rows.get(7).findElements(By.tagName("td")).get(3).getText(), "Москва");
        Assert.assertEquals(rows.get(8).findElements(By.tagName("td")).get(3).getText(), "Москва");
        Assert.assertEquals(rows.get(9).findElements(By.tagName("td")).get(3).getText(), "Москва");

        driver.findElement(By.id("st_index")).sendKeys("1");
        driver.findElement(By.id("del")).click();
        driver.findElement(By.id("st_index")).sendKeys("2");
        driver.findElement(By.id("del")).click();
        driver.findElement(By.id("st_index")).sendKeys("3");
        driver.findElement(By.id("del")).click();

        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Выйти?') or @value='Выйти?']")).click();
    }

    @Test
    public void testShowPassangers() throws InterruptedException {
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getTitle().contains("Вход и регистрация"));
        driver.findElement(By.xpath("//*[@name='user_login']")).sendKeys("join4");
        driver.findElement(By.xpath("//*[@name='user_psw']")).sendKeys("newspaper");
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Welcome, join4!"));
        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();

        driver.findElement(By.xpath("//*[contains(text(), 'Панель администратора') or @value='Панель администратора']")).click();
        driver.findElement(By.xpath("//*[@id='route_id_1']")).sendKeys("2");
        driver.findElement(By.xpath("//*[contains(text(), 'Показать пассажиров рейса') or @value='Показать пассажиров рейса']")).click();

        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        Assert.assertEquals(rows.size(), 3);
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).size(), 9);
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(0).getText(), "2");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(1).getText(), "180");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(2).getText(), "Филипп Фролович Куликов");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(3).getText(), "+79958566336");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(4).getText(), "design2@gmail.com");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(5).getText(), "Аэропорт");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(6).getText(), "Парк");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(7).getText(), "1");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(8).getText(), "50.0");

        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(0).getText(), "3");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(1).getText(), "180");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(2).getText(), "Князев Владлен Изотович");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(3).getText(), "8 (566) 420-47-01");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(4).getText(), "time7@gmail.com");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(5).getText(), "Аэропорт");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(6).getText(), "Парк");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(7).getText(), "99");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(8).getText(), "100.0");

        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Выйти?') or @value='Выйти?']")).click();
    }

    @Test
    public void testLoginAndEditUsersByAdmin() throws InterruptedException {
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getTitle().contains("Вход и регистрация"));
        driver.findElement(By.xpath("//*[@name='user_login']")).sendKeys("join4");
        driver.findElement(By.xpath("//*[@name='user_psw']")).sendKeys("newspaper");
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Welcome, join4!"));
        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();

        driver.findElement(By.xpath("//*[contains(text(), 'Панель администратора') or @value='Панель администратора']")).click();
        driver.findElement(By.xpath("//*[@id='user_id_2']")).sendKeys("2");
        driver.findElement(By.xpath("//*[contains(text(), 'Войти в аккаунт пользователя и редактировать его данные') or @value='Войти в аккаунт пользователя и редактировать его данные']")).click();

        Assert.assertTrue(driver.getPageSource().contains("Князев Владлен Изотович"));
        Assert.assertTrue(driver.getPageSource().contains("doctor1"));
        Assert.assertTrue(driver.getPageSource().contains("task2"));
        Assert.assertTrue(driver.getPageSource().contains("time7@gmail.com"));
        Assert.assertTrue(driver.getPageSource().contains("8 (566) 420-47-01"));

        driver.findElement(By.xpath("//*[@name='passwd']")).sendKeys("task1");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить пароль!') or @value='Обновить пароль!']")).click();
        Assert.assertFalse(driver.getPageSource().contains("task2"));
        Assert.assertTrue(driver.getPageSource().contains("task1"));
        driver.findElement(By.xpath("//*[@name='passwd']")).sendKeys("task2");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить пароль!') or @value='Обновить пароль!']")).click();

        driver.findElement(By.xpath("//*[@name='login']")).sendKeys("doctor2");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить логин!') or @value='Обновить логин!']")).click();
        Assert.assertFalse(driver.getPageSource().contains("doctor1"));
        Assert.assertTrue(driver.getPageSource().contains("doctor2"));
        driver.findElement(By.xpath("//*[@name='login']")).sendKeys("doctor1");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить логин!') or @value='Обновить логин!']")).click();

        driver.findElement(By.xpath("//*[@name='name']")).sendKeys("Иванов Иван");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить ФИО!') or @value='Обновить ФИО!']")).click();
        Assert.assertFalse(driver.getPageSource().contains("Князев Владлен Изотович"));
        Assert.assertTrue(driver.getPageSource().contains("Иванов Иван"));
        driver.findElement(By.xpath("//*[@name='name']")).sendKeys("Князев Владлен Изотович");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить ФИО!') or @value='Обновить ФИО!']")).click();

        driver.findElement(By.xpath("//*[@name='phone']")).sendKeys("55-55-55");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить телефон!') or @value='Обновить телефон!']")).click();
        Assert.assertFalse(driver.getPageSource().contains("8 (566) 420-47-01"));
        Assert.assertTrue(driver.getPageSource().contains("55-55-55"));
        driver.findElement(By.xpath("//*[@name='phone']")).sendKeys("8 (566) 420-47-01");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить телефон!') or @value='Обновить телефон!']")).click();

        driver.findElement(By.xpath("//*[@name='email']")).sendKeys("yy@mail.ru");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить email!') or @value='Обновить email!']")).click();
        Assert.assertFalse(driver.getPageSource().contains("time7@gmail.com"));
        Assert.assertTrue(driver.getPageSource().contains("yy@mail.ru"));
        driver.findElement(By.xpath("//*[@name='email']")).sendKeys("time7@gmail.com");
        driver.findElement(By.xpath("//*[contains(text(), 'Обновить email!') or @value='Обновить email!']")).click();

        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();

        // тестируем то что мы именно зашли в аккаунт пользователя, а не остались на админском
        Assert.assertFalse(driver.getPageSource().contains("Панель администратора"));
        driver.findElement(By.xpath("//*[contains(text(), 'Выйти?') or @value='Выйти?']")).click();
    }

    @Test
    public void testBuyTicketAndShowHistory() throws InterruptedException {
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getTitle().contains("Вход и регистрация"));
        driver.findElement(By.xpath("//*[@name='user_login']")).sendKeys("doctor1");
        driver.findElement(By.xpath("//*[@name='user_psw']")).sendKeys("task2");
        driver.findElement(By.xpath("//*[contains(text(), 'Войти') or @value='Войти']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Welcome, doctor1!"));
        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Вы успешно авторизованы!"));
        driver.findElement(By.xpath("//*[contains(text(), 'Заказы') or @value='Заказы']")).click();

        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        Long last = Long.valueOf(rows.size());
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).size(), 7);
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(0).getText(), "3");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(1).getText(), "180");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(2).getText(), "Князев Владлен Изотович");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(3).getText(), "Аэропорт");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(4).getText(), "Парк");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(5).getText(), "99");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(6).getText(), "100.0");

        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(0).getText(), "4");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(1).getText(), "110M");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(2).getText(), "Князев Владлен Изотович");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(3).getText(), "Ул. Революционная");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(4).getText(), "Парк");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(5).getText(), "5");
        Assert.assertEquals(rows.get(2).findElements(By.tagName("td")).get(6).getText(), "100.0");

        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();

        driver.findElement(By.xpath("//*[@name='arr_city']")).sendKeys("Москва");
        driver.findElement(By.xpath("//*[@name='arr_st']")).sendKeys("Парк");
        driver.findElement(By.xpath("//*[contains(text(), 'Найти!') or @value='Найти!']")).click();

        driver.findElement(By.cssSelector("tr:nth-child(3) #place")).sendKeys("7");
        driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(9) .btn")).click();

        rows = driver.findElements(By.tagName("tr"));
        String id = rows.get(1).findElements(By.tagName("td")).get(0).getText();
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(1).getText(), "180");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(2).getText(), "Князев Владлен Изотович");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(3).getText(), "Университет");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(4).getText(), "Парк");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(5).getText(), "7");
        Assert.assertEquals(rows.get(1).findElements(By.tagName("td")).get(6).getText(), "100.0");

        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();

        Assert.assertTrue(driver.getPageSource().contains("Вы успешно авторизованы!"));
        driver.findElement(By.xpath("//*[contains(text(), 'Заказы') or @value='Заказы']")).click();

        rows = driver.findElements(By.tagName("tr"));
        Assert.assertTrue(Long.valueOf(rows.size()) == last + 1);

        Assert.assertEquals(rows.get(rows.size() - 1).findElements(By.tagName("td")).get(0).getText(), id);
        Assert.assertEquals(rows.get(rows.size() - 1).findElements(By.tagName("td")).get(1).getText(), "180");
        Assert.assertEquals(rows.get(rows.size() - 1).findElements(By.tagName("td")).get(2).getText(), "Князев Владлен Изотович");
        Assert.assertEquals(rows.get(rows.size() - 1).findElements(By.tagName("td")).get(3).getText(), "Университет");
        Assert.assertEquals(rows.get(rows.size() - 1).findElements(By.tagName("td")).get(4).getText(), "Парк");
        Assert.assertEquals(rows.get(rows.size() - 1).findElements(By.tagName("td")).get(5).getText(), "7");
        Assert.assertEquals(rows.get(rows.size() - 1).findElements(By.tagName("td")).get(6).getText(), "100.0");

        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Выйти?') or @value='Выйти?']")).click();
    }

    @Test
    public void testBuyTicketUnauthorized() throws InterruptedException {
        driver.findElement(By.xpath("//*[@name='arr_city']")).sendKeys("Москва");
        driver.findElement(By.xpath("//*[@name='arr_st']")).sendKeys("Парк");
        driver.findElement(By.xpath("//*[contains(text(), 'Найти!') or @value='Найти!']")).click();

        driver.findElement(By.cssSelector("tr:nth-child(3) #place")).sendKeys("7");
        driver.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(9) .btn")).click();

        Assert.assertTrue(driver.getPageSource().contains("Пожалуйста авторизуйтесь!"));
        driver.findElement(By.xpath("//*[contains(text(), 'Домой') or @value='Домой']")).click();
    }
}
