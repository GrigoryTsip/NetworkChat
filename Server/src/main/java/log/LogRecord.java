package log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.IDFactory;
import server.Settings;
import talkshow.ExitCode;
import talkshow.Message;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static server.Server.*;

/**
 * Структура записи лог-файла.
 *
 * @author gntsi
 * @version 1.0
 * @created 30-янв-2023 16:19:26
 */
public class LogRecord {

    private long idLogRecord;
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
    protected String recipientNick;
    /**
     * Код завершения операции
     */
    protected ExitCode codeOfResult;

    public OperationType operationType;

    public LogRecord() {
        IDFactory idFactory = new IDFactory();
        this.idLogRecord = idFactory.buildID(this);
    }

    /**
     * Запись о запуске сервера содержит только дату и тип операции START_SERVER.
     * Остальные поля пусты.
     *
     * @param oper     Тип операции
     * @param message  Сообщение, инициировавшее операцию, или null
     * @param exitCode Код завершения операции
     */
    public void formLogRecord(OperationType oper, Message message, ExitCode exitCode) throws InterruptedException {

        Callable<Message> inLog = () -> {
            LogRecord logRecord;
            switch (oper) {
                case START_SERVER -> {
                    ServerStart serverStart = new ServerStart();
                    serverStart.setData();
                    serverStart.setCodeOfResult(exitCode);
                    logRecord = serverStart;
                }
                case REGISTRATION -> {
                    Registration registration = new Registration();
                    registration.setData();
                    registration.setSenderNick(stringMessageList
                            .get(message.getIDStringMessage()));
                    registration.setCodeOfResult(exitCode);
                    logRecord = registration;
                }
                case CONNECTION -> {
                    Connection connection = new Connection();
                    connection.setData();
                    connection.setSenderNick();
                    connection.setCodeOfResult(exitCode);
                    logRecord = connection;
                }
                case RECEIVE_MESSAGE -> {
                    ReceiveMessage inMessage = new ReceiveMessage();
                    inMessage.setData();

                }
                case SEND_MESSAGE -> {
                }
                case EXIT_CHAT -> {
                    ExitChat exitChat = new ExitChat();
                    exitChat.setData();
                    exitChat.setSenderNick();
                    exitChat.setCodeOfResult(exitCode);
                    logRecord = exitChat;
                }
                default -> throw new IllegalStateException("Unexpected value: " + oper);
            }
            writeLogRecord(logRecord);
            return null;
        };

        Future<Message> task = threadPool.submit(inLog);
        sendLogTask.put(task);
    }

    /**
     * Лог-файл представляет собой файл формата json с записями, соответствующими логированным операциям чата.
     * Метод открывает файл для дописывания записей в конец файла.
     */
    public FileWriter openLogFile(String path, String name) {

        try (FileWriter file = new FileWriter(path + name, true)) {
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Лог-файл представляет собой файл формата json с записями, соответствующими
     * логированным операциям чата.
     *
     * @param record Объект для записи в лог.
     */
    public void writeLogRecord(LogRecord record) {

        Settings file = new Settings();

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder
                .setPrettyPrinting()
                .create();
        try {
            file.getReferenceToLogFile().write(gson.toJson(record));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Тип операции.
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * Дата и время операции.
     */
    public Date getData() {
        return data;
    }

    /**
     * Дата и время операции.
     */
    public void setData() {
        this.data = new GregorianCalendar().getTime();
    }

    /**
     * Ник отправителя сообщения.
     */
    public String getSenderNick() {
        return senderNick;
    }

    /**
     * Ник отправителя сообщения.
     */
    public void setSenderNick(String newVal) {
        this.senderNick = newVal;
    }

    /**
     * Тело сообщения.
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * Тело сообщения.
     */
    public void setMessageBody(String newVal) {
        messageBody = newVal;
    }

    /**
     * Строка с никами получателей. В качестве разделителей используется ведущий @.
     */
    public String getRecipientNick() {
        return recipientNick;
    }

    /**
     * Строка с никами получателей. В качестве разделителей используется ведущий @.
     */
    public void setRecipientNick(String newVal) {
        recipientNick = newVal;
    }

    /**
     * Код завершения операции
     */
    public ExitCode getCodeOfResult() {
        return codeOfResult;
    }

    /**
     * Код завершения операции
     */
    public void setCodeOfResult(ExitCode newVal) {
        codeOfResult = newVal;
    }

}