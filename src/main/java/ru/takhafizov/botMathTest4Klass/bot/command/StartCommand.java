package ru.takhafizov.botMathTest4Klass.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.takhafizov.botMathTest4Klass.bot.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда "Старт"
 */
@Slf4j
public class StartCommand extends ServiceCommand {
    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        log.info(String.format("Пользователь %s. Выполнение команды %s", userName,
                this.getCommandIdentifier()));
        String answerText = "Привет, " + userName + "! Тест по математике (4 класс). Готов продолжить?";

        // Создаем клавиатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("Да, продолжить!");
        keyboardFirstRow.add("Нет, не знаю математику!");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);

        // и устанавливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                answerText, replyKeyboardMarkup);
    }
}
