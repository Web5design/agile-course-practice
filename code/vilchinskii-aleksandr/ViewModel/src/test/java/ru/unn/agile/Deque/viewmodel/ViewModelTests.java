package ru.unn.agile.Deque.viewmodel;

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
        assertEquals("", viewModel.getTxtItem());
    }

    @Test
    public void isStatusWaitingWhenAddingWithEmptyField() {
        viewModel.addFirst();
        assertEquals(ViewStatus.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWithNotEmptyField() {
        setInputData("1");

        assertEquals(ViewStatus.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void canReportBadFormat() {
        setInputData("a");

        assertEquals(ViewStatus.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void areAddButtonsDisabledInitially() {
        assertTrue(viewModel.getIsAddingDisabled());
    }

    @Test
    public void areAddButtonsDisabledWhenFormatIsBad() {
        setInputData("a");

        assertTrue(viewModel.getIsAddingDisabled());
    }

    @Test
    public void areAddButtonsEnabledWithCorrectInput() {
        setInputData("1");

        assertFalse(viewModel.getIsAddingDisabled());
    }

    @Test
    public void isStatusEmptyIfToGetFromEmptyDeque() {
        viewModel.getFirst();
        assertEquals(ViewStatus.EMPTY.toString(), viewModel.getStatus());
    }

    @Test
    public void isTxtItemEmptyIfToGetFromEmptyDeque() {
        viewModel.getFirst();
        assertEquals("", viewModel.getTxtItem());
    }

    @Test
    public void canGetFirst() {
        String item = addOneAsFirst();

        viewModel.getFirst();

        assertEquals(item, viewModel.getTxtItem());
    }

    @Test
    public void canGetLast() {
        String item = addOneAsLast();

        viewModel.getLast();

        assertEquals(item, viewModel.getTxtItem());
    }

    @Test
    public void canRemoveFirst() {
        String item = addOneAsFirst();

        viewModel.removeFirst();

        assertEquals(item, viewModel.getTxtItem());
        assertTrue(viewModel.isDequeEmpty());
    }

    @Test
    public void canRemoveLast() {
        String item = addOneAsLast();

        viewModel.removeLast();

        assertEquals(item, viewModel.getTxtItem());
        assertTrue(viewModel.isDequeEmpty());
    }

    @Test
    public void isLogMessageAddedWhenInputIsChanged() {
        setInputData("1");
        assertEquals(ILogger.Level.DEBUG + "Item to add: 1", viewModel.getLog().get(0));
    }

    @Test
    public void canLogAddFirst() {
        String item = addOneAsFirst();

        int logSize = viewModel.getLog().size();
        assertEquals(ILogger.Level.INFO + "Added as first: 1", viewModel.getLog().get(logSize - 1));
    }

    @Test
    public void canLogAddLast() {
        String item = addOneAsLast();

        int logSize = viewModel.getLog().size();
        assertEquals(ILogger.Level.INFO + "Added as last: 1", viewModel.getLog().get(logSize - 1));
    }

    @Test
    public void canLogBadFormat() {
        setInputData("a");
        assertEquals(ILogger.Level.ERROR + "Incorrect item: a", viewModel.getLog().get(0));
    }

    @Test
    public void canLogItemIsGot() {
        String item = addOneAsFirst();

        viewModel.getFirst();

        int logSize = viewModel.getLog().size();
        assertEquals(ILogger.Level.INFO + "Managed to get item: 1", viewModel.getLog().get(logSize - 1));
    }

    @Test
    public void canLogGetFirst() {
        String item = addOneAsFirst();

        viewModel.getFirst();

        int logSize = viewModel.getLog().size();
        assertEquals(ILogger.Level.DEBUG + "Getting the first one", viewModel.getLog().get(logSize - 2));
    }

    @Test
    public void canLogGetLast() {
        String item = addOneAsLast();

        viewModel.getLast();

        int logSize = viewModel.getLog().size();
        assertEquals(ILogger.Level.DEBUG + "Getting the last one", viewModel.getLog().get(logSize - 2));
    }

    @Test
    public void canLogRemoveFirst() {
        String item = addOneAsFirst();

        viewModel.removeFirst();

        int logSize = viewModel.getLog().size();
        assertEquals(ILogger.Level.DEBUG + "Removing the first one", viewModel.getLog().get(logSize - 2));
    }

    @Test
    public void canLogRemoveLast() {
        String item = addOneAsLast();

        viewModel.removeLast();

        int logSize = viewModel.getLog().size();
        assertEquals(ILogger.Level.DEBUG + "Removing the last one", viewModel.getLog().get(logSize - 2));
    }


    private String addOneAsFirst() {
        String item = "1";
        setInputData(item);
        viewModel.addFirst();
        return item;
    }

    private String addOneAsLast() {
        String item = "1";
        setInputData(item);
        viewModel.addLast();
        return item;
    }

    private void setInputData(final String input) {
        viewModel.txtItemProperty().set(input);
    }
}
