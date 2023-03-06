/**
 * SpendRepository.java: интерфейс представляет собой «репозиторий», для работы с базой, расширенный JpaRepository.
 * Данный интерфейс создан для реализации работы с базой данных при расходовании "денег".
 */

package ru.SkillFactorydemo.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.SkillFactorydemo.tgbot.entity.Spend;

@Repository     // Аннотация Spring, указывает что интерфейс является репозиторием и предоставляет механизмы для хранения и поиска.
public interface SpendRepository extends JpaRepository<Spend, Long> {
}