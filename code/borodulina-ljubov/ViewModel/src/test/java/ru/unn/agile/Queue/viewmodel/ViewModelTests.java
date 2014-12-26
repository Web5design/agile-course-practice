package ru.unn.agile.Queue.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel = new ViewModel();

    public void setLogger(final ILogger logger) {
        viewModel.setLogger(logger);
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel();
        viewModel.setLogger(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValue() {
        assertEquals("", viewModel.getTxtToAdd());
        assertEquals("Empty", viewModel.getElement());
    }
    @Test

    public void isStateWaitingWhenAddingWithEmptyField() {
        viewModel.add();

        assertEquals(ViewState.AWAITING.toString(), viewModel.getState());
    }

    @Test
    public void isStateReadyWhileFieldIsNotEmpty() {
        setInput("1");

        assertEquals(ViewState.READY.toString(), viewModel.getState());
    }

    @Test
    public void canReportAboutBadFormat() {
        setInput("z");

        assertEquals(ViewState.BAD_INPUT.toString(), viewModel.getState());
    }

    @Test
    public void areAddButtonsDisabledInitially() {
        assertTrue(viewModel.getIsAddingDisabled());
    }

    @Test
    public void areAddButtonsDisabledWhenFormatIsBad() {
        setInput("z");

        assertTrue(viewModel.getIsAddingDisabled());
    }

    @Test
    public void areAddButtonsEnabledWithCorrectInput() {
        setInput("1");

        assertFalse(viewModel.getIsAddingDisabled());
    }

    @Test
    public void isStateEmptyIfToRemoveFromEmptyQueue() {
        viewModel.remove();

        assertEquals(ViewState.EMPTY.toString(), viewModel.getState());
    }

    @Test
    public void canRemove() {
        String item = enqueueOne();

        viewModel.remove();

        assertEquals(item, viewModel.getTxtToAdd());
        assertEquals("Empty", viewModel.getElement());
    }

    @Test
    public void isLogMessageAddedWhenInputIsProvided() {
        setInput("1");
        assertTrue(viewModel.getLog().get(0)
                .matches(".*" + ILogger.Level.DBG + "Item to add: 1" + "$"));
    }

    @Test
    public void canLogAdd() {
        enqueueOne();

        int logSize = viewModel.getLog().size();
        assertTrue(viewModel.getLog().get(logSize - 1)
                .matches(".*" + ILogger.Level.INFO + "Added 1" + "$"));
    }

    @Test
    public void canLogBadInput() {
        setInput("a");
        assertTrue(viewModel.getLog().get(0)
                .matches(".*" + ILogger.Level.ERR + "Incorrect item: a" + "$"));
    }

    @Test
    public void canLogRemove() {
        enqueueOne();

        viewModel.remove();

        int logSize = viewModel.getLog().size();

        assertTrue(viewModel.getLog().get(logSize - 1)
                .matches(".*" + ILogger.Level.INFO + "Remove 1" + "$"));
    }

    private String enqueueOne() {
        String item = "1";
        setInput(item);
        viewModel.add();
        return item;
    }

    private void setInput(final String input) {
        viewModel.txtToAddProperty().set(input);
    }
}
