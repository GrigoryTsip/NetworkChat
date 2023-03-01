//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package stringmsg;

import server.IDFactory;
import server.Settings;
import talkshow.Message;
import talkshow.MessageFactory;
import talkshow.MessageType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class ParseStringMessage {
    private long idParseStringMessage;
    private Date messageData = new Date();
    private String[] recipients;
    private String recipientsWrong;
    private String recipientsNoActive;
    protected String senderNick;
    protected long talkID;
    protected String talkOwner;
    protected String messageBlocType;
    protected String messageBlocData;
    protected String messageBlocTalk;
    protected String messageBlocSender;
    protected String messageBlocRecipients;
    protected String messageBlocExit;
    protected String messageBlocBody;

    public ParseStringMessage() {
    }

    public ParseStringMessage(StringMessage strMsg) {
        IDFactory idFactory = new IDFactory();
        this.idParseStringMessage = idFactory.buildID(this);
        idFactory.objectConnection(strMsg.getStringMessageID(), this.idParseStringMessage);
    }

    public Date getMessageBloc() {
        return this.messageData;
    }

    public String[] getRecipients() {
        return this.recipients;
    }
    public String getTalkOwner() {
        return this.talkOwner;
    }

    public String getRecipientsWrong() {
        return this.recipientsWrong;
    }

    protected void setRecipientsWrong(String newVal) {
        this.recipientsWrong = newVal;
    }

    public String getRecipientsNoActive() {
        return this.recipientsNoActive;
    }

    protected void setRecipientsNoActive(String newVal) {
        this.recipientsNoActive = newVal;
    }

    public long getIDParseStringMessage() {
        return this.idParseStringMessage;
    }

    public Message parseMessage(StringMessage currentMessage) {

        Message mesg = new Message();
        /*
        ѕодстроки сообщени€ - массив блоков.
        messageBloc[1] - тип сообщени€;
        messageBloc[2] - дата;
        messageBloc[3] - ник владельца беседы;
        messageBloc[4] - ник отправител€;
        messageBloc[5] - список ников получателей;
        messageBloc[6] - тело сообщени€;
        messageBloc[7] - код завершени€.
         */
        mesStringParse(currentMessage.getMessage());

        MessageFactory msgFactory = new MessageFactory();
        msgFactory.setType(mesg, MessageType.valueOf(this.messageBlocType));

        try {
            this.messageData = Settings.formatter.parse(this.messageBlocData);
            msgFactory.setData(mesg, this.messageData);
        } catch (ParseException e) {
            System.out.println(e);
        }

        msgFactory.setStringMessage(mesg, currentMessage.getStringMessageID());
        msgFactory.setDirection(mesg, true);
        if (!this.messageBlocTalk.isEmpty()) {
            this.talkOwner = this.messageBlocTalk;
            //this.talkID = Long.parseLong(this.messageBlocTalk);
        } else this.talkOwner = null;
        this.senderNick = this.messageBlocSender;

        switch (mesg.getType()) {
            case CHAT_REGISTRATION -> mesg = (new ChatRegistration(this)).senderRegistration(mesg);
            case CHAT_CONNECTION -> mesg = (new ChatConnection(this)).senderConnection(mesg);
            case EXIT_THE_CHAT -> mesg = (new ExitTheChat()).exitTheChat(mesg);
            case RECEIVED_MESSAGE -> mesg = (new ReceivedMessage(this)).sendToAddressee(mesg);
            default -> System.out.println("Ќеверный тип сообщени€");
        }

        return mesg;
    }

    public void mesStringParse(String mes) {
        if (mes != null) {
            String[] messageBloc = mes.split((new Settings()).getMessageSeparator());

            int blocSize = messageBloc.length;
            int frstID = 1;
            this.messageBlocType = messageBloc[frstID++];
            this.messageBlocData = messageBloc[frstID++];
            this.messageBlocTalk = messageBloc[frstID++];
            this.messageBlocSender = messageBloc[frstID++];
            if (blocSize > frstID) this.messageBlocRecipients = messageBloc[frstID++];
            if (this.messageBlocRecipients != null)
                this.recipients = this.messageBlocRecipients.split(";");
            if (blocSize > frstID) this.messageBlocBody = messageBloc[frstID++];
            if (blocSize > frstID) this.messageBlocExit = messageBloc[frstID];
        }
    }

    public boolean checkSenderNick(String senderNick) {
        return senderNick.indexOf("@") == 0;
    }

    public String getMessageBody() {
        return this.messageBlocBody;
    }

    public String getSenderNick() {
        return this.senderNick;
    }

    public String getMessageBlocRecipients() {
        return this.messageBlocRecipients;
    }

    public void setMessageBlocRecipients(String newVal) {
        this.messageBlocRecipients = newVal;
    }

    public boolean checkSenderName(String senderName) {
        return !senderName.isEmpty();
    }

    public void checkRecipients(Message message) {
        MessageFactory msgFactory = new MessageFactory();
        ArrayList<Long> activeUserID = msgFactory.getActiveRecipient(message, this.messageBlocRecipients);
        msgFactory.connectUserToTalk(message, activeUserID);
    }
}
