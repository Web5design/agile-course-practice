package ru.unn.agile.CreditCalculator.infrastructure;

import ru.unn.agile.CreditCalculator.ViewModel.ViewModel;
import ru.unn.agile.CreditCalculator.ViewModel.ViewModelTests;

public class ViewModelWithLogWriterTests extends ViewModelTests {
    @Override
    public void setUp() {
        LogWriter realLogger =
            new LogWriter("./ViewModelWithTxtLoggerTests-Zolotov-Andrey.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
