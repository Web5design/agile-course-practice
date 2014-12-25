package ru.unn.agile.ComplexProcent.ViewModel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import ru.unn.agile.ComplexProcent.Model.ComplexDeposit;

public class ViewModel {
    private final StringProperty txtBase = new SimpleStringProperty("");
    private final StringProperty txtIntCount = new SimpleStringProperty("");
    private final StringProperty txtPercent = new SimpleStringProperty("");

    private final StringProperty result = new SimpleStringProperty("");
    private final StringProperty status = new SimpleStringProperty(Status.WAITING.toString());

    private final ObjectProperty<LocalDate> dtPkrStart = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> dtPkrEnd = new SimpleObjectProperty<>();

    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();

    private final List<DataValueChangeListener> dateChangedListeners = new ArrayList<>();
    private final List<TxtFieldsChangeListener> valueChangedListeners = new ArrayList<>();

    private ILogger logger;
    private final StringProperty logs = new SimpleStringProperty();

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        start();
    }

    public ViewModel() {
        start();
    }

    public void calculate() {
        if (calculationDisabled.get()) {
            return;
        }
        GregorianCalendar startDate = convertToGregorian(dtPkrStart.get());
        GregorianCalendar endDate = convertToGregorian(dtPkrEnd.get());
        ComplexDeposit calcDeposit = new ComplexDeposit();
        calcDeposit.setPercent(txtPercent.get())
                    .setBase(txtBase.get())
                    .setInterestCountInYear(txtIntCount.get())
                    .setStartDate(startDate)
                    .setFinishDate(endDate);
        result.set(String.format("%.2f", calcDeposit.getCapitalizedBase()));
        status.set(Status.SUCCESS.toString());

        StringBuilder message = new StringBuilder(LogMessages.CALCULATE_WAS_PRESSED);
        message.append("Arguments")
                .append(": IntCount = ").append(txtIntCount.get())
                .append("; Percents = ").append(txtPercent.get())
                .append("; Base = ").append(txtBase.get())
                .append("; Start = ").append(dtPkrStart.get())
                .append("; End = ").append(dtPkrEnd.get());
        logger.log(message.toString());
        updateLogs();
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        for (TxtFieldsChangeListener listener : valueChangedListeners) {
            if (listener.wasChanged()) {
                StringBuilder message = new StringBuilder(LogMessages.EDITING_FINISHED);
                message.append(buildMessageEdit());
                logger.log(message.toString());
                updateLogs();
                listener.sync();
                break;
            }
        }

        for (DataValueChangeListener listener : dateChangedListeners) {
            if (listener.isChanged()) {
                StringBuilder message = new StringBuilder(LogMessages.EDITING_FINISHED);
                message.append(buildMessageEdit());
                logger.log(message.toString());
                updateLogs();
                listener.cache();
                break;
            }
        }
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    public final boolean getCalculationDisabled() {
        return calculationDisabled.get();
    }

    public ObjectProperty<LocalDate> dtPkrStartProperty() {
        return dtPkrStart;
    }

    public ObjectProperty<LocalDate> dtPkrEndProperty() {
        return dtPkrEnd;
    }

    public StringProperty txtBaseProperty() {
        return txtBase;
    }

    public StringProperty txtInterestCountProperty() {
        return txtIntCount;
    }

    public StringProperty txtPercentProperty() {
        return txtPercent;
    }

    public StringProperty resultProperty() {
        return result;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getResult() {
        return result.get();
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public final String getStatus() {
        return status.get();
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = new String();
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }


    private GregorianCalendar convertToGregorian(final LocalDate dtPkr) {
        if (dtPkr == null) {
            return null;
        }
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.set(dtPkr.getYear(), dtPkr.getMonthValue(), dtPkr.getDayOfMonth());
        return startDate;
    }

    private String buildMessageEdit() {
        return "Input arguments are: ["
                + txtIntCount.get() + "; "
                + txtPercent.get() + "; "
                + txtBase.get() + "; "
                + dtPkrStart.get() + ";"
                + dtPkrEnd.get() + "]";
    }

    private void start() {
        Locale.setDefault(Locale.ENGLISH);
        dtPkrStart.set(LocalDate.now());
        bindDeterminateDisable();
        createFieldsValueChangingListeners();
    }

    private boolean hasNegativeFields() {
        try {
             return Double.parseDouble(txtBase.get()) < 0
                    || Double.parseDouble(txtPercent.get()) < 0
                    || Integer.parseInt(txtIntCount.get()) < 0;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean hasEmptyFields() {
        return txtBase.get().isEmpty() || txtPercent.get().isEmpty() || txtIntCount.get().isEmpty()
                || dtPkrEnd.getValue() == null || dtPkrStart.getValue() == null;
    }

    private boolean isIncorrectDate() {
        if (!(dtPkrEnd.getValue() == null || dtPkrEnd.getValue() == null)) {
            return dtPkrEnd.get().compareTo(dtPkrStart.get()) < 0;
        }
        return false;
    }

    private boolean isIncorrectTxtData() {
        try {
            if (!txtIntCount.get().isEmpty()) {
                Integer.parseInt(txtIntCount.get());
            }
            if (!txtPercent.get().isEmpty()) {
                Double.parseDouble(txtPercent.get());
            }
            if (!txtBase.get().isEmpty()) {
                Double.parseDouble(txtBase.get());
            }
        } catch (NumberFormatException nfe) {
            return true;
        }
        return false;
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (hasEmptyFields()) {
            inputStatus = Status.WAITING;
        }
        if (isIncorrectTxtData() || hasNegativeFields()) {
            return Status.BAD_FORMAT;
        }
        if (isIncorrectDate()) {
            return Status.BAD_DATE;
        }
        return inputStatus;
    }

    private void createFieldsValueChangingListeners() {
        final List<StringProperty> fields = new ArrayList<StringProperty>() {
            {
                add(txtBase);
                add(txtIntCount);
                add(txtPercent);
            }
        };
        for (StringProperty field : fields) {
            final TxtFieldsChangeListener listener = new TxtFieldsChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
        final DataValueChangeListener endDataListener = new DataValueChangeListener();
        dtPkrEnd.addListener(endDataListener);
        dateChangedListeners.add(endDataListener);
        final DataValueChangeListener startDataListener = new DataValueChangeListener();
        dtPkrEnd.addListener(startDataListener);
        dateChangedListeners.add(startDataListener);
    }

    public void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    private void bindDeterminateDisable() {
        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(txtBase, txtIntCount, txtPercent, dtPkrEnd, dtPkrStart);
            }

            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());
    }

    private class TxtFieldsChangeListener implements ChangeListener<String> {
        private String lastValue = new String();
        private String currentValue = new String();

        public boolean wasChanged() {
            return !currentValue.equals(lastValue);
        }

        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String prevValue, final String newValue) {
            status.set(getInputStatus().toString());
            currentValue = newValue;
        }

        public void sync() {
            lastValue = currentValue;
        }
    }

    private boolean isEqualWithNullDate(final LocalDate lastValue, final LocalDate newValue) {
        if (lastValue == null) { return false; }
        return lastValue.isEqual(newValue);
    }

    private class DataValueChangeListener implements ChangeListener<LocalDate> {
        private LocalDate lastValue;
        private LocalDate currentValue;
        @Override
        public void changed(final ObservableValue<? extends LocalDate> observable,
                            final LocalDate lastValue, final LocalDate newValue) {
            status.set(getInputStatus().toString());
            currentValue = newValue;
        }
        public boolean isChanged() {
            if (lastValue == null && currentValue == null) {
                return false;
            }
            return !isEqualWithNullDate(lastValue, currentValue);
        }

        public void cache() {
            lastValue = currentValue;
        }
    }
}

final class LogMessages {
    public static final String CALCULATE_WAS_PRESSED = "Calculate. ";
    public static final String EDITING_FINISHED = "Updated input. ";

    private LogMessages() { }
}

enum Status {
    WAITING("Заполните поля"),
    READY("Нажмите на кнопку 'Рассчитать'."),
    BAD_FORMAT("Что то ввели не так!"),
    SUCCESS("Готово!"),
    BAD_DATE("Неверная дата конца вклада");

    private final String name;

    private Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

