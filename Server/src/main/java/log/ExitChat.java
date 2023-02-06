package log;

import talkshow.ExitCode;
import talkshow.Message;

import static log.OperationType.EXIT_CHAT;

/**
 * @author gntsi
 * @version 1.0
 * @created 04-фев-2023 13:53:06
 */
public class ExitChat extends LogRecord {

    private final OperationType type = EXIT_CHAT;

    public ExitChat() { super(); }

    /**
     * Дата и время операции.
     */
    public void setData() { super.setData(); }

    /**
     * Ник отправителя сообщения.
     */

    public void setSenderNick(String newVal) {
        this.senderNick = newVal;
    }

    /**
     * Код завершения операции
     */

    public void setCodeOfResult(ExitCode newVal) {
        codeOfResult = newVal;
    }

    public Message exitChat() {

    }

}