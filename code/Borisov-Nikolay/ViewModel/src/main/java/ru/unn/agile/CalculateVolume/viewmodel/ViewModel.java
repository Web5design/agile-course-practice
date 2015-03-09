package ru.unn.agile.CalculateVolume.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.CalculateVolume.Model.*;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {

    private final StringProperty stringFirstParam = new SimpleStringProperty();
    private final StringProperty stringSecondParam = new SimpleStringProperty();
    private final StringProperty stringThirdParam = new SimpleStringProperty();
    private final StringProperty labelFirstParamName = new SimpleStringProperty();
    private final StringProperty labelSecondParamName = new SimpleStringProperty();
    private final StringProperty labelThirdParamName = new SimpleStringProperty();
    private final StringProperty calculateLogs = new SimpleStringProperty();
    private final StringProperty figure = new SimpleStringProperty();
    private final ObjectProperty<ObservableList<String>> figures =
            new SimpleObjectProperty<>(FXCollections.
                    observableArrayList("Cone", "Cylinder", "Ellipsoid", "Parallelepiped"));
    private final StringProperty calculateStatus = new SimpleStringProperty();
    private final StringProperty calculateResult = new SimpleStringProperty();
    private final BooleanProperty calculateDisabled = new SimpleBooleanProperty();
    private final BooleanProperty thirdParamDisabled = new SimpleBooleanProperty();
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();
    private final List<ComboBoxElementChangeListener> comboBoxElementChangeListener =
            new ArrayList<>();
    private List<ValCashChangeListener> valueCachingChangedListeners;
    private ILogger logger;

    public void setLog(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public ViewModel() {
        initialization();
    }

    public ViewModel(final ILogger logger) {
        setLog(logger);
        initialization();
    }

    private void initialization() {
        calculateStatus.set(CalculateStatus.WAITING.toString());
        calculateDisabled.set(true);
        thirdParamDisabled.set(true);
        stringFirstParam.set("");
        stringSecondParam.set("");
        stringThirdParam.set("");
        labelFirstParamName.set("Radius");
        labelSecondParamName.set("Height");
        labelThirdParamName.set("");
        calculateStatus.set(CalculateStatus.WAITING.toString());
        calculateResult.set("");
        figure.set("Cone");

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(stringFirstParam);
                super.bind(stringSecondParam);
                super.bind(stringThirdParam);
            }

            @Override
            protected boolean computeValue() {
                return getInputStatus() == CalculateStatus.READY;
            }
        };
        calculateDisabled.bind(couldCalculate.not());
        BooleanBinding whatFormula = new BooleanBinding() {
            {
                super.bind(figure);
            }

            @Override
            protected boolean computeValue() {
                return getInputStatus() == CalculateStatus.READY;
            }
        };
        valueCachingChangedListeners = new ArrayList<>();
        final List<StringProperty> fieldsText = new ArrayList<StringProperty>() {
            {
                add(stringFirstParam);
                add(stringSecondParam);
                add(stringThirdParam);
            }
        };
        for (StringProperty val : fieldsText) {
            final ValCashChangeListener listenerCash = new ValCashChangeListener();
            val.addListener(listenerCash);
            valueCachingChangedListeners.add(listenerCash);
        }
        for (StringProperty field : fieldsText) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
        final List<StringProperty> fieldsComboBox = new ArrayList<StringProperty>() {
            {
                add(figure);
            }
        };
        for (StringProperty field : fieldsComboBox) {
            final ComboBoxElementChangeListener listener = new ComboBoxElementChangeListener();
            field.addListener(listener);
            comboBoxElementChangeListener.add(listener);
        }
    }

    public void onFocusChanged(final Boolean oldVal, final Boolean newVal) {
        if (!oldVal && newVal) {
            return;
        }
        for (ValCashChangeListener listener : valueCachingChangedListeners) {
            if (listener.isChanged()) {
                StringBuilder message = new StringBuilder(LoggingInfo.FINIS_EDITD.toString());
                message.append("Input arguments are: [")
                        .append(stringFirstParam.get()).append("; ")
                        .append(stringSecondParam.get());
                if (!getThirdParamDisabled()) {
                    message.append("; ").append(stringThirdParam.get());
                }
                message.append("] ");
                logger.log(message.toString());
                updateLogs();
                listener.cache();
                break;
            }
        }
    }

    public void onOperationChanged(final String oldValue, final String newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }
        StringBuilder mes = new StringBuilder(LoggingInfo.CHANGED_OPERATION.toString());
        mes.append(newValue.toString());
        logger.log(mes.toString());
        updateLogs();
    }

    private class ValCashChangeListener implements ChangeListener<String> {
        private String lastValue = new String();
        private String nowValue = new String();

        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldVal, final String newVal) {
            if (oldVal.equals(newVal)) {
                return;
            }
            calculateStatus.set(getInputStatus().toString());
            nowValue = newVal;
        }

        public boolean isChanged() {
            return !lastValue.equals(nowValue);
        }

        public void cache() {
            lastValue = nowValue;
        }
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = new String();
        for (String log : fullLog) {
            record += log + "\n";
        }
        calculateLogs.set(record);
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    public StringProperty stringFirstParamProperty() {
        return stringFirstParam;
    }

    public final String getStringFirstParam() {
        return stringFirstParam.get();
    }

    public StringProperty stringSecondParamProperty() {
        return stringSecondParam;
    }

    public final String getStringSecondParam() {
        return stringSecondParam.get();
    }

    public StringProperty stringThirdParamProperty() {
        return stringThirdParam;
    }

    public final String getStringThirdParam() {
        return stringThirdParam.get();
    }

    public StringProperty labelFirstParamNameProperty() {
        return labelFirstParamName;
    }

    public final String getLabelFirstParamName() {
        return labelFirstParamName.get();
    }

    public StringProperty labelSecondParamNameProperty() {
        return labelSecondParamName;
    }

    public final String getLabelSecondParamName() {
        return labelSecondParamName.get();
    }

    public StringProperty labelThirdParamNameProperty() {
        return labelThirdParamName;
    }

    public final String getLabelThirdParamName() {
        return labelThirdParamName.get();
    }

    public StringProperty calculateLogsProperty() {
        return calculateLogs;
    }

    public final String getCalculateLogs() {
        return calculateLogs.get();
    }

    public StringProperty figureProperty() {
        return figure;
    }

    public ObjectProperty<ObservableList<String>> figuresProperty() {
        return figures;
    }

    public final ObservableList<String> getFigures() {
        return figures.get();
    }

    public StringProperty calculateStatusProperty() {
        return calculateStatus;
    }

    public final String getCalculateStatus() {
        return calculateStatus.get();
    }

    public StringProperty calculateResultProperty() {
        return calculateResult;
    }

    public final String getCalculateResult() {
        return calculateResult.get();
    }

    public BooleanProperty calculateDisabledProperty() {
        return calculateDisabled;
    }

    public final boolean getCalculateDisabled() {
        return calculateDisabled.get();
    }

    public BooleanProperty thirdParamDisabledProperty() {
        return thirdParamDisabled;
    }

    public final boolean getThirdParamDisabled() {
        return thirdParamDisabled.get();
    }

    public void calculate() {
        if (calculateDisabled.get()) {
            return;
        }
        double param1 = Double.parseDouble(stringFirstParam.get());
        double param2 = Double.parseDouble(stringSecondParam.get());
        double param3 = 0;
        if (stringThirdParam.get() != "") {
            param3 = Double.parseDouble(stringThirdParam.get());
        }
        if (figure.get().toString() == "Cone") {
            Cone cone = new Cone(param1, param2);
            calculateResult.set(Double.toString(cone.calculateVolume()));
        }
        if (figure.get().toString().equalsIgnoreCase("Cylinder")) {
            Cylinder cylinder = new Cylinder(param1, param2);
            calculateResult.set(Double.toString(cylinder.calculateVolume()));
        }
        if (figure.get().toString().equalsIgnoreCase("Ellipsoid")) {
            Ellipsoid ellipsoid = new Ellipsoid(param1, param2, param3);
            calculateResult.set(Double.toString(ellipsoid.calculateVolume()));
        }
        if (figure.get().toString().equalsIgnoreCase("Parallelepiped")) {
            Parallelepiped parallelepiped = new Parallelepiped(param1, param2, param3);
            calculateResult.set(Double.toString(parallelepiped.calculateVolume()));
        }
        calculateStatus.set(CalculateStatus.SUCCESS.toString());

        StringBuilder message = new StringBuilder(LoggingInfo.PRESSED_CALCULATE.toString());
        message.append("Arguments")
                .append(": ").append(labelFirstParamName.get()).append(" = ")
                .append(stringFirstParam.get())
                .append("; ").append(labelSecondParamName.get()).append(" = ")
                .append(stringSecondParam.get());
        if (!getThirdParamDisabled()) {
            message.append("; ").append(labelThirdParamName.get())
                    .append(" = ").append(stringThirdParam.get());
        }
        message.append(" Figure: ").append(figure.get().toString()).append(".");
        logger.log(message.toString());
        updateLogs();
    }

    private CalculateStatus getInputStatus() {
        CalculateStatus inputStatus = CalculateStatus.READY;
        if (isInputDataFull()) {
            inputStatus = CalculateStatus.WAITING;
        }
        try {
            if (!stringFirstParam.get().isEmpty()) {
                Double.parseDouble(stringFirstParam.get());
            }
            if (!stringSecondParam.get().isEmpty()) {
                Double.parseDouble(stringSecondParam.get());
            }
            if (!stringThirdParam.get().isEmpty()) {
                Double.parseDouble(stringThirdParam.get());
            }
        } catch (NumberFormatException nfe) {
            inputStatus = CalculateStatus.BAD_FORMAT_OF_INPUT;
        }
        return inputStatus;
    }

    private boolean isInputDataFull() {
        boolean isFigureWithThreeParam = figure.get().toString() == "Ellipsoid"
                || figure.get().toString() == "Parallelepiped";
        boolean firstParamEmpty = stringFirstParam.get().isEmpty();
        boolean secondParamEmpty = stringSecondParam.get().isEmpty();
        boolean thirdParamEmpty = stringThirdParam.get().isEmpty() && isFigureWithThreeParam;
        return firstParamEmpty || secondParamEmpty || thirdParamEmpty;
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            calculateStatus.set(getInputStatus().toString());
        }
    }

    private class ComboBoxElementChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (figure.get().toString() == "Ellipsoid") {
                labelFirstParamName.set("Radius A");
                labelSecondParamName.set("Radius B");
                labelThirdParamName.set("Radius C");
                thirdParamDisabled.set(false);
            }
            if (figure.get().toString() == "Parallelepiped") {
                labelFirstParamName.set("Length");
                labelSecondParamName.set("Width");
                labelThirdParamName.set("Height");
                thirdParamDisabled.set(false);
            }
            if (figure.get().toString() == "Cone" || figure.get().toString() == "Cylinder") {
                labelFirstParamName.set("Radius");
                labelSecondParamName.set("Height");
                labelThirdParamName.set("");
                thirdParamDisabled.set(true);
            }
            stringFirstParam.set("");
            stringSecondParam.set("");
            stringThirdParam.set("");
            calculateResult.set("");
        }
    }
}

enum CalculateStatus {
    WAITING("Please enter input data"),
    BAD_FORMAT_OF_INPUT("Bad format of data"),
    READY("Press Calculate"),
    SUCCESS("Success");
    private final String name;

    private CalculateStatus(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

enum LoggingInfo {
    PRESSED_CALCULATE("Calculate. "),
    CHANGED_OPERATION("Operation was changed to "),
    FINIS_EDITD("Updated input. ");
    private final String info;

    private LoggingInfo(final String info) {
        this.info = info;
    }

    public String toString() {
        return info;
    }
}
