package junit;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import java.util.stream.Stream;




public class SimpleTest {



    @CsvSource(value = {"annarain/Qwerty123#" }
            ,delimiter = '/')
    @ParameterizedTest(name = "Авторизация {0}{1}")
    void loginTools(String testData, String password) {
//        Предусловия:
        Selenide.open("https://demoqa.com/login");
//        Шаги:
        $("#userName").setValue(testData);
        $("#password").setValue(password);
        $("#login").click();
//        Ожидаемый результат:
        $$("#userName-value")
                .find(Condition.text(testData));
    }

    @ValueSource(strings = {"Anna"})
    @ParameterizedTest(name = "Заполнение формы {0}")
    void loginTextBox(String testData) {
//        Предусловия:
        Selenide.open("https://demoqa.com/text-box");
//        Шаги:
        $("#userName").setValue(testData);
        $("#userEmail").setValue(testData + "@mail.ru");
        $("#currentAddress").setValue("Address test111go1");
        $("#permanentAddress").setValue("Address 2");
        $("#submit").click();

//        Ожидаемый результат:

        $("[class='border col-md-12 col-sm-12']").shouldHave(
                text("Anna"),
                text("anna@mail.ru"),
                text("Address test"),
                text ("Address 2"));
    }

    static Stream<Arguments> checkMethod() {
        return Stream.of(
                Arguments.of("annarain" ,"Qwerty123#")

        );
    }



    @MethodSource(value = "checkMethod")
    @ParameterizedTest(name = "Проверка авторизации")
    void validationAuto (String login, String password2){
        Selenide.open("https://demoqa.com/login");
        $("#userName").setValue(login);
        $("#password").setValue(password2);
        $("#login").click();

        $$("#userName-value")
                .find(Condition.text(login));

    }



    @Disabled("testqa-1177")
    @DisplayName("Нерабочий тест")
    @Test
    void firstTest() {
       Selenide.open("http://the-internet.herokuapp.com/login");
       $("username").setValue("Anna");
       $("password").setValue("qwerty123");
       $x("//*[@class=\"radius\"]").click();

    }

    @AfterEach
    void close() {
        Selenide.closeWebDriver();
    }


}

