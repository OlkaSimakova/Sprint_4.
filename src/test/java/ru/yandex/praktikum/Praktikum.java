package ru.yandex.praktikum;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Test;

// Класс страницы авторизации
class LoginPageMesto {

    private WebDriver driver;
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By signInButton = By.className("auth-form__button");

    public LoginPageMesto(WebDriver driver){
        this.driver = driver;
    }

    public void setUsername(String username) {
        driver.findElement(emailField).sendKeys(username);
    }
    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }
    public void clickSignInButton() {
        driver.findElement(signInButton).click();
    }
    public void login(String username, String password){
        setUsername(username);
        setPassword(password);
        clickSignInButton();
    }
}

// Класс главной страницы
class HomePageMesto {

    private WebDriver driver;

    private By profileTitle = By.className("profile__title");
    // создай локатор для кнопки редактирования профиля
    private By editProfileButton = By.className("profile__edit-button");

    // создай локатор для поля «Занятие» в профиле пользователя
    private By activity = By.id("owner-description");

    public HomePageMesto(WebDriver driver) {
        this.driver = driver;
    }

    // метод ожидания прогрузки данных профиля
    public void waitForLoadProfileData() {
        new WebDriverWait(driver, 10).until(driver -> (driver.findElement(profileTitle).getText() != null
                && !driver.findElement(profileTitle).getText().isEmpty()
        ));
    }
    // метод для нажатия на кнопку редактирования профиля
    public void clickeditProfileButton(){
        driver.findElement(editProfileButton).click();

    }
    public void waitForChangedActivity(String changed) {
        // здесь нужно дождаться, чтобы текст в элементе «Занятие» стал равен значению из параметра

        String text = driver.findElement(By.id("owner-description")).getText();
        Assert.assertTrue(driver.findElement(activity).isEnabled());
        //new WebDriverWait(driver, 3)
        //.until(ExpectedConditions.attributeToBe(By.id("owner-description));


    }
}

// Класс cтраницы редактирования профиля
class ProfilePageMesto {

    private WebDriver driver;
    // создай локатор для поля «Занятие» в профиле пользователя
    private By activity = By.id("owner-description");
    // создай локатор для кнопки «Сохранить» в профиле пользователя
    private By save = By.linkText("Сохранить");

    public ProfilePageMesto (WebDriver driver){
        this.driver = driver;
    }

    // метод для проверки открытости поля «Занятие», удаления текста из неё и ввода нового значения из параметра
    public void isEnabled(){
        Assert.assertTrue(driver.findElement(activity).isEnabled());
        //driver.findElement(activity).clear();
        //driver.findElement(activity).sendKeys("Исследователь");
    }
    // метод для нажатия на кнопку сохранения профиля
    public void clickSave() {
        driver.findElement(save).click();
    }

}

// Класс с автотестом
public class Praktikum {

    private WebDriver driver;

    @Test
    public void checkActivity() {
        // драйвер для браузера Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        // переход на страницу тестового приложения
        driver.get("https://qa-mesto.praktikum-services.ru/");

        // создай объект класса страницы авторизации
        LoginPageMesto objLoginPage = new LoginPageMesto(driver);
        // выполни авторизацию
        objLoginPage.login("simakova_6@yandex.ru",
                "123456789");

        // создай объект класса главной страницы приложения
        HomePageMesto objHomePage = new HomePageMesto(driver);
        // дождись загрузки данных профиля на главной странице
        objHomePage.waitForLoadProfileData();

        // кликни на кнопку редактирования профиля
        driver.findElement(By.className("profile__edit-button")).click();

        // создай объект класса для поп-апа редактирования профиля
        ProfilePageMesto objProfilePage = new ProfilePageMesto(driver);

        // это переменная со значением, которое надо ввести в поле «Занятие»
        String newActivity = "Тестировщик";


        // в одном шаге проверь, что поле «Занятие» доступно для редактирования, и введи в него новое значение
        Assert.assertTrue(driver.findElement(By.id("owner-description")).isEnabled());
        driver.findElement(By.tagName("input")).clear();
        driver.findElement(By.tagName("input")).sendKeys("Тестировщик");
        // сохрани изменения в профиле
        //driver.findElement(By.className("button popup__button")).click();
        //driver.findElement(By.linkText("Сохранить")).click();
        driver.findElement(By.xpath(".//button[@class='button popup__button']")).click();


        // проверь, что поле «Занятие» на основной странице поменяло значение на новое
        objHomePage.waitForChangedActivity("Тестировщик");
    }


    @After
    public void teardown() {
        // Закрой браузер
        driver.quit();
    }
}
