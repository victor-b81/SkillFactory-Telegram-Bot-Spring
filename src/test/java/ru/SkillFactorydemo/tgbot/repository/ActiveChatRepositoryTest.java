package ru.SkillFactorydemo.tgbot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.SkillFactorydemo.tgbot.entity.ActiveChat;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ActiveChatRepositoryTest {

    @Autowired
    private ActiveChatRepository activeChatRepository;

    @Test
    void findActiveChatByChatId() {
    }

    @Test
    public void testRepo_found(){
        final ActiveChat activeChat = new ActiveChat(); // создаём экземпляр сущности
        activeChat.setChatId(12345L);                   // присваиваем сущности ИД чата 12345
        activeChatRepository.save(activeChat);          // сохраняем в репозитории сущность
        Optional<ActiveChat> activeChatById = activeChatRepository.findActiveChatByChatId(12345L);      // ищем ид чата 12345 в репозитории и если ид существует, то возвращаем его значение в объект activeChatById
        assertTrue(activeChatById.isPresent());         // проверяем, существует ли объект activeChatById
        assertEquals(12345L, activeChatById.get().getChatId());     // сверяем совпадает ли результат с ожиданиями
    }

    @Test
    public void testRepo_notFound() {
        final ActiveChat activeChat = new ActiveChat();  // создаём экземпляр сущности
        activeChat.setChatId(12345L);                    // присваиваем сущности ИД чата 12345
        activeChatRepository.save(activeChat);           // сохраняем в репозитории сущность
        Optional<ActiveChat> activeChatByChatId = activeChatRepository.findActiveChatByChatId(54321L);      //  ищем ид чата 54321 в репозитории и если ид существует, то возвращаем его значение в объект activeChatById
        assertFalse(activeChatByChatId.isPresent());     // проверяем, существует ли объект activeChatById
    }
}