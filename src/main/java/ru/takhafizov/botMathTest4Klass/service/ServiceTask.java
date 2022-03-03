package ru.takhafizov.botMathTest4Klass.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.takhafizov.botMathTest4Klass.model.Task;

import java.util.*;

@Service
public class ServiceTask {
    private final List<Task> taskList;

    private final Map<String, List<Map<Integer, String>>> chatUserTaskIndex = new HashMap<>();
//    private final Map<String, String> chatUserTaskAnswer = new HashMap<>();

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
        int taskIndex = 0;
        List<Map<Integer, String>> mapList;

        if (chatUserTaskIndex.containsKey(chatId)) {
            mapList = chatUserTaskIndex.get(chatId);
            taskIndex = mapList.stream()
                    .flatMap(r -> r.entrySet().stream().filter(f -> f.getValue().length() > 0).map(Map.Entry::getKey))
                    .max((o1, o2) -> (o2 > o1) ? 1 : 0).orElse(0);
        }

        if (taskIndex == taskList.size()) {
            return Optional.empty();
        }
        chatUserTaskIndex.put(chatId, taskIndex);
        return Optional.of(taskList.get(taskIndex));
    }

    public String getCorrectAnswer(String chatId, String selectedAnswer) {
        if (!chatUserTaskIndex.containsKey(chatId)) {
            return null;
        }

        return taskList.get(chatUserTaskIndex.get(chatId)).getCorrectAnswer();
    }
}
