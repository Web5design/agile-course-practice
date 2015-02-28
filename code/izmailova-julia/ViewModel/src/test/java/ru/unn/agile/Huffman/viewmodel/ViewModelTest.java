package ru.unn.agile.Huffman.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class ViewModelTest {
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
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getCode());
        assertEquals("", viewModel.getCodeMessage());
    }

    @Test
    public void isStatusWaitingWhenProgrammIsStart() {
        assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenMessageisCome() {
        viewModel.setMessage("0");
        assertEquals(ViewModel.Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusBadFormatWhenMessageisWrong() {
        viewModel.setMessage("");
        assertEquals(ViewModel.Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void isStatusSuccessWhenMessageIsCorrectAndProgrammIsRun() {
        viewModel.setMessage("some_message");
        viewModel.gettree();
        assertEquals(ViewModel.Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void canGetCodesWithMessage() {
        viewModel.setMessage("01");
        viewModel.gettree();
        assertEquals(viewModel.getCode(), "0 0\n1 1\n");
    }

    @Test
    public void canGetMessageCodeWithMessage() {
        viewModel.setMessage("01");
        viewModel.gettree();
        assertEquals(viewModel.getCodeMessage(), "0 1 ");
    }
}
