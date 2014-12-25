package ru.unn.agile.CreditCalculator.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.CreditCalculator.ViewModel.ViewModel.Currency;
import ru.unn.agile.CreditCalculator.ViewModel.ViewModel.TypePayment;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static ru.unn.agile.CreditCalculator.ViewModel.RegexMatcher.matchesPattern;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        FakeLog logger = new FakeLog();
        viewModel = new ViewModel(logger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getSum());
        assertEquals("", viewModel.getPaymentPeriod());
        assertEquals("", viewModel.getInterestRate());
        assertEquals("", viewModel.getStartMonth());
        assertEquals(Currency.RUR, viewModel.getCurrency());
        assertEquals(TypePayment.Annuity, viewModel.getTypePayment());
        assertEquals("", viewModel.getAllSum());
        assertEquals(ViewModel.UserInputStatus.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingInTheBeginning() {
        assertEquals(ViewModel.UserInputStatus.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingWhenCalculateWithEmptyFields() {
        viewModel.calculate();

        assertEquals(ViewModel.UserInputStatus.WAITING, viewModel.getStatus());
    }
    @Test
    public void isStatusSuccessAnnuity() {
        viewModel.setSum("1");
        viewModel.setPaymentPeriod("1");
        viewModel.setInterestRate("16.1");
        viewModel.setStartMonth("4");
        viewModel.setTypePayment(TypePayment.Annuity);
        viewModel.setCurrency(Currency.RUR);
        viewModel.calculate();

        assertEquals(ViewModel.UserInputStatus.SUCCESS, viewModel.getStatus());
    }
    @Test
    public void isStatusSuccessDifferentiated() {
        viewModel.setSum("1");
        viewModel.setPaymentPeriod("1");
        viewModel.setInterestRate("16.1");
        viewModel.setStartMonth("4");
        viewModel.setTypePayment(TypePayment.Differentiated);
        viewModel.setCurrency(Currency.RUR);
        viewModel.calculate();

        assertEquals(ViewModel.UserInputStatus.SUCCESS, viewModel.getStatus());
    }
    @Test
    public void isStatusSuccessDollar() {
        viewModel.setSum("1");
        viewModel.setPaymentPeriod("1");
        viewModel.setInterestRate("16.1");
        viewModel.setStartMonth("4");
        viewModel.setTypePayment(TypePayment.Differentiated);
        viewModel.setCurrency(Currency.USD);
        viewModel.calculate();

        assertEquals(ViewModel.UserInputStatus.SUCCESS, viewModel.getStatus());
    }
    @Test
    public void isBadFormatSum() {
        viewModel.setSum("test");
        viewModel.setPaymentPeriod("4");
        viewModel.setInterestRate("16.1");
        viewModel.setStartMonth("4");

        viewModel.calculate();

        assertEquals(ViewModel.UserInputStatus.BAD_FORMAT, viewModel.getStatus());
    }
    @Test
    public void isBadFormatPaymentPeriod() {
        viewModel.setSum("1");
        viewModel.setPaymentPeriod("test");
        viewModel.setInterestRate("16.1");
        viewModel.setStartMonth("4");

        viewModel.calculate();

        assertEquals(ViewModel.UserInputStatus.BAD_FORMAT, viewModel.getStatus());
    }
    @Test
    public void isBadFormatInterestRate() {
        viewModel.setSum("1");
        viewModel.setPaymentPeriod("4");
        viewModel.setInterestRate("test");
        viewModel.setStartMonth("4");

        viewModel.calculate();

        assertEquals(ViewModel.UserInputStatus.BAD_FORMAT, viewModel.getStatus());
    }
    @Test
    public void isBadFormatStartMonth() {
        viewModel.setSum("1");
        viewModel.setPaymentPeriod("4");
        viewModel.setInterestRate("16.1");
        viewModel.setStartMonth("test");

        viewModel.calculate();

        assertEquals(ViewModel.UserInputStatus.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void isNullSum() {
        viewModel.setSum("0");
        viewModel.setPaymentPeriod("4");
        viewModel.setInterestRate("16.1");
        viewModel.setStartMonth("3");

        viewModel.calculate();

        assertEquals(ViewModel.UserInputStatus.IS_NULL, viewModel.getStatus());
    }

    @Test
    public void isNullPaymentPeriod() {
        viewModel.setSum("2000");
        viewModel.setPaymentPeriod("0");
        viewModel.setInterestRate("16.1");
        viewModel.setStartMonth("3");

        viewModel.calculate();

        assertEquals(ViewModel.UserInputStatus.IS_NULL, viewModel.getStatus());
    }

    @Test
    public void isNullInterestRate() {
        viewModel.setSum("2000");
        viewModel.setPaymentPeriod("2");
        viewModel.setInterestRate("0");
        viewModel.setStartMonth("3");

        viewModel.calculate();

        assertEquals(ViewModel.UserInputStatus.IS_NULL, viewModel.getStatus());
    }

    @Test
    public void isNullStartMonth() {
        viewModel.setSum("2000");
        viewModel.setPaymentPeriod("2");
        viewModel.setInterestRate("4.3");
        viewModel.setStartMonth("0");

        viewModel.calculate();

        assertEquals(ViewModel.UserInputStatus.IS_NULL, viewModel.getStatus());
    }

    @Test
    public void canGetAllSumPayment() {
        setViewModelVariables();

        viewModel.calculate();
        Double allSum = Double.parseDouble(viewModel.getAllSum());
        assertTrue(allSum > 10430 && allSum < 10450);
    }

    @Test
    public void canGetStartDateOfPayment() {
        viewModel.setSum("10000");
        viewModel.setPaymentPeriod("14");
        viewModel.setInterestRate("15");
        viewModel.setStartMonth("1");
        viewModel.setTypePayment(TypePayment.Annuity);
        viewModel.setCurrency(Currency.USD);

        viewModel.calculate();
        assertTrue(viewModel.getStartDateOfPayment().equals("2.2014"));
    }


    @Test
    public void canGetFinishDateOfPayment() {
        setViewModelVariables();
        viewModel.calculate();
        assertTrue(viewModel.getFinishDateOfPayment().equals("5.2015"));
    }

    @Test
    public void canGetOverPayment() {
        setViewModelVariables();
        viewModel.calculate();
        Double overPayment = Double.parseDouble(viewModel.getOverPayment());
        assertTrue(overPayment > 430 && overPayment < 450);
    }

    @Test
    public void canGetFirstPayment() {
        setViewModelVariables();
        viewModel.calculate();
        Double firstPayment = Double.parseDouble(viewModel.getFirstPayment());
        assertTrue(firstPayment > 1730 && firstPayment < 1750);
    }

    @Test
    public void canSetAddTypePaymentAnnuity() {
        viewModel.setTypePayment(TypePayment.Annuity);
        assertEquals(TypePayment.Annuity, viewModel.getTypePayment());
    }

    @Test
    public void canSetAddTypePaymentDifferentiated() {
        viewModel.setTypePayment(TypePayment.Differentiated);
        assertEquals(TypePayment.Differentiated, viewModel.getTypePayment());
    }

    @Test
    public void canGetTypePaymentName() {
        String annuity = TypePayment.Annuity.toString();
        assertEquals("Annuity", annuity);
    }

    @Test
    public void canGetCurrencyName() {
        String rub = Currency.RUR.toString();
        assertEquals("R", rub);
    }
    @Test
    public void canSetCurrencyDollar() {
        viewModel.setCurrency(Currency.USD);
        assertEquals(Currency.USD, viewModel.getCurrency());
    }

    @Test
    public void canSetCurrencyRub() {
        viewModel.setCurrency(Currency.RUR);
        assertEquals(Currency.RUR, viewModel.getCurrency());
    }

    public void setViewModelVariables() {
        viewModel.setSum("10000");
        viewModel.setPaymentPeriod("6");
        viewModel.setInterestRate("15");
        viewModel.setStartMonth("11");
        viewModel.setTypePayment(TypePayment.Annuity);
        viewModel.setCurrency(Currency.RUR);
    }
    @Test
    public void canCreateViewModelWithLogger() {
        FakeLog logger = new FakeLog();
        ViewModel viewModelLogged = new ViewModel(logger);

        assertNotNull(viewModelLogged);
    }

    @Test
    public void isLogEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isCalculatePuttingSomething() {
        viewModel.calculate();

        List<String> log = viewModel.getLog();
        assertNotEquals(0, log.size());
    }

    @Test
    public void isLogContainsProperMessage() {
        viewModel.calculate();
        String report = viewModel.getLog().get(0);

        assertThat(report,
                matchesPattern(".*" + ViewModel.LogMessages.CALCULATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void isLogContainsInputArguments() {
        setViewModelVariables();

        viewModel.calculate();

        String report = viewModel.getLog().get(0);
        assertThat(report, matchesPattern(".*" + viewModel.getSum()
                        + ".*" + viewModel.getPaymentPeriod()
                        + ".*" + viewModel.getInterestRate()
                        + ".*" + viewModel.getStartMonth()
                        + ".*" + viewModel.getCurrency()
                        + ".*" + viewModel.getTypePayment() + ".*"
        ));
    }

    @Test
    public void isProperlyFormattingInfoAboutArguments() {
        setViewModelVariables();

        viewModel.calculate();
        String report = viewModel.getLog().get(0);

        assertThat(report, matchesPattern(".*Arguments"
                        + ": sum = " + viewModel.getSum()
                        + "; paymentPeriod = " + viewModel.getPaymentPeriod()
                        + "; interestRate = " + viewModel.getInterestRate()
                        + "; startMonth = " + viewModel.getStartMonth()
                        + "; currency: " + viewModel.getCurrency()
                        + "; typePayment: " + viewModel.getTypePayment()
                        + ".*"
        ));
    }

    @Test
     public void isRurCurrencyMentionedInTheLog() {
        viewModel.setCurrency(Currency.RUR);

        viewModel.calculate();

        String report = viewModel.getLog().get(0);
        assertThat(report, matchesPattern(".*R.*"));
    }

    @Test
    public void isUSDMulOperationMentionedInTheLog() {
        viewModel.setCurrency(Currency.USD);

        viewModel.calculate();

        String report = viewModel.getLog().get(0);
        assertThat(report, matchesPattern(".*$.*"));
    }
   @Test
    public void isAnnTypePaymentMentionedInTheLog() {
        viewModel.setTypePayment(TypePayment.Annuity);

        viewModel.calculate();

        String report = viewModel.getLog().get(0);
        assertThat(report, matchesPattern(".*Ann.*"));
    }

    @Test
    public void isDifTypePaymentMentionedInTheLog() {
        viewModel.setTypePayment(TypePayment.Differentiated);

        viewModel.calculate();

        String report = viewModel.getLog().get(0);
        assertThat(report, matchesPattern(".*Diff.*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        setViewModelVariables();

        viewModel.calculate();
        viewModel.calculate();
        viewModel.calculate();

        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void canSeeCurrencyChangeInLog() {
        viewModel.setCurrency(Currency.USD);

        String report = viewModel.getLog().get(0);
        assertThat(report,
                matchesPattern(".*" + ViewModel.LogMessages.CURRENCY_WAS_CHANGED + "\\$.*"));
    }

    @Test
    public void isCurrencyNotLoggedWhenNotChanged() {
        viewModel.setCurrency(Currency.RUR);
        viewModel.setCurrency(Currency.RUR);

        assertEquals(0, viewModel.getLog().size());
    }

    @Test
    public void isEditingFinishLogged() {
        viewModel.setSum("10000");

        viewModel.focusLost();

        String report = viewModel.getLog().get(0);
        assertThat(report, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void areArgumentsCorrectlyLoggedOnEditingFinish() {
        setViewModelVariables();
        viewModel.focusLost();

        String report = viewModel.getLog().get(0);
        assertThat(report, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED
                + "Input arguments are: \\["
                + viewModel.getSum() + "; "
                + viewModel.getPaymentPeriod() + "; "
                + viewModel.getInterestRate() + "; "
                + viewModel.getStartMonth() + "; "
                + viewModel.getCurrency() + "; "
                + viewModel.getTypePayment() + "\\]"));
    }

    @Test
    public void doNotLogSameParametersTwiceWithPartialInput() {
        viewModel.setSum("1000");
        viewModel.setSum("1000");
        viewModel.setSum("1000");

        viewModel.focusLost();
        viewModel.focusLost();
        viewModel.focusLost();

        assertEquals(1, viewModel.getLog().size());
    }
    @Test
    public void doNotLogSameParametersTwice() {
        setViewModelVariables();
        setViewModelVariables();

        viewModel.focusLost();
        viewModel.focusLost();

        String report = viewModel.getLog().get(0);
        assertThat(report, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED + ".*"));
        assertEquals(1, viewModel.getLog().size());
    }

}
