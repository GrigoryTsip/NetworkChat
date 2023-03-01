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
 * Класс, предназначенный для организации и обслуживания потоков (нитей).
 * Включает потокозащищенные очереди задач потоков и их результатов.
 * Контролирует завершение задач и очистку очередей.
 *
 * @author gntsi
 * @version 1.0
 * @created 13-фев-2023 15:37:58
 */
public class ThreadService {

    /**
     * Пул потоков.
     */
    public static ExecutorService threadPool;
    /**
     * Размер очереди необработанных сообщений.
     */
    private static final int ABQ_CAPACITY = 30;
    /**
     * Очередь поступивших необработанных сообщений.
     */
    public static final ArrayBlockingQueue<StringMessage> stringMessages = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * Очередь обработанных сообщений, готовых к отправке адресату.
     */
    public static final ArrayBlockingQueue<Message> prepareMessages = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * Очередь обработанных сообщений, готовых к логированию.
     */
    public static final ArrayBlockingQueue<Message> prepareMessagesLog = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * Очередь задач на чтение приходящих сообщений.
     */
    public static final ArrayBlockingQueue<Future<MessageType>> readMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * Очередь задач на разбор поступивших сообщений.
     */
    public static final ArrayBlockingQueue<Future<Message>> parseMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * Очередь задач на подготовку сообщений к отправке адресату.
     */
    public static final ArrayBlockingQueue<Future<ArrayList<Message>>> sendMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * Очередь задач отправки сообщений.
     */
    public static final ArrayBlockingQueue<Future<ExitCode>> outMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    /**
     * Очередь задач на подготовку записей и запись в лог.
     */
    public static final ArrayBlockingQueue<Future<LogRecord>> sendLogTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);

    public ThreadService() {

    }

    /**
     * Инициализируем пул потоков.
     *
     * @param threadsNumber Количество потоков в пуле
     */
    public void initPoolThreads(int threadsNumber) {
        threadPool = Executors.newFixedThreadPool(threadsNumber);
    }

    /**
     * Сервис осуществляет контроль потоков чтения входящих сообщений.
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
     * Сервис контроля потоков обработки сообщений для дальнейшей отправки и
     * логирования.
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
     * Сервис осуществляет контроль потоков обработки исходящих сообщений для
     * логирования.
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
     * Сервис контроля потоков отправки сообщений.
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
     * Сервис контроля потоков записи в лог.
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