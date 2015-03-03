package ru.unn.agile.BitArray.viewModel;

import java.util.List;

public interface ILogger {
    void log(final String s);

    List<String> getLog();
}
