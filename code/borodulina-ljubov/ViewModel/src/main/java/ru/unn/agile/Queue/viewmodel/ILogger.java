package ru.unn.agile.Queue.viewmodel;

import java.util.List;

public interface ILogger {
    void log(final Level level, final String s);

    List<String> getLog();

    public enum Level {
        ERR("LOG_ERR: "),
        INFO("LOG_INFO: "),
        DBG("LOG_DBG: ");

        private final String level;

        private Level(final String level) {
            this.level = level;
        }

        public String toString() {
            return level;
        }
    }
}
