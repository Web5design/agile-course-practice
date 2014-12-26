package ru.unn.agile.LeftistHeap.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.LeftistHeap.Model.LeftistHeap;
import ru.unn.agile.LeftistHeap.infrastrucure.TxtFileLogger;
import ru.unn.agile.LeftistHeap.viewmodel.ViewModel;

public class LeftistHeapView {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField key;
    @FXML
    private TextField value;
    @FXML
    private TextField newKey;

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
        viewModel.setLogger(new TxtFileLogger("./log.log"));

        final ChangeListener<Boolean> onFocusChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };

        key.textProperty().bindBidirectional(viewModel.keyProperty());
        key.focusedProperty().addListener(onFocusChangeListener);

        value.textProperty().bindBidirectional(viewModel.valueProperty());
        value.focusedProperty().addListener(onFocusChangeListener);

        newKey.textProperty().bindBidirectional(viewModel.newKeyProperty());
        newKey.focusedProperty().addListener(onFocusChangeListener);

        cbHeap.valueProperty().bindBidirectional(viewModel.heapProperty());
        cbHeap.valueProperty().addListener(
                new ChangeListener<LeftistHeap<String>>() {
                    @Override
                    public void changed(
                            final ObservableValue<? extends LeftistHeap<String>> observable,
                            final LeftistHeap<String> oldValue,
                            final LeftistHeap<String> newValue) {
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
