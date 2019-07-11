package com.logger.logger;

import com.logger.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class Logger {

    @Autowired
    private SessionService sessionService;

    /**
     *
     * @param httpEntity it can be RequestEntity, ResponseEntity or Exception
     * @param sessionId
     */
    public void log(Object httpEntity, String sessionId) {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = dateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (sessionId == null) {
            sessionId = "commonCalls";
        }

        if (httpEntity instanceof RequestEntity) {
            sessionService.updateCallCount(sessionId);
        }

        String separator = File.separator;
        StringBuilder rootPath = new StringBuilder();
        rootPath.append("D:").append(separator).append("logs").append(separator);

        StringBuilder logPath = new StringBuilder();
        logPath.append(calendar.get(Calendar.YEAR)).append(separator).append(calendar.get(Calendar.MONTH) + 1).append(separator)
                .append(calendar.get(Calendar.DAY_OF_MONTH)).append(separator).append(sessionId).append(separator);

        StringBuilder fullPathString = new StringBuilder(rootPath.toString() + logPath.toString());

        int callCount = sessionService.getCallCount(sessionId);

        if (httpEntity instanceof RequestEntity) {
            fullPathString.append(callCount).append(separator).append("request").append(separator);
        } else {
            fullPathString.append(callCount).append(separator).append("response").append(separator);
        }

        File fullPath = new File(fullPathString.toString());
        File logFile;

        if (!fullPath.exists()) {
            fullPath.mkdirs();
        }

        logFile = new File(fullPathString.toString() + callCount + ".log");

        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile));){
            writer.write(time + "\n");
            if (httpEntity instanceof Exception) {
                ((Exception) httpEntity).printStackTrace(writer);
            } else {
                writer.write( httpEntity + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
