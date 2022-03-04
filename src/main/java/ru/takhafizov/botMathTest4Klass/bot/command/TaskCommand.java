package ru.takhafizov.botMathTest4Klass.bot.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.takhafizov.botMathTest4Klass.model.Task;
import ru.takhafizov.botMathTest4Klass.service.ServiceTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaskCommand {
    private static final String ERROR_MESSAGE = "Прости, но ты ввел что-то не то...\n\n" +
            "Возможно, Вам поможет /help";

    private final ServiceTask serviceTask;

    public SendMessage taskCommandExecute(String chatId, String userName, String text) {

        if (text.equals("Да, продолжить!")) {
            Optional<Task> optionalTask = serviceTask.getFirstTaskByChatId(chatId);

            return optionalTask.map(task -> generateTaskMessage(chatId, task, ""))
                    .orElseGet(() -> generateTextMessage(chatId, "Увы... пока заданий нет!"));
        }
        if (text.equals("Нет, не знаю математику!")) {
            return generateTextMessage(chatId, "Очень жаль... " + userName + " приходи еще.");
        }
        if (serviceTask.saveAnswer(chatId, text)) {
            Optional<Task> optionalTask = serviceTask.getNextTaskByChatId(chatId);
            return optionalTask.map(task -> generateTaskMessage(chatId, task, "Ответ принят\n"))
                    .orElseGet(() -> generateTextMessage(chatId, serviceTask.getResultTest(chatId)));
        }

        return generateTextMessage(chatId, ERROR_MESSAGE);
    }

    private SendMessage generateTextMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        return message;
    }

    private SendMessage generateTaskMessage(String chatId, Task task, String prefix) {
        SendMessage message = new SendMessage();
//        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setReplyMarkup(getTaskAnswerKeyboard(task.getAnswers()));
        message.setText(prefix + task.getName() + ": " + task.getQuestion());
        return message;
    }

    private ReplyKeyboardMarkup getTaskAnswerKeyboard(List<String> answers) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(answers.get(0));
        keyboardFirstRow.add(answers.get(1));
        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardFirstRow.add(answers.get(2));
        keyboardFirstRow.add(answers.get(3));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }
}
