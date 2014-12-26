package ru.unn.agile.LeftistHeap.infrastrucure;

public class LoggerException extends Exception {
    public LoggerException(final String message) {
        super(message);
    }

    public LoggerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
