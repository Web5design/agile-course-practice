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

    private final ILogger log;

    public ViewModel(final ILogger logger) {
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

        log = logger;
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
        log.log("");
    }

    private class InputChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
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
