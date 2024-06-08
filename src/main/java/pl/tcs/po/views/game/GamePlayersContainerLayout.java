package pl.tcs.po.views.game;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import pl.tcs.po.model.GameModel;
import pl.tcs.po.model.Player;
import pl.tcs.po.service.GameService;

public class GamePlayersContainerLayout extends HorizontalLayout {
    QuestionLayout questionLayout;
    PlayerListLayout playerListLayout;
    GameService gameService;

    public GamePlayersContainerLayout(GameModel gameModel, Player player, GameService gameService) {
        questionLayout = new QuestionLayout(gameModel, player, gameService);
        playerListLayout = new PlayerListLayout(gameModel);
        add(questionLayout, playerListLayout);
    }

    public void update(GameModel gameModel, Player player) {
        questionLayout.update(gameModel, player);
        playerListLayout.update(gameModel);
    }
}
