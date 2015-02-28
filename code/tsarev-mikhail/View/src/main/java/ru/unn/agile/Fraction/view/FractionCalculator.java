package ru.unn.agile.Fraction.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import ru.unn.agile.Fraction.Model.Fraction.Operation;
import ru.unn.agile.Fraction.viewmodel.ViewModel;

import java.awt.*;

public class FractionCalculator {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField firstDenom;
    @FXML
    private TextField secondDenom;
    @FXML
    private Label resultDenom;
    @FXML
    private TextField firstNumer;
    @FXML
    private TextField secondNumer;
    @FXML
    private Label resultNumer;
    @FXML
    private Label statusLabel;
    @FXML
    private ComboBox<Operation> cbOperation;
    @FXML
    private Button calculateButton;

    @FXML
    void initialize() {

        // Two-way binding hasn't supported by FXML yet, so place it in code-behind
        firstNumer.textProperty().bindBidirectional(viewModel.firstNumeratorProperty());
        firstDenom.textProperty().bindBidirectional(viewModel.firstDenominatorProperty());
        secondNumer.textProperty().bindBidirectional(viewModel.secondNumeratorProperty());
        secondDenom.textProperty().bindBidirectional(viewModel.secondDenominatorProperty());
        resultDenom.textProperty().bind(viewModel.resultDenominatorProperty());
        resultNumer.textProperty().bind(viewModel.resultNumeratorProperty());
        statusLabel.textProperty().bind(viewModel.statusProperty());

        cbOperation.valueProperty().bindBidirectional(viewModel.operationProperty());
        cbOperation.itemsProperty().bind(viewModel.operationsProperty());

        calculateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
        calculateButton.disableProperty().bind(viewModel.calculationDisabledProperty());
    }
}
