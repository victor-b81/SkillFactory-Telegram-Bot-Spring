/**
 * FinanceService.java: сервисный класс предназначенный для обработки поступающих команд addincomes/addspend.
 *    addFinanceOperation() - метод проверяющий, если operationType=="/addincomes" то записываем в базу данных,
 *    в таблицу Incomes, иначе если любое другое значение(addspend) то записываем значение в базу данных, в таблицу Spend.
 */

package ru.SkillFactorydemo.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.SkillFactorydemo.tgbot.entity.Income;
import ru.SkillFactorydemo.tgbot.entity.Spend;
import ru.SkillFactorydemo.tgbot.repository.IncomeRepository;
import ru.SkillFactorydemo.tgbot.repository.SpendRepository;

import java.math.BigDecimal;

@Service    // Аннотация Spring, указывается что данный класс является сервисным, поставщика услуг.
@RequiredArgsConstructor  // Аннотация lombok, для генерирования конструктора.
public class FinanceService {

    private static final String ADD_INCOME = "/addincomes";

    private final IncomeRepository incomeRepository;
    private final SpendRepository spendRepository;


    public String addFinanceOperation(String operationType, String price, Long chatId) {
        String message;
        if (ADD_INCOME.equalsIgnoreCase(operationType)) {
            Income income = new Income();
            income.setChatId(chatId);
            income.setIncome(new BigDecimal(price));
            incomeRepository.save(income);
            message = "Доход в размере " + price + " был успешно добавлен";
        } else {
            Spend spend = new Spend();
            spend.setChatId(chatId);
            spend.setSpend(new BigDecimal(price));
            spendRepository.save(spend);
            message = "Расход в размере " + price + " был успешно добавлен";
        }
        return message;
    }
}