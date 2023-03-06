/**
 * ActiveChat.java: часть слоя Entity сущностей.
 * Данный слой-сущность, служит для создания таблицы "Активные чаты".
 * И обладает двумя полями для SQL таблицы ACTIVE_CHAT - поле ID и поле CHAT_ID
 */

package ru.SkillFactorydemo.tgbot.entity;

import lombok.Data;

import javax.persistence.*;

@Entity     // Аннотация JPA, объявляет что данный класс является сущностью
@Data       // Аннотация Lombok, генерируем геттеры и сеттеры
@Table(name = "ACTIVE_CHAT") // Аннотация JPA, указываем, что данный класс-сущность будет храниться в таблице ACTIVE_CHAT
public class ActiveChat {

    @Id     // Аннотация JPA, указываем первичный ключ и назначаем полю колонку ID, в таблице ACTIVE_CHAT
    @GeneratedValue     // Аннотация JPA, указываем генерацию первичного ключа без указания стратегии (AUTO)
    private Long id;    // Поле - уникальный идентификатор

    @Column(name = "CHAT_ID")   // Аннотация JPA, указываем имя колонки CHAT_ID, в таблице ACTIVE_CHAT
    private Long chatId;    // Уникальный идентификатор телеграм чата
}
