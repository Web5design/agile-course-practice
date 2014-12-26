package ru.unn.agile.Deque.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.Deque.model.Deque;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ViewModel {
    private ILogger logger;
    private final StringProperty txtItem = new SimpleStringProperty();
    private final StringProperty status   = new SimpleStringProperty();
    private final BooleanProperty isAddingDisabled  = new SimpleBooleanProperty();
    private final StringProperty logs = new SimpleStringProperty();

    private final Deque<Integer> deque = Deque.create();

    private final InputChangeListener valueChangedListener = new InputChangeListener();

    private class InputChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            String inputStatus = getInputStatus().toString();
            status.set(inputStatus);
            if (inputStatus == ViewStatus.BAD_FORMAT.toString()) {
                log(ILogger.Level.ERROR, "Incorrect item: " + newValue);
            } else {
                log(ILogger.Level.DEBUG, "Item to add: " + newValue);
            }
        }
    }

    public ViewModel() {
        txtItem.set("");
        status.set(ViewStatus.WAITING.toString());

        BooleanBinding canAdd = new BooleanBinding() {
            {
                super.bind(txtItem);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == ViewStatus.READY;
            }
        };
        isAddingDisabled.bind(canAdd.not());

        txtItem.addListener(valueChangedListener);
    }

    public void addFirst() {
        if (isAddingDisabled.get()) {
            return;
        }

        Integer item = Integer.parseInt(getTxtItem());
        deque.addFirst(item);
        log(ILogger.Level.INFO, "Added as first: " + getTxtItem());
    }

    public void addLast() {
        if (isAddingDisabled.get()) {
            return;
        }

        Integer item = Integer.parseInt(getTxtItem());
        deque.addLast(item);
        log(ILogger.Level.INFO, "Added as last: " + getTxtItem());
    }

    public void getFirst() {
        log(ILogger.Level.DEBUG, "Getting the first one");
        getItem(new GetFirstCommand());
    }

    public void getLast() {
        log(ILogger.Level.DEBUG, "Getting the last one");
        getItem(new GetLastCommand());
    }

    public void removeFirst() {
        log(ILogger.Level.DEBUG, "Removing the first one");
        getItem(new RemoveFirstCommand());
    }

    public void removeLast() {
        log(ILogger.Level.DEBUG, "Removing the last one");
        getItem(new RemoveLastCommand());
    }

    public void setLogger(final ILogger newLogger) {
        logger = newLogger;
    }

    public List<String> getLog() {
        if (logger != null) {
            return logger.getLog();
        }
        return new ArrayList<String>();
    }

    private void log(final ILogger.Level level, final String msg) {
        if (logger == null) {
            return;
        }

        logger.log(level, msg);
        refreshLogs();
    }

    private void refreshLogs() {
        if (logger == null) {
            return;
        }

        List<String> storedLog = logger.getLog();
        String logToView = "";
        for (String s : storedLog) {
            logToView += s + "\n";
        }
        logs.set(logToView);
    }

    public StringProperty txtItemProperty() {
        return txtItem;
    }

    public final String getTxtItem() {
        return txtItem.get();
    }

    public BooleanProperty isAddingDisabledProperty() {
        return isAddingDisabled;
    }

    public final boolean getIsAddingDisabled() {
        return isAddingDisabled.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getStatus() {
        return status.get();
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    public boolean isDequeEmpty() {
        return deque.isEmpty();
    }

    public interface Command {
        Integer execute(final Deque<Integer> deque);
    }

    public class GetFirstCommand implements Command {
        public Integer execute(final Deque<Integer> deque) {
            return deque.getFirst();
        }
    }

    public class GetLastCommand implements Command {
        public Integer execute(final Deque<Integer> deque) {
            return deque.getLast();
        }
    }

    public class RemoveFirstCommand implements Command {
        public Integer execute(final Deque<Integer> deque) {
            return deque.removeFirst();
        }
    }

    public class RemoveLastCommand implements Command {
        public Integer execute(final Deque<Integer> deque) {
            return deque.removeLast();
        }
    }

    private void getItem(final Command getter) {
        try {
            Integer item = getter.execute(deque);
            txtItem.set(item.toString());
            status.set(ViewStatus.SUCCESS.toString());
            log(ILogger.Level.INFO, "Managed to get item: " + item.toString());
        } catch (NoSuchElementException nsee) {
            txtItem.set("");
            status.set(ViewStatus.EMPTY.toString());
            log(ILogger.Level.ERROR, "Unable to retrieve an item");
        }
    }

    private ViewStatus getInputStatus() {
        ViewStatus inputStatus = ViewStatus.READY;
        if (getTxtItem().isEmpty()) {
            inputStatus = ViewStatus.WAITING;
            return inputStatus;
        }

        try {
            Integer.parseInt(getTxtItem());
        } catch (NumberFormatException nfe) {
            inputStatus = ViewStatus.BAD_FORMAT;
        }

        return inputStatus;
    }
}

enum ViewStatus {
    WAITING("Insert an item please"),
    READY("Press add button"),
    BAD_FORMAT("Incorrect data"),
    EMPTY("Container is empty"),
    SUCCESS("Success");

    private final String name;

    private ViewStatus(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
