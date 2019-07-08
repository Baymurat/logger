package com.logger.logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logger {
    public static void main(String[] args) throws IOException {
        //log("TEST", 11);
        String string = "one/session/12345/next";
        Pattern pattern = Pattern.compile("session/\\d+");
        Matcher matcher = pattern.matcher(string);


        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    public void log(Object message, String sessionId) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = dateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String separator = File.separator;
        StringBuilder rootPath = new StringBuilder();
        rootPath.append("D:").append(separator).append("logs").append(separator);

        StringBuilder logPath = new StringBuilder();
        logPath.append(calendar.get(Calendar.YEAR)).append(separator).append(calendar.get(Calendar.MONTH) + 1).append(separator)
                .append(calendar.get(Calendar.DAY_OF_MONTH)).append(separator).append(sessionId).append(separator);
        String callCount = "1";

        File fullPath = new File(rootPath.toString() + logPath.toString());
        File logFile;

        if (!fullPath.exists()) {
            fullPath.mkdirs();
            logFile = new File(rootPath.toString() + logPath.toString() + callCount + ".log");
        } else {
            //TODO call count
            logFile = new File(rootPath.toString() + logPath.toString() + callCount + ".log");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile));){
            writer.write(time + "\t");
            if (message instanceof Exception) {
                ((Exception) message).printStackTrace(writer);
            } else {
                writer.write(time + "\t" + message + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
