package ru.unn.agile.CalculateVolume.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
        txtFirstParam.textProperty().bindBidirectional(viewModel.stringFirstParamProperty());
        txtSecondParam.textProperty().bindBidirectional(viewModel.stringSecondParamProperty());
        txtThirdParam.textProperty().bindBidirectional(viewModel.stringThirdParamProperty());
        cbFigure.valueProperty().bindBidirectional(viewModel.figureProperty());
        btnCalculateVolume.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
