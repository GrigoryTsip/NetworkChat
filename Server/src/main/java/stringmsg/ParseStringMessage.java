package stringmsg;

import log.ExitChat;
import log.SendMessage;
import server.IDFactory;
import server.Settings;
import talkshow.ExitCode;
import talkshow.Message;
import talkshow.MessageType;
import talkshow.TalkShow;
import user.User;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import static server.Server.stringMessages;
import static server.Settings.formatter;
import static talkshow.MessageType.*;

/**
 * �����, ����������� �������� ������� ������ ������������ ���������.
 *
 * @author gntsi
 * @version 1.0
 * @updated 02-���-2023 10:37:31
 */
public class ParseStringMessage {

    private final long idParseStringMessage;
    /**
     * ���� ���� ��������� � ������� Date.
     */
    private Date messageData;
    /**
     * ������������������ ������, ���������� � ���������.
     */
    private TalkShow talk;
    private User sender;

    /**
     * �������� ��������� - ������ �����.
     */
    private ConcurrentHashMap<String, User> recipients;
    /**
     * ������������ ��� �������������������� ����������.
     */
    private String recipientsWrong;

    /**
     * ���������� ����������.
     */
    private String recipientsNoArtive;

    String senderNick;
    String messageBody;
    private long talkID;
    protected String currentMessage;
    /**
     * �������� ���������, ������� ��� � ����.
     */
    private String recipientsNoActive;

    public ParseStringMessage() throws InterruptedException {
        IDFactory idFactory = new IDFactory();
        idParseStringMessage = idFactory.buildID(this);
    }

    /**
     * ���� ���� ��������� � ������� Date.
     */
    public Date getMessageBloc() {
        return messageData;
    }

    /**
     * ������������������ ������, ���������� � ���������.
     */
    public TalkShow getTalk() {
        return this.talk;
    }

    public User getSender() {
        return this.sender;
    }

    /**
     * �������� ��������� - ������ �����.
     */
    public ConcurrentHashMap<String, User> getRecipients() {
        return this.recipients;
    }

    /**
     * ������������ ��� �������������������� ����������.
     */
    public String getRecipientsWrong() {
        return this.recipientsWrong;
    }

    /**
     * �������� ���������, ������� ��� � ����.
     */
    public String getRecipientsNoActive() {
        return this.recipientsNoActive;
    }

    /**
     * �������� ���������, ������� ��� � ����.
     */
    public long getIDParseStringMessage() {
        return this.idParseStringMessage;
    }

    /**
     * ��������� ������ ��������� � �������� ������������ ������ Message.
     */
    public Message parseMessage(StringMessage currentMessage) throws ParseException, InterruptedException {

        Settings set = new Settings();
        Message mesg = new Message();
        String[] recipArray;

        /*
        ��������� ��������� - ������ ������.
        messageBloc[1] - ���� � ������ �������, �������� � Settings;
        messageBloc[2] - ������������� ������ ��� ��� ������;
        messageBloc[3] - ��� �����������;
        messageBloc[4] - ������ ����� �����������;
        messageBloc[5] - ���� ���������.
         */
        String[] messageBloc = currentMessage.getMessage().split(set.getMessageSeparator());
        try {
            messageData = formatter.parse(messageBloc[1]);
            mesg.setDataOfMessage(messageData);
        } catch (ParseException ignored) {
        }

        mesg.setIDStringMessage(currentMessage.getStringMessageID());
        mesg.setDirection(true);

        talkID = Long.parseLong(messageBloc[2]);
        senderNick = messageBloc[3];

        recipArray = messageBloc[4].split(";");
        messageBody = messageBloc[5];
        mesg.setBody(messageBody);

        /*
        ��������� ��� ���������:
            ����������� (CHAT_REGISTRATION) :
                �� ������ - ������;
                ���� ��� �����������;
                ������ ����������� - ������;
                � ���� ��������� - ��� ��������������� �� ����� 30 ��������.

              ������� (CHAT_CONNECTION) :
                �� ������  - ������;
                ���� ��� �����������;
                ������ ����������� - ������;
                � ���� ��������� - �����.

              ��������� � ������ �� ���� (EXIT_THE_CHAT):
                �� ������  - ������;
                ���� ��� �����������;
                ������ ����������� - ������;
                � ���� ��������� "/exit".

              ������� ��������� �������� (MESSAGE_TO_ADDRESSEE):
                � ���� �� ������ - ����� ��������������� ����� ������ 1
                (��������������� ��� ��� ��������� ����������);
                ��� ����������� - ���������� � @ � �� ����� 12 ��������;
                ������ ����� �����������; ���������� � ���� ����� ��, ���������
                "������ � �������";
                ���� ���� ��������� ����� ���� ������.
        */

        boolean ctrlMessage = (talkID == 0);
        MessageType type;
        if (ctrlMessage) {
            type = (messageBody.equals("")) ? CHAT_CONNECTION : CHAT_REGISTRATION;
            type = (messageBody.equals("/exit")) ? EXIT_THE_CHAT : type;
        } else {
            type = (talkID > 1) ? MESSAGE_TO_ADDRESSEE : ERROR_MESSAGE;
        }

        // ��������� ������ ����������� ���������
        ExitCode exitCode;
        switch (type) {
            case CHAT_REGISTRATION -> mesg = new ChatRegistration().senderRegistration(mesg);
            case CHAT_CONNECTION -> mesg = new ChatConnection().senderConnection(mesg, currentMessage);
            case EXIT_THE_CHAT -> mesg = new ExitChat().exitChat();
            case MESSAGE_TO_ADDRESSEE -> mesg = new MessageToAddressee().sendToAddressee(mesg);
            case ERROR_MESSAGE -> mesg = new ErrorMessage().sendErrorMessage(mesg);
            default -> System.out.println("�������� ��� ���������");
        }
        return mesg;
    }

    public boolean checkSenderNick(String senderNick) {
        return (senderNick.indexOf("@") != 0);
    }

    public boolean checkSenderName(String senderNick) {
        return (senderNick.isEmpty()) || (senderNick.indexOf("@") == 0) || (senderNick.length() > 30);
    }

    private boolean checkConnectionFormat(String senderNick) {
        return true;
    }

    private boolean checkExitTheChatFormat(String senderNick) {
        return true;
    }
}