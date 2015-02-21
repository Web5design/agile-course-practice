package ru.unn.agile.Fraction.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.Fraction.Model.Fraction;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final SimpleStringProperty resultNominator = new SimpleStringProperty();
    private final SimpleStringProperty resultNumerator = new SimpleStringProperty();
    private final SimpleStringProperty firstDenominator = new SimpleStringProperty();
    private final SimpleStringProperty firstNumerator = new SimpleStringProperty();
    private final SimpleStringProperty secondDenominator = new SimpleStringProperty();
    private final SimpleStringProperty secondNumerator = new SimpleStringProperty();

    private final StringProperty status = new SimpleStringProperty();

    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    // FXML needs default c-tor for binding
    public ViewModel() {
        resultNominator.set("");
        resultNumerator.set("");
        firstDenominator.set("");
        firstNumerator.set("");
        secondDenominator.set("");
        secondNumerator.set("");
        status.set(Status.WAITING.toString());

        // Add listeners to the input text fields
        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(firstDenominator);
            add(firstNumerator);
            add(secondDenominator);
            add(secondNumerator);
        } };

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public StringProperty resultNominatorProperty() {
        return resultNominator;
    }

    public StringProperty resultNumeratorProperty() {
        return resultNumerator;
    }

    public StringProperty firstDenominatorProperty() {
        return firstDenominator;
    }

    public StringProperty firstNumeratorProperty() {
        return firstNumerator;
    }

    public StringProperty secondDenominatorProperty() {
        return secondDenominator;
    }

    public StringProperty secondNumeratorProperty() {
        return secondNumerator;
    }

    public StringProperty statusProperty() {
        return status;
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (firstDenominator.get().isEmpty() || firstNumerator.get().isEmpty()
                || secondDenominator.get().isEmpty() || secondNumerator.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!firstDenominator.get().isEmpty()) {
                Integer.parseInt(firstDenominator.get());
            }
            if (!firstNumerator.get().isEmpty()) {
                Integer.parseInt(firstNumerator.get());
            }
            if (!secondDenominator.get().isEmpty()) {
                Integer.parseInt(secondDenominator.get());
            }
            if (!secondNumerator.get().isEmpty()) {
                Integer.parseInt(secondNumerator.get());
            }
        } catch (NumberFormatException nfe) {
            inputStatus = Status.BAD_FORMAT;
        }
        try {
            if (inputStatus == Status.READY) {
                new Fraction(Integer.parseInt(firstNumerator.get()),
                                Integer.parseInt(firstDenominator.get()));
                new Fraction(Integer.parseInt(secondNumerator.get()),
                                Integer.parseInt(secondDenominator.get()));
            }
        } catch (IllegalArgumentException iae) {
            inputStatus = Status.IMPOSSIBLE_FRACTION;
        }

        return inputStatus;
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
        }
    }
}

enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Calculate'"),
    BAD_FORMAT("Bad format"),
    IMPOSSIBLE_FRACTION("Such fraction can not exists");

    private final String name;
    private Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
