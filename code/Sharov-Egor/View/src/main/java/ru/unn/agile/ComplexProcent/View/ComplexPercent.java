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

        viewModel.setLogger(new TxtLogger("./TxtLogger-lab3.log"));

        final ChangeListener<Boolean> focusChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };

        txtBase.textProperty().bindBidirectional(viewModel.txtBaseProperty());
        txtBase.focusedProperty().addListener(focusChangeListener);

        txtInterestCount.textProperty().bindBidirectional(viewModel.txtInterestCountProperty());
        txtInterestCount.focusedProperty().addListener(focusChangeListener);

        txtPercent.textProperty().bindBidirectional(viewModel.txtPercentProperty());
        txtPercent.focusedProperty().addListener(focusChangeListener);

        dtPkrStart.valueProperty().bindBidirectional(viewModel.dtPkrStartProperty());
        dtPkrStart.focusedProperty().addListener(focusChangeListener);

        dtPkrEnd.valueProperty().bindBidirectional(viewModel.dtPkrEndProperty());
        dtPkrEnd.focusedProperty().addListener(focusChangeListener);


        btnCount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
