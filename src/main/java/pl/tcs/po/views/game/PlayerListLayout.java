package pl.tcs.po.views.game;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import pl.tcs.po.model.GameModel;
import pl.tcs.po.model.Player;

public class PlayerListLayout extends VerticalLayout {

    public PlayerListLayout(GameModel gameModel) {
        Grid<Player> grid = new Grid<>(Player.class);

        grid.setItems(gameModel.getPlayers());
        grid.setColumns("name", "score");
        grid.setWidthFull();
        add(grid);
    }

    void update(GameModel gameModel) {
        getUI().ifPresent(ui -> ui.access(() -> {
            Grid<Player> grid = new Grid<>(Player.class);
            grid.setItems(gameModel.getPlayers());
            grid.setColumns("name", "score", "answered");
            grid.setWidthFull();
            removeAll();
            add(grid);
        }));
    }

}
