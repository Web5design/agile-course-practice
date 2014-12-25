package ru.unn.agile.BitArray.infrastructure;

import ru.unn.agile.BitArray.viewModel.ViewModel;
import ru.unn.agile.BitArray.viewModel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModelWithTxtLoggerTests-lab3.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
