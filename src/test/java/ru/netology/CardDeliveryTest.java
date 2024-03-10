package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    public String getDate(int cnt) {
        LocalDate targetDate = LocalDate.now().plusDays(cnt);
        String date = targetDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;

    }

    @Test
    void shouldSentRequest() {
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        String date = getDate(5);
        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Райан Гослинг");
        $("[data-test-id=phone] input").setValue("+79230000000");
        $("[data-test-id=agreement].checkbox").click();
        $("button.button ").click();
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(matchText(date));
    }

    @Test
    void shouldNotAdminCity() {
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        String date = getDate(5);
        $("[data-test-id=city] input").setValue("Юрга");
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Райан Гослинг");
        $("[data-test-id=phone] input").setValue("+79230000000");
        $("[data-test-id=agreement].checkbox").click();
        $("button.button ").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible, exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldOneName() {
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        String date = getDate(5);
        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Ёрайан Гослинг");
        $("[data-test-id=phone] input").setValue("+79230000000");
        $("[data-test-id=agreement].checkbox").click();
        $("button.button ").click();
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(matchText(date));
    }

    @Test
    void shouldNotValidName() {
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        String date = getDate(5);
        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Гослинг");
        $("[data-test-id=phone] input").setValue("+79230000000");
        $("[data-test-id=agreement].checkbox").click();
        $("button.button ").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible, exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotValidPhone() {
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        String date = getDate(5);
        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Райан Гослинг");
        $("[data-test-id=phone] input").setValue("89230000000");
        $("[data-test-id=agreement].checkbox").click();
        $("button.button ").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible, exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldEmptyCheckbox() {
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        String date = getDate(5);
        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Райан Гослинг");
        $("[data-test-id=phone] input").setValue("+79230000000");
        $("button.button ").click();
        $("[data-test-id=agreement].input_invalid").shouldBe(visible);
    }

    @Test
    void shouldNotValidData() {
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        String date = getDate(0);
        $("[data-test-id=city] input").setValue("Кемерово");
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Райан Гослинг");
        $("[data-test-id=phone] input").setValue("+79230000000");
        $("button.button ").click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(visible, exactText("Заказ на выбранную дату невозможен"));
    }
}
