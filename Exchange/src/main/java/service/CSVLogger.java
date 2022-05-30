package service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.*;

public class CSVLogger {
    private CSVLogger(){}

    private static Formatter CSVFormatter = new Formatter() {
        @Override
        public String format(LogRecord logRecord) {
            String level = logRecord.getLevel().toString();
            Date moment = Calendar.getInstance().getTime();
            String action = logRecord.getMessage();
            String name = logRecord.getLoggerName();

            return level + "," + moment.toString() + "," + action + "," + name + "\n";
        }
    };

    public static Logger getInstance() {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        try {
            FileHandler handler = new FileHandler("src/main/java/files/logging.csv", true);
            handler.setFormatter(CSVFormatter);
            logger.addHandler(handler);
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getCause() + "\n" + e.getLocalizedMessage());
        }
        return logger;
    }
}
