package ru.unn.agile.ComplexProcent.ViewModel;

import java.util.List;

public interface ILogger {
    void log(final String s);

    List<String> getLog();
}
