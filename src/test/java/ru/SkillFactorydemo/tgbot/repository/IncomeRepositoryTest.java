/**
 * Класс-тест для тестирования IncomeRepositoryTest
 *
 */

package ru.SkillFactorydemo.tgbot.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.SkillFactorydemo.tgbot.config.TestJpaConfig;
import ru.SkillFactorydemo.tgbot.entity.Income;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

// указываем, какой файл конфигурации подключать, какой лоадер использовать, включаем транзакционность
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { TestJpaConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class IncomeRepositoryTest {

    // некий dao-сервис с нашим объектом, просто абстрактно, для примера
    @Resource
    private IncomeRepository incomeRepository;

    @Test
    public void givenObj_whenSave_thenGetOk() {
        // создали объект, чем-то его заполнили
        Income income = new Income();
        income.setId(1111L);
        // сохранили в базу
        incomeRepository.save(income);
        // нашли объект в базе
        Optional<Income> incomeFound = incomeRepository.findById(1111L);
        assertTrue(incomeFound.isPresent());
        // проверили, что это действительно он
        //assertEquals(new BigDecimal("1111.00"), incomeFound.get().getId());
    }
}