/**
 * CurrencyController.java: класс контроллер, обработки запросов поступающих в web сервер Tomcat.
 * Данный класс содержит два метода
 * getValuteCursOnDate() - метод возвращает курсы валют
 * getStatsAboutIncomesThetGreater() - метод возвращает количество денежных поступлений, больше суммы, переданной в http запросе GET.
 * Пример запроса:
 *      http://localhost:9090/getStats?amount=19
 *        вернет количество поступлений, сумма которых больше 19.
 *      http://localhost:9090/getCurrencies
 *        вернет курсы валют ЦБР.
 * */

package ru.SkillFactorydemo.tgbot.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.SkillFactorydemo.tgbot.dto.ValuteCursOnDate;
import ru.SkillFactorydemo.tgbot.service.CentralRussianBankService;
import ru.SkillFactorydemo.tgbot.service.StatService;

import java.util.List;

@RestController     // Аннотация REST API, позволяет классу обрабатывать запросы, сделанные клиентом (такие как запросы GET , POST , Delete, PUT).
@RequiredArgsConstructor       // Аннотация lombok, для генерирования конструктора.
public class CurrencyController {

    private final CentralRussianBankService centralRussianBankService;
    private final StatService statService;

    @GetMapping("/getCurrencies")    // Аннотация Spring, сопоставляет HTTP-GET с методом. Действует как ярлык http://localhost:9090/getCurrencies
    public List<ValuteCursOnDate> getValuteCursOnDate() throws Exception {
        return centralRussianBankService.getCurrenciesFromCbr();
    }

    @GetMapping("/getStats")    // Аннотация Spring, сопоставляет HTTP-GET с методом. Действует как ярлык http://localhost:9090/getStats
    @ApiOperation(value = "Получение количества пополнений, которые превышают определенную сумму")  // Аннотация Spring, для описания операции
    public int getStatsAboutIncomesThetGreater(@RequestParam(value = "amount") Long amount){
        return statService.getCountOfIncomesThatGreater(amount);
    }

}