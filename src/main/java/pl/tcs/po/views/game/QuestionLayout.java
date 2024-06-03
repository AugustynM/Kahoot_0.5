package pl.tcs.po.views.game;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import pl.tcs.po.model.Answers;
import pl.tcs.po.model.CorrectAnswers;
import pl.tcs.po.model.QuestionModel;

public class QuestionLayout extends VerticalLayout {
    private Button submitButton = new Button("Submit");
    private QuestionModel question;
    CheckboxGroup<String> questionGroup = new CheckboxGroup<>();

    public QuestionLayout(QuestionModel q) {
        if (q != null) {
            createQuestionGUI(q);
        } else {
            add("No current question");
        }
    }

    void update(QuestionModel q) {
        getUI().ifPresent(ui -> ui.access(() -> {
            removeAll();
            if (q != null) {
                createQuestionGUI(q);
            } else {
                add("No current question");
            }
        }));
    }

    void createQuestionGUI(QuestionModel q) {
        question = q;
        questionGroup.setLabel(question.getQuestion());
        add(questionGroup, submitButton);

        Answers answers = question.getAnswers();
        List<String> answerLabels = new ArrayList<>();
        if (answers.getAnswerA() != null)
            answerLabels.add(answers.getAnswerA());
        if (answers.getAnswerB() != null)
            answerLabels.add(answers.getAnswerB());
        if (answers.getAnswerC() != null)
            answerLabels.add(answers.getAnswerC());
        if (answers.getAnswerD() != null)
            answerLabels.add(answers.getAnswerD());
        if (answers.getAnswerE() != null)
            answerLabels.add(answers.getAnswerE());
        if (answers.getAnswerF() != null)
            answerLabels.add(answers.getAnswerF());

        CorrectAnswers correctAnswers = question.getCorrectAnswers();

        questionGroup.setItems(answerLabels);
        submitButton = new Button("Submit");
        submitButton.addClickListener(e -> {
            if (questionGroup.getValue() != null) {
                boolean isCorrect = true;
                List<String> selectedAnswers = new ArrayList<>(questionGroup.getValue());
                if (correctAnswers.isAnswerACorrect() != selectedAnswers.contains(answers.getAnswerA()))
                    isCorrect = false;
                if (correctAnswers.isAnswerBCorrect() != selectedAnswers.contains(answers.getAnswerB()))
                    isCorrect = false;
                if (correctAnswers.isAnswerCCorrect() != selectedAnswers.contains(answers.getAnswerC()))
                    isCorrect = false;
                if (correctAnswers.isAnswerDCorrect() != selectedAnswers.contains(answers.getAnswerD()))
                    isCorrect = false;
                if (correctAnswers.isAnswerECorrect() != selectedAnswers.contains(answers.getAnswerE()))
                    isCorrect = false;
                if (correctAnswers.isAnswerFCorrect() != selectedAnswers.contains(answers.getAnswerF()))
                    isCorrect = false;
                if (isCorrect)
                    Notification.show("Correct!");
                else
                    Notification.show("Incorrect!");
            }
        });
        submitButton.addClickShortcut(Key.ENTER);

    }

}
