package ru.unn.agile.ComplexProcent.View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import ru.unn.agile.ComplexProcent.ViewModel.ViewModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ru.unn.agile.ComplexProcent.Infrastructure.TxtLogger;

public class ComplexPercent extends VBox {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtBase;
    @FXML
    private TextField txtInterestCount;
    @FXML
    private TextField txtPercent;
    @FXML
    private Button btnCount;
    @FXML
    private DatePicker dtPkrStart;
    @FXML
    private DatePicker dtPkrEnd;

    @FXML
    void initialize() {

        final ChangeListener<Boolean> focusChangedListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };

        viewModel.setLogger(new TxtLogger("TxtLog-ComplexPercents.txt"));

        txtBase.textProperty().bindBidirectional(viewModel.txtBaseProperty());
        txtBase.focusedProperty().addListener(focusChangedListener);

        txtInterestCount.textProperty().bindBidirectional(viewModel.txtInterestCountProperty());
        txtInterestCount.focusedProperty().addListener(focusChangedListener);

        txtPercent.textProperty().bindBidirectional(viewModel.txtPercentProperty());
        txtPercent.focusedProperty().addListener(focusChangedListener);

        dtPkrStart.valueProperty().bindBidirectional(viewModel.dtPkrStartProperty());
        dtPkrStart.focusedProperty().addListener(focusChangedListener);

        dtPkrEnd.valueProperty().bindBidirectional(viewModel.dtPkrEndProperty());
        dtPkrEnd.focusedProperty().addListener(focusChangedListener);

        btnCount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
