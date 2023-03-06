/**
 * Income.java: часть слоя Entity сущностей.
 * Данный слой-сущность, служит для создания таблицы "Поступления".
 * И обладает тремя полями для SQL таблицы INCOMES - поля ID, CHAT_ID и INCOME
 */

package ru.SkillFactorydemo.tgbot.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity     // Аннотация JPA, объявляет что данный класс является сущностью
@Data       // Аннотация Lombok, генерируем геттеры и сеттеры
@Table(name = "INCOMES")    // Аннотация JPA, указываем, что данный класс-сущность будет храниться в таблице INCOMES
public class Income {

    @Id     // Аннотация JPA, указываем первичный ключ и назначаем полю колонку ID, в таблице INCOMES
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Аннотация JPA, указываем стратегию генерирования первичного ключа
    private Long id;    // Поле - уникальный идентификатор

    @Column(name = "CHAT_ID")   // Аннотация JPA, назначаем полю имя колонки CHAT_ID, в таблице INCOMES
    private Long chatId;    // Поле - идентификатор телеграм чата

    @Column(name = "INCOME")   // Аннотация JPA, назначаем полю имя колонки INCOME, в таблице INCOMES
    private BigDecimal income;  // Поле - значение поступления "денег"
}