package ru.SkillFactorydemo.tgbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.SkillFactorydemo.tgbot.repository.StatsRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StatService {

    private final StatsRepository statsRepository;
    public int getCountOfIncomesThatGreater (Long amount){
        return statsRepository.getCountOfIncomesThatGreaterThan(amount);
    }
}
