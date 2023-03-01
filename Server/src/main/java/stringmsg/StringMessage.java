package stringmsg;

import server.IDFactory;
import server.ThreadService;
import talkshow.Message;

import java.net.Socket;
import java.util.concurrent.*;

import static server.ThreadService.*;

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
     * @param message    ���������� ��������� � �������������� ����
     * @param userSocket ������ �� ����� ��������� ����
     */
    public StringMessage(String message, Socket userSocket) {
        IDFactory idFactory = new IDFactory();
        this.stringMessageID = idFactory.buildID(this);
        this.message = message;
        this.userSocket = userSocket;
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


    public void takeStringMessage() throws InterruptedException, ExecutionException {

        Callable<Message> stringParse = () -> {

            StringMessage curMessage = stringMessages.take();
            ParseStringMessage parseStringMessage =
                    new ParseStringMessage(curMessage);
            return parseStringMessage.parseMessage(curMessage);
        };

        ThreadService threadSevice = new ThreadService();

        Future<Message> taskParseMessage = threadPool.submit(stringParse);
        parseMessageTask.put(taskParseMessage);
        threadSevice.controlPrepareTask();
    }
}