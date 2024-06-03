package pl.tcs.po.views.gameCreator;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.RangeInput;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
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

        NumberField maxQuestionsField = new NumberField();
        maxQuestionsField.setEnabled(false);
        maxQuestionsField.setValue(5.0);
        maxQuestionsField.setMaxWidth("45px");

        RangeInput maxQuestionsSlider = new RangeInput();
        maxQuestionsSlider.setMin(1);
        maxQuestionsSlider.setMax(30);
        maxQuestionsSlider.setValue(5.0);
        maxQuestionsSlider.setStep(1.0);
        maxQuestionsSlider.addValueChangeListener(e -> {
            maxQuestionsField.setValue(maxQuestionsSlider.getValue());
        });

        NumberField timeLimitField = new NumberField();
        timeLimitField.setEnabled(false);
        timeLimitField.setValue(10.0);
        timeLimitField.setMaxWidth("45px");

        RangeInput timeLimitSlider = new RangeInput();
        timeLimitSlider.setMin(5);
        timeLimitSlider.setMax(60);
        timeLimitSlider.setValue(10.0);
        timeLimitSlider.setStep(5.0);
        timeLimitSlider.addValueChangeListener(e -> {
            timeLimitField.setValue(timeLimitSlider.getValue());
        });

        Button createGameButton = new Button("Create Game");
        createGameButton.addClickListener(e -> {
            if (gameNameField.isEmpty()) {
                Notification.show("Game name cannot be empty");
                return;
            }
            GameModel game = gameService.createGame(gameNameField.getValue(), maxQuestionsField.getValue().intValue(),
                    (int) timeLimitField.getValue().intValue());
            Notification.show("Game " + gameNameField.getValue() + " created");
            // createGameButton.getUI().ifPresent(ui -> ui.navigate(GameView.class,
            // game.getId()));
        });

        layout.add("Create Game");
        layout.add(gameNameField);
        layout.add("Max questions (1-30)");
        layout.add(new HorizontalLayout(maxQuestionsSlider, maxQuestionsField));
        layout.add("Time limit: (5-60s)");
        layout.add(new HorizontalLayout(timeLimitSlider, timeLimitField));
        layout.add(createGameButton);
        add(layout);
    }
}
