package ru.unn.agile.BitArray.viewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.BitArray.model.BitArray.Operation;
import java.util.List;

import static org.junit.Assert.*;
import static ru.unn.agile.BitArray.viewModel.RegexMatcher.matchesPattern;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        FakeLogger fakeLogger = new FakeLogger();
        viewModel = new ViewModel(fakeLogger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValuesForInputFields() {
        assertEquals("", viewModel.bitArray1StrValue().get());
        assertEquals("", viewModel.bitArray2StrValue().get());
        assertEquals(Operation.AND, viewModel.firstBitOperation().get());
        assertEquals("", viewModel.resultProperty().get());
        assertEquals(InputStatus.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFields() {
        viewModel.calculate();
        assertEquals(InputStatus.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFill() {
        setInputDataForTwoArrays();

        assertEquals(InputStatus.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormat() {
        viewModel.bitArray1StrValue().set("daasa");

        assertEquals(InputStatus.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfNotEnoughCorrectData() {
        viewModel.bitArray1StrValue().set("10011");

        assertEquals(InputStatus.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenFormatIsBad() {
        setInputDataForTwoArrays();
        viewModel.bitArray1StrValue().set("trash");

        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenInitialized() {
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWithIncompleteInput() {
        viewModel.bitArray1StrValue().set("1111");

        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInput() {
        setInputDataForTwoArrays();

        assertFalse(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void canSetAndOperation() {
        viewModel.firstBitOperation().set(Operation.AND);
        assertEquals(Operation.AND, viewModel.firstBitOperation().get());
    }

    @Test
    public void addIsDefaultOperation() {
        assertEquals(Operation.AND, viewModel.firstBitOperation().get());
    }

    @Test
    public void operationAndHasCorrectResult() {
        viewModel.bitArray1StrValue().set("1111");
        viewModel.bitArray2StrValue().set("0000");

        viewModel.calculate();

        assertEquals("0000", viewModel.resultProperty().get());
    }

    @Test
    public void canSetSuccessMessage() {
        setInputDataForTwoArrays();

        viewModel.calculate();

        assertEquals(InputStatus.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetBadFormatMessage() {
        viewModel.bitArray1StrValue().set("#selfie");

        assertEquals(InputStatus.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenSetProperData() {
        setInputDataForTwoArrays();

        assertEquals(InputStatus.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void operationXorHasCorrectResult() {
        viewModel.bitArray1StrValue().set("1111");
        viewModel.bitArray2StrValue().set("1010");
        viewModel.firstBitOperation().set(Operation.XOR);

        viewModel.calculate();

        assertEquals("0101", viewModel.resultProperty().get());
    }

    @Test
    public void operationOrHasCorrectResult() {
        viewModel.bitArray1StrValue().set("1010");
        viewModel.bitArray2StrValue().set("0110");
        viewModel.firstBitOperation().set(Operation.OR);

        viewModel.calculate();

        assertEquals("1110", viewModel.resultProperty().get());
    }

    @Test
    public void canCalculate() {
        viewModel.bitArray1StrValue().set("1010");
        viewModel.bitArray2StrValue().set("0110");
        viewModel.bitArray3StrValue().set("0110");
        viewModel.firstBitOperation().set(Operation.OR);
        viewModel.secondBitOperation().set(Operation.XOR);

        viewModel.calculate();

        assertEquals("1000", viewModel.resultProperty().get());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModelLogged = new ViewModel(logger);

        assertNotNull(viewModelLogged);
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        new ViewModel(null);
        fail("Exception wasn't thrown");
    }

    @Test
    public void isLogEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void isCalculatePuttingSomething() {
        setInputDataForTwoArrays();
        viewModel.calculate();

        List<String> log = viewModel.getLog();
        assertNotEquals(0, log.size());
    }

    @Test
    public void isLogContainsProperMessageAfterCalculation() {
        setInputDataForTwoArrays();
        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertThat(message,
                matchesPattern(".*" + ViewModel.LogMessages.CALCULATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void isLogContainsInputArguments() {
        setInputDataForTwoArrays();

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*" + viewModel.bitArray1StrValue().get()
                        + ".*" + viewModel.bitArray2StrValue().get()
                        + ".*"
        ));
    }

    @Test
    public void isProperlyFormattingInfoAboutArguments() {
        setInputDataForTwoArrays();

        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertThat(message, matchesPattern(".*Arguments"
                        + ": BitArray1 = " + viewModel.bitArray1StrValue().get()
                        + "; BitArray2 = " + viewModel.bitArray2StrValue().get()
                        + "; BitArray3 = " + viewModel.bitArray3StrValue().get()
                        + ".*"
        ));
    }

    @Test
    public void isAndOperationMentionedInTheLog() {
        viewModel.onOperationChanged(Operation.OR, Operation.AND);

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*And.*"));
    }

    @Test
    public void isOrOperationMentionedInTheLog() {
        viewModel.onOperationChanged(Operation.AND, Operation.OR);

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*Or.*"));
    }

    @Test
    public void isXorOperationMentionedInTheLog() {
        viewModel.onOperationChanged(Operation.OR, Operation.XOR);

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*Xor.*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        setInputDataForTwoArrays();

        viewModel.calculate();
        viewModel.calculate();
        viewModel.calculate();

        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void canSeeOperationChangeInLog() {
        viewModel.onOperationChanged(Operation.XOR, Operation.OR);

        String message = viewModel.getLog().get(0);
        assertThat(message,
                matchesPattern(".*" + ViewModel.LogMessages.OPERATION_WAS_CHANGED + "Or.*"));
    }

    @Test
    public void isOperationNotLoggedWhenNotChanged() {
        viewModel.onOperationChanged(Operation.OR, Operation.XOR);
        viewModel.onOperationChanged(Operation.XOR, Operation.XOR);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void isEditingFinishLogged() {
        viewModel.bitArray1StrValue().set("1101");

        viewModel.onFocusChanged(true, false);

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void isEditingFinishLoggedWhenPerformNot() {
        viewModel.bitArray1StrValue().set("1101");

        viewModel.performNot(0);

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void areArgumentsCorrectlyLoggedOnEditingFinish() {
        setInputDataForTwoArrays();
        viewModel.onFocusChanged(true, false);

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED
                + "Input arguments are: \\["
                + viewModel.bitArray1StrValue().get() + "; "
                + viewModel.bitArray2StrValue().get() + "; "
                + viewModel.bitArray3StrValue().get() + "\\]"));
    }

    @Test
    public void isCalculateNotCalledWhenButtonIsDisabled() {
        viewModel.calculate();

        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void doNotLogSameParametersTwiceWithPartialInput() {
        viewModel.bitArray1StrValue().set("1101");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.bitArray1StrValue().set("1101");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
    }

    private void setInputDataForTwoArrays() {
        viewModel.bitArray1StrValue().set("1101");
        viewModel.bitArray2StrValue().set("1011");
    }

    private void setInputDataForThreeArrays() {
        viewModel.bitArray1StrValue().set("1101");
        viewModel.bitArray2StrValue().set("1011");
        viewModel.bitArray2StrValue().set("1001");
    }
}
