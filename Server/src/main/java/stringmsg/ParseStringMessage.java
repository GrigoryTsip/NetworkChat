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
 * Класс, выполняющий операции разбора строки поступившего сообщения.
 *
 * @author gntsi
 * @version 1.0
 * @updated 02-фев-2023 10:37:31
 */
public class ParseStringMessage {

    private final long idParseStringMessage;
    /**
     * Поле даты сообщения в формате Date.
     */
    private Date messageData;
    /**
     * Идентифицированная беседа, полученная в сообщении.
     */
    private TalkShow talk;
    private User sender;

    /**
     * Адресаты сообщения - массив ников.
     */
    private ConcurrentHashMap<String, User> recipients;
    /**
     * Неопознанные или незарегистрированные получатели.
     */
    private String recipientsWrong;

    /**
     * Неактивные получатели.
     */
    private String recipientsNoArtive;

    String senderNick;
    String messageBody;
    private long talkID;
    protected String currentMessage;
    /**
     * Адресаты сообщения, которых нет в чате.
     */
    private String recipientsNoActive;

    public ParseStringMessage() throws InterruptedException {
        IDFactory idFactory = new IDFactory();
        idParseStringMessage = idFactory.buildID(this);
    }

    /**
     * Поле даты сообщения в формате Date.
     */
    public Date getMessageBloc() {
        return messageData;
    }

    /**
     * Идентифицированная беседа, полученная в сообщении.
     */
    public TalkShow getTalk() {
        return this.talk;
    }

    public User getSender() {
        return this.sender;
    }

    /**
     * Адресаты сообщения - массив ников.
     */
    public ConcurrentHashMap<String, User> getRecipients() {
        return this.recipients;
    }

    /**
     * Неопознанные или незарегистрированные получатели.
     */
    public String getRecipientsWrong() {
        return this.recipientsWrong;
    }

    /**
     * Адресаты сообщения, которых нет в чате.
     */
    public String getRecipientsNoActive() {
        return this.recipientsNoActive;
    }

    /**
     * Адресаты сообщения, которых нет в чате.
     */
    public long getIDParseStringMessage() {
        return this.idParseStringMessage;
    }

    /**
     * Разобрать строку сообщения и первично сформировать объект Message.
     */
    public Message parseMessage(StringMessage currentMessage) throws ParseException, InterruptedException {

        Settings set = new Settings();
        Message mesg = new Message();
        String[] recipArray;

        /*
        Подстроки сообщения - массив блоков.
        messageBloc[1] - дата в едином формате, заданном в Settings;
        messageBloc[2] - идентификатор беседы или код ошибки;
        messageBloc[3] - ник отправителя;
        messageBloc[4] - список ников получателей;
        messageBloc[5] - тело сообщения.
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
        Проверяем тип сообщения:
            Регистрация (CHAT_REGISTRATION) :
                ИД беседы - пустое;
                есть ник отправителя;
                список получателей - пустой;
                в теле сообщения - имя регистрируемого не более 30 символов.

              Коннект (CHAT_CONNECTION) :
                ИД беседы  - пустое;
                есть ник отправителя;
                список получателей - пустой;
                в теле сообщения - пусто.

              Сообщение о выходе из чата (EXIT_THE_CHAT):
                ИД беседы  - пустое;
                есть ник отправителя;
                список получателей - пустой;
                в теле сообщения "/exit".

              Обычное сообщение адресату (MESSAGE_TO_ADDRESSEE):
                в поле ИД беседы - целое неотрицательное число больше 1
                (зарезервирована как код успешного завершения);
                ник отправителя - начинается с @ и не более 12 символов;
                список ников получателей; требования к нику такие же, разделены
                "точкой с запятой";
                поле тела сообщения может быть пустым.
        */

        boolean ctrlMessage = (talkID == 0);
        MessageType type;
        if (ctrlMessage) {
            type = (messageBody.equals("")) ? CHAT_CONNECTION : CHAT_REGISTRATION;
            type = (messageBody.equals("/exit")) ? EXIT_THE_CHAT : type;
        } else {
            type = (talkID > 1) ? MESSAGE_TO_ADDRESSEE : ERROR_MESSAGE;
        }

        // Проверяем формат переданного сообщения
        ExitCode exitCode;
        switch (type) {
            case CHAT_REGISTRATION -> mesg = new ChatRegistration().senderRegistration(mesg);
            case CHAT_CONNECTION -> mesg = new ChatConnection().senderConnection(mesg, currentMessage);
            case EXIT_THE_CHAT -> mesg = new ExitChat().exitChat();
            case MESSAGE_TO_ADDRESSEE -> mesg = new MessageToAddressee().sendToAddressee(mesg);
            case ERROR_MESSAGE -> mesg = new ErrorMessage().sendErrorMessage(mesg);
            default -> System.out.println("Неверный тип сообщения");
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