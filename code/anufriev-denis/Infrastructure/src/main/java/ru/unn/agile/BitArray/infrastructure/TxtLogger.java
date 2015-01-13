package ru.unn.agile.BitArray.infrastructure;

import ru.unn.agile.BitArray.viewModel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private final String filename;
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

    public TxtLogger(final String filename) {
        this.filename = filename;

        BufferedWriter bufferWriter = null;
        try {
            bufferWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = bufferWriter;
    }

    @Override
    public void log(final String logString) {
        try {
            writer.write(now() + " > " + logString);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader br;
        ArrayList<String> log = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();

            while (line != null) {
                log.add(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

}
