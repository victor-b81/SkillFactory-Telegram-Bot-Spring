package ru.SkillFactorydemo.tgbot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.SkillFactorydemo.tgbot.entity.Spend;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest        // Аннотация JPA, используется для тестирования репозиториев JPA
class SpendRepositoryTest {

    @Autowired
    private SpendRepository spendRepository;

    @Test      // Аннотация JUnit, указываем на данный метод как тестовый
    public void testDataScripts(){             // Метод тестирования, добавления записей в таблицу SQL
        Optional<Spend> spend = spendRepository.findById(12345L);
        assertTrue(spend.isPresent());
        assertEquals(new BigDecimal("1000.00"), spend.get().getSpend());     // сверяем совпадает ли содержимое поля spend с ожиданиями
    }

    @Test      // Аннотация JUnit, указываем на данный метод как тестовый
    public void testRepo(){                       // Метод тестирования доступности репозитория
        for (int i = 0; i < 10; i++, spendRepository.save(new Spend())); // в цикле записываем в репозиторий income 11 записей
        final List<Spend> found = spendRepository.findAll();             // возвращаем в список все найденные записи из income репозитория
        assertEquals(11, found.size());                            // сверяем количество найденных записей с ожидаемым количеством
    }

}