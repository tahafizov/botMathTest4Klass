package ru.takhafizov.botMathTest4Klass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.takhafizov.botMathTest4Klass.bot.Bot;

@SpringBootApplication
public class BotMathTest4KlassApplication {

	@Autowired
	private Bot bot;

	@Bean
	public TelegramBotsApi telegramBotsApi() {
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(bot);
			return botsApi;
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		SpringApplication.run(BotMathTest4KlassApplication.class, args);
	}
}
