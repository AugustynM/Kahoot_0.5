package pl.tcs.po.model;

import java.util.List;

import lombok.Data;

@Data
public class Game {
    static enum GameStatus {
        WAITING,
        IN_PROGRESS,
        FINISHED
    }

    String name;
    GameStatus status;
    int maxQuestions;
    int currentQuestion;
    QuestionModel currentQuestionModel;
    List<Player> players;
}
