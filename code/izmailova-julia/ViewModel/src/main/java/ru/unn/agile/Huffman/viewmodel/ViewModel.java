package ru.unn.agile.Huffman.viewmodel;
import ru.unn.agile.Huffman.model.Huffman;

import java.util.List;

public class ViewModel {
    private String message;
    private String codes;
    private String codeMessage;
    private String status;
    private final ILogg logg;

    public ViewModel(final ILogg logg) {
        if (logg == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logg = logg;
        message = "";
        codes = "";
        codeMessage = "";
        status = CodeStatus.WAITING;
    }

    public ViewModel gettree() {
        if (message == "" || ((int) message.toCharArray()[0]) == 0) {
            codes = "";
            codeMessage = "";
            status = CodeStatus.BAD_FORMAT;
            return this;
        }
        Huffman[] huff = Huffman.buildTree(message);
        logg.log(LogMessages.TREE_IS_BUILD);
        String s = "";
        String[] st = new String[ (int) 'z'];
        Huffman.buildCode(st, huff, s, message.length(), -1);
        codes = Huffman.getcode(st);
        codeMessage = Huffman.writeCode(st, message.toCharArray());
        logg.log(LogMessages.CODING_IS_COMPLITED + codeMessage);
        status = CodeStatus.SUCCESS;
        return this;
    }

    public List<String> getLog() {
        return logg.takeLog();
    }

    private String logInputMessage() {
        String logMessage = LogMessages.MESSAGE_IS_INPUTED + message;
        return logMessage;
    }

    public void setMessage(final String message) {
        if (message == "") {
            status = CodeStatus.BAD_FORMAT;
        }
        if (message.equals(this.message)) {
            return;
        }
        status = CodeStatus.READY;
        this.message = message;
        logg.log(logInputMessage());
    }

    public String getCode() {
        return codes;
    }

    public String getCodeMessage() {
        return codeMessage;
    }

    public String getStatus() {
        return status;
    }

    public final class CodeStatus {
        public static final String WAITING = "Please write your message";
        public static final String READY = "Press Code";
        public static final String BAD_FORMAT = "Bad format";
        public static final String SUCCESS = "Success";

        private CodeStatus() { }
    }

    public final class LogMessages {
        public static final String MESSAGE_IS_INPUTED = "Message: ";
        public static final String TREE_IS_BUILD = "Tree is build";
        public static final String CODING_IS_COMPLITED = "Codding message: ";

        private LogMessages() { }
    }
}

