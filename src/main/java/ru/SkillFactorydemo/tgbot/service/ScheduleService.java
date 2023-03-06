/**
 * ScheduleService.java: сервисный класс Планировщик задач.
 * Каждые три часа опрашивает сервис CentralRussianBankService, извлекает курсы валют, проверяет на изменения +/- 10 единиц.
 * Если таковое изменение произошло, уведомляет всех пользователей Телеграм бота.
 *
 *    notifyAboutChangesInCurrencyRate() - метод реализующий функционал данного сервиса.
 */

package ru.SkillFactorydemo.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.SkillFactorydemo.tgbot.dto.ValuteCursOnDate;
import ru.SkillFactorydemo.tgbot.entity.ActiveChat;
import ru.SkillFactorydemo.tgbot.repository.ActiveChatRepository;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component      // Аннотация Spring, позволяет автоматически обнаруживать пользовательские bean-компоненты.
@RequiredArgsConstructor  // Аннотация lombok, для генерирования конструктора.
public class ScheduleService {

    private final ActiveChatRepository activeChatRepository;
    private final BotService botService;
    private final CentralRussianBankService centralRussianBankService;
    private final List<ValuteCursOnDate> previousRates = new ArrayList<>();

    @Scheduled(cron = "0 0 0/3 ? * *")      // Аннотация Spring, позволяет запланировать задание в формате cron, установлено 3 часа
    public void notifyAboutChangesInCurrencyRate() {
        try {
            List<ValuteCursOnDate> currentRates = centralRussianBankService.getCurrenciesFromCbr();
            Set<Long> activeChatIds = activeChatRepository.findAll().stream().map(ActiveChat::getChatId).collect(Collectors.toSet());
            if (!previousRates.isEmpty()) {
                for (int index = 0; index < currentRates.size(); index++) {
                    if (currentRates.get(index).getCourse() - previousRates.get(index).getCourse() >= 10.0) {
                        botService.sendNotificationToAllActiveChats("Курс " + currentRates.get(index).getName() + " увеличился на 10 рублей", activeChatIds);
                    } else if (currentRates.get(index).getCourse() - previousRates.get(index).getCourse() <= 10.0) {
                        botService.sendNotificationToAllActiveChats("Курс " + currentRates.get(index).getName() + " уменьшился на 10 рублей", activeChatIds);
                    }
                }
            } else {
                previousRates.addAll(currentRates);
            }
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }
}
