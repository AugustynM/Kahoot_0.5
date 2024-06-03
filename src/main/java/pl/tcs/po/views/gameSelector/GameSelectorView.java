package pl.tcs.po.views.gameSelector;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.shared.Registration;

import jakarta.annotation.PostConstruct;
import pl.tcs.po.model.GameModel;
import pl.tcs.po.service.GameListBroadcaster;
import pl.tcs.po.service.GameService;
import pl.tcs.po.views.MainLayout;
import pl.tcs.po.views.game.GameView;

@PageTitle("Kahoot v 0.5")
@Route(value = "/gameSelector", layout = MainLayout.class)
@RouteAlias(value = "/gameSelector", layout = MainLayout.class)
public class GameSelectorView extends VerticalLayout {

    @Autowired
    private GameService gameService;

    private VerticalLayout gamesLayout;

    Registration gameListBroadcasterRegistration;

    public GameSelectorView() {
    }

    @PostConstruct
    void init() {
        gamesLayout = new VerticalLayout();

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

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        gameListBroadcasterRegistration = GameListBroadcaster.register(gameList -> {
            ui.access(() -> {
                gamesLayout.removeAll();
                for (GameModel game : gameList) {
                    Button gameButton = new Button(game.getName());
                    gameButton.addClickListener(e -> gameButton.getUI().ifPresent(ui2 -> ui2.navigate(
                            GameView.class, game.getId())));
                    gamesLayout.add(gameButton);
                }

            });
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        gameListBroadcasterRegistration.remove();
        gameListBroadcasterRegistration = null;
    }
}
