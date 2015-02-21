package ru.unn.agile.Fraction.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewModel {
    private final SimpleStringProperty resultNominator = new SimpleStringProperty();
    private final SimpleStringProperty resultNumerator = new SimpleStringProperty();
    private final SimpleStringProperty firstNominator = new SimpleStringProperty();
    private final SimpleStringProperty firstNumerator = new SimpleStringProperty();
    private final SimpleStringProperty secondNominator = new SimpleStringProperty();
    private final SimpleStringProperty secondNumerator = new SimpleStringProperty();

    private final StringProperty status = new SimpleStringProperty();

    // FXML needs default c-tor for binding
    public ViewModel() {
        resultNominator.set("");
        resultNumerator.set("");
        firstNominator.set("");
        firstNumerator.set("");
        secondNominator.set("");
        secondNumerator.set("");
        status.set(Status.WAITING.toString());
    }

    public StringProperty resultNominatorProperty() {
        return resultNominator;
    }

    public StringProperty resultNumeratorProperty() {
        return resultNumerator;
    }

    public StringProperty firstNominatorProperty() {
        return firstNominator;
    }

    public StringProperty firstNumeratorProperty() {
        return firstNumerator;
    }

    public StringProperty secondNominatorProperty() {
        return secondNominator;
    }

    public StringProperty secondNumeratorProperty() {
        return secondNumerator;
    }

    public StringProperty statusProperty() {
        return status;
    }
}

enum Status {
    WAITING("Please provide input data");

    private final String name;
    private Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
