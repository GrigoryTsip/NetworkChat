//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package stringmsg;

import server.IDFactory;
import talkshow.*;
import user.ActiveUser;
import user.RWUserFile;
import user.User;

import java.util.ArrayList;

public class ChatConnection extends ParseStringMessage {
    private StringMessage currentMessage = new StringMessage();

    public ChatConnection(ParseStringMessage m) {
        this.senderNick = m.getSenderNick();
        IDFactory idFactory = new IDFactory();
        ArrayList<Object> arr = idFactory.getRelatedObjects(StringMessage.class, m.getIDParseStringMessage());
        this.currentMessage = (StringMessage)arr.get(0);
    }

    public Message senderConnection(Message inMessage) {
        ExitCode exitCode;
        if (!this.checkSenderNick(this.senderNick)) {
            exitCode = ExitCode.WRONG_MESSAGE_FORMAT;
        } else if (!RWUserFile.userList.containsKey(this.senderNick)) {
            exitCode = ExitCode.USER_NOT_REGISTER;
        } else {
            exitCode = ExitCode.SUCCESS;
        }

        IDFactory idFactory = new IDFactory();
        User user;
        if (exitCode == ExitCode.SUCCESS) {
            user = (User)RWUserFile.userList.get(this.senderNick);
            ActiveUser activeUser = (ActiveUser)ActiveUser.activeUserList.get(this.currentMessage.getUserSocket());
            if (activeUser != null) {
                activeUser.setUserID(user.getID());
                idFactory.objectConnection(activeUser.getID(), user.getID());
                activeUser.setCondition(true);
                activeUser.setUserSocket(this.currentMessage.getUserSocket());
                TalkShow talk = new TalkShow(user);
                new UserInTalk(user, talk);
            } else {
                exitCode = ExitCode.SERVER_FAULT;
                System.out.println(ExitCode.toString(exitCode));
            }
        } else {
            user = null;
        }

        if (user != null) {
            inMessage.setSenderID(user.getID());
        } else {
            inMessage.setSenderID(0L);
        }

        inMessage.setType(MessageType.CHAT_CONNECTION);
        inMessage.setResult(exitCode);
        return inMessage;
    }
}
