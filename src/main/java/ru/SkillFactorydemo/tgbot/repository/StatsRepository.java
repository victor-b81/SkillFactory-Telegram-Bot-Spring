/**
 * StatsRepository.java: интерфейс представляет собой «репозиторий», для работы с базой.
 * Данный интерфейс создан для реализации работы с базой данных
 * при запросе количества поступлений более указанной суммы "денег".
 * - Дополнительно реализован NamedParameterJdbcTemplate с передачей параметров внутри метода.
 * - getCountOfIncomesThatGreaterThan: метод возвращает количественное значение по средствам отправки SQL запроса.
 * - Реализован внутренний класс интерфейс приведения SQl запроса к виду Java
 */

package ru.SkillFactorydemo.tgbot.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository     // Аннотация Spring, указывает что интерфейс является репозиторием и предоставляет механизмы для хранения и поиска.
@RequiredArgsConstructor  //  Аннотация lombok, для генерирования конструктора.
public class StatsRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // Метод возвращает значение ответа SQl запроса
    public int getCountOfIncomesThatGreaterThan (Long amount){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("amount", amount);
        return namedParameterJdbcTemplate.queryForObject("SELECT count(*) FROM incomes WHERE INCOME > :amount", parameters, new StatsRowMapper());
    }

    // Реализация интерфейса, который приводит результат SQL запроса к виду Java
    private static final class StatsRowMapper implements RowMapper<Integer>{
        @Override
        public Integer mapRow(ResultSet resultset, int i) throws SQLException {
            return resultset.getInt("COUNT");
        }
    }
}
