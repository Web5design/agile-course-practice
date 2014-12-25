package ru.unn.agile.ComplexProcent.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        FakeLoggerArrList fakeLogger = new FakeLoggerArrList();
        viewModel = new ViewModel(fakeLogger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.txtBaseProperty().get());
        assertEquals("", viewModel.txtInterestCountProperty().get());
        assertEquals("", viewModel.txtPercentProperty().get());
        assertEquals(LocalDate.now(), viewModel.dtPkrStartProperty().get());
        assertEquals(null, viewModel.dtPkrEndProperty().get());
        assertEquals("", viewModel.resultProperty().get());
    }

    @Test
    public void canCalculate() {
        setInputData();
        viewModel.calculate();
        assertNotNull(viewModel.getResult());
    }

    @Test
    public void canCalcCapitalizeInOnePeriod() {
        setInputData();
        viewModel.calculate();
        assertEquals("1045.00", viewModel.getResult());
    }

    @Test
    public void canCalcCapitalizeInOnePeriodFewYears() {
        setTxtFields("1000", "4.5", "1");
        viewModel.dtPkrStartProperty().set(LocalDate.of(2014, 7, 10));
        viewModel.dtPkrEndProperty().set(LocalDate.of(2016, 7, 10));
        viewModel.calculate();
        assertEquals("1092.16", viewModel.getResult());
    }

    @Test
    public void canCalcCapitalizeInFewPeriodForYear() {
        setTxtFields("1000", "4.5", "3");
        viewModel.dtPkrStartProperty().set(LocalDate.of(2014, 7, 10));
        viewModel.dtPkrEndProperty().set(LocalDate.of(2015, 7, 10));
        viewModel.calculate();
        assertEquals("1045.94", viewModel.getResult());
    }

    @Test
    public void canCalcCapitalizeInFewPeriodForFewYears() {
        setTxtFields("1000", "4.5", "3");
        viewModel.dtPkrStartProperty().set(LocalDate.of(2014, 7, 10));
        viewModel.dtPkrEndProperty().set(LocalDate.of(2018, 7, 10));
        viewModel.calculate();
        assertEquals("1196.95", viewModel.getResult());
    }

    @Test
    public void canCalcCapitalizeInLessThenYear() {
        setTxtFields("1000", "4.5", "3");
        viewModel.dtPkrStartProperty().set(LocalDate.of(2014, 7, 10));
        viewModel.dtPkrEndProperty().set(LocalDate.of(2015, 5, 11));
        viewModel.calculate();
        assertEquals("1038.27", viewModel.getResult());
    }

    @Test
    public void defaultStatusAndButton() {
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
        assertEquals(viewModel.getCalculationDisabled(), true);
    }

    @Test
    public void statusIsBadFormatWhenHasNegative() {
        setInputData();
        assertEquals(viewModel.getStatus(), Status.READY.toString());
        viewModel.txtPercentProperty().set("-100");
        assertEquals(viewModel.getStatus(), Status.BAD_FORMAT.toString());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFill() {
        setInputData();
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormat() {
        viewModel.txtBaseProperty().set("sadcacasa");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormatForInterestCount() {
        viewModel.txtInterestCountProperty().set("5,4");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfNotEnoughCorrectData() {
        viewModel.txtBaseProperty().set("1");
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledInitially() {
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenFormatIsBad() {
        setInputData();
        viewModel.txtBaseProperty().set("spam");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWithIncompleteInput() {
        viewModel.txtBaseProperty().set("1000");
        viewModel.txtInterestCountProperty().set("1");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInput() {
        setInputData();
        assertFalse(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWithErasing() {
        setInputData();
        viewModel.txtBaseProperty().set("");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void canChangeDatePickers() {
        viewModel.dtPkrStartProperty().set(LocalDate.of(2016, 10, 9));
        viewModel.dtPkrEndProperty().set(LocalDate.of(2014, 1, 1));
        viewModel.dtPkrEndProperty().set(LocalDate.now());
        assertEquals(viewModel.dtPkrEndProperty().get(), LocalDate.now());
        assertEquals(viewModel.dtPkrStartProperty().asString().get(), "2016-10-09");
    }

    @Test
    public void canSetSuccessMessage() {
        setInputData();
        viewModel.calculate();
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetBadFormatMessage() {
        viewModel.txtPercentProperty().set("www.google.ru");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public  void statusWaitingWhenPickerEmpty() {
        setTxtFields("1000", "4" , "1");
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void canSetBadDateStatus() {
        setTxtFields("1000", "4", "1");
        viewModel.dtPkrStartProperty().set(LocalDate.of(2014, 7, 10));
        viewModel.dtPkrEndProperty().set(LocalDate.of(2013, 7 , 10));
        assertEquals(Status.BAD_DATE.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsReadyWhenSetProperData() {
        setInputData();
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void isThrowExceptionCaused() {
        try {
            new ViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void logIsEmptyOnTheStart() {
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsProperMessageAfterCalculation() {
        setInputData();
        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.CALCULATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void logContainsInputArgumentsAfterCalculation() {
        setInputData();
        viewModel.calculate();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + viewModel.txtInterestCountProperty().get()
                + ".*" + viewModel.txtPercentProperty().get()
                + ".*" + viewModel.txtBaseProperty().get()
                + ".*" + viewModel.dtPkrStartProperty().get()
                + ".*" + viewModel.dtPkrEndProperty().get() + ".*"));
    }

    @Test
    public void argumentsHasProperFormat() {
        setInputData();

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*Arguments"
                + ": IntCount = " + viewModel.txtInterestCountProperty().get()
                + "; Percents = " + viewModel.txtPercentProperty().get()
                + "; Base = " + viewModel.txtBaseProperty().get()
                + "; Start = " + viewModel.dtPkrStartProperty().get()
                + "; End = " + viewModel.dtPkrEndProperty().get() + ".*"));
    }

    @Test
    public void canPutFewLogs() {
        setInputData();

        viewModel.calculate();
        viewModel.calculate();
        viewModel.calculate();
        viewModel.calculate();
        assertEquals(4, viewModel.getLog().size());
    }

    @Test
    public void argumentsLoggedOnChange() {
        setInputData();

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.EDITING_FINISHED
                + "Input arguments are: \\["
                + viewModel.txtInterestCountProperty().get() + "; "
                + viewModel.txtPercentProperty().get() + "; "
                + viewModel.txtBaseProperty().get() + "; "
                + viewModel.dtPkrStartProperty().get() + ";"
                + viewModel.dtPkrEndProperty().get() + "\\]"));
    }

    @Test
    public void calculateIsNotCalledWhenButtonIsDisabled() {
        viewModel.calculate();

        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void canRecogniseChanging() {
        viewModel.txtBaseProperty().set("100");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.txtBaseProperty().set("3000");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void canRecogniseDateChanging() {
        viewModel.dtPkrEndProperty().set(LocalDate.of(2014, 7, 10));
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.dtPkrEndProperty().set(LocalDate.of(2015, 7, 10));
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void doNotLogSameParametersTwiceWithPartialInput() {
        viewModel.txtBaseProperty().set("1");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.txtBaseProperty().set("1");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
    }

    private void setTxtFields(final String base, final String percent, final String interestCount) {
        viewModel.txtBaseProperty().set(base);
        viewModel.txtInterestCountProperty().set(interestCount);
        viewModel.txtPercentProperty().set(percent);
    }

    private void setInputData() {
        viewModel.txtBaseProperty().set("1000");
        viewModel.txtPercentProperty().set("4.5");
        viewModel.txtInterestCountProperty().set("1");
        viewModel.dtPkrStartProperty().set(LocalDate.of(2014, 7, 10));
        viewModel.dtPkrEndProperty().set(LocalDate.of(2015, 7, 10));
    }

}
