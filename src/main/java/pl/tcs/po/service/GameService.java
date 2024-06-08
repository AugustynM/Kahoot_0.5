package pl.tcs.po.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
        GameBroadcaster.broadcast(game);
    }

    public void finishGame(int id) {
        GameModel game = games.get(id);
        game.setStatus(GameModel.GameStatus.FINISHED);
        GameBroadcaster.broadcast(game);
    }

    public void nextQuestion(int id) {
        GameModel game = games.get(id);
        game.setCurrentQuestion(game.getCurrentQuestion() + 1);
        if (game.getCurrentQuestion() >= game.getMaxQuestions()) {
            finishGame(id);
        } else {
            game.setCurrentQuestionModel(questionsClient.getQuestions().get(0));
            game.setNextQuestionTimeMillis(System.currentTimeMillis() + game.getTimeLimit() * 1000);
            for (Player player : game.getPlayers()) {
                player.setAnswered(false);
            }
            GameBroadcaster.broadcast(game);
        }
    }

    public Player addPlayer(int id, String name) {
        GameModel game = games.get(id);
        Player player = new Player(game.getNextPlayerId(), name);
        game.getPlayers().add(player);
        game.setNextPlayerId(game.getNextPlayerId() + 1);
        GameBroadcaster.broadcast(game);
        return player;
    }

    public void removePlayer(int id, int playerId) {
        GameModel game = games.get(id);
        game.getPlayers().removeIf(player -> player.getId() == playerId);
        GameBroadcaster.broadcast(game);
    }

    public void answer(int id, int playerId, boolean isCorrect) {
        GameModel game = games.get(id);
        Player player = game.getPlayer(playerId);
        if (player == null || player.isAnswered()) {
            return;
        }
        player.setAnswered(true);
        if (isCorrect) {
            game.getPlayers().get(playerId).setScore(game.getPlayers().get(playerId).getScore() + 1);
        }
        GameBroadcaster.broadcast(game);
    }

    @Scheduled(fixedRate = 5000)
    public void updateGames() {
        long now = System.currentTimeMillis();
        for (GameModel game : games) {
            if (game.getStatus() == GameModel.GameStatus.IN_PROGRESS && now >= game.getNextQuestionTimeMillis()) {
                nextQuestion(game.getId());
            }
        }
    }

    @PostConstruct
    void init() {
    }
}
