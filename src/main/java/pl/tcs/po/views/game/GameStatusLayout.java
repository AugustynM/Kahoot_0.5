package pl.tcs.po.views.game;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import pl.tcs.po.model.GameModel;
import pl.tcs.po.service.GameService;

public class GameStatusLayout extends HorizontalLayout {
    GameModel gameModel;

    GameService gameService;

    public GameStatusLayout(GameModel gameModel, GameService gameService) {
        this.gameModel = gameModel;
        this.gameService = gameService;
        StringBuilder sb = new StringBuilder();
        sb.append(gameModel.getName());
        sb.append(" | ");
        GameModel.GameStatus status = gameModel.getStatus();
        if (status == GameModel.GameStatus.WAITING) {
            sb.append("Waiting");
        } else if (status == GameModel.GameStatus.IN_PROGRESS) {
            sb.append("In progress - question #");
            sb.append(gameModel.getCurrentQuestion());
            sb.append("/");
            sb.append(gameModel.getMaxQuestions());
        } else if (status == GameModel.GameStatus.FINISHED) {
            sb.append("Finished");
        }
        add(sb.toString());
        if (status == GameModel.GameStatus.WAITING) {
            add(new Button("Start", e -> {
                gameService.startGame(gameModel.getId());
            }));
        }
    }

    public void update(GameModel gameModel) {
        this.gameModel = gameModel;
        removeAll();
        StringBuilder sb = new StringBuilder();
        sb.append(gameModel.getName());
        sb.append(" | ");
        GameModel.GameStatus status = gameModel.getStatus();
        if (status == GameModel.GameStatus.WAITING) {
            sb.append("Waiting");
        } else if (status == GameModel.GameStatus.IN_PROGRESS) {
            sb.append("In progress - question #");
            sb.append(gameModel.getCurrentQuestion());
            sb.append("/");
            sb.append(gameModel.getMaxQuestions());
        } else if (status == GameModel.GameStatus.FINISHED) {
            sb.append("Finished");
        }
        add(sb.toString());
        if (status == GameModel.GameStatus.WAITING) {
            add(new Button("Start", e -> {
                gameService.startGame(gameModel.getId());
            }));
        }
    }
}
