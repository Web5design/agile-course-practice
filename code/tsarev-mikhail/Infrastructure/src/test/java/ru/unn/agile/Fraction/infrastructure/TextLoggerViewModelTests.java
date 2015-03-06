package ru.unn.agile.Fraction.infrastructure;

import ru.unn.agile.Fraction.viewmodel.ViewModel;
import ru.unn.agile.Fraction.viewmodel.ViewModelTests;

public class TextLoggerViewModelTests extends ViewModelTests {
    @Override
    public void setUp() {
        TextLogger realLogger =
                new TextLogger("./TextLoggerViewModelTests.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}

