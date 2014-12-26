package ru.unn.agile.QuadraticEquation.infrastructure;

import ru.unn.agile.QuadraticEquation.viewmodel.ViewModel;
import ru.unn.agile.QuadraticEquation.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModelWithTxtLoggerTests.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
