package ru.takhafizov.botMathTest4Klass.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.takhafizov.botMathTest4Klass.model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServiceTask {
    private final List<Task> taskList;

    private final Map<String, Integer> chatUserTaskIndex = new HashMap<>();
    private final Map<String, String> chatUserTaskAnswer = new HashMap<>();

    public List<Task> getTaskList() {
        return taskList;
    }

    public ServiceTask() {
        taskList = new ArrayList<>();
        taskList.add(Task.builder()
                .name("Задание 1")
                .question("Вычислите выражение: 524 : 2")
                .answers(Arrays.asList("240", "262", "242", "253"))
                .correctAnswer("262")
                .build());
    }

    public Optional<Task> getNextTaskByChatId(String chatId) {
        int taskIndex = chatUserTaskIndex.containsKey(chatId) ?
                chatUserTaskIndex.get(chatId) + 1 : 0;

        if (taskIndex == taskList.size()) {
            return Optional.empty();
        }
        chatUserTaskIndex.put(chatId, taskIndex);
        return Optional.of(taskList.get(taskIndex));
    }
}
