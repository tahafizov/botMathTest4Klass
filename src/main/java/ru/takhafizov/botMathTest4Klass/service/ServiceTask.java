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
                .name("Вопрос 1")
                .question("Вычисли выражение: 524 : 2")
                .answers(Arrays.asList("240", "262", "242", "253"))
                .correctAnswer("262")
                .build());
        taskList.add(Task.builder()
                .name("Вопрос 2")
                .question("Найди значение выражения: 3 * 18 + 48 : 12")
                .answers(Arrays.asList("40", "36", "63", "58"))
                .correctAnswer("58")
                .build());
        taskList.add(Task.builder()
                .name("Вопрос 3")
                .question("Аня, Боря и Вера съели вместе 14 конфет. Больше 8 конфет не съел никто. Аня съела больше всех конфет, а Боря съел на 1 конфету больше, чем Вера?")
                .answers(Arrays.asList("8", "7", "6", "5"))
                .correctAnswer("7")
                .build());
        taskList.add(Task.builder()
                .name("Вопрос 4")
                .question("Найди значение выражения: 3652 - (98 * 16 - 378)")
                .answers(Arrays.asList("2462", "1706", "2596", "2084"))
                .correctAnswer("2462")
                .build());
    }

    public Optional<Task> getFirstTaskByChatId(String chatId) {
        Optional<Integer> optionalInteger = getMaxTaskIndex(chatId);
        if (optionalInteger.isPresent()) {
            chatUserTask.put(chatId, new ArrayList<>());
        }
        return getNextTaskByChatId(chatId);
    }

    public Optional<Task> getNextTaskByChatId(String chatId) {

        List<Map<Integer, String>> mapList;
        int taskIndex;

        Optional<Integer> optionalInteger = getMaxTaskIndex(chatId);
        if (optionalInteger.isPresent()) {
            taskIndex = optionalInteger.get();
            taskIndex++;
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
            if (chatUserTask.get(chatId).get(taskIndex).containsKey(taskIndex)) {
                if (!chatUserTask.get(chatId).get(taskIndex).get(taskIndex).equals("")) {
                    return false;
                }
            }
            chatUserTask.get(chatId).get(taskIndex).put(taskIndex, answer);
            return true;
        }
        return false;
    }

    public String getResultTest(String chatId) {
        if (!chatUserTask.containsKey(chatId)) {
            return "Что-то пошло не так. Попробуйте еще раз пройти тест!";
        }
        List<Map<Integer, String>> mapList = chatUserTask.get(chatId);
        int countCorrectAnswers = 0;
        for (Map<Integer, String> map : mapList) {
            for (Map.Entry<Integer, String> es : map.entrySet()) {
                if (taskList.get(es.getKey()).getCorrectAnswer().equals(es.getValue())) {
                    countCorrectAnswers++;
                }
            }
        }
        return "Выполнено заданий: " + mapList.size() + "\n"
                + "Из них правильных: " + countCorrectAnswers + "\n"
                + "Процент выполнения: " + Math.round((float) (countCorrectAnswers * 100) / (float) mapList.size())  + "%";
    }

    private Optional<Integer> getMaxTaskIndex(String chatId) {
        if (!chatUserTask.containsKey(chatId)) {
            return Optional.empty();
        }
        return chatUserTask.get(chatId).stream()
                    .flatMap(r -> r.keySet().stream())
                    .max(Integer::compare);
    }
}
