package ru.unn.agile.LeftistHeap.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import ru.unn.agile.LeftistHeap.Model.HeapNode;
import ru.unn.agile.LeftistHeap.Model.LeftistHeap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewModel {
    private final StringProperty key;

    private final StringProperty value;

    private final StringProperty newKey;

    private final StringProperty result;

    private final StringProperty status;

    private final ObjectProperty<ObservableList<LeftistHeap<String>>> heaps;

    private final ObjectProperty<LeftistHeap<String>> heap;

    private final StringProperty logs;

    private ILogger logger;

    private List<ValueChangeListener> valueChangedListeners;

    public void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Parameter 'logger' can't be null");
        }
        this.logger = logger;
    }

    public ViewModel(final ILogger logger) {
        this();
        setLogger(logger);
    }

    public ViewModel() {
        key = new SimpleStringProperty("");
        newKey = new SimpleStringProperty("");
        value = new SimpleStringProperty("");
        result = new SimpleStringProperty("");
        status = new SimpleStringProperty("");
        logs = new SimpleStringProperty("");

        LeftistHeap<String> defaultSelectedHeap = new LeftistHeap<String>("heap 1");

        heaps = new SimpleObjectProperty<ObservableList<LeftistHeap<String>>>(
                FXCollections.observableArrayList(
                        Arrays.asList(
                                defaultSelectedHeap,
                                new LeftistHeap<String>("heap 2"))));

        heap = new SimpleObjectProperty<>();
        heap.set(defaultSelectedHeap);

        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(key);
            add(value);
            add(newKey);
        } };

        valueChangedListeners = new ArrayList<>();

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    public StringProperty keyProperty() {
        return key;
    }

    public StringProperty valueProperty() {
        return value;
    }

    public StringProperty newKeyProperty() {
        return newKey;
    }

    public StringProperty resultProperty() {
        return result;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getStatus() {
        return statusProperty().get();
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public String getLogs() {
        return logs.get();
    }

    public String getResult() {
        return resultProperty().get();
    }

    public ObjectProperty<ObservableList<LeftistHeap<String>>> heapsProperty() {
        return heaps;
    }
    public final ObservableList<LeftistHeap<String>> getHeaps() {
        return heaps.get();
    }

    public ObjectProperty<LeftistHeap<String>> heapProperty() {
        return heap;
    }

    public void add() {
        String key = keyProperty().get();

        if (validNumber(key)) {
            getSelectedHeap().add(Integer.parseInt(keyProperty().get()), valueProperty().get());
            setOutput(
                    String.format("New element added to heap '%s'", getSelectedHeap().getName()),
                    Status.OK);

            logger.log(String.format(
                    LogMessages.ELEMENT_WAS_ADDED_FORMAT,
                    keyProperty().get(),
                    valueProperty().get(),
                    getSelectedHeap().getName()));
            updateLogs();
            return;
        }

        setOutput("Element was not added", Status.BAD_INPUT);

        logger.log(String.format(
                LogMessages.ELEMENT_WAS_NOT_ADDED_FORMAT,
                keyProperty().get(),
                valueProperty().get(),
                getSelectedHeap().getName()));
        updateLogs();
    }

    public void extract() {
        String key = keyProperty().get();

        if (validNumber(key)) {
            HeapNode<String> node = getSelectedHeap().extractElementWithKey(
                    Integer.parseInt(keyProperty().get()));

            String message = "";

            if (node == null) {
                message = String.format(
                        "Heap '%s' not contain elements with key '%s'",
                        getSelectedHeap().getName(),
                        key);

                String logMessage = String.format(
                        LogMessages.TRY_EXTRACT_NOT_EXIST_ELEMENT,
                        key,
                        getSelectedHeap().getName());
                logger.log(logMessage);
            } else {
                message = node.toString();

                String logMessage = String.format(
                        LogMessages.ELEMENT_WAS_EXTRACTED_FORMAT,
                        node.getKey(),
                        node.getValue(),
                        getSelectedHeap().getName());
                logger.log(logMessage);
            }

            setOutput(message, Status.OK);
            updateLogs();
            return;
        }

        String logMessage = String.format(
                LogMessages.TRY_EXTRACT_BY_BAD_FORMATED_KEY_FORMAT,
                key,
                getSelectedHeap().getName());
        logger.log(logMessage);
        updateLogs();

        setOutput("Any element was not deleted", Status.BAD_INPUT);
    }

    public void extractMinimum() {
        HeapNode<String> node = getSelectedHeap().extractMin();

        if (node == null) {
            setOutput(String.format("Heap '%s' is empty", getSelectedHeap().getName()), Status.OK);

            logger.log(
                    String.format(
                            LogMessages.EXTRACTED_MIN_FROM_EMPTY_FORMAT,
                            getSelectedHeap().getName()
                    ));
            updateLogs();

            return;
        }

        setOutput(node.toString(), Status.OK);
        logger.log(
                String.format(
                        LogMessages.EXTRACTED_MIN_FORMAT,
                        node.getKey(),
                        node.getValue(),
                        getSelectedHeap().getName()
                ));
        updateLogs();
    }

    public void merge() {
        heaps.get().get(1).merge(heaps.get().get(0));

        setOutput(
                String.format(
                        "Merged heap '%s' with heap '%s'",
                        getHeap(1).getName(),
                        getHeap(0).getName()),
                Status.OK);

        logger.log(
                String.format(
                        LogMessages.MERGED_HEAPS_FORMAT,
                        getHeap(1).getName(),
                        getHeap(0).getName())
        );
        updateLogs();
    }

    public void decreaseKey() {
        String key = keyProperty().get();
        String newKey = newKeyProperty().get();

        if (validNumber(key) && validNumber(newKey)) {
            try {
                if (getSelectedHeap().decreaseKey(
                        Integer.parseInt(key),
                        Integer.parseInt(newKey))) {

                    setOutput("Key decreased", Status.OK);
                    logger.log(
                            String.format(
                                    LogMessages.KEY_DECREASED_FORMAT,
                                    key,
                                    newKey,
                                    getSelectedHeap().getName()
                            ));
                    updateLogs();
                    return;
                } else {
                    setOutput(
                            String.format(
                                    "Key '%s' not found in heap '%s'",
                                    key,
                                    getSelectedHeap().getName()),
                            Status.OK);

                    logger.log(
                            String.format(
                                    LogMessages.KEY_FOR_DECREASE_NOT_FOUND_FORMAT,
                                    getSelectedHeap().getName(),
                                    key
                            ));
                    updateLogs();
                    return;
                }
            } catch (IllegalArgumentException exception) {
                setOutput("New key must be less than current", Status.OK);

                logger.log(
                        String.format(
                                LogMessages.GREATER_KEY_FOR_DEVREASE_FORMAT,
                                key,
                                newKey
                        ));
                updateLogs();

                return;
            }
        }

        setOutput("Any key was not decreased", Status.BAD_INPUT);
        logger.log(
                String.format(
                        LogMessages.INVALID_INPUT_FORMAT,
                        key,
                        valueProperty().get(),
                        newKey
                ));
        updateLogs();
    }

    private Boolean validNumber(final String number) {
        if (number.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    private void setOutput(final String message, final Status actionStatus) {
        result.set(message);
        status.set(actionStatus.toString());
    }

    private LeftistHeap<String> getSelectedHeap() {
        return heap.get();
    }

    private LeftistHeap<String> getHeap(final int index) {
        return heaps.get().get(index);
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (newValue && !oldValue) {
            return;
        }

        for (ValueChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                logger.log(String.format(
                        LogMessages.INPUT_WAS_UPDATED_FORMAT,
                        keyProperty().get(),
                        valueProperty().get(),
                        newKeyProperty().get()
                ));
                updateLogs();

                listener.cache();
                break;
            }
        }
    }

    public void onHeapChanged(final LeftistHeap<String> oldHeap,
                              final LeftistHeap<String> newHeap) {
        if (oldHeap.equals(newHeap)) {
            return;
        }

        String message = String.format(
                LogMessages.HEAP_WAS_CHANGED_FORMAT,
                newHeap.getName());
        logger.log(message.toString());
        updateLogs();
    }

    private void updateLogs() {
        StringBuilder record = new StringBuilder();
        for (String log : logger.getLog()) {
            record.append(log + "\n");
        }
        logs.set(record.toString());
    }

    private class ValueChangeListener implements ChangeListener<String> {
        private String prevValue = new String();
        private String currValue = new String();

        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }

            status.set(getStatus());
            currValue = newValue;
        }

        public boolean isChanged() {
            return !prevValue.equals(currValue);
        }

        public void cache() {
            prevValue = currValue;
        }
    }
}

