package talkshow;

import server.IDFactory;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicLong;

import static talkshow.MessageType.ERROR_MESSAGE;

/**
 * Класс, описывающий сообщение в чате.
 *
 * @author gntsi
 * @version 1.0
 * @created 30-янв-2023 16:19:26
 */
public class Message {

    /**
     * Уникальный идентификатор сообщения.
     */
    private final long idMessage;
    /**
     * Идентификатор беседы с точки зрения Сервера.
     */
    private long idTalkShow;

    /**
     * Ссылка на исходное полученое сообщение.
     */
    private long idStringMessage;

    /**
     * Тип сообщения, представленный enum MessageType.
     */
    private MessageType type;
    /**
     * Направление сообщения относительно обработчика (Сервера или Клиента):
     * true - входящее;
     * false - исходящее.
     */
    private boolean direction;
    /**
     * Дата и время отправки сообщения.
     */
    private Date dataOfMessage;
    /**
     * Идентификатор отправителя сообщения.
     */
    private long senderID;
    /**
     * Строка тела сообщения.
     */
    private String body;
    /**
     * Возвращаемый Сервером код завершения операции из enum ExitCode.
     */
    private ExitCode result;
    private String userNotInChat = null;
    private String usersNotActive = null;

    IDFactory idFactory = new IDFactory();

    /**
     * Счетчик значений идентификатора объектов. Содержит следующее свободное значение.
     */
    public Message() {
        this.idMessage = idFactory.buildID(this);

    }

    /**
     * Конструктор, создающий ответное сообщение указанного типа.
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
     * Уникальный идентификатор сообщения.
     */
    public long getIDMessage() {
        return idMessage;
    }

    /**
     * Тип сообщения, представленный enum MessageType.
     */
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType newVal) {
        type = newVal;
    }

    /**
     * Ссылка на исходное полученое сообщение.
     */
    public void setIDStringMessage(long newVal) {
        this.idStringMessage = newVal;
    }

    public long getIDStringMessage() {
        return this.idStringMessage;
    }

    /**
     * Дата и время отправки сообщения.
     */
    public Date getDataOfMessage() {
        return dataOfMessage;
    }

    public void setDataOfMessage(Date newVal) {
        dataOfMessage = newVal;
    }

    /**
     * Строка тела сообщения.
     */
    public String getBody() {
        return body;
    }

    public void setBody(String newVal) {
        body = newVal;
    }

    /**
     * Возвращаемый Сервером код завершения операции из enum ExitCode.
     */
    public ExitCode getResult() {
        return result;
    }

    public void setResult(ExitCode newVal) {
        result = newVal;
    }

    /**
     * Идентификатор отправителя сообщения.
     */
    public long getSenderID() {
        return senderID;
    }

    public void setSenderID(long newVal) {
        senderID = newVal;
    }

    /**
     * Направление сообщения относительно обработчика (Сервера или Клиента):
     * true - входящее;
     * false - исходящее.
     */
    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean newVal) {
        direction = newVal;
    }

    /**
     * Создаем сообщение об ошибке и в конструкторе формируем поля:
     * idMessage
     * type = ERROR_MESSAGE
     * direction = false
     * dataOfMessage = <current>
     * senderID  = <отправитель входящего сообщения>
     * body = <тело входящего сообщения>
     * UserNotInChat и UserNotActive переносятся из исходного сообщения.
     *
     * @param inMessage Входящее сообщение
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