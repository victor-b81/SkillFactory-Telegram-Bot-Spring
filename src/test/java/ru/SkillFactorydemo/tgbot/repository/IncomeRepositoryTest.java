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
class IncomeRepositoryTest {

    @Autowired
    private IncomeRepository incomeRepository;

    @Test      // Аннотация JUnit, указываем на данный метод как тестовый
    public void testDataScripts() {             // Метод тестирования, добавления записей в таблицу SQL
        Optional<Income> income = incomeRepository.findById(12345L);
        assertTrue(income.isPresent());
        assertEquals(new BigDecimal("3000.00"), income.get().getIncome());
    }

    @Test      // Аннотация JUnit, указываем на данный метод как тестовый
    public void testRepo(){
        for (int i = 0; i < 10; i++, incomeRepository.save(new Income()));
        final List<Income> found = incomeRepository.findAll();
        assertEquals(11, found.size());
    }
}