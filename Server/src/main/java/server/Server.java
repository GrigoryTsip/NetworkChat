package server;

import log.LogRecord;
import stringmsg.BuildStringMessage;
import talkshow.ExitCode;
import talkshow.Message;
import user.ActiveUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.GregorianCalendar;
import java.util.concurrent.*;

import static talkshow.ExitCode.SERVER_NOT_INITIALIZE;
import static talkshow.ExitCode.SUCCESS;
import static talkshow.MessageType.START_SERVER;

/**
 * @author gntsi
 * @version 1.0
 * @created 30-���-2023 16:51:48
 */
public class Server {

    /**
     * ������ ���������� ����� �� ��������� ��������� ���������� ����.
     */
    private ServerSocket serverSocket;
    private BufferedReader serverInSocket;
    private BufferedReader serverOutSocket;

    private final LogRecord log = new LogRecord();

    public Server() {
    }

    /**
     * �������������� ����� �������, ������ ����� ����� � ���������� �� ���������
     * ������������� ������.
     * � ������ ��������� ������������� ������� ������ � ��������� ������.
     */
    public void runServer(int port, int reuseaddr) throws InterruptedException, ExecutionException {

        try {
            ServerSocket servSocket = new ServerSocket(port, reuseaddr);

            serverSocket = servSocket;
            System.out.println("������ �������");
            System.out.println(ExitCode.toString(SUCCESS));
            ThreadService.prepareMessagesLog.put(serverStartMessage(SUCCESS));
            log.formLogRecord();
            runClientSocket();

        } catch (RuntimeException | IOException e) {
            ThreadService.prepareMessagesLog.put(serverStartMessage(SERVER_NOT_INITIALIZE));
            log.formLogRecord();
            System.out.println(ExitCode.toString(SERVER_NOT_INITIALIZE));
            throw new RuntimeException(e);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Message serverStartMessage(ExitCode code) {
        Message message = new Message();
        message.setDataOfMessage(new GregorianCalendar().getTime());
        message.setType(START_SERVER);
        message.setResult(code);
        return message;
    }

    /**
     * �������������� ���������� �����, �������� ��� � ��� ������� � ��������� � �������� ��������� �� ����������
     * ����.
     */
    public void runClientSocket() throws IOException, InterruptedException, ExecutionException {

        while (true) {
            Socket clientSocket = serverSocket.accept();
            //������� ������������ � ��������� ������ ���������, � �����
            // ��������� ��������� ����� �� ������ � ����������
            ActiveUser activeUser = new ActiveUser(clientSocket);
            serverInSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            activeUser.runUserThreadIn();
        }
    }

    public void sendChatMessage(BuildStringMessage message) throws InterruptedException, ExecutionException {
        ActiveUser activeUser = new ActiveUser(message.getRecipientSocket());
        activeUser.runUserThreadOut(message.getBuildMessage());

        new ThreadService().controlOutTask();
    }
}