package ru.unn.agile.Fraction.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ru.unn.agile.Fraction.Model.Fraction.Operation;

import java.util.List;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void defaultResultIsEmpty() {
        assertEquals("", viewModel.resultDenominatorProperty().get());
        assertEquals("", viewModel.resultNumeratorProperty().get());
    }

    @Test
    public void defaultArgumentsIsEmpty() {
        assertEquals("", viewModel.firstDenominatorProperty().get());
        assertEquals("", viewModel.firstNumeratorProperty().get());
        assertEquals("", viewModel.secondDenominatorProperty().get());
        assertEquals("", viewModel.secondNumeratorProperty().get());
    }

    @Test
    public void defaultStateIsWaiting() {
        assertEquals(IOStatus.WAITING.toString(), viewModel.ioStatusProperty().get());
    }

    @Test
    public void defaultOperationIsAdd() {
        assertEquals(Operation.ADD, viewModel.operationProperty().get());
    }

    @Test
    public void calculationIsDisabledByDefault() {
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void statusReadyWithGoodInput() {
        setInputData();
        assertEquals(IOStatus.READY.toString(), viewModel.ioStatusProperty().get());
    }

    @Test
    public void statusWaitingWithClearField() {
        setInputData();
        viewModel.firstDenominatorProperty().set("");
        assertEquals(IOStatus.WAITING.toString(), viewModel.ioStatusProperty().get());
    }

    @Test
    public void statusBadFormatWithBadInput() {
        setInputData();
        viewModel.firstDenominatorProperty().set("trash");
        assertEquals(IOStatus.BAD_FORMAT.toString(), viewModel.ioStatusProperty().get());
    }

    @Test
    public void statusDivByZeroWithBadDenominator() {
        setInputData();
        viewModel.firstDenominatorProperty().set("0");
        assertEquals(IOStatus.DIVISION_BY_ZERO.toString(), viewModel.ioStatusProperty().get());
    }

    @Test
    public void statusDivByZeroWithDivByZero() {
        setInputData();
        viewModel.operationProperty().set(Operation.DIVIDE);
        viewModel.secondNumeratorProperty().set("0");
        assertEquals(IOStatus.DIVISION_BY_ZERO.toString(), viewModel.ioStatusProperty().get());
    }

    @Test
    public void canSetAddOperation() {
        viewModel.operationProperty().set(Operation.ADD);
        assertEquals(Operation.ADD, viewModel.operationProperty().get());
    }

    @Test
    public void canSetSubtractOperation() {
        viewModel.operationProperty().set(Operation.SUBTRACT);
        assertEquals(Operation.SUBTRACT, viewModel.operationProperty().get());
    }

    @Test
    public void canSetMultiplyOperation() {
        viewModel.operationProperty().set(Operation.MULTIPLY);
        assertEquals(Operation.MULTIPLY, viewModel.operationProperty().get());
    }

    @Test
    public void canSetDivideOperation() {
        viewModel.operationProperty().set(Operation.DIVIDE);
        assertEquals(Operation.DIVIDE, viewModel.operationProperty().get());
    }

    @Test
    public void calculationEnabledWithProperInput() {
        setInputData();
        assertFalse(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculationDisabledWithEmptyField() {
        setInputData();
        viewModel.firstDenominatorProperty().set("");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculationDisabledWithTrashField() {
        setInputData();
        viewModel.firstDenominatorProperty().set("trash");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculationDisabledWithZeroDenominator() {
        setInputData();
        viewModel.firstDenominatorProperty().set("0");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculationDisabledWithDivByZero() {
        setInputData();
        viewModel.secondNumeratorProperty().set("0");
        viewModel.operationProperty().set(Operation.DIVIDE);
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void resultOfAddIsRight() {
        setInputData();
        viewModel.operationProperty().set(Operation.ADD);
        viewModel.calculate();
        assertEquals("7", viewModel.resultNumeratorProperty().get());
        assertEquals("6", viewModel.resultDenominatorProperty().get());
    }

    @Test
    public void resultOfSubtractIsRight() {
        setInputData();
        viewModel.operationProperty().set(Operation.SUBTRACT);
        viewModel.calculate();
        assertEquals("-1", viewModel.resultNumeratorProperty().get());
        assertEquals("6", viewModel.resultDenominatorProperty().get());
    }

    @Test
    public void resultOfMultiplyIsRight() {
        setInputData();
        viewModel.operationProperty().set(Operation.MULTIPLY);
        viewModel.calculate();
        assertEquals("1", viewModel.resultNumeratorProperty().get());
        assertEquals("3", viewModel.resultDenominatorProperty().get());
    }

    @Test
    public void resultOfDivideIsRight() {
        setInputData();
        viewModel.operationProperty().set(Operation.DIVIDE);
        viewModel.calculate();
        assertEquals("3", viewModel.resultNumeratorProperty().get());
        assertEquals("4", viewModel.resultDenominatorProperty().get());
    }

    @Test
    public void logIsEmptyWhenStarted() {
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void inputLoggedCorrectly() {
        viewModel.firstNumeratorProperty().set("1");
        String message = viewModel.getLog().get(0);
        StringBuilder expectedMessage = new StringBuilder(LogMessages.INPUT_UPDATED);
        expectedMessage.append("first numerator: ");
        expectedMessage.append("1");
        expectedMessage.append(",first denominator: ");
        expectedMessage.append("");
        expectedMessage.append(",second numerator: ");
        expectedMessage.append("");
        expectedMessage.append(",second denominator: ");
        expectedMessage.append("");
        assertTrue(message.contains(expectedMessage));
    }

    @Test
    public void operationChangedLoggedCorrectly() {
        viewModel.operationProperty().set(Operation.DIVIDE);
        String message = viewModel.getLog().get(0);
        StringBuilder expectedMessage = new StringBuilder(LogMessages.OPERATION_CHANGED);
        expectedMessage.append(Operation.DIVIDE.toString());
        assertTrue(message.contains(expectedMessage));
    }

    @Test
    public void calculatePressedLoggedCorrectly() {
        setInputData();
        viewModel.operationProperty().set(Operation.DIVIDE);
        viewModel.calculate();
        List<String> log = viewModel.getLog();
        String message = log.get(log.size() - 1);
        StringBuilder expectedMessage = new StringBuilder(LogMessages.CALCULATE_PRESSED);
        expectedMessage.append("3");
        expectedMessage.append("/");
        expectedMessage.append("4");
        assertTrue(message.contains(expectedMessage));
    }

    @Test
    public void logsPropertyAreCleanInBeginning() {
        assertTrue(viewModel.logsProperty().get().isEmpty());
    }

    @Test
    public void logsPropertyContainsLogs() {
        setInputData();
        assertTrue(viewModel.logsProperty().get().contains(LogMessages.INPUT_UPDATED));
    }

    private void setInputData() {
        viewModel.firstNumeratorProperty().set("1");
        viewModel.firstDenominatorProperty().set("2");
        viewModel.secondNumeratorProperty().set("2");
        viewModel.secondDenominatorProperty().set("3");
    }
}
