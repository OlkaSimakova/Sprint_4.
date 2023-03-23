package YandexScooterPageTest;

import YandexScooterPage.FormForOrder;
import YandexScooterPage.MainPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ScooterOrderFormTest extends DriverForTest {
    private String name;
    private String surname;
    private String address;
    private String phone;
    private String commentField;

    public ScooterOrderFormTest( String name, String surname, String address,
                                String phone, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.commentField = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getDataForForm() {
        return new Object[][]{
                {"Ольга", "Симакова", "Москва", "89153456789", "Первый заказ"},
                {"Елена", "Еремина", "Ярославль", "89785642345", "Второй заказ"},
                       };
    }

    @Test
    // Тест для кнопки "Заказать" вверху страницы
    public void scooterButtonOrderOne () {
        MainPage mainPage = new MainPage(driver);
        mainPage.checkCookeIsDisplayed();
        mainPage.scooterButtonOrderOne();
        FormForOrder formForOrder = new FormForOrder(driver);
        formForOrder.filling(name, surname, address,
                phone, commentField);
    }

    @Test
    // Тест для кнопки "Заказать" внизу траницы
    public void scooterButtonOrderTwo() {
        MainPage mainPage = new MainPage(driver);
        mainPage.checkCookeIsDisplayed();
        mainPage.scooterButtonOrderTwo();
        FormForOrder formForOrder = new FormForOrder(driver);
        formForOrder.filling(name, surname, address,
                phone, commentField);
    }
}