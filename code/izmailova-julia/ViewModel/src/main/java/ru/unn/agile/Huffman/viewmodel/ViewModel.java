package ru.unn.agile.Huffman.viewmodel;
import ru.unn.agile.Huffman.model.Huffman;
import javafx.beans.property.*;

public class ViewModel {
    private String message;
    private String codes;
    private String codeMessage;
    private String status;
    public ViewModel() {
        message = "";
        codes = "";
        codeMessage = "";
        status = Status.WAITING;
    }

    public ViewModel gettree() {
        if (message == "" || ((int) message.toCharArray()[0]) == 0) {
            codes = "";
            codeMessage = "";
            status = Status.BAD_FORMAT;
            return this;
        }
        Huffman[] huff = Huffman.buildTree(message);
        String s = "";
        String[] st = new String[ (int) 'z'];
        Huffman.buildCode(st, huff, s, message.length(), -1);
        codes = Huffman.getcode(st);
        codeMessage = Huffman.writeCode(st, message.toCharArray());
        status = Status.SUCCESS;
        return this;
    }

    public void setMessage(final String message) {
        if (message == "") {
            status = Status.BAD_FORMAT;
        }
        if (message.equals(this.message)) {
            return;
        }
        status = Status.READY;
        this.message = message;
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

    public final class Status {
        public static final String WAITING = "Please write your message";
        public static final String READY = "Press Code";
        public static final String BAD_FORMAT = "Bad format";
        public static final String SUCCESS = "Success";

        private Status() { }
    }
}
