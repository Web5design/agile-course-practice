package ru.unn.agile.QuadraticEquation.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.QuadraticEquation.viewmodel.ViewModel;
import ru.unn.agile.QuadraticEquation.infrastructure.TxtLogger;

public class SolutionOfQuadraticEquations {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtCoefA;
    @FXML
    private TextField txtCoefB;
    @FXML
    private TextField txtCoefC;
    @FXML
    private Button btnSolve;

    @FXML
    void initialize() {

        viewModel.setLogger(new TxtLogger("./TxtLogger.log"));

        final ChangeListener<Boolean> onFocusChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };

        txtCoefA.textProperty().bindBidirectional(viewModel.firstCoefficientProperty());
        txtCoefA.focusedProperty().addListener(onFocusChangeListener);

        txtCoefB.textProperty().bindBidirectional(viewModel.secondCoefficientProperty());
        txtCoefB.focusedProperty().addListener(onFocusChangeListener);

        txtCoefC.textProperty().bindBidirectional(viewModel.thirdCoefficientProperty());
        txtCoefC.focusedProperty().addListener(onFocusChangeListener);

        btnSolve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.solve();
            }
        });
    }
}
