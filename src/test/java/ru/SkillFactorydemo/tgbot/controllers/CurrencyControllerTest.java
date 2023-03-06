/**
 * Класс-тест для тестирования CurrencyControllerTest
 * Проверка запроса курсов ЦБР
 */

package ru.SkillFactorydemo.tgbot.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test  // Аннотация JUnit, указываем на данный метод как тестовый
    void getValuteCursOnDate() {
    }

    @Test  // Аннотация JUnit, указываем на данный метод как тестовый
    void getStatsAboutIncomesThetGreater() {
    }

    @Test  // Аннотация JUnit, указываем на данный метод как тестовый
    public void testWhenAskAboutAllCurrencies() throws Exception {
        mockMvc.perform(get("/getCurrencies"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}