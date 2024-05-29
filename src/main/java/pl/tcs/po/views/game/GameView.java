package pl.tcs.po.views.game;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import jakarta.annotation.PostConstruct;
import pl.tcs.po.model.GameModel;
import pl.tcs.po.model.QuestionModel;
import pl.tcs.po.service.GameService;
import pl.tcs.po.views.MainLayout;
import pl.tcs.po.webClient.QuestionsClient;

@PageTitle("Kahoot v 0.5")
@Route(value = "/games", layout = MainLayout.class)
@RouteAlias(value = "/games", layout = MainLayout.class)
public class GameView extends VerticalLayout implements HasUrlParameter<Integer> {

    private int gameId;

    VerticalLayout questionLayout;
    VerticalLayout errorLayout = new GameNotFoundLayout();

    private QuestionModel question;
    private GameModel gameModel = null;

    @Autowired
    private QuestionsClient questionsClient;

    @Autowired
    private GameService gameService;

    public GameView() {
    }

    @PostConstruct
    void init() {
        setMargin(true);
        questionLayout = errorLayout;
        add(questionLayout);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer parameter) {
        if (parameter == null) {
            replace(questionLayout, errorLayout);
        } else {
            gameId = parameter;
            gameModel = gameService.getGame(gameId);

            if (gameModel != null && gameModel.getCurrentQuestionModel() != null) {
                question = gameModel.getCurrentQuestionModel();
                VerticalLayout newQuestionLayout = new QuestionLayout(question);
                this.replace(questionLayout, newQuestionLayout);
            }
            else {
                replace(questionLayout, errorLayout);
            }
        }
    }

}
