package ru.unn.agile.calculateSalary.ViewModel;

import java.util.List;

public interface UniversalLogger {
    void textInLog(final String message);
    List<String> getLog();
}
