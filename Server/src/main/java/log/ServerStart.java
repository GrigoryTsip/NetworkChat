package log;


import talkshow.ExitCode;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static log.OperationType.START_SERVER;
import static server.Settings.calendar;

/**
 * @author gntsi
 * @version 1.0
 * @created 30-янв-2023 16:19:26
 */
public class ServerStart extends LogRecord {

    private final OperationType type = START_SERVER;

    private ServerStart serverStart;

    public ServerStart() {
    }

    /**
     * Дата и время операции.
     */
    public void setData() {
        this.data = calendar.getTime();
    }

    public void setCodeOfResult(ExitCode code) {
        this.codeOfResult = code;
    }
}