package ru.unn.agile.ComplexProcent.Infrastructure;

import ru.unn.agile.ComplexProcent.ViewModel.*;
public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
