/**
 * ActiveChatRepository.java: интерфейс представляет собой «репозиторий», для работы с базой, расширенный JpaRepository.
 * Данный интерфейс, содержит findActiveChatByChatId - абстрактный метод поиска активного чата по его ID.
 */
package ru.SkillFactorydemo.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.SkillFactorydemo.tgbot.entity.ActiveChat;
import java.util.Optional;

@Repository     // Аннотация Spring, указывает что интерфейс является репозиторием и предоставляет механизмы для хранения и поиска.
public interface ActiveChatRepository extends JpaRepository<ActiveChat, Long> {

    Optional<ActiveChat> findActiveChatByChatId(Long chatId);

}
