package ru.unn.agile.Deque.viewmodel;

import java.util.List;

public interface ILogger {
    void log(Level level, final String msg);

    List<String> getLog();

    public enum Level {
        ERROR("ERROR: "),
        INFO("INFO: "),
        DEBUG("DEBUG: ");

        private final String name;

        private Level(final String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }
}
