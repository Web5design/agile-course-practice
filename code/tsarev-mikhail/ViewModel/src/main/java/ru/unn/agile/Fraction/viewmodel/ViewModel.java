package ru.unn.agile.Fraction.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.Fraction.Model.Fraction;

import java.util.ArrayList;
import java.util.List;

import ru.unn.agile.Fraction.Model.Fraction.Operation;

public class ViewModel {
    private final SimpleStringProperty resultDenominator = new SimpleStringProperty();
    private final SimpleStringProperty resultNumerator = new SimpleStringProperty();
    private final SimpleStringProperty firstNumerator = new SimpleStringProperty();
    private final SimpleStringProperty firstDenominator = new SimpleStringProperty();
    private final SimpleStringProperty secondNumerator = new SimpleStringProperty();
    private final SimpleStringProperty secondDenominator = new SimpleStringProperty();

    private final StringProperty ioStatus = new SimpleStringProperty();
    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();

    private final List<InputChangeListener> inputChangedListeners = new ArrayList<>();
    private final ObjectProperty<ObservableList<Operation>> operations =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Operation.values()));
    private final ObjectProperty<Operation> operation = new SimpleObjectProperty<>();

    private ILogger log;

    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        init();
        log = logger;
    }

    private void init() {
        resultDenominator.set("");
        resultNumerator.set("");
        firstDenominator.set("");
        firstNumerator.set("");
        secondDenominator.set("");
        secondNumerator.set("");
        operation.set(Operation.ADD);
        ioStatus.set(IOStatus.WAITING.toString());

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(firstNumerator, firstDenominator, secondNumerator, secondDenominator);
            }
            @Override
            protected boolean computeValue() {
                return getIOStatus() == IOStatus.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());

        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(firstNumerator);
            add(firstDenominator);
            add(secondNumerator);
            add(secondDenominator);
        } };

        for (StringProperty field : fields) {
            final InputChangeListener listener = new InputChangeListener();
            field.addListener(listener);
            inputChangedListeners.add(listener);
        }
        operation.addListener(new OperationChangeListener());
    }

    public StringProperty resultNumeratorProperty() {
        return resultNumerator;
    }

    public StringProperty resultDenominatorProperty() {
        return resultDenominator;
    }

    public StringProperty firstNumeratorProperty() {
        return firstNumerator;
    }

    public StringProperty firstDenominatorProperty() {
        return firstDenominator;
    }

    public StringProperty secondNumeratorProperty() {
        return secondNumerator;
    }

    public StringProperty secondDenominatorProperty() {
        return secondDenominator;
    }

    public StringProperty ioStatusProperty() {
        return ioStatus;
    }

    public ObjectProperty<Operation> operationProperty() {
        return operation;
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public ObjectProperty<ObservableList<Operation>> operationsProperty() {
        return operations;
    }

    private void logCalculation() {
        StringBuilder message = new StringBuilder(LogMessages.CALCULATE_PRESSED);
        message.append(resultNumerator.get());
        message.append("/");
        message.append(resultDenominator.get());
        log.log(message.toString());
    }

    public void calculate() {
        if (calculationDisabled.get()) {
            return;
        }

        Fraction f1 = new Fraction(Integer.parseInt(firstNumerator.get()),
                                    Integer.parseInt(firstDenominator.get()));
        Fraction f2 = new Fraction(Integer.parseInt(secondNumerator.get()),
                                    Integer.parseInt(secondDenominator.get()));

        Fraction result = operation.get().apply(f1, f2);
        resultNumerator.set(Integer.toString(result.getNumerator()));
        resultDenominator.set(Integer.toString(result.getDenominator()));
        ioStatus.set(IOStatus.SUCCESS.toString());
        logCalculation();
    }

    private boolean hasEmptyInputFields() {
        return firstDenominator.get().isEmpty() || firstNumerator.get().isEmpty()
                || secondDenominator.get().isEmpty() || secondNumerator.get().isEmpty();
    }

    private IOStatus getIOStatus() {
        IOStatus status = IOStatus.READY;
        if (hasEmptyInputFields()) {
            status = IOStatus.WAITING;
        } else {
            try {
                try {
                    Fraction operand1 = new Fraction(Integer.parseInt(firstNumerator.get()),
                                        Integer.parseInt(firstDenominator.get()));
                    Fraction operand2 = new Fraction(Integer.parseInt(secondNumerator.get()),
                            Integer.parseInt(secondDenominator.get()));
                    operation.get().apply(operand1, operand2);
                } catch (ArithmeticException ex) {
                    status = IOStatus.DIVISION_BY_ZERO;
                }
            } catch (NumberFormatException ex) {
                status = IOStatus.BAD_FORMAT;
            }
        }

        return status;
    }

    private void logInputUpdate() {
        StringBuilder message = new StringBuilder(LogMessages.INPUT_UPDATED);
        message.append("first numerator: ");
        message.append(firstNumerator.get());
        message.append(",first denominator: ");
        message.append(firstDenominator.get());
        message.append(",second numerator: ");
        message.append(secondNumerator.get());
        message.append(",second denominator: ");
        message.append(secondDenominator.get());
        log.log(message.toString());
    }

    private void logOperationUpdate() {
        StringBuilder message = new StringBuilder(LogMessages.OPERATION_CHANGED);
        message.append(operation.get().toString());
        log.log(message.toString());
    }

    public final List<String> getLog() {
        return log.getLog();
    }

    private class InputChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            logInputUpdate();
            ioStatus.set(getIOStatus().toString());
        }
    }

    private class OperationChangeListener implements ChangeListener<Operation> {
        @Override
        public void changed(final ObservableValue<? extends Operation> observable,
                            final Operation oldValue, final Operation newValue) {
            logOperationUpdate();
            ioStatus.set(getIOStatus().toString());
        }
    }
}

enum IOStatus {
    WAITING("Please provide input data"),
    READY("Press 'Calculate'"),
    BAD_FORMAT("Bad format"),
    DIVISION_BY_ZERO("Division by zero occurs somewhere"),
    SUCCESS("Success");

    private final String name;
    private IOStatus(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}

final class LogMessages {
    public static final String INPUT_UPDATED = "User updated input: ";
    public static final String OPERATION_CHANGED = "Set operation: ";
    public static final String CALCULATE_PRESSED = "'Calculate' pressed, result: ";

    private LogMessages() { }
}
