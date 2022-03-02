package ru.takhafizov.botMathTest4Klass.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Суперкласс для сервисных команд
 */
@Slf4j
abstract class ServiceCommand extends BotCommand {
    protected ServiceCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }
    /**
     * Отправка ответа пользователю
     */
    void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        if (replyKeyboardMarkup != null) {
            message.setReplyMarkup(replyKeyboardMarkup);
        }

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error(String.format("Ошибка %s. Команда %s. Пользователь: %s%n", e.getMessage(), commandName, userName));
            e.printStackTrace();
        }
    }
}
