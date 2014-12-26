package ru.unn.agile.LeftistHeap.infrastructure;

import ru.unn.agile.LeftistHeap.infrastrucure.LoggerException;
import ru.unn.agile.LeftistHeap.infrastrucure.TxtFileLogger;
import ru.unn.agile.LeftistHeap.viewmodel.ViewModel;
import ru.unn.agile.LeftistHeap.viewmodel.ViewModelTests;

import static org.junit.Assert.fail;

public class ViewModelWithTxtFileLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        try {
            TxtFileLogger realLogger =
                    new TxtFileLogger("./ViewModel_with_TxtFileLogger_Tests.log");
            super.setExternalViewModel(new ViewModel(realLogger));
        } catch (LoggerException exception) {
            fail("Can not create logger: " + exception.getMessage());
        }
    }
}
