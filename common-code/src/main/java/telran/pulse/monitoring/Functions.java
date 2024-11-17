package telran.pulse.monitoring;

import java.time.ZoneId;
import java.util.logging.*;
import static telran.pulse.monitoring.Constants.*;

public interface Functions {
    public static Logger loggerSetUp(Logger logger) {
        Level loggerLevel = getLoggerLevel(logger);
        LogManager.getLogManager().reset();
        Handler handler = new ConsoleHandler();
        logger.setLevel(loggerLevel);
        handler.setLevel(Level.FINEST);
        logger.addHandler(handler);
        logger.config("logger level is " + loggerLevel);
        logger.config("System time zone is " + String.valueOf(ZoneId.systemDefault()));
        return logger;
    }

    private static Level getLoggerLevel(Logger logger) {
        String levelStr = System.getenv()
                .getOrDefault(LOGGER_LEVEL_ENV_VARIABLE, DEFAULT_LOGGER_LEVEL);
        Level res = null;
        try {
            res = Level.parse(levelStr);
        } catch (Exception e) {
            logger.warning(levelStr + " wrong logger level take default value " + DEFAULT_LOGGER_LEVEL);
            res = Level.parse(DEFAULT_LOGGER_LEVEL);
        }
        return res;
    }
}
