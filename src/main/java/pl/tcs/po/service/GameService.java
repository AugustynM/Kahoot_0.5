package pl.tcs.po.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import pl.tcs.po.model.GameModel;
import pl.tcs.po.model.Player;
import pl.tcs.po.webClient.QuestionsClient;

@Service
public class GameService {
    @Autowired
    private QuestionsClient questionsClient;

    private final List<GameModel> games = new ArrayList<>();
    private int nextGameId = 0;

    public GameModel createGame(String name, int maxQuestions, int timeLimit) {
        GameModel game = new GameModel(nextGameId, name, maxQuestions, timeLimit);
        nextGameId++;
        games.add(game);
        GameListBroadcaster.broadcast(games);
        return game;
    }

    public GameModel getGame(int id) {
        if (id < games.size())
            return games.get(id);
        return null;
    }

    public List<GameModel> getGames() {
        return games;
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

    @PostConstruct
    void init() {
        createGame("Game 1", 5, 10);
        createGame("Game 2", 5, 10);
        createGame("Game 3", 5, 10);
        startGame(0);
        startGame(1);
        startGame(2);
        addPlayer(0, "Player 1");
        addPlayer(0, "Player 2");
        addPlayer(1, "Player 3");
        addPlayer(1, "Player 4");
        addPlayer(2, "Player 5");
        addPlayer(2, "Player 6");
    }
}
