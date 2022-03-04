package ru.takhafizov.botMathTest4Klass.service;

import org.springframework.stereotype.Service;
import ru.takhafizov.botMathTest4Klass.model.Task;

import java.util.*;
import java.util.function.Consumer;

@Service
public class ServiceTask {
    private final List<Task> taskList;

    private final Map<String, List<Map<Integer, String>>> chatUserTask = new HashMap<>();

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

        List<Map<Integer, String>> mapList;
        int taskIndex;

        Optional<Integer> optionalInteger = getMaxTaskIndex(chatId);
        if (optionalInteger.isPresent()) {
            taskIndex = optionalInteger.get();
            mapList = chatUserTask.get(chatId);
        } else {
            taskIndex = 0;
            mapList = new ArrayList<>();
        }

        if (taskIndex == taskList.size()) {
            return Optional.empty();
        }

        Map<Integer, String> currentMap = new HashMap<>();
        currentMap.put(taskIndex, "");
        mapList.add(currentMap);
        chatUserTask.put(chatId, mapList);
        return Optional.of(taskList.get(taskIndex));
    }


    public boolean saveAnswer(String chatId, String answer) {
        if (!chatUserTask.containsKey(chatId)) {
            return false;
        }
        Optional<Integer> optionalInteger = getMaxTaskIndex(chatId);
        if (optionalInteger.isPresent()) {
            Integer taskIndex = optionalInteger.get();
            chatUserTask.get(chatId).get(taskIndex).put(taskIndex, answer);
            return true;
        }
        return false;
    }

    private Optional<Integer> getMaxTaskIndex(String chatId) {
        if (!chatUserTask.containsKey(chatId)) {
            return Optional.empty();
        }
        return chatUserTask.get(chatId).stream()
                    .flatMap(r -> r.entrySet().stream().filter(f -> f.getValue().length() > 0).map(Map.Entry::getKey))
                    .max((o1, o2) -> (o2 > o1) ? 1 : 0);
    }
}
