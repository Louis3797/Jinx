package org.jinx.logging_formatter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogFileFormatter extends SimpleFormatter {
    private final Date date = new Date();

    @Override
    public String format(LogRecord record) {
        date.setTime(record.getMillis());
        String source;
        if (record.getSourceClassName() != null) {
            source = record.getSourceClassName();
            if (record.getSourceMethodName() != null) {
                source += " " + record.getSourceMethodName();
            }
        } else {
            source = record.getLoggerName();
        }
        String message = formatMessage(record);
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        }
        return "Date: " + date + "\nSource: " +
                source + "\nLogger Name: " +
                record.getLoggerName() + "\nLevel: " +
                record.getLevel().getLocalizedName() + "\nMessage: " +
                message + "\nError: " +
                throwable + "\n*******************************************************************\n";
    }
}
