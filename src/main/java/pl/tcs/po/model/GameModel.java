package pl.tcs.po.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data 
@RequiredArgsConstructor
public class GameModel {
    public static enum GameStatus {
        WAITING,
        IN_PROGRESS,
        FINISHED
    }

    final int id;
    final String name;
    GameStatus status = GameStatus.WAITING;

    final int maxQuestions;
    int currentQuestion = 0;
    QuestionModel currentQuestionModel = null;

    final int timeLimit;

    final List<Player> players = new ArrayList<>();
    int nextPlayerId = 0;
}
