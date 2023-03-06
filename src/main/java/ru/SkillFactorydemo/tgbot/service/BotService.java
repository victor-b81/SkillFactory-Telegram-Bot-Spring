/**
 * BotService.java: сервисный класс предназначенный для работы с TelegramAPI.
 * Включает в себя следующие методы:
 *      onUpdateReceived() - метод обработки поступивших в чат телеграм бота сообщений.
 *      start() - метод @PostConstruct, служит для вывода в лог информации и боте.
 *      getBotUsername() - метод возвращает имя Telegram бота.
 *      getBotToken() - метод возвращает TelegramAPI ключ бота.
 *      sendNotificationToAllActiveChats() - метод рассылает сообщения во все активные чаты.
 *      putPreviousCommand() - метод сохраняет список команд определенного чата.
 *      getPreviousCommand() - метод получает список команд определенного чата.
 */

package ru.SkillFactorydemo.tgbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.testcontainers.shaded.org.apache.commons.lang3.StringUtils;
import ru.SkillFactorydemo.tgbot.dto.ValuteCursOnDate;
import ru.SkillFactorydemo.tgbot.entity.ActiveChat;
import ru.SkillFactorydemo.tgbot.repository.ActiveChatRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service    // Аннотация Spring, указывается что данный класс является сервисным, поставщика услуг.
@Slf4j      // Подключаем логирование из lombok'a
@RequiredArgsConstructor  // Аннотация lombok, для генерирования конструктора.
public class BotService extends TelegramLongPollingBot {
    private Map<Long, List<String>> previousCommands = new ConcurrentHashMap<>(); // Коллекция истории команд.

    // Присваиваем значения переменным для проверки поступивших команд.
    private static final String CURRENT_RATES = "/currentrates";
    private static final String ADD_INCOME = "/addincomes";
    private static final String ADD_SPEND = "/addspend";

    // Объявляем сервисы
    private final CentralRussianBankService centralRussianBankService;
    private final FinanceService financeService;
    private final ActiveChatRepository activeChatRepository;

    @Value("${bot.api.key}")  // Аннотация Spring, Получаем из файла application.properties, api key - Телеграм бота
    private String apiKey;

    @Value("${bot.name}")  // Аннотация Spring, Получаем из файла application.properties имя Телеграм бота
    private String name;

    // Данный метод, является основным и занимается обработкой полученных из чата Телеграм команд.
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        try {
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(String.valueOf(chatId));
            if (CURRENT_RATES.equalsIgnoreCase(message.getText())) {
                for (ValuteCursOnDate valuteCursOnDate : centralRussianBankService.getCurrenciesFromCbr()) {
                    response.setText(StringUtils.defaultIfBlank(response.getText(), "") + valuteCursOnDate.getName() + " - " + valuteCursOnDate.getCourse() + "\n");
                }
            } else if (ADD_INCOME.equalsIgnoreCase(message.getText())) {
                response.setText("Отправьте мне сумму полученного дохода");
            } else if (ADD_SPEND.equalsIgnoreCase(message.getText())) {
                response.setText("Отправьте мне сумму расходов");
            } else {
                response.setText(financeService.addFinanceOperation(getPreviousCommand(message.getChatId()), message.getText(), message.getChatId()));
            }

            putPreviousCommand(message.getChatId(), message.getText());
            execute(response);
            if (activeChatRepository.findActiveChatByChatId(chatId).isEmpty()) {
                ActiveChat activeChat = new ActiveChat();
                activeChat.setChatId(chatId);
                activeChatRepository.save(activeChat);
            }
        } catch (TelegramApiException e) {
            log.error("Возникла проблема при получении данных от сервисов ЦБ РФ TelegramApiException", e);
            //e.printStackTrace();
        } catch (Exception e) {
            log.error("Возникла проблема при получении данных от сервисов ЦБ РФ Exception", e);
            //e.printStackTrace();
        }
    }

    // Данный метод выводит в лог имя бота, его токен и будет вызван сразу после того, как данный бин будет создан.
    @PostConstruct  // Аннотация Spring, вызывает данные методы сразу после инициализации, данные методы работают даже если пусты.
    public void start() {
        log.info("username: {}, token: {}", name, apiKey);
    }

    // Данный метод возвращает имя Telegram бота и его необходимо переопределять
    @Override
    public String getBotUsername() {
        return name;
    }
    // Данный метод возвращает TelegramAPI ключ бота и его необходимо переопределять
    @Override
    public String getBotToken() {
        return apiKey;
    }

    // Данный метод рассылает сообщения во все активные чаты
    public void sendNotificationToAllActiveChats(String message, Set<Long> chatIds) {
        for (Long id : chatIds) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(id));
            sendMessage.setText(message);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

// Данный метод сохраняет список команд определенного чата
    private void putPreviousCommand(Long chatId, String command) {
        if (previousCommands.get(chatId) == null) {
            List<String> commands = new ArrayList<>();
            commands.add(command);
            previousCommands.put(chatId, commands);
        } else {
            previousCommands.get(chatId).add(command);
        }
    }

    // Данный метод получает список команд определенного чата
    private String getPreviousCommand(Long chatId) {
        return previousCommands.get(chatId)
                .get(previousCommands.get(chatId).size() - 1);
    }
}