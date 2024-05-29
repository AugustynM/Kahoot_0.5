package pl.tcs.po.views.game;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import pl.tcs.po.model.GameModel;
import pl.tcs.po.model.Player;

public class PlayerListLayout extends VerticalLayout{

    public PlayerListLayout(GameModel gameModel) {
        for (Player player : gameModel.getPlayers()) {
            add(player.getName() + " : " + player.getScore());
        }
    }
    
}
