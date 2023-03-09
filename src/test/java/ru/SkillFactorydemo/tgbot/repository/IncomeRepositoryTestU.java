/**
 * Класс-тест для тестирования IncomeRepositoryTest
 *
 */

package ru.SkillFactorydemo.tgbot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.SkillFactorydemo.tgbot.entity.Income;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest        // Аннотация JPA, используется для тестирования репозиториев JPA
class IncomeRepositoryTestU {

    @Autowired
    private IncomeRepository incomeRepository;

    @Test      // Аннотация JUnit, указываем на данный метод как тестовый
    public void testDataScripts() {             // Метод тестирования, добавления записей в таблицу SQL
        Optional<Income> income = incomeRepository.findById(12345L);      // ищем ид чата 12345 в репозитории и если ид существует, то возвращаем его значение в объект activeChatById
        assertTrue(income.isPresent());         // проверяем, существует ли объект activeChatById
        assertEquals(new BigDecimal("3000.00"), income.get().getIncome());     // сверяем совпадает ли содержимое поля income с ожиданиями
    }

    @Test      // Аннотация JUnit, указываем на данный метод как тестовый
    public void testRepo(){                       // Метод тестирования доступности репозитория
        for (int i = 0; i < 10; i++, incomeRepository.save(new Income())); // в цикле записываем в репозиторий income 11 записей
        final List<Income> found = incomeRepository.findAll();             // возвращаем в список все найденные записи из income репозитория
        assertEquals(11, found.size());                            // сверяем количество найденных записей с ожидаемым количеством
    }
}