package ru.unn.agile.Fraction.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewModel {
    private final SimpleStringProperty resultNominator = new SimpleStringProperty();
    private final SimpleStringProperty resultNumerator = new SimpleStringProperty();

    // FXML needs default c-tor for binding
    public ViewModel() {
        resultNominator.set("");
        resultNumerator.set("");
    }

    public StringProperty resultNominatorProperty() {
        return resultNominator;
    }

    public StringProperty resultNumeratorProperty() {
        return resultNumerator;
    }
}
