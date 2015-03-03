package ru.unn.agile.Queue;

import ru.unn.agile.Queue.viewmodel.ViewModelTests;

public class ViewModelWithPlainTextLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        super.setLogger(new PlainTextLogger("ViewModel_PlainTextLogger_Tests.txt"));
    }
}
