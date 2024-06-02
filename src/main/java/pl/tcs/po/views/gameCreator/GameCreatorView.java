package pl.tcs.po.views.gameCreator;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import jakarta.annotation.PostConstruct;
import pl.tcs.po.model.GameModel;
import pl.tcs.po.service.GameService;
import pl.tcs.po.views.MainLayout;

@PageTitle("Kahoot v 0.5")
@Route(value = "/gameCreator", layout = MainLayout.class)
@RouteAlias(value = "/gameCreator", layout = MainLayout.class)
public class GameCreatorView extends VerticalLayout {

    @Autowired
    private GameService gameService;

    private VerticalLayout layout;

    public GameCreatorView() {
    }

    @PostConstruct
    void init() {
        layout = new VerticalLayout();

        TextField gameNameField = new TextField("Game Name");

        Button createGameButton = new Button("Create Game");
        createGameButton.addClickListener(e -> {
            GameModel game = gameService.createGame(gameNameField.getValue(), 5);
            // createGameButton.getUI().ifPresent(ui -> ui.navigate(GameView.class,
            // game.getId()));
        });

        layout.add("Create Game");
        layout.add(gameNameField);
        layout.add(createGameButton);
        add(layout);
    }
}
