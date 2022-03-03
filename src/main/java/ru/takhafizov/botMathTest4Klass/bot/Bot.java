package ru.takhafizov.botMathTest4Klass.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.takhafizov.botMathTest4Klass.bot.command.HelpCommand;
import ru.takhafizov.botMathTest4Klass.bot.command.StartCommand;
import ru.takhafizov.botMathTest4Klass.bot.command.TaskCommand;
import ru.takhafizov.botMathTest4Klass.bot.utils.Utils;
import ru.takhafizov.botMathTest4Klass.model.Task;
import ru.takhafizov.botMathTest4Klass.service.ServiceTask;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Bot extends TelegramLongPollingCommandBot {

    @Value("${BOT_NAME}")
    private String BOT_NAME;
    @Value("${BOT_TOKEN}")
    private String BOT_TOKEN;

    @Autowired
    private TaskCommand taskCommand;

    @Autowired
    private ServiceTask serviceTask;

    public Bot() {
        super();

        register(new StartCommand("start", "Старт"));
        register(new HelpCommand("help","Помощь"));
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        String textFromUser = msg.getText();
        String userName = Utils.getUserName(msg);
        String chatId = msg.getChatId().toString();

        try {
            execute(taskCommand.taskCommandExecute(chatId, userName, textFromUser));
        } catch (TelegramApiException e) {
            log.error(String.format("Ошибка %s. Сообщение, не являющееся командой. Пользователь: %s", e.getMessage(),
                    userName));
            e.printStackTrace();
        }

    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    /**
     * Отправка ответа
     * @param chatId id чата
     * @param userName имя пользователя
     * @param text текст ответа
     */
    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            log.error(String.format("Ошибка %s. Сообщение, не являющееся командой. Пользователь: %s", e.getMessage(),
                    userName));
            e.printStackTrace();
        }
    }
}
