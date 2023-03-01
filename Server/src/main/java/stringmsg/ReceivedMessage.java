//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package stringmsg;

import server.IDFactory;
import talkshow.*;
import user.RWUserFile;
import user.User;

import java.util.ArrayList;

public class ReceivedMessage extends ParseStringMessage {
    public ReceivedMessage(ParseStringMessage m) {
        this.senderNick = m.getSenderNick();
        this.messageBlocRecipients = m.getMessageBlocRecipients();
        this.messageBlocBody = m.getMessageBody();
        this.talkOwner = m.getTalkOwner();
        IDFactory idFactory = new IDFactory();
        StringMessage currentMessage = new StringMessage();
        ArrayList<Object> arr = idFactory.getRelatedObjects(StringMessage.class, m.getIDParseStringMessage());
        currentMessage = (StringMessage)arr.get(0);
    }

    public Message sendToAddressee(Message mesg) {
        MessageFactory msgFactory = new MessageFactory();
        msgFactory.setType(mesg, MessageType.RECEIVED_MESSAGE);
        ExitCode exitCode;
        if (this.checkSenderNick(this.senderNick)) {
            msgFactory.setSender(mesg, this.senderNick);
            msgFactory.setBody(mesg, this.messageBlocBody);
            User user = (User)RWUserFile.userList.get(this.senderNick.trim());
            if (user != null) {
                if (msgFactory.getUserActive(user) != null) {
                    TalkShow talk = msgFactory.getTalkShow(mesg, this.talkOwner);
                    if (talk != null) {
                        msgFactory.setTalk(mesg, talk.getIDTalkShow());
                        ArrayList<Long> usersID = msgFactory.getActiveRecipient(mesg, this.messageBlocRecipients);
                        if (!usersID.isEmpty()) {
                            msgFactory.connectUserToTalk(mesg, usersID);
                            if (this.getRecipientsWrong().isEmpty()) {
                                if (this.getRecipientsNoActive().isEmpty()) {
                                    exitCode = ExitCode.SUCCESS;
                                } else {
                                    exitCode = ExitCode.NOT_ACTIVE_USERS;
                                }
                            } else {
                                exitCode = ExitCode.USERS_NOT_IN_CHAT;
                            }
                        } else {
                            exitCode = ExitCode.WRONG_RECIPIENTS;
                        }
                    } else {
                        exitCode = ExitCode.FALSE_TALKSHOW_IDENTIFIER;
                    }
                } else {
                    exitCode = ExitCode.USER_NOT_ACTIVE;
                }
            } else {
                exitCode = ExitCode.USER_NOT_REGISTER;
            }
        } else {
            exitCode = ExitCode.WRONG_MESSAGE_FORMAT;
        }

        msgFactory.setResult(mesg, exitCode);
        if (exitCode != ExitCode.NOT_ACTIVE_USERS && exitCode != ExitCode.USERS_NOT_IN_CHAT) {
            msgFactory.setType(mesg, MessageType.ERROR_MESSAGE);
        }

        return mesg;
    }
}
