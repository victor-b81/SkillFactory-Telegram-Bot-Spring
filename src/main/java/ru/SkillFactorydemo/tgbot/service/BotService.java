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

@Service //Данный класс является сервисом
@Slf4j //Подключаем логирование из Lombok'a
@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {
    private Map<Long, List<String>> previousCommands = new ConcurrentHashMap<>();

    private static final String CURRENT_RATES = "/currentrates";
    private static final String ADD_INCOME = "/addincomes";
    private static final String ADD_SPEND = "/addspend";

    private final CentralRussianBankService centralRussianBankService;
    private final FinanceService financeService;
    private final ActiveChatRepository activeChatRepository;

    @Value("${bot.api.key}") //Сюда будет вставлено значение из application.properties, в котором будет указан api key, полученный от BotFather
    private String apiKey;

    @Value("${bot.name}") //Как будут звать нашего бота
    private String name;
    //Это основной метод, который связан с обработкой сообщений
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
            log.error("Возникла проблема при получении данных от сервисов ЦБ РФ", e);
            //e.printStackTrace();
        } catch (Exception e) {
            log.error("Возникла проблема при получении данных от сервисов ЦБ РФ", e);
            //e.printStackTrace();
        }
    }

    //Данный метод будет вызван сразу после того, как данный бин будет создан - это обеспечено аннотацией Spring PostConstruct
    @PostConstruct
    public void start() {
        log.info("username: {}, token: {}", name, apiKey);
    }

    //Данный метод просто возвращает данные о имени бота и его необходимо переопределять
    @Override
    public String getBotUsername() {
        return name;
    }
    //Данный метод возвращает API ключ для взаимодействия с Telegram
    @Override
    public String getBotToken() {
        return apiKey;
    }

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


    private void putPreviousCommand(Long chatId, String command) {
        if (previousCommands.get(chatId) == null) {
            List<String> commands = new ArrayList<>();
            commands.add(command);
            previousCommands.put(chatId, commands);
        } else {
            previousCommands.get(chatId).add(command);
        }
    }

    private String getPreviousCommand(Long chatId) {
        return previousCommands.get(chatId)
                .get(previousCommands.get(chatId).size() - 1);
    }
}