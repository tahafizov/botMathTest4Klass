package ru.takhafizov.botMathTest4Klass.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    private String name;
    private String question;
    private List<String> answers;
    private String correctAnswer;
}
