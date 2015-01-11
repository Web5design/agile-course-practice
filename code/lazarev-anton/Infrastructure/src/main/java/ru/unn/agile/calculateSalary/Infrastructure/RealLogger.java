package ru.unn.agile.calculateSalary.Infrastructure;

import ru.unn.agile.calculateSalary.ViewModel.UniversalLogger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RealLogger implements UniversalLogger {
    private static final String FORMAT_OF_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter inputContainer;
    private final String fileName;

    public RealLogger(final String loggerName) {
        this.fileName = loggerName;
        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(loggerName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        inputContainer = logWriter;
    }

    private static String rightNow() {
        Calendar nowData = Calendar.getInstance();
        SimpleDateFormat formatNowData = new SimpleDateFormat(FORMAT_OF_NOW, Locale.ENGLISH);
        return  formatNowData.format(nowData.getTime());
    }

    @Override
    public void textInLog(final String message) {
        try {
            inputContainer.write(rightNow() + " > " + message);
            inputContainer.newLine();
            inputContainer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader fromTextBuffer;
        ArrayList<String> allLog = new ArrayList<String>();
        try {
            fromTextBuffer = new BufferedReader(new FileReader(fileName));
            String line = fromTextBuffer.readLine();
            while (line != null) {
                allLog.add(line);
                line = fromTextBuffer.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allLog;
    }
}
