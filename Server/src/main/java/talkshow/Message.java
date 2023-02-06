package talkshow;

import server.IDFactory;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicLong;

import static talkshow.MessageType.ERROR_MESSAGE;

/**
 * �����, ����������� ��������� � ����.
 *
 * @author gntsi
 * @version 1.0
 * @created 30-���-2023 16:19:26
 */
public class Message {

    /**
     * ���������� ������������� ���������.
     */
    private final long idMessage;
    /**
     * ������������� ������ � ����� ������ �������.
     */
    private long idTalkShow;

    /**
     * ������ �� �������� ��������� ���������.
     */
    private long idStringMessage;

    /**
     * ��� ���������, �������������� enum MessageType.
     */
    private MessageType type;
    /**
     * ����������� ��������� ������������ ����������� (������� ��� �������):
     * true - ��������;
     * false - ���������.
     */
    private boolean direction;
    /**
     * ���� � ����� �������� ���������.
     */
    private Date dataOfMessage;
    /**
     * ������������� ����������� ���������.
     */
    private long senderID;
    /**
     * ������ ���� ���������.
     */
    private String body;
    /**
     * ������������ �������� ��� ���������� �������� �� enum ExitCode.
     */
    private ExitCode result;
    private String userNotInChat = null;
    private String usersNotActive = null;

    IDFactory idFactory = new IDFactory();

    /**
     * ������� �������� �������������� ��������. �������� ��������� ��������� ��������.
     */
    public Message() {
        this.idMessage = idFactory.buildID(this);

    }

    /**
     * �����������, ��������� �������� ��������� ���������� ����.
     */
    public Message(Message inMessage, MessageType type) {
        this.idMessage = idFactory.buildID( this);

        this.idStringMessage = inMessage.idStringMessage;
        this.idTalkShow = inMessage.idTalkShow;

        this.type = type;
        this.direction = false;
        this.dataOfMessage = new GregorianCalendar().getTime();
        this.senderID = inMessage.senderID;
        this.body = inMessage.body;
        this.userNotInChat = inMessage.userNotInChat;
        this.usersNotActive = inMessage.usersNotActive;
    }

    /**
     * ���������� ������������� ���������.
     */
    public long getIDMessage() {
        return idMessage;
    }

    /**
     * ��� ���������, �������������� enum MessageType.
     */
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType newVal) {
        type = newVal;
    }

    /**
     * ������ �� �������� ��������� ���������.
     */
    public void setIDStringMessage(long newVal) {
        this.idStringMessage = newVal;
    }

    public long getIDStringMessage() {
        return this.idStringMessage;
    }

    /**
     * ���� � ����� �������� ���������.
     */
    public Date getDataOfMessage() {
        return dataOfMessage;
    }

    public void setDataOfMessage(Date newVal) {
        dataOfMessage = newVal;
    }

    /**
     * ������ ���� ���������.
     */
    public String getBody() {
        return body;
    }

    public void setBody(String newVal) {
        body = newVal;
    }

    /**
     * ������������ �������� ��� ���������� �������� �� enum ExitCode.
     */
    public ExitCode getResult() {
        return result;
    }

    public void setResult(ExitCode newVal) {
        result = newVal;
    }

    /**
     * ������������� ����������� ���������.
     */
    public long getSenderID() {
        return senderID;
    }

    public void setSenderID(long newVal) {
        senderID = newVal;
    }

    /**
     * ����������� ��������� ������������ ����������� (������� ��� �������):
     * true - ��������;
     * false - ���������.
     */
    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean newVal) {
        direction = newVal;
    }

    /**
     * ������� ��������� �� ������ � � ������������ ��������� ����:
     * idMessage
     * type = ERROR_MESSAGE
     * direction = false
     * dataOfMessage = <current>
     * senderID  = <����������� ��������� ���������>
     * body = <���� ��������� ���������>
     * UserNotInChat � UserNotActive ����������� �� ��������� ���������.
     *
     * @param inMessage �������� ���������
     */
    public Message createErrorMessage(Message inMessage) {
        return new Message(inMessage, ERROR_MESSAGE);

    }

    public String getUserNotInChat() {
        return userNotInChat;
    }

    public void setUserNotInChat(String newVal) {
        userNotInChat = newVal;
    }

    public String getUsersNotActive() {
        return usersNotActive;
    }

    public void setUsersNotActive(String newVal) {
        usersNotActive = newVal;
    }

}