package server;


import log.LogRecord;
import stringmsg.BuildStringMessage;
import stringmsg.StringMessage;
import talkshow.ExitCode;
import talkshow.Message;
import talkshow.MessageType;

import java.util.ArrayList;
import java.util.concurrent.*;

import static talkshow.MessageType.EXIT_THE_CHAT;

/**
 * �����, ��������������� ��� ����������� � ������������ ������� (�����).
 * �������� ���������������� ������� ����� ������� � �� �����������.
 * ������������ ���������� ����� � ������� ��������.
 *
 * @author gntsi
 * @version 1.0
 * @created 13-���-2023 15:37:58
 */
public class ThreadService {

    /**
     * ��� �������.
     */
    public static ExecutorService threadPool;
    /**
     * ������ ������� �������������� ���������.
     */
    private static final int ABQ_CAPACITY = 30;
    /**
     * ������� ����������� �������������� ���������.
     */
    public static final ArrayBlockingQueue<StringMessage> stringMessages = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * ������� ������������ ���������, ������� � �������� ��������.
     */
    public static final ArrayBlockingQueue<Message> prepareMessages = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * ������� ������������ ���������, ������� � �����������.
     */
    public static final ArrayBlockingQueue<Message> prepareMessagesLog = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * ������� ����� �� ������ ���������� ���������.
     */
    public static final ArrayBlockingQueue<Future<MessageType>> readMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * ������� ����� �� ������ ����������� ���������.
     */
    public static final ArrayBlockingQueue<Future<Message>> parseMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * ������� ����� �� ���������� ��������� � �������� ��������.
     */
    public static final ArrayBlockingQueue<Future<ArrayList<Message>>> sendMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * ������� ����� �������� ���������.
     */
    public static final ArrayBlockingQueue<Future<ExitCode>> outMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * ������� ����� �� ���������� ������� � ������ � ���.
     */
    public static final ArrayBlockingQueue<Future<LogRecord>> sendLogTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);

    public ThreadService() {

    }

    /**
     * �������������� ��� �������.
     *
     * @param threadsNumber ���������� ������� � ����
     */
    public void initPoolThreads(int threadsNumber) {
        threadPool = Executors.newFixedThreadPool(threadsNumber);
    }

    /**
     * ������ ������������ �������� ������� ������ �������� ���������.
     */
    public void controlReadTask() {

        ArrayList<Future<MessageType>> removableTask = new ArrayList<>();

        for (Future<MessageType> task : readMessageTask) {
            if (task.isDone()) {
                removableTask.add(task);
            }
        }
        for (Future<MessageType> task : removableTask) {
            readMessageTask.remove(task);
        }
    }

    /**
     * ������ �������� ������� ��������� ��������� ��� ���������� �������� �
     * �����������.
     */
    public void controlPrepareTask() throws ExecutionException, InterruptedException {
        ArrayList<Future<Message>> removableTask = new ArrayList<>();

        for (Future<Message> task : parseMessageTask) {
            if (task.isDone()) {
                Message msg = task.get();
                if (!(EXIT_THE_CHAT.equals(msg.getType()))) prepareMessages.put(msg);
                prepareMessagesLog.put(msg);
                new BuildStringMessage().sendMessageList();
                new LogRecord().formLogRecord();
                removableTask.add(task);
            }
        }

        for (Future<Message> task : removableTask) {
            parseMessageTask.remove(task);
        }
    }

    /**
     * ������ ������������ �������� ������� ��������� ��������� ��������� ���
     * �����������.
     */
    public void controlSendLogTask() throws ExecutionException, InterruptedException {
        ArrayList<Future<ArrayList<Message>>> removableTask = new ArrayList<>();
        LogRecord logRecord = new LogRecord();

        for (Future<ArrayList<Message>> task : sendMessageTask) {
            if (task.isDone()) {
                ArrayList<Message> arrMsg = task.get();
                for (Message msg : arrMsg) {
                    prepareMessagesLog.put(msg);
                    logRecord.formLogRecord();
                }
                removableTask.add(task);
            }
        }

        for (Future<ArrayList<Message>> task : removableTask) {
            sendMessageTask.remove(task);
        }
    }

    /**
     * ������ �������� ������� �������� ���������.
     */
    public void controlOutTask() throws ExecutionException, InterruptedException {
        ArrayList<Future<ExitCode>> removableTask = new ArrayList<>();

        for (Future<ExitCode> task : outMessageTask) {
            if (task.isDone()) task.get();
            removableTask.add(task);
        }

        for (Future<ExitCode> task : removableTask) {
            outMessageTask.remove(task);
        }
    }

    /**
     * ������ �������� ������� ������ � ���.
     */
    public void controlLogTask() throws ExecutionException, InterruptedException {
        ArrayList<Future<LogRecord>> removableTask = new ArrayList<>();

        for (Future<LogRecord> task : sendLogTask) {
            if (task.isDone()) task.get();
            removableTask.add(task);
        }

        for (Future<LogRecord> task : removableTask) {
            sendLogTask.remove(task);
        }
    }

}