package stringmsg;

import talkshow.ExitCode;
import talkshow.Message;
import talkshow.MessageType;
import user.User;

import static talkshow.ExitCode.*;
import static talkshow.ExitCode.SUCCESS;
import static user.RWUserFile.userList;

/**
 *  ласс дл€ осуществлени€ корректной регистрации участника чата.
 *
 * @author gntsi
 * @version 1.0
 * @created 03-фев-2023 19:26:21
 */
public class ChatRegistration extends ParseStringMessage {


    public ChatRegistration() throws InterruptedException {
    }

    /**
     * ѕроверка корректности сообщени€ с запросом о регистрации.
     * в случае отсутстви€ ошибок, регистраци€ участника чата.
     */
    public Message senderRegistration(Message mesg) {

        ExitCode exitCode;

        if (checkSenderNick(senderNick)) {
            exitCode = WRONG_MESSAGE_FORMAT;
        } else {
            if (userList.containsKey(senderNick)) {
                exitCode = NICK_ALREADY_USED;
            } else {
                if (checkSenderName(messageBody)) {
                    exitCode = MISSING_USER_NAME;
                } else exitCode = SUCCESS;
            }
        }
        User user;
        switch (exitCode) {
            case SUCCESS -> {
                user = new User();
                user.setNickName(senderNick);
                user.setName(messageBody);
                userList.put(senderNick, user);
            }
            case NICK_ALREADY_USED -> user = userList.get(senderNick);
            case MISSING_USER_NAME -> user = null;
            case WRONG_MESSAGE_FORMAT -> user = null;
            default -> user = null;
        }
        if (user != null) mesg.setSenderID(user.getID());
        else mesg.setSenderID(0);

        mesg.setType(MessageType.CHAT_REGISTRATION);
        mesg.setResult(exitCode);
        return mesg;
    }

    /**
     * ‘ормирование ответного сообщени€ как об ошибке в формате сообщени€, так и
     * успешной регистрации или коннекте.
     */
    public Message formResultRegistrationMessage() {

        return null;
    }

}