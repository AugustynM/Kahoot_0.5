package pl.tcs.po.views.gameSelector;

import pl.tcs.po.model.GameModel;
import pl.tcs.po.service.GameService;
import pl.tcs.po.views.MainLayout;
import pl.tcs.po.views.game.GameView;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import jakarta.annotation.PostConstruct;

@PageTitle("Kahoot v 0.5")
@Route(value = "/gameSelector", layout = MainLayout.class)
@RouteAlias(value = "/gameSelector", layout = MainLayout.class)
public class GameSelectorView extends VerticalLayout {

    @Autowired
    private GameService gameService;

    public GameSelectorView() {
    }

    @PostConstruct
    void init() {
        VerticalLayout gamesLayout = new VerticalLayout();

        List<GameModel> games = gameService.getGames();
        for (GameModel game : games) {
            Button gameButton = new Button(game.getName());
            gameButton.addClickListener(e -> gameButton.getUI().ifPresent(ui -> ui.navigate(
                    GameView.class, game.getId())));
            gamesLayout.add(gameButton);
        }

        add("Select Game");
        add(gamesLayout);
    }

}
