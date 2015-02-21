package ru.unn.agile.Fraction.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

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

    private void setInputData() {
        viewModel.firstDenominatorProperty().set("1");
        viewModel.firstNumeratorProperty().set("2");
        viewModel.secondDenominatorProperty().set("2");
        viewModel.secondNumeratorProperty().set("3");
    }
}
