package stringmsg;

import server.IDFactory;
import talkshow.ExitCode;
import talkshow.Message;
import talkshow.MessageType;
import talkshow.TalkShow;
import user.ActiveUser;
import user.User;

import static talkshow.ExitCode.*;
import static talkshow.ExitCode.SUCCESS;
import static user.RWUserFile.userList;

/**
 * Класс для осуществления корректного присоединения (коннекта) участника чата к
 * беседе.
 *
 * @author gntsi
 * @version 1.0
 * @created 04-фев-2023 8:54:17
 */
public class ChatConnection extends ParseStringMessage {

    public ChatConnection() throws InterruptedException {
    }

    /**
     * Проверка корректности сообщения с запросом о коннекте.
     * В случае отсутствия ошибок, присоединение участника чата к беседе
     * (если она есть) или создание беседы.
     * На входе предварительно сформированный объект входящего сообщения
     * с заполненными полями:
     * dataOfMessage
     * idStringMessage
     * body.
     */
    public Message senderConnection(Message inMessage, StringMessage currentMessage) {

        ExitCode exitCode;

        if (checkSenderNick(senderNick)) {
            exitCode = WRONG_MESSAGE_FORMAT;
        } else {
            if (!userList.containsKey(senderNick)) {
                exitCode = USER_NOT_REGISTER;
            } else exitCode = SUCCESS;
        }
        User user;
        ActiveUser activeUser;
        IDFactory idFactory = new IDFactory();
        if (exitCode == SUCCESS) {
            user = userList.get(senderNick);
            activeUser = (ActiveUser) idFactory.getObject(user.getID());
            if (activeUser == null) activeUser = new ActiveUser(user);

            activeUser.setCondition(true);
            activeUser.setUserSocket(currentMessage.getUserSocket());

            TalkShow talk = new TalkShow(user);

        } else user = null;

        if (user != null) inMessage.setSenderID(user.getID());
        else inMessage.setSenderID(0);

        inMessage.setType(MessageType.CHAT_REGISTRATION);
        inMessage.setResult(exitCode);

        return inMessage;
    }

    /**
     *
     */
    public Message formResultConnectionMessage(Message inMessage) {
        return null;
    }

}