package membermessage;

import server.IDFactory;
import server.Settings;
import stringmsg.ParseStringMessage;

/**
 * Класс, отвечающий за разбор пришедшего сообщения и оценку его соответствия
 * правилам.
 *
 * @author gntsi
 * @version 1.0
 * @created 21-фев-2023 18:05:19
 */
public class ParseMemberMessage {

    private long parseMessageID;
    private String parseMessageType;
    private String parseMessageData;
    private String parseMessageTalk;
    private String parseMessageSender;
    private String parseMessageRecipient;
    private String parseMessageBody;
    private String parseMessageExitCode;

    // временная заглушка
    public ParseMemberMessage() {}

    /**
     * Конструктор ParseStringMessage. Объекты этого класса могут
     * создаваться в привязке к соответствующим объектам MemberMessage.
     */
    public ParseMemberMessage(MemberMessage memberMessage) {
        IDFactory idFactory = new IDFactory();
        this.parseMessageID = idFactory.buildID(this);
        idFactory.objectConnection(this.parseMessageID, memberMessage.getMemberMessageID());
    }

    public String getParseMessageExitCode() {
        return parseMessageExitCode;
    }

    /**
     *
     */
    public void setParseMessageExitCode(String newVal) {
        parseMessageExitCode = newVal.trim();
    }

    public long getParseMessageID() {
        return parseMessageID;
    }

    public String getParseMessageType() {
        return parseMessageType;
    }

    /**
     *
     */
    public void setParseMessageType(String newVal) {
        parseMessageType = newVal.trim();
    }

    public String getParseMessageData() {
        return parseMessageData;
    }

    /**
     *
     */
    public void setParseMessageData(String newVal) {
        parseMessageData = newVal.trim();
    }

    public String getParseMessageTalk() {
        return parseMessageTalk;
    }

    /**
     *
     */
    public void setParseMessageTalk(String newVal) {
        parseMessageTalk = newVal.trim();
    }

    public String getParseMessageSender() {
        return parseMessageSender;
    }

    /**
     *
     */
    public void setParseMessageSender(String newVal) {
        parseMessageSender = newVal.trim();
    }

    public String getParseMessageRecipient() {
        return parseMessageRecipient;
    }

    /**
     *
     */
    public void setParseMessageRecipient(String newVal) {
        parseMessageRecipient = newVal.trim();
    }

    public String getParseMessageBody() {
        return parseMessageBody;
    }

    /**
     *
     */
    public void setParseMessageBody(String newVal) {
        parseMessageBody = newVal.trim();
    }

    /**
     * Метод, выполняющий операции разбора строки поступившего сообщения.
     */


    public void parseMessage(MemberMessage memberMessage) {

        ParseStringMessage psm = new ParseStringMessage();
        mesStringParse(memberMessage.getMemberMessage());
        memberMessage.setTalkOwnerNick(this.parseMessageTalk);



    }

    public void mesStringParse(String mes) {
        if (mes != null) {
            String[] messageBloc = mes.split((new Settings()).getMessageSeparator());

            int blocSize = messageBloc.length;
            int frstID = 1;
            this.parseMessageType = messageBloc[frstID++];
            this.parseMessageData = messageBloc[frstID++];
            this.parseMessageTalk = messageBloc[frstID++];
            this.parseMessageSender = messageBloc[frstID++];
            if (blocSize > frstID) this.parseMessageRecipient = messageBloc[frstID++];
            if (blocSize > frstID) this.parseMessageBody = messageBloc[frstID++];
            if (blocSize > frstID) this.parseMessageExitCode = messageBloc[frstID];
        }
    }

}