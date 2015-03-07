package ru.unn.agile.CalculateVolume.infrastructure;

import ru.unn.agile.CalculateVolume.viewmodel.ViewModel;
import ru.unn.agile.CalculateVolume.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("./ViewModelTxtLogTest.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
