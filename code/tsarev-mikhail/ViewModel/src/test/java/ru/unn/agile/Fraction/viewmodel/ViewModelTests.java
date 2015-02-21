package ru.unn.agile.Fraction.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
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
        assertEquals("", viewModel.resultNominatorProperty().get());
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
    public void statusImpossibleWithBadInput() {
        setInputData();
        viewModel.firstDenominatorProperty().set("0");
        assertEquals(Status.IMPOSSIBLE_FRACTION.toString(), viewModel.statusProperty().get());
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

    private void setInputData() {
        viewModel.firstDenominatorProperty().set("1");
        viewModel.firstNumeratorProperty().set("2");
        viewModel.secondDenominatorProperty().set("2");
        viewModel.secondNumeratorProperty().set("3");
    }
}
