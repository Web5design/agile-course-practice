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
        assertEquals("", viewModel.firstNominatorProperty().get());
        assertEquals("", viewModel.firstNumeratorProperty().get());
        assertEquals("", viewModel.secondNominatorProperty().get());
        assertEquals("", viewModel.secondNumeratorProperty().get());
    }

    @Test
    public void defaultStateIsWaiting() {
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }
}