enum Status {
    BAD_INPUT("Bad input"),
    OK("OK");

    private final String name;
    private Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}

final class LogMessages {
    public static final String HEAP_WAS_CHANGED_FORMAT = "Heap was changed to '%s'";

    public static final String INPUT_WAS_UPDATED_FORMAT =
            "Updated input: key - %s, value - %s, newKey - %s";

    public static final String ELEMENT_WAS_ADDED_FORMAT = "Added element --%s, %s-- to '%s'";

    public static final String ELEMENT_WAS_NOT_ADDED_FORMAT =
            "Element --%s, %s-- was not added to '%s'. It have bad format";

    public static final String ELEMENT_WAS_EXTRACTED_FORMAT =
            "Element --%d, %s-- was extracted from '%s'";

    public static final String TRY_EXTRACT_BY_BAD_FORMATED_KEY_FORMAT =
            "Try extracting by bad formatted key '%s' from '%s'";

    public static final String TRY_EXTRACT_NOT_EXIST_ELEMENT =
            "Try extracting not exist element with key '%s' from '%s'";

    public static final String MERGED_HEAPS_FORMAT = "Merged heap '%s' with heap '%s'";

    public static final String EXTRACTED_MIN_FROM_EMPTY_FORMAT =
            "Try extracted minimum element from empty heap '%s'";

    public static final String EXTRACTED_MIN_FORMAT =
            "Extracted minimum element --%s, %s-- from '%s'";

    public static final String KEY_DECREASED_FORMAT =
            "Decrease key %s to %s in '%s'";

    public static final String KEY_FOR_DECREASE_NOT_FOUND_FORMAT =
            "Heap '%s' not contain key %s for decrease";

    public static final String GREATER_KEY_FOR_DEVREASE_FORMAT =
            "Can not decrease key %s to %s";

    public static final String INVALID_INPUT_FORMAT =
            "Invalid input: key - %s, value - %s, newKey - %s";

    private LogMessages() { }
}
