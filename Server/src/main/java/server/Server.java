package server;

import log.LogRecord;
import log.OperationType;
import log.ServerStart;
import stringmsg.StringMessage;
import talkshow.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

import static talkshow.ExitCode.SERVER_NOT_INITIALIZE;
import static talkshow.ExitCode.SUCCESS;

/**
 * @author gntsi
 * @version 1.0
 * @created 30-���-2023 16:51:48
 */
public class Server {

    /**
     * ������ ���������� ����� �� ��������� ��������� ���������� ����.
     */
    private static ServerSocket serverSocket;
    // ������� �������������� ���������
    public static final int ABQ_CAPACITY = 10;
    public static final ArrayBlockingQueue<StringMessage> stringMessages = new ArrayBlockingQueue<>(ABQ_CAPACITY);

    public static final ArrayBlockingQueue<Future<StringMessage>> readMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    public static final ArrayBlockingQueue<Future<Message>> parseMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    public static final ArrayBlockingQueue<Future<Message>> sendMessageTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
    public static final ArrayBlockingQueue<Future<Message>> sendLogTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);

    private final LogRecord log = new ServerStart();
    /**
     * ��� �������.
     */
    public static ExecutorService threadPool;

    public Server() {
    }

    /**
     * �������������� ����� �������, ������ ����� ����� � ���������� �� ���������
     * ������������� ������.
     * � ������ ��������� ������������� ������� ������ � ��������� ������.
     */
    public void runServer(int port, int reuseaddr) throws InterruptedException {

        try (ServerSocket servSocket = new ServerSocket(port, reuseaddr)) {
            serverSocket = servSocket;
            SUCCESS.resultMessage(SUCCESS);
            log.formLogRecord(OperationType.START_SERVER, null, SUCCESS);
        } catch (RuntimeException | IOException e) {
            log.formLogRecord(OperationType.START_SERVER, null, SERVER_NOT_INITIALIZE);
            SERVER_NOT_INITIALIZE.resultMessage(SERVER_NOT_INITIALIZE);
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * �������������� ���������� �����, �������� ��� � ��� ������� � ��������� � �������� ��������� �� ����������
     * ����.
     */
    public void runClientSocket() throws InterruptedException {

        Callable<StringMessage> userMessage = () -> {

            StringMessage clientMessage;
            clientMessage = new StringMessage();

            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                clientMessage.setInputCanal(in);
                clientMessage.setOutputCanal(out);
                clientMessage.setMessage(in.readLine());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return clientMessage;
        };

        Future<StringMessage> taskReadMessage = threadPool.submit(userMessage);
        readMessageTask.put(taskReadMessage);
    }



    /**
     * �������������� ��� �������.
     */
    public void initPoolThreads(int threadsNumber) {
         threadPool = Executors.newFixedThreadPool(threadsNumber);
    }


}