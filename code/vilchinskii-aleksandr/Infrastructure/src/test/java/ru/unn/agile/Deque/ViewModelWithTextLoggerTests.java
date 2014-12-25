package ru.unn.agile.Deque;

import ru.unn.agile.Deque.viewmodel.ViewModelTests;

public class ViewModelWithTextLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        super.setLogger(new TextLogger("ViewModel_TextLogger_Tests.txt"));
    }
}
