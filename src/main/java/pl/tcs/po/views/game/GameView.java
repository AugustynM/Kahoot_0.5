package pl.tcs.po.views.game;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.shared.Registration;

import jakarta.annotation.PostConstruct;
import pl.tcs.po.model.GameModel;
import pl.tcs.po.model.QuestionModel;
import pl.tcs.po.service.GameBroadcaster;
import pl.tcs.po.service.GameService;
import pl.tcs.po.views.MainLayout;

@PageTitle("Kahoot v 0.5")
@Route(value = "/games", layout = MainLayout.class)
@RouteAlias(value = "/games", layout = MainLayout.class)
public class GameView extends VerticalLayout implements HasUrlParameter<Integer> {

    private int gameId;

    GamePlayersContainerLayout gamePlayersContainerLayout = null;

    Registration gameBroadcasterRegistration;

    private QuestionModel question;
    private GameModel gameModel = null;

    @Autowired
    private GameService gameService;

    public GameView() {
    }

    @PostConstruct
    void init() {
        setMargin(true);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer parameter) {
        if (parameter == null) {
        } else {
            gameId = parameter;
            refresh(gameService.getGame(gameId));
            refreshRegistration();
        }
    }

    private void refresh(GameModel g) {
        if (gameModel == null) {
            gameModel = g;
            if (gameModel != null) {
                question = gameModel.getCurrentQuestionModel();
                gamePlayersContainerLayout = new GamePlayersContainerLayout(question, gameModel);
                gamePlayersContainerLayout.setWidthFull();
                add(gamePlayersContainerLayout);
            } else {
            }
        } else {
            gameModel = g;
            if (gameModel != null) {
                question = gameModel.getCurrentQuestionModel();
                gamePlayersContainerLayout.update(question, gameModel);
            } else {
                remove(gamePlayersContainerLayout);
            }
        }
    }

    private void refreshRegistration() {
        if (gameBroadcasterRegistration != null) {
            gameBroadcasterRegistration.remove();
        }
        if (gameModel == null) {
            return;
        }
        gameBroadcasterRegistration = GameBroadcaster.register(game -> {
            UI ui = this.getUI().get();
            ui.access(() -> {
                refresh(game);
            });
        }, gameId);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        // refreshRegistration();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        if (gameBroadcasterRegistration != null)
            gameBroadcasterRegistration.remove();
        gameBroadcasterRegistration = null;
    }

}
