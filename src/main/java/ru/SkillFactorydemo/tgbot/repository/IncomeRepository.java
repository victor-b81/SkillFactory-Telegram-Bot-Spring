package ru.SkillFactorydemo.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.SkillFactorydemo.tgbot.entity.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
}