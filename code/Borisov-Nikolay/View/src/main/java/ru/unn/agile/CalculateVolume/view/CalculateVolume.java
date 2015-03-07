package ru.unn.agile.CalculateVolume.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.CalculateVolume.infrastructure.TxtLogger;
import ru.unn.agile.CalculateVolume.viewmodel.ViewModel;

public class CalculateVolume {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtFirstParam;
    @FXML
    private TextField txtSecondParam;
    @FXML
    private TextField txtThirdParam;
    @FXML
    private Button btnCalculateVolume;
    @FXML
    private ComboBox<String> cbFigure;

    @FXML
    void initialize() {
        viewModel.setLog(new TxtLogger("./txtLog.log"));
        final ChangeListener<Boolean> focusChangingListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };
        txtFirstParam.textProperty().bindBidirectional(viewModel.stringFirstParamProperty());
        txtFirstParam.focusedProperty().addListener(focusChangingListener);
        txtSecondParam.textProperty().bindBidirectional(viewModel.stringSecondParamProperty());
        txtSecondParam.focusedProperty().addListener(focusChangingListener);
        txtThirdParam.textProperty().bindBidirectional(viewModel.stringThirdParamProperty());
        txtThirdParam.focusedProperty().addListener(focusChangingListener);
        cbFigure.valueProperty().bindBidirectional(viewModel.figureProperty());
        cbFigure.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable,
                                final String oldValue,
                                final String newValue) {
                viewModel.onOperationChanged(oldValue, newValue);
            }
        });

        btnCalculateVolume.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
