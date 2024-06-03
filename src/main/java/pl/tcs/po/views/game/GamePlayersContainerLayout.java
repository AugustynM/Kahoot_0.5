package pl.tcs.po.views.game;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import pl.tcs.po.model.GameModel;
import pl.tcs.po.model.QuestionModel;

public class GamePlayersContainerLayout extends HorizontalLayout {
    QuestionLayout questionLayout;
    PlayerListLayout playerListLayout;

    public GamePlayersContainerLayout(QuestionModel questionModel, GameModel gameModel) {
        questionLayout = new QuestionLayout(questionModel);
        playerListLayout = new PlayerListLayout(gameModel);
        add(questionLayout, playerListLayout);
    }

    public void update(QuestionModel questionModel, GameModel gameModel) {
        questionLayout.update(questionModel);
        playerListLayout.update(gameModel);
    }
}
