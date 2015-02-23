package ru.unn.agile.Fraction.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ru.unn.agile.Fraction.Model.Fraction.Operation;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
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
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
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
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusWaitingWithClearField() {
        setInputData();
        viewModel.firstDenominatorProperty().set("");
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusBadFormatWithBadInput() {
        setInputData();
        viewModel.firstDenominatorProperty().set("trash");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusDivByZeroWithBadDenominator() {
        setInputData();
        viewModel.firstDenominatorProperty().set("0");
        assertEquals(Status.DIVISION_BY_ZERO.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusDivByZeroWithDivByZero() {
        setInputData();
        viewModel.operationProperty().set(Operation.DIVIDE);
        viewModel.secondNumeratorProperty().set("0");
        assertEquals(Status.DIVISION_BY_ZERO.toString(), viewModel.statusProperty().get());
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

    private void setInputData() {
        viewModel.firstNumeratorProperty().set("1");
        viewModel.firstDenominatorProperty().set("2");
        viewModel.secondNumeratorProperty().set("2");
        viewModel.secondDenominatorProperty().set("3");
    }
}
