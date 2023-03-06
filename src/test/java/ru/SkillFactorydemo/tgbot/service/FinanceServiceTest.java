/**
 * Класс-тест для тестирования FinanceService
 */

package ru.SkillFactorydemo.tgbot.service;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.SkillFactorydemo.tgbot.repository.IncomeRepository;
import ru.SkillFactorydemo.tgbot.repository.SpendRepository;

@SpringBootTest     // Аннотация SpringBoot, указываем, что это тестовый класс
@TestInstance(TestInstance.Lifecycle.PER_CLASS)     // Аннотация JUnit, указывает, что инстанс теста создаётся на весь класс
class FinanceServiceTest {

    @InjectMocks        // Аннотация Mockito, имитирует сервис, создает экземпляр класса и внедряет макеты.
    private FinanceService financeService;

    @Mock
    private SpendRepository spendRepository;    // Аннотация Mockito, задаем имитацию репозитория

    @Mock
    private IncomeRepository incomeRepository;    // Аннотация Mockito, задаем имитацию репозитория

    @BeforeEach     // Аннотация JUnit, выполнение перед тестами.
    public void beforeAll() {
        System.out.println(System.currentTimeMillis());   // запишем время, когда начался каждый тест
    }

    @AfterEach     // Аннотация JUnit, выполнение после тестов.
    public void afterEach() {
        System.out.println(System.currentTimeMillis());    // запишем время, когда закончился каждый тест
    }

    // Тестовый метод, первого кейса
    @DisplayName("ADD_INCOME_test")     // Аннотация JUnit, задаем имя для тестового метода.
    @Test                               // Аннотация JUnit, указываем на данный метод как тестовый
    public void addFinanceOperationAddIncomeTest() {
        String price = "150.0";         // установили произвольное значение переменной
        String message = financeService.addFinanceOperation("/addincomes", price, 500L);    // обращаемся к методу с произвольными параметрами
        Assertions.assertEquals("Доход в размере " + price + " был успешно добавлен", message);       // убеждаемся, что получили ожидаемый результат
    }

    // Тестовый метод, второго кейса, с другими параметрами
    @DisplayName("non_ADD_INCOME_test")     // Аннотация JUnit, задаем имя для тестового метода.
    @Test                                   // Аннотация JUnit, указываем на данный метод как тестовый
    public void addFinanceOperationElseBranchTest() {
        String price = "200";         // установили произвольное значение переменной
        String message = financeService.addFinanceOperation("/nan", price, 250L);    // обращаемся к методу с произвольными параметрами
        Assertions.assertEquals("Расход в размере " + price + " был успешно добавлен", message);  // убеждаемся, что получили ожидаемый результат
    }
}