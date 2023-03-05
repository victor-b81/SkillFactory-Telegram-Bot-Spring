/**
 * ActiveChat.java: часть слоя Entity сущностей.
 * Данный слой-сущность, служит для создания структуры реляционных баз данных.
 * И создает два поля для SQL таблицы ACTIVE_CHAT - поле ID и поле CHAT_ID
 */

package ru.SkillFactorydemo.tgbot.entity;

import lombok.Data;

import javax.persistence.*;

@Entity     // Аннотация JPA, объявляет что данный класс является сущностью
@Data   // Аннотация Lombok, генерируем геттеры и сеттеры
@Table(name = "ACTIVE_CHAT") // Аннотация JPA, указываем, что данный класс-сущность будет храниться в таблице ACTIVE_CHAT
public class ActiveChat {

    @Id     // Аннотация JPA, указываем первичный ключ в таблице базы данных
    @GeneratedValue     // Аннотация JPA, указываем первичный ключ в таблице базы данных
    private Long id; //Уникальный идентификатор в системе нашего бота

    @Column(name = "CHAT_ID")
    private Long chatId; //Уникальный идентификатор в системе Telegram
}
