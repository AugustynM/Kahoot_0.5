package pl.tcs.po.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tcs.po.model.GameModel;
import pl.tcs.po.model.Player;
import pl.tcs.po.webClient.QuestionsClient;

@Service
public class GameService {
    @Autowired
    private QuestionsClient questionsClient;

    private final List<GameModel> games = new ArrayList<>();

    public GameModel createGame(String name, int maxQuestions) {
        GameModel game = new GameModel(games.size(), name, maxQuestions);
        games.add(game);
        return game;
    }

    public GameModel getGame(int id) {
        return games.get(id);
    }

    public void startGame(int id) {
        GameModel game = games.get(id);
        game.setStatus(GameModel.GameStatus.IN_PROGRESS);
        game.setCurrentQuestionModel(questionsClient.getQuestions().get(0));
    }

    public void finishGame(int id) {
        GameModel game = games.get(id);
        game.setStatus(GameModel.GameStatus.FINISHED);
    }

    public void removeGame(int id) {
        games.remove(id);
    }

    public void nextQuestion(int id) {
        GameModel game = games.get(id);
        game.setCurrentQuestion(game.getCurrentQuestion() + 1);
        if (game.getCurrentQuestion() >= game.getMaxQuestions()) {
            finishGame(id);
        } else {
            game.setCurrentQuestionModel(questionsClient.getQuestions().get(0));
        }
    }

    public void addPlayer(int id, String name) {
        GameModel game = games.get(id);
        game.getPlayers().add(new Player(game.getNextPlayerId(), name));
        game.setNextPlayerId(game.getNextPlayerId() + 1);
    }

    public void removePlayer(int id, int playerId) {
        GameModel game = games.get(id);
        game.getPlayers().removeIf(player -> player.getId() == playerId);
    }
}
