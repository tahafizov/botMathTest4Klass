package ru.takhafizov.botMathTest4Klass.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.takhafizov.botMathTest4Klass.bot.utils.Utils;

/**
 * Команда "Помощь"
 */
@Slf4j
public class HelpCommand extends ServiceCommand {
    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);
        log.info(String.format("Пользователь %s. Выполнение команды %s", userName,
                this.getCommandIdentifier()));

        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Я бот, который поможет тебе решать математику!", null);
    }
}
