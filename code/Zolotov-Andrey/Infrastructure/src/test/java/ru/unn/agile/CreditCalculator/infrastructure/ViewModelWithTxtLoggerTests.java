package ru.unn.agile.CreditCalculator.infrastructure;

import ru.unn.agile.CreditCalculator.ViewModel.ViewModel;
import ru.unn.agile.CreditCalculator.ViewModel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModelWithTxtLoggerTests-Zolotov-Andrey.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
