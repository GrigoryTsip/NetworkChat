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
 * ��������� ������ ���-�����.
 *
 * @author gntsi
 * @version 1.0
 * @created 30-���-2023 16:19:26
 */
public class LogRecord {

    private long idLogRecord;
    /**
     * ���� � ����� ��������.
     */
    protected Date data;
    /**
     * ��� ����������� ���������.
     */
    protected String senderNick;
    /**
     * ���� ���������.
     */
    protected String messageBody;
    /**
     * ������ � ������ �����������. � �������� ������������ ������������ ������� @.
     */
    protected String recipientNick;
    /**
     * ��� ���������� ��������
     */
    protected ExitCode codeOfResult;

    public OperationType operationType;

    public LogRecord() {
        IDFactory idFactory = new IDFactory();
        this.idLogRecord = idFactory.buildID(this);
    }

    /**
     * ������ � ������� ������� �������� ������ ���� � ��� �������� START_SERVER.
     * ��������� ���� �����.
     *
     * @param oper     ��� ��������
     * @param message  ���������, �������������� ��������, ��� null
     * @param exitCode ��� ���������� ��������
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
     * ���-���� ������������ ����� ���� ������� json � ��������, ���������������� ������������ ��������� ����.
     * ����� ��������� ���� ��� ����������� ������� � ����� �����.
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
     * ���-���� ������������ ����� ���� ������� json � ��������, ����������������
     * ������������ ��������� ����.
     *
     * @param record ������ ��� ������ � ���.
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
     * ��� ��������.
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * ���� � ����� ��������.
     */
    public Date getData() {
        return data;
    }

    /**
     * ���� � ����� ��������.
     */
    public void setData() {
        this.data = new GregorianCalendar().getTime();
    }

    /**
     * ��� ����������� ���������.
     */
    public String getSenderNick() {
        return senderNick;
    }

    /**
     * ��� ����������� ���������.
     */
    public void setSenderNick(String newVal) {
        this.senderNick = newVal;
    }

    /**
     * ���� ���������.
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * ���� ���������.
     */
    public void setMessageBody(String newVal) {
        messageBody = newVal;
    }

    /**
     * ������ � ������ �����������. � �������� ������������ ������������ ������� @.
     */
    public String getRecipientNick() {
        return recipientNick;
    }

    /**
     * ������ � ������ �����������. � �������� ������������ ������������ ������� @.
     */
    public void setRecipientNick(String newVal) {
        recipientNick = newVal;
    }

    /**
     * ��� ���������� ��������
     */
    public ExitCode getCodeOfResult() {
        return codeOfResult;
    }

    /**
     * ��� ���������� ��������
     */
    public void setCodeOfResult(ExitCode newVal) {
        codeOfResult = newVal;
    }

}