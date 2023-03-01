//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package talkshow;

import server.IDFactory;

import java.util.Date;

public class Message {
    private final long idMessage;
    private long idTalkShow;
    private long idStringMessage;
    private MessageType type;
    private boolean direction;
    private Date dataOfMessage = new Date();
    private long senderID = 0L;
    private long recipientID = 0L;
    private String body = null;
    private ExitCode result;
    private String userNotInChat = null;
    private String usersNotActive = null;
    IDFactory idFactory = new IDFactory();

    public Message() {
        this.idMessage = this.idFactory.buildID(this);
    }

    public Message(Message inMessage) {
        this.idMessage = this.idFactory.buildID(this);
        this.idStringMessage = inMessage.idStringMessage;
        this.idTalkShow = inMessage.idTalkShow;
        this.type = inMessage.getType();
        this.direction = false;
        this.dataOfMessage = inMessage.getDataOfMessage();
        this.senderID = inMessage.senderID;
        this.recipientID = inMessage.recipientID;
        this.body = inMessage.body;
        this.result = inMessage.getResult();
        this.userNotInChat = inMessage.userNotInChat;
        this.usersNotActive = inMessage.usersNotActive;
    }

    public long getIDMessage() {
        return this.idMessage;
    }

    public MessageType getType() {
        return this.type;
    }

    public void setType(MessageType newVal) {
        this.type = newVal;
    }

    public void setIDStringMessage(long newVal) {
        this.idStringMessage = newVal;
    }

    public long getIDStringMessage() {
        return this.idStringMessage;
    }

    public void setIDTalkShow(long newVal) {
        this.idTalkShow = newVal;
    }

    public long getIDTalkShow() {
        return this.idTalkShow;
    }

    public Date getDataOfMessage() {
        return this.dataOfMessage;
    }

    public void setDataOfMessage(Date newVal) {
        this.dataOfMessage = newVal;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String newVal) {
        this.body = newVal;
    }

    public ExitCode getResult() {
        return this.result;
    }

    public void setResult(ExitCode newVal) {
        this.result = newVal;
    }

    public long getSenderID() {
        return this.senderID;
    }

    public void setSenderID(long newVal) {
        this.senderID = newVal;
    }

    public long getRecipientID() {
        return this.recipientID;
    }

    public void setRecipientID(long newVal) {
        this.recipientID = newVal;
    }

    public boolean isDirection() {
        return this.direction;
    }

    public void setDirection(boolean newVal) {
        this.direction = newVal;
    }

    public Message createErrorMessage(Message inMessage) {
        Message mesg = new Message(inMessage);
        mesg.setType(MessageType.ERROR_MESSAGE);
        return mesg;
    }

    public String getUserNotInChat() {
        return this.userNotInChat;
    }

    public void setUserNotInChat(String newVal) {
        this.userNotInChat = newVal;
    }

    public String getUsersNotActive() {
        return this.usersNotActive;
    }

    public void setUsersNotActive(String newVal) {
        this.usersNotActive = newVal;
    }
}
