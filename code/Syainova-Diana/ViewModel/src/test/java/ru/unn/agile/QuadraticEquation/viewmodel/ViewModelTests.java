package ru.unn.agile.QuadraticEquation.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModelForTests;

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModelForTests = viewModel;
    }

    @Before
    public void setUp() {
        if (viewModelForTests == null) {
            viewModelForTests = new ViewModel(new FakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModelForTests = null;
    }

    @Test
    public void canSetDefaultValuesCoefficientsAndRoots() {
        assertEquals("", viewModelForTests.firstCoefficientProperty().get());
        assertEquals("", viewModelForTests.secondCoefficientProperty().get());
        assertEquals("", viewModelForTests.firstRootResultProperty().get());
        assertEquals("", viewModelForTests.secondRootResultProperty().get());
        assertEquals(systemStatus.WAITING.toString(), viewModelForTests.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenSolveWithEmptyFields() {
        viewModelForTests.solve();

        assertEquals(systemStatus.WAITING.toString(), viewModelForTests.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFill() {
        setEquationData();

        assertEquals(systemStatus.READY.toString(), viewModelForTests.statusProperty().get());
    }

    @Test
    public void canReportWhenTheInputCoefficientHasBadFormat() {
        viewModelForTests.firstCoefficientProperty().set("/");

        assertEquals(systemStatus.BAD_FORMAT.toString(), viewModelForTests.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenNotAllCoefficientsIntroduced() {
        viewModelForTests.firstCoefficientProperty().set("2");
        viewModelForTests.secondCoefficientProperty().set("1");

        assertEquals(systemStatus.WAITING.toString(), viewModelForTests.statusProperty().get());
    }

    @Test
    public void solveButtonIsDisabledInitially() {
        assertTrue(viewModelForTests.solvingDisabledProperty().get());
    }

    @Test
    public void solveButtonIsDisabledWhenFormatIsBad() {
        setEquationData();
        viewModelForTests.thirdCoefficientProperty().set("ttt");

        assertTrue(viewModelForTests.solvingDisabledProperty().get());
    }

    @Test
    public void solveButtonIsDisabledWheNotAllCoefficientsIntroduced() {
        viewModelForTests.firstCoefficientProperty().set("4");
        viewModelForTests.thirdCoefficientProperty().set("2");

        assertTrue(viewModelForTests.solvingDisabledProperty().get());
    }

    @Test
    public void solveButtonIsEnabledWhenAllCorrectCoefficientIntroduced() {
        setEquationData();

        assertFalse(viewModelForTests.solvingDisabledProperty().get());
    }

    @Test
    public void solveButtonIsEnabledWhenFirstCoefficientIsNull() {
        setEquationData();
        viewModelForTests.firstCoefficientProperty().set("0");

        assertFalse(viewModelForTests.solvingDisabledProperty().get());
    }

    @Test
    public void solvingHasTwoDifferentRoots() {
        viewModelForTests.firstCoefficientProperty().set("1");
        viewModelForTests.secondCoefficientProperty().set("1");
        viewModelForTests.thirdCoefficientProperty().set("-2");

        viewModelForTests.solve();

        assertEquals("x = 1.0", viewModelForTests.firstRootResultProperty().get());
        assertEquals("x = -2.0", viewModelForTests.secondRootResultProperty().get());
    }

    @Test
    public void statusIsSuccessWhenEquationCorrectlySolved() {
        setEquationData();

        viewModelForTests.solve();

        assertEquals(systemStatus.SUCCESS.toString(), viewModelForTests.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenEnterCorrectCoefficients() {
        setEquationData();

        assertEquals(systemStatus.READY.toString(), viewModelForTests.statusProperty().get());
    }

    @Test
    public void canReportBadFormatCoefficients() {
        viewModelForTests.firstCoefficientProperty().set("incorrect coefficients");

        assertEquals(systemStatus.BAD_FORMAT.toString(), viewModelForTests.statusProperty().get());
    }

    @Test
    public void solvingHasTwoSameRoots() {
        viewModelForTests.firstCoefficientProperty().set("1");
        viewModelForTests.secondCoefficientProperty().set("-10");
        viewModelForTests.thirdCoefficientProperty().set("25");

        viewModelForTests.solve();

        assertEquals("x = 5.0", viewModelForTests.firstRootResultProperty().get());
        assertEquals("x = 5.0", viewModelForTests.secondRootResultProperty().get());
    }

    @Test
    public void solvingHasNotRealRoots() {
        viewModelForTests.firstCoefficientProperty().set("1");
        viewModelForTests.secondCoefficientProperty().set("1");
        viewModelForTests.thirdCoefficientProperty().set("2");

        viewModelForTests.solve();

        assertEquals(systemStatus.NO_ROOTS.toString(), viewModelForTests.statusProperty().get());
    }

    @Test
    public void canReportStatusWhenFirstCoefficientIsNull() {
        setEquationData();
        viewModelForTests.firstCoefficientProperty().set("0");

        viewModelForTests.solve();

        assertEquals(systemStatus.INCORRECT_COEF.toString(),
                viewModelForTests.statusProperty().get());
    }

    @Test
    public void canReportStatusWhenFirstCoefficientIsNullOtherKind() {
        setEquationData();
        viewModelForTests.firstCoefficientProperty().set("00.0");

        viewModelForTests.solve();

        assertEquals(systemStatus.INCORRECT_COEF.toString(),
                viewModelForTests.statusProperty().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionWhenViewModelCreatedWithNullLogger() {
            new ViewModel(null);
    }

    @Test
    public void logIsEmptyBeforeActions() {
        List<String> log = viewModelForTests.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logIncludesProperMessageAfterSolving() {
        setEquationData();

        viewModelForTests.solve();

        String message = viewModelForTests.getLog().get(0);

        assertTrue(message.matches(".*" + TypeOfLogMessages.SOLVE_WAS_PRESSED + ".*"));
    }

    @Test
    public void logIncludesInputCoefficientsAfterSolving() {
        setEquationData();

        viewModelForTests.solve();

        String message = viewModelForTests.getLog().get(0);

        assertTrue(message.matches(".*" + viewModelForTests.firstCoefficientProperty().get()
                + ".*" + viewModelForTests.secondCoefficientProperty().get()
                + ".*" + viewModelForTests.thirdCoefficientProperty().get() + ".*"));
    }

    @Test
    public void coefficientIsInfoIsProperlyFormatted() {
        setEquationData();

        viewModelForTests.solve();

        String message = viewModelForTests.getLog().get(0);

        assertTrue(message.matches(".*Coefficients"
                + ": a = " + viewModelForTests.firstCoefficientProperty().get()
                + "; b = " + viewModelForTests.secondCoefficientProperty().get()
                + "; c = " + viewModelForTests.thirdCoefficientProperty().get() + ".*"));
    }

    @Test
    public void canAddSeveralMessagesInLog() {
        setEquationData();

        viewModelForTests.solve();
        viewModelForTests.solve();
        viewModelForTests.solve();

        assertEquals(3, viewModelForTests.getLog().size());
    }

    @Test
    public void coefficientsAreCorrectlyLogged() {
        setEquationData();

        viewModelForTests.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        String message = viewModelForTests.getLog().get(0);

        assertTrue(message.matches(".*" + TypeOfLogMessages.INPUT_EDITING_FINISHED
                + "Input coefficients are: \\["
                + viewModelForTests.firstCoefficientProperty().get() + "; "
                + viewModelForTests.secondCoefficientProperty().get() + "; "
                + viewModelForTests.thirdCoefficientProperty().get() + "\\]"));
    }

    @Test
    public void solveIsNotCalledWhenButtonIsDisabled() {
        viewModelForTests.solve();

        assertTrue(viewModelForTests.getLog().isEmpty());
    }

    @Test
    public void doNotLogSameParametersTwice() {
        viewModelForTests.firstCoefficientProperty().set("5");
        viewModelForTests.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModelForTests.firstCoefficientProperty().set("5");
        viewModelForTests.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModelForTests.getLog().size());
    }

    public void setEquationData() {
        viewModelForTests.firstCoefficientProperty().set("5");
        viewModelForTests.secondCoefficientProperty().set("-4");
        viewModelForTests.thirdCoefficientProperty().set("-3");
    }
}
