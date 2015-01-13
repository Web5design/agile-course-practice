package ru.unn.agile.QuadraticEquation.viewmodel;

import javafx.beans.property.*;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.QuadraticEquation.Model.QuadraticEquation;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final StringProperty firstCoef = new SimpleStringProperty("");
    private final StringProperty secondCoef = new SimpleStringProperty("");
    private final StringProperty thirdCoef = new SimpleStringProperty("");

    private final StringProperty firstRootResult = new SimpleStringProperty("");
    private final StringProperty secondRootResult = new SimpleStringProperty("");

    private final BooleanProperty solvingDisabled = new SimpleBooleanProperty();

    private final StringProperty status = new SimpleStringProperty(systemStatus.WAITING.toString());

    private final StringProperty logs = new SimpleStringProperty("");

    private final List<ValueCachingChangeListener> valueChangedListeners = new ArrayList<>();

    private ILogger logger;

    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    private void bindEquationDisabled() {
        BooleanBinding couldSolve = new BooleanBinding() {
            {
                super.bind(firstCoef, secondCoef, thirdCoef);
            }

            @Override
            protected boolean computeValue() {
                return getEquationStatus() == systemStatus.READY;
            }
        };
        solvingDisabled.bind(couldSolve.not());
    }

    private void createFieldsValueChangedListeners() {
        final List<StringProperty> fields = new ArrayList<StringProperty>() {
            {
                add(firstCoef);
                add(secondCoef);
                add(thirdCoef);
            }
        };
        for (StringProperty field : fields) {
            final ValueCachingChangeListener listener = new ValueCachingChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    private void init() {
        bindEquationDisabled();
        createFieldsValueChangedListeners();
    }

    public void solve() {
        if (solvingDisabled.get()) {
            return;
        }
        try {
            QuadraticEquation equation = new QuadraticEquation(
                    Double.parseDouble(firstCoef.get()),
                    Double.parseDouble(secondCoef.get()),
                    Double.parseDouble(thirdCoef.get()));

            firstRootResult.set("x = " + equation.getFirstRoot());
            secondRootResult.set("x = " + equation.getSecondRoot());
            status.set(systemStatus.SUCCESS.toString());
        } catch (ArithmeticException iag) {
            status.set(systemStatus.NO_ROOTS.toString());
        } catch (IllegalArgumentException ae) {
            status.set(systemStatus.INCORRECT_COEF.toString());
        }
        StringBuilder message = new StringBuilder(TypeOfLogMessages.SOLVE_WAS_PRESSED);
        message.append("Coefficients")
                .append(": a = ").append(firstCoef.get())
                .append("; b = ").append(secondCoef.get())
                .append("; c = ").append(thirdCoef.get()).append(".");
        logger.log(message.toString());
        updateRecordsInLog();
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        for (ValueCachingChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                StringBuilder message = new StringBuilder(TypeOfLogMessages.INPUT_EDITING_FINISHED);
                message.append("Input coefficients are: [")
                        .append(firstCoef.get()).append("; ")
                        .append(secondCoef.get()).append("; ")
                        .append(thirdCoef.get()).append("]");
                logger.log(message.toString());
                updateRecordsInLog();

                listener.cache();
                break;
            }
        }
    }

    public StringProperty firstCoefficientProperty() {
        return firstCoef;
    }

    public StringProperty secondCoefficientProperty() {
        return secondCoef;
    }

    public StringProperty thirdCoefficientProperty() {
        return thirdCoef;
    }

    public StringProperty firstRootResultProperty() {
        return firstRootResult;
    }

    public StringProperty secondRootResultProperty() {
        return secondRootResult;
    }

    public final String getFirstRootResult() {
        return firstRootResult.get();
    }

    public final String getSecondRootResult() {
        return secondRootResult.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getStatus() {
        return status.get();
    }

    public BooleanProperty solvingDisabledProperty() {
        return solvingDisabled;
    }

    public final boolean getSolvingDisabledProperty() {
        return solvingDisabled.get();
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    public final List<String> getLog() {
        return logger.getLog();
    }


    private systemStatus getEquationStatus() {
        systemStatus equationStatus = systemStatus.READY;
        if (firstCoef.get().isEmpty() || secondCoef.get().isEmpty() || thirdCoef.get().isEmpty()) {
            equationStatus = systemStatus.WAITING;
        }
        try {
            if (!firstCoef.get().isEmpty()) {
                Double.parseDouble(firstCoef.get());
            }
            if (!secondCoef.get().isEmpty()) {
                Double.parseDouble(secondCoef.get());
            }
            if (!thirdCoef.get().isEmpty()) {
                Double.parseDouble(thirdCoef.get());
            }
        } catch (NumberFormatException nfe) {
            equationStatus = systemStatus.BAD_FORMAT;
        }

        return equationStatus;
    }

    private void updateRecordsInLog() {
        List<String> filledLog = logger.getLog();
        String record = new String();
        for (String log : filledLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    private class ValueCachingChangeListener implements ChangeListener<String> {
        private String pastValue = new String();
        private String presentValue = new String();
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }
            status.set(getEquationStatus().toString());
            presentValue = newValue;
        }
        public boolean isChanged() {
            return !pastValue.equals(presentValue);
        }
        public void cache() {
            pastValue = presentValue;
        }
    }
}
