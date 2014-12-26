package ru.unn.agile.LeftistHeap.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.LeftistHeap.infrastrucure.LoggerException;
import ru.unn.agile.LeftistHeap.infrastrucure.TxtFileLogger;
import ru.unn.agile.LeftistHeap.viewmodel.ViewModel;

public class LeftistHeap {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField key1;
    @FXML
    private TextField value1;
    @FXML
    private TextField newKey1;

    @FXML
    private Button add;
    @FXML
    private Button getMinimum;
    @FXML
    private Button delete;
    @FXML
    private Button decrease;
    @FXML
    private Button merge;

    @FXML
    private ComboBox<ru.unn.agile.LeftistHeap.Model.LeftistHeap<String>> cbHeap;

    @FXML
    void initialize() {
        try {
            viewModel.setLogger(new TxtFileLogger("./mainLog.log"));
        } catch (LoggerException exception) {
            exception.printStackTrace();
        }

        final ChangeListener<Boolean> focusChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };

        key1.textProperty().bindBidirectional(viewModel.keyProperty());
        key1.focusedProperty().addListener(focusChangeListener);

        value1.textProperty().bindBidirectional(viewModel.valueProperty());
        value1.focusedProperty().addListener(focusChangeListener);

        newKey1.textProperty().bindBidirectional(viewModel.newKeyProperty());
        newKey1.focusedProperty().addListener(focusChangeListener);

        cbHeap.valueProperty().bindBidirectional(viewModel.heapProperty());
        cbHeap.valueProperty().addListener(
                new ChangeListener<ru.unn.agile.LeftistHeap.Model.LeftistHeap<String>>() {
                    @Override
                    public void changed(
                            final ObservableValue<? extends ru.unn.agile.LeftistHeap.Model
                                    .LeftistHeap<String>> observable,
                            final ru.unn.agile.LeftistHeap.Model
                                    .LeftistHeap<String> oldValue,
                            final ru.unn.agile.LeftistHeap.Model
                                    .LeftistHeap<String> newValue) {
                viewModel.onHeapChanged(oldValue, newValue);
            }
        });

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.add();
            }
        });

        getMinimum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.extractMinimum();
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.extract();
            }
        });

        merge.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.merge();
            }
        });

        decrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.decreaseKey();
            }
        });
    }
}
