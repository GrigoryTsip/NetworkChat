//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package stringmsg;

import server.IDFactory;
import talkshow.ExitCode;
import talkshow.Message;
import talkshow.MessageFactory;
import user.RWUserFile;
import user.User;

import java.net.Socket;
import java.util.ArrayList;

public class ChatRegistration extends ParseStringMessage {
    private final String senderName;
    private Socket socket;

    public ChatRegistration(ParseStringMessage m) {
        this.senderNick = m.getSenderNick();
        this.senderName = m.getMessageBody();
        IDFactory idFactory = new IDFactory();
        StringMessage currentMessage = new StringMessage();
        ArrayList<Object> arr = idFactory.getRelatedObjects(StringMessage.class, m.getIDParseStringMessage());
        currentMessage = (StringMessage)arr.get(0);
        this.socket = currentMessage.getUserSocket();
    }

    public Message senderRegistration(Message mesg) {
        new MessageFactory();
        ExitCode exitCode;
        if (!this.checkSenderNick(this.senderNick)) {
            exitCode = ExitCode.WRONG_MESSAGE_FORMAT;
        } else if (RWUserFile.userList.containsKey(this.senderNick)) {
            exitCode = ExitCode.NICK_ALREADY_USED;
        } else if (!this.checkSenderName(this.senderName)) {
            exitCode = ExitCode.MISSING_USER_NAME;
        } else {
            exitCode = ExitCode.SUCCESS;
        }

        if (exitCode == ExitCode.SUCCESS) {
            User user = new User(this.socket);
            user.setNickName(this.senderNick);
            user.setName(this.senderName);
            RWUserFile.userList.put(this.senderNick, user);
            mesg.setSenderID(user.getID());
        }

        MessageFactory msgFactory = new MessageFactory();
        msgFactory.setResult(mesg, exitCode);
        msgFactory.setBody(mesg, this.senderName);
        return mesg;
    }
}
