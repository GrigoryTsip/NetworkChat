package log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.ThreadService;
import stringmsg.ParseStringMessage;
import talkshow.ExitCode;
import talkshow.Message;
import talkshow.MessageFactory;
import talkshow.MessageType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static server.ThreadService.*;
import static talkshow.MessageType.*;

/**
 * Структура записи лог-файла.
 *
 * @author gntsi
 * @version 1.0
 * @created 30-янв-2023 16:19:26
 */
public class LogRecord {

    private String messageType;

    /**
     * Дата и время операции.
     */
    protected Date data;
    /**
     * Ник отправителя сообщения.
     */
    protected String senderNick;
    /**
     * Тело сообщения.
     */
    protected String messageBody;
    /**
     * Строка с никами получателей. В качестве разделителей используется ведущий @.
     */
    protected String recipientNicks;
    /**
     * Код завершения операции
     */
    protected String codeOfResult;
    private String wrongRecipients;
    private String notActiveRecipients;

    private ParseStringMessage parseStrMsg;


    public LogRecord() {
    }

    public LogRecord(ParseStringMessage psm) {
        //связываем формируемую запись в логе с исходным сообщением
        this.parseStrMsg = psm;
    }

    /**
     * Запись о запуске сервера содержит только дату и тип операции START_SERVER.
     * Остальные поля пусты.
     */
    public void formLogRecord() throws InterruptedException, ExecutionException {

        Callable<LogRecord> inLog = () -> {
            Message message = prepareMessagesLog.take();
            return getLogRecord(message);
        };

        ThreadService threadSevice = new ThreadService();
        Future<LogRecord> task = threadPool.submit(inLog);
        sendLogTask.put(task);
        threadSevice.controlLogTask();
    }

    public LogRecord getLogRecord(Message message) {
        MessageFactory msgFactory = new MessageFactory();
        MessageType type = message.getType();
        LogRecord logRecord = new LogRecord();

        logRecord.setMessageType(type);
        logRecord.setData(message.getDataOfMessage());

        if (type != START_SERVER) {
            logRecord.setSenderNick(msgFactory.getSenderNick(message.getSenderID()));
            logRecord.setParseStrMsg(msgFactory.getParseStringMessage(message.getIDStringMessage()));
            this.recipientNicks = recipientsList(logRecord.parseStrMsg.getRecipients());

            if (type == RECEIVED_MESSAGE) {
                logRecord.setRecipientNick(this.getRecipientNick());
                logRecord.setWrongRecipients(message.getUserNotInChat());
                logRecord.setNotActiveRecipients(message.getUsersNotActive());
            } else {
                logRecord.setWrongRecipients(null);
                logRecord.setNotActiveRecipients(null);
                if (message.getType() == SEND_MESSAGE) {
                    msgFactory.getActiveRecipient(message,
                            logRecord.parseStrMsg.getMessageBlocRecipients());
                    logRecord.recipientNicks = logRecord.parseStrMsg.getMessageBlocRecipients();
                } else {
                    logRecord.setRecipientNick(null);
                }
            }
            logRecord.setMessageBody(message.getBody());
        }

        ExitCode exitCode = message.getResult();
        logRecord.setCodeOfResult(ExitCode.toString(exitCode));

        logRecord.parseStrMsg = null;
        writeLogRecord(logRecord);
        return logRecord;
    }


    private String recipientsList(String[] recipients) {
        if (recipients == null || recipients.length == 0) return null;

        StringBuilder rcp = new StringBuilder(recipients[0].trim());
        for (int i = 1; i < recipients.length; i++) {
            rcp.append(";").append(recipients[i].trim());
        }
        return rcp.toString();
    }

    /**
     * Лог-файл представляет собой файл формата json с записями, соответствующими
     * логированным операциям чата.
     *
     * @param record Объект для записи в лог.
     */
    public void writeLogRecord(LogRecord record) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder
                .setPrettyPrinting()
                .setDateFormat("dd-MM-yyyy HH:mm:ss")
                .create();
        String fileName = "Server/src/main/resources/logfile.json"; // getPathToFiles() + "logfile.json";
        try (FileWriter logfile = new FileWriter(fileName, true)) {
            logfile.write(gson.toJson(record));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Дата и время операции.
     */
    public Date getData() {
        return data;
    }

    public void setData(Date newVal) {
        data = newVal;
    }

    /**
     * Ник отправителя сообщения.
     */
    public String getSenderNick() {
        return senderNick;
    }

    public void setSenderNick(String newVal) {
        senderNick = newVal;
    }

    /**
     * Тело сообщения.
     */
    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String newVal) {
        messageBody = newVal;
    }

    /**
     * Строка с никами получателей. В качестве разделителей используется ведущий @.
     */
    public String getRecipientNick() {
        return recipientNicks;
    }

    public void setRecipientNick(String newVal) {
        recipientNicks = newVal;
    }

    /**
     * Код завершения операции
     */
    public String getCodeOfResult() {
        return codeOfResult;
    }

    public void setCodeOfResult(String newVal) {
        codeOfResult = newVal;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType newVal) {
        messageType = String.valueOf(newVal.toString(newVal));
    }

    public String getWrongRecipients() {
        return wrongRecipients;
    }

    public void setWrongRecipients(String newVal) {
        wrongRecipients = newVal;
    }

    public String getNotActiveRecipients() {
        return notActiveRecipients;
    }

    public void setNotActiveRecipients(String newVal) {
        notActiveRecipients = newVal;
    }

    public ParseStringMessage getParseStrMsg() {
        return parseStrMsg;
    }

    public void setParseStrMsg(ParseStringMessage newVal) {
        this.parseStrMsg = newVal;
    }
}