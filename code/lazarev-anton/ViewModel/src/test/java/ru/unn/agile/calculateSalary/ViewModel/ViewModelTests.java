package ru.unn.agile.calculateSalary.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.calculateSalary.ViewModel.ViewModel.Status;
import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setViewModel(final ViewModel inViewModel) {
        this.viewModel = inViewModel;
    }

    @Before
    public void setUpEmptyExample() {
        FakeLogger myLogger = new FakeLogger();
        viewModel = new ViewModel(myLogger);
    }

    @After
    public void deleteExample() {
        viewModel = null;
    }

    @Test
    public void checkDefaultEmptyParameters() {
        assertEquals("", viewModel.getSalary());
        assertEquals("", viewModel.getWorkedHours());
        assertEquals("", viewModel.getCountMonth());
        assertEquals("", viewModel.getCountYear());
        assertEquals("", viewModel.getVacationLength());
        assertEquals("", viewModel.getStartVacationDay());
        assertEquals("", viewModel.getVacationMonth());
        assertEquals("", viewModel.getVacationYear());
        assertEquals("", viewModel.getResult());
    }

    @Test
    public void checkStatusInBegin() {
        assertEquals(Status.COUNT_WAITING, viewModel.getStatus());
    }

    @Test
    public void checkSetters() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("145");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2000");
        viewModel.setVacationLength("25");
        viewModel.setStartVacationDay("15");
        viewModel.setVacationMonth("3");
        viewModel.setVacationYear("2008");

        assertEquals("10000", viewModel.getSalary());
        assertEquals("145", viewModel.getWorkedHours());
        assertEquals("5", viewModel.getCountMonth());
        assertEquals("2000", viewModel.getCountYear());
        assertEquals("25", viewModel.getVacationLength());
        assertEquals("15", viewModel.getStartVacationDay());
        assertEquals("3", viewModel.getVacationMonth());
        assertEquals("2008", viewModel.getVacationYear());
    }

    @Test
    public void checkStatusWhenInputEmpty() {
        viewModel.calculate();

        assertEquals(Status.COUNT_WAITING, viewModel.getStatus());
    }

    @Test
    public void checkStatusWhenOneOfCountFieldEmpty() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("150");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("");

        viewModel.calculate();

        assertEquals(Status.COUNT_WAITING, viewModel.getStatus());
        assertFalse(viewModel.getCalculateButtonEnable());
        assertEquals("", viewModel.getResult());
    }

    @Test
    public void checkStatusWhenOneOfVacationFieldEmpty() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("150");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2010");
        viewModel.setVacationLength("10");
        viewModel.setStartVacationDay("15");
        viewModel.setVacationMonth("3");

        viewModel.calculate();

        assertEquals(Status.VACATION_WAITING, viewModel.getStatus());
        assertFalse(viewModel.getCalculateButtonEnable());
        assertEquals("", viewModel.getResult());
    }

    @Test
     public void checkStatusWhenCountInputWithChar() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("a");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2000");

        viewModel.calculate();

        assertEquals(Status.BAD_COUNT_FORMAT, viewModel.getStatus());
        assertFalse(viewModel.getCalculateButtonEnable());
        assertEquals("", viewModel.getResult());
    }

    @Test
    public void checkStatusWhenCountInputWithIncorrectMonth() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("200");
        viewModel.setCountMonth("50");
        viewModel.setCountYear("2000");

        viewModel.calculate();

        assertEquals(Status.BAD_MONTH_FORMAT, viewModel.getStatus());
        assertFalse(viewModel.getCalculateButtonEnable());
        assertEquals("", viewModel.getResult());
    }

    @Test
    public void checkStatusWhenCountInputWithIncorrectYear() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("200");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("19191");

        viewModel.calculate();

        assertEquals(Status.BAD_YEAR_FORMAT, viewModel.getStatus());
        assertFalse(viewModel.getCalculateButtonEnable());
        assertEquals("", viewModel.getResult());
    }

    @Test
    public void checkStatusWhenVacationInputWithIncorrectYear() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("200");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2005");
        viewModel.setVacationLength("10");
        viewModel.setStartVacationDay("15");
        viewModel.setVacationMonth("3");
        viewModel.setVacationYear("2614");

        viewModel.calculate();

        assertEquals(Status.BAD_YEAR_FORMAT, viewModel.getStatus());
        assertFalse(viewModel.getCalculateButtonEnable());
        assertEquals("", viewModel.getResult());
    }

    @Test
         public void checkStatusWhenVacationInputWithIncorrectDay() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("200");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2005");
        viewModel.setVacationLength("10");
        viewModel.setStartVacationDay("98");
        viewModel.setVacationMonth("3");
        viewModel.setVacationYear("2014");

        viewModel.calculate();

        assertEquals(Status.BAD_DAY_FORMAT, viewModel.getStatus());
        assertFalse(viewModel.getCalculateButtonEnable());
        assertEquals("", viewModel.getResult());
    }

    @Test
    public void checkStatusWhenVacationInputWithIncorrectMonth() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("200");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2005");
        viewModel.setVacationLength("10");
        viewModel.setStartVacationDay("28");
        viewModel.setVacationMonth("25");
        viewModel.setVacationYear("2014");

        viewModel.calculate();

        assertEquals(Status.BAD_MONTH_FORMAT, viewModel.getStatus());
        assertFalse(viewModel.getCalculateButtonEnable());
        assertEquals("", viewModel.getResult());
    }

    @Test
    public void checkStatusWhenCorrectVacationInput() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("150");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2000");
        viewModel.setVacationLength("10");
        viewModel.setStartVacationDay("15");
        viewModel.setVacationMonth("3");
        viewModel.setVacationYear("2014");

        viewModel.calculate();

        assertEquals(Status.CASH, viewModel.getStatus());
        assertTrue(viewModel.getCalculateButtonEnable());
    }

    @Test
    public void checkStatusWhenIncorrectVacationInput() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("150");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2000");
        viewModel.setVacationLength("q");
        viewModel.setStartVacationDay("15");
        viewModel.setVacationMonth("3");
        viewModel.setVacationYear("2014");

        viewModel.calculate();

        assertEquals("", viewModel.getResult());
        assertEquals(Status.BAD_VACATION_FORMAT, viewModel.getStatus());
        assertFalse(viewModel.getCalculateButtonEnable());
    }

    @Test
    public void checkResultWithNormalParameters() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("184");
        viewModel.setCountMonth("10");
        viewModel.setCountYear("2014");

        viewModel.calculate();

        assertEquals("8700.0", viewModel.getResult());
        assertEquals(Status.CASH, viewModel.getStatus());
        assertTrue(viewModel.getCalculateButtonEnable());
    }

    @Test
    public void checkResultWithOvertime() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("200");
        viewModel.setCountMonth("10");
        viewModel.setCountYear("2014");

        viewModel.calculate();

        assertEquals("10213.04", viewModel.getResult());
        assertEquals(Status.CASH, viewModel.getStatus());
        assertTrue(viewModel.getCalculateButtonEnable());
    }

    @Test
    public void checkResultWithLessTime() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("154");
        viewModel.setCountMonth("10");
        viewModel.setCountYear("2014");

        viewModel.calculate();

        assertEquals("7281.52", viewModel.getResult());
        assertEquals(Status.CASH, viewModel.getStatus());
        assertTrue(viewModel.getCalculateButtonEnable());
    }

    @Test
    public void checkResultWithVacationIncludeInCountMonth() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("104");
        viewModel.setCountMonth("10");
        viewModel.setCountYear("2014");
        viewModel.setVacationLength("14");
        viewModel.setStartVacationDay("6");
        viewModel.setVacationMonth("10");
        viewModel.setVacationYear("2014");

        viewModel.calculate();

        assertEquals("4917.39", viewModel.getResult());
        assertEquals(Status.CASH, viewModel.getStatus());
        assertTrue(viewModel.getCalculateButtonEnable());
    }

    @Test
    public void checkResultWithVacationStartInCountMonthAndEndInAnother() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("144");
        viewModel.setCountMonth("10");
        viewModel.setCountYear("2014");
        viewModel.setVacationLength("14");
        viewModel.setStartVacationDay("27");
        viewModel.setVacationMonth("10");
        viewModel.setVacationYear("2014");

        viewModel.calculate();

        assertEquals("6808.7", viewModel.getResult());
        assertEquals(Status.CASH, viewModel.getStatus());
        assertTrue(viewModel.getCalculateButtonEnable());
    }

    @Test (expected = ArithmeticException.class)
    public void checkResultWithNegativeWorkedHours() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("-144");
        viewModel.setCountMonth("10");
        viewModel.setCountYear("2014");

        viewModel.calculate();
    }

    @Test (expected = ArithmeticException.class)
    public void checkResultWithNegativeVacationLength() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("144");
        viewModel.setCountMonth("10");
        viewModel.setCountYear("2014");
        viewModel.setVacationLength("-14");
        viewModel.setStartVacationDay("27");
        viewModel.setVacationMonth("10");
        viewModel.setVacationYear("2014");

        viewModel.calculate();
    }

    @Test (expected = ArithmeticException.class)
    public void checkResultWithNegativeSalary() {
        viewModel.setSalary("-10000");
        viewModel.setWorkedHours("144");
        viewModel.setCountMonth("10");

        viewModel.setCountYear("2014");
        viewModel.calculate();
    }

    @Test
    public void checkStatusAndButtonWhenIncorrectDate() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("144");
        viewModel.setCountMonth("35");
        viewModel.setCountYear("2014");

        viewModel.calculate();

        assertEquals("", viewModel.getResult());
        assertEquals(Status.BAD_MONTH_FORMAT, viewModel.getStatus());
        assertFalse(viewModel.getCalculateButtonEnable());
    }

    @Test
    public void checkStatusAndButtonWhenIncorrectVacationDate() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("144");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2014");
        viewModel.setVacationLength("14");
        viewModel.setStartVacationDay("27");
        viewModel.setVacationMonth("10");
        viewModel.setVacationYear("2500");

        viewModel.calculate();

        assertEquals("", viewModel.getResult());
        assertEquals(Status.BAD_YEAR_FORMAT, viewModel.getStatus());
        assertFalse(viewModel.getCalculateButtonEnable());
    }

    @Test
    public void canCreateLogger() {
        FakeLogger myLogger = new FakeLogger();
        ViewModel viewModelWithLogger = new ViewModel(myLogger);

        assertNotNull(viewModelWithLogger);
    }

    @Test (expected = IllegalArgumentException.class)
    public void exceptionWhenLoggerNull() {
        ViewModel viewModel = new ViewModel(null);
        assertNull(viewModel);
    }

    @Test
    public void logIsEmptyWhenCalculatorStart() {
        List<String> log = viewModel.getLog();
        assertEquals(0, log.size());
    }

    @Test
    public void logIsEmptyWhenCalculateEmptyFields() {
        viewModel.calculate();

        List<String> log = viewModel.getLog();
        assertEquals(0, log.size());
    }

    @Test
        public void logWhenOneCountFieldType() {
        viewModel.setSalary("25000");

        viewModel.countFocusLost();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*"
                + ViewModel.LogMessageTemplates.COUNT_MESSAGE
                + ".*" + viewModel.getSalary()
                + ".*"));
    }

    @Test
    public void logWhenCountDataType() {
        viewModel.setCountMonth("10");
        viewModel.setCountYear("2014");

        viewModel.countFocusLost();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*"
                + ViewModel.LogMessageTemplates.COUNT_MESSAGE
                + ".*" + viewModel.getCountMonth()
                + ".*" + viewModel.getCountYear() + ".*"));
    }

    @Test
    public void logWhenAllFieldsType() {
        viewModel.setSalary("35000");
        viewModel.setWorkedHours("190");
        viewModel.setCountMonth("11");
        viewModel.setCountYear("2000");

        viewModel.countFocusLost();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*"
                + ViewModel.LogMessageTemplates.COUNT_MESSAGE
                + ".*" + viewModel.getSalary()
                + ".*" + viewModel.getWorkedHours()
                + ".*" + viewModel.getCountMonth()
                + ".*" + viewModel.getCountYear() + ".*"));
    }

    @Test
    public void logWhenOneVacationFieldType() {
        viewModel.setVacationLength("25");

        viewModel.vacationFocusLost();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*"
                + ViewModel.LogMessageTemplates.VACATION_MESSAGE
                + ".*" + viewModel.getVacationLength()
                + ".*"));
    }

    @Test
    public void logWhenDateOfVacationFilled() {
        viewModel.setStartVacationDay("13");
        viewModel.setVacationMonth("3");
        viewModel.setVacationYear("2000");

        viewModel.vacationFocusLost();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + ViewModel.LogMessageTemplates.VACATION_MESSAGE
                + ".*" + viewModel.getStartVacationDay()
                + ".*" + viewModel.getVacationMonth()
                + ".*" + viewModel.getVacationYear() + ".*"));
    }

    @Test
    public void logWhenCountFilledButVacationFilledNotAll() {
        viewModel.setSalary("18635");
        viewModel.setWorkedHours("121");
        viewModel.setCountMonth("1");
        viewModel.setCountYear("2009");
        viewModel.setStartVacationDay("11");
        viewModel.setVacationMonth("5");
        viewModel.setVacationYear("2001");


        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + ViewModel.LogMessageTemplates.CALCULATE_MESSAGE
                + ".*" + viewModel.getSalary()
                + ".*" + viewModel.getWorkedHours()
                + ".*" + viewModel.getCountMonth()
                + ".*" + viewModel.getCountYear() + ".*"));
    }

    @Test
    public void logWhenCountFilledAndVacationFilled() {
        viewModel.setSalary("16000");
        viewModel.setWorkedHours("130");
        viewModel.setCountMonth("3");
        viewModel.setCountYear("2008");
        viewModel.setVacationLength("45");
        viewModel.setStartVacationDay("19");
        viewModel.setVacationMonth("5");
        viewModel.setVacationYear("2001");

        viewModel.calculate();
        String message = viewModel.getLog().get(0);
        String vacationMessage = viewModel.getLog().get(1);

        assertTrue(message.matches(".*"
                + ViewModel.LogMessageTemplates.CALCULATE_MESSAGE + ".*"));
        assertTrue(vacationMessage.matches(".*"
                + " And this vacation data" + ".*"));
    }

    @Test
    public void notLogEqualInputInCount() {
        viewModel.setSalary("18000");
        viewModel.setSalary("18000");

        viewModel.countFocusLost();
        viewModel.countFocusLost();

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void notLogEqualInputInVacation() {
        viewModel.setStartVacationDay("35");
        viewModel.setStartVacationDay("35");

        viewModel.vacationFocusLost();
        viewModel.vacationFocusLost();

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void notLogEqualInputInBothTypesOfFields() {
        viewModel.setSalary("85000");
        viewModel.setSalary("85000");
        viewModel.setStartVacationDay("15");
        viewModel.setStartVacationDay("15");

        viewModel.countFocusLost();
        viewModel.countFocusLost();
        viewModel.vacationFocusLost();
        viewModel.vacationFocusLost();

        assertEquals(2, viewModel.getLog().size());
    }
}
