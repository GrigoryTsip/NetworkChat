package stringmsg;

import server.IDFactory;
import talkshow.Message;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.*;

import static server.Server.*;

/**
 * ����� �������������� ���������� ��� ������������ ���������.
 * ������ ���������� ��������� ������������ ����� ������, ����������:
 * ����������� - ��������� messageSeparator � ���������� ������������� ���� �
 * ������� ��� ��������;
 * ����� �� ������������ ���� ������������� ������ (idTalkShow) �
 * �����������; ���� ������������� �� ���������, �� ������ ���� ��� �����������;
 * ��������� ���� - ����� �����������, �������������� ����� �����������
 * (userNickname) � �����������;
 * ����� ������������� ���� � ������ ����������� ���������, ���������������
 * ������ ���������� ������; ���� � ���� ����� ��������� "������ � �������"; ����
 * ����������� ������������;
 * ����� ����� ����������� ���� ���������� ���� ���������; ��� �����������
 * ��������� ���� � ���� ��������� ���������� ��� ���;
 * ����� ��������� ��������� ������������.
 * <p>
 * ��������� ��������� ��������� �������� ��������� �����������, ���� � ���������
 * ���������� ������. ������ ��������� ��������� �� ������:
 * ��������� �����������;
 * ������������� ���� � ������� ��� �������� ��������� ���������;
 * ����� �� ������������ ���������� ��� ������ ��������� ��������� ���������
 * � �����������;
 * ��������� ���� - �������������� ���� � ���� �� ������������������ � ����
 * ��������� (���� ����) � �����������;
 * ����� ������������� ���� � ������ ���������� �������������, ������� ��
 * ������� ��������� ��������� (���� ����); ���� � ���� ����� ��������� "������ �
 * �������"; ���� ����������� ������������;
 * ����� ���� ���� ���� ��������� - ������ ����������� ��������� ���
 * ����������� ������������� ������ � �������� ����������;
 * ����� ��������� ��������� ������������.
 * <p>
 * ������������ �������� ��������� ��������� � �������� ��������� ��������
 * ���������, �� ����������� ����� ����������� - � ���� ����� ������ ���� ���.
 *
 * @author gntsi
 * @version 1.0
 * @updated 02-���-2023 10:55:40
 */
public class StringMessage {

    /**
     * ������ ����������� ��� ������������� ���������.
     */
    private final long stringMessageID;
    private String message;
    /**
     * ������ (�����), ������������ ������ �������������� �������
     * � ������� ��������� ����.
     */
    private Socket userSocket;

    public StringMessage() {
        IDFactory idFactory = new IDFactory();
        this.stringMessageID = idFactory.buildID(this);
    }

    /**
     * ����������� ������. ��������� �������������� ��������� � ���� ������ ���
     * ���������� ���������.
     *
     * @param message ���������� ��������� � �������������� ����
     */

    public StringMessage(String message) {
        IDFactory idFactory = new IDFactory();
        this.stringMessageID = idFactory.buildID(this);
        this.message = message;
    }

    /**
     * �������� �����, �� �������� ������ ���� ��������� �����.
     */
    public void setUserSocket(Socket newVal) {
        this.userSocket = newVal;
    }

    public Socket getUserSocket() {
        return this.userSocket;
    }

    /**
     * ������ ����������� ��� ������������� ���������.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * ������ ����������� ��� ������������� ���������.
     */
    public void setMessage(String newVal) {
        message = newVal;
    }

    /**
     * �������� �� ������ (��������, ��� �������� ������� Message).
     */
    public long getStringMessageID() {
        return this.stringMessageID;
    }

    /**
     * ����������� ������. ��������� �������������� ��������� � ���� ������ ���
     * ���������� ���������.
     *
     * @param message ���������� ��������� � �������������� ����
     * @param userSocket      ������ �� ����� ��������� ����
     */
    public StringMessage(String message, Socket userSocket) {
        IDFactory idFactory = new IDFactory();
        this.stringMessageID = idFactory.buildID(this);
        this.message = message;
        this.userSocket = userSocket;
    }

    public void takeStringMessage() throws InterruptedException, ExecutionException {

        Callable<Message> stringParse = () -> {

            IDFactory idFactory = new IDFactory();

            ParseStringMessage parseStringMessage = new ParseStringMessage();
            StringMessage curMessage = stringMessages.take();
            idFactory.objectConnection(
                    parseStringMessage.getIDParseStringMessage(),
                    curMessage.getStringMessageID());
            return parseStringMessage.parseMessage(curMessage);
        };

        ArrayBlockingQueue<Future<StringMessage>> removableTask = new ArrayBlockingQueue<>(ABQ_CAPACITY);
        while (!readMessageTask.isEmpty()) {
            for (Future<StringMessage> taskReadMessage : readMessageTask) {
                if (taskReadMessage.isDone()) {
                    removableTask.put(taskReadMessage);

                    stringMessages.put(taskReadMessage.get());
                    taskReadMessage.cancel(true);
                }
            }

            while (!removableTask.isEmpty()) {
                Future<StringMessage> task = removableTask.take();
                readMessageTask.remove(task);
                task.cancel(true);

                Future<Message> taskParseMessage = threadPool.submit(stringParse);
                parseMessageTask.put(taskParseMessage);
            }
        }
    }

    public void buildSendMessage() {

    }
}