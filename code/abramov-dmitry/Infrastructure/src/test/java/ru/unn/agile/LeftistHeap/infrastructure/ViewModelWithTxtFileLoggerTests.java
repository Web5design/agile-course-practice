package ru.unn.agile.LeftistHeap.infrastructure;

import ru.unn.agile.LeftistHeap.infrastrucure.TxtFileLogger;
import ru.unn.agile.LeftistHeap.viewmodel.ViewModel;
import ru.unn.agile.LeftistHeap.viewmodel.ViewModelTests;

public class ViewModelWithTxtFileLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtFileLogger realLogger = new TxtFileLogger("./ViewModel_with_TxtFileLogger_Tests.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
