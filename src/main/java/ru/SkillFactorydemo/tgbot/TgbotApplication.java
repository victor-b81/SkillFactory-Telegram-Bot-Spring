/** TgbotApplication.java: исполняющий файл.
 * Выполнение SpringApplication.run(TgbotApplication.class, args); - запускает Spring приложение
 * Аннотация EnableScheduling: Включает возможность запланированного выполнения задач Spring. Таких как @Scheduled, @Bean.
 * Аннотация SpringBootApplication: эквивалентна использованию @Configuration, @EnableAutoConfigurationи @ComponentScan.
 * */

package ru.SkillFactorydemo.tgbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TgbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgbotApplication.class, args);
	}

}
