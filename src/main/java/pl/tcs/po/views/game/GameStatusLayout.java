package pl.tcs.po.views.game;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import pl.tcs.po.model.GameModel;
import pl.tcs.po.model.Player;
import pl.tcs.po.service.GameService;

public class GameStatusLayout extends HorizontalLayout {
    GameModel gameModel;
    Player player;

    GameService gameService;

    Dialog nameDialog = new Dialog();

    public GameStatusLayout(GameModel gameModel, GameService gameService, Player player) {
        this.gameModel = gameModel;
        this.gameService = gameService;
        this.player = player;

        nameDialog.setHeaderTitle("Join game");
        Input nameInput = new Input();
        nameInput.setPlaceholder("Enter your name");
        nameDialog.add(nameInput);
        nameDialog.getFooter().add(new Button("Join", e -> {
            if (nameInput.getValue() != null && !nameInput.getValue().isEmpty()) {
                ((GameView) getParent().get()).joinGame(nameInput.getValue());
                nameDialog.close();
            } else {
                nameInput.setPlaceholder("Name cannot be empty");
            }
        }));
        nameDialog.getFooter().add(new Button("Cancel", e -> {
            nameDialog.close();
        }));

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
        if (player != null && status == GameModel.GameStatus.WAITING) {
            add(new Button("Start", e -> {
                gameService.startGame(gameModel.getId());
            }));
        }
        if (player == null && status != GameModel.GameStatus.FINISHED) {
            add(new Button("Join", e -> {
                nameDialog.open();
            }));
        }
    }

    public void update(GameModel gameModel, Player player) {
        this.gameModel = gameModel;
        this.player = player;
        removeAll();
        StringBuilder sb = new StringBuilder();
        sb.append(gameModel.getName());
        sb.append(" | ");
        GameModel.GameStatus status = gameModel.getStatus();
        if (status == GameModel.GameStatus.WAITING) {
            sb.append("Waiting");
        } else if (status == GameModel.GameStatus.IN_PROGRESS) {
            sb.append("In progress - question #");
            sb.append(gameModel.getCurrentQuestion() + 1);
            sb.append("/");
            sb.append(gameModel.getMaxQuestions());
        } else if (status == GameModel.GameStatus.FINISHED) {
            sb.append("Finished");
        }
        add(sb.toString());
        if (player != null && status == GameModel.GameStatus.WAITING) {
            add(new Button("Start", e -> {
                gameService.startGame(gameModel.getId());
            }));
        }
        if (player == null && status != GameModel.GameStatus.FINISHED) {
            add(new Button("Join", e -> {
                nameDialog.open();
            }));
        }
    }
}
