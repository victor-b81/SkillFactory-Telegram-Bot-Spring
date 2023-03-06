/**
 * StatService.java: сервисный класс предназначенный для обработки поступающей команды "/getStats".
 *    getCountOfIncomesThatGreater() - метод реализующий функционал данного сервиса. Возращающий значение обработки БД.
 */

package ru.SkillFactorydemo.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.SkillFactorydemo.tgbot.repository.StatsRepository;

@Service    // Аннотация Spring, указывается что данный класс является сервисным, поставщика услуг.
@RequiredArgsConstructor  // Аннотация lombok, для генерирования конструктора.
public class StatService {

    private final StatsRepository statsRepository;
    public int getCountOfIncomesThatGreater (Long amount){
        return statsRepository.getCountOfIncomesThatGreaterThan(amount);
    }
}
