//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package talkshow;

import server.IDFactory;
import stringmsg.ParseStringMessage;
import user.ActiveUser;
import user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static user.RWUserFile.userList;

public class MessageFactory {
    public MessageFactory() {
    }

    public void setTalk(Message mesg, long talkID) {
        if (talkID != 0L) {
            mesg.setIDTalkShow(talkID);
            (new IDFactory()).objectConnection(mesg.getIDMessage(), talkID);
        }

    }

    public void setStringMessage(Message mesg, long strMesgID) {
        mesg.setIDStringMessage(strMesgID);
        (new IDFactory()).objectConnection(mesg.getIDMessage(), strMesgID);
    }

    public void setType(Message mesg, MessageType type) {
        mesg.setType(type);
    }

    public void setDirection(Message mesg, boolean direction) {
        mesg.setDirection(direction);
    }

    public void setData(Message mesg, Date data) {
        mesg.setDataOfMessage(data);
    }

    public void setSender(Message mesg, String senderNick) {
        User user = userList.get(senderNick);
        long id = user.getID();
        mesg.setSenderID(id);
    }

    public String getSenderNick(long senderID) {
        User user = (User) IDFactory.objectList.get(senderID);
        return user == null ? null : user.getNickName();
    }

    public void setBody(Message mesg, String body) {
        mesg.setBody(body);
    }

    public void setResult(Message mesg, ExitCode result) {
        mesg.setResult(result);
    }

    public ArrayList<Long> getActiveRecipient(Message mesg, String recipients) {
        if (recipients == null) return null;

        StringBuilder wrongNick = new StringBuilder();
        StringBuilder noActive = new StringBuilder();
        StringBuilder activeUser = new StringBuilder();
        ArrayList<Long> activeUserIDs = new ArrayList<>();
        String[] nicks = recipients.split(";");

        for (String nik : nicks) {
            User user = userList.get(nik.trim());
            if (user == null) {
                wrongNick.append(nik.trim()).append(";");
            } else if (this.getUserActive(user) == null) {
                noActive.append(nik.trim()).append(";");
            } else {
                activeUser.append(nik.trim()).append(";");
                activeUserIDs.add(user.getID());
            }
        }

        int idw = wrongNick.length() - 1;
        int idn = noActive.length() - 1;
        int ida = activeUser.length() - 1;
        if (idw > 0) mesg.setUserNotInChat(wrongNick.deleteCharAt(idw).toString());
        if (idn > 0) mesg.setUsersNotActive(noActive.deleteCharAt(idn).toString());
        ParseStringMessage parsStr = getParseStringMessage(mesg.getIDStringMessage());
        if (ida > 0) parsStr.setMessageBlocRecipients(activeUser.deleteCharAt(ida).toString());

        return activeUserIDs;
    }

    public String getActiveNick(Message mesg) {
        ParseStringMessage pars = getParseStringMessage(mesg.getIDMessage());
        if (pars == null) return null;
        String[] activeNick = pars.getMessageBlocRecipients().split(";");
        ArrayList<String> activeList = new ArrayList<>();
        for (String s : activeNick) activeList.add(s.trim());

        if (mesg.getUserNotInChat() != null) {
            String[] notUser = mesg.getUserNotInChat().split(";");
            for (String s : notUser) activeList.remove(s.trim());
        }
        if (mesg.getUsersNotActive() != null) {
            String[] notActive = mesg.getUsersNotActive().split(";");
            for (String s : notActive) activeList.remove(s.trim());
        }
        StringBuilder sb = new StringBuilder();
        for (String s : activeList) {
            sb.append(s).append(";");
        }
        return sb.toString();
    }

    public void connectUserToTalk(Message mesg, ArrayList<Long> recipients) {
        TalkShow talk;
        IDFactory idFactory = new IDFactory();
        ArrayList<Object> objects = idFactory.getRelatedObjects(TalkShow.class, mesg.getIDTalkShow());
        talk = (TalkShow) objects.get(0);
        UserInTalk userInTalk = new UserInTalk();
        objects = idFactory.getRelatedObjects(UserInTalk.class, talk.getIDTalkShow());
        Iterator var7 = recipients.iterator();

        while (var7.hasNext()) {
            long userID = (Long) var7.next();
            boolean inTalk = false;
            Iterator var11 = objects.iterator();

            while (var11.hasNext()) {
                Object o = var11.next();
                UserInTalk u = (UserInTalk) o;
                if (userID == u.getUserID()) {
                    inTalk = true;
                    break;
                }
            }

            if (!inTalk) {
                User user = (User) idFactory.getObject(userID);
                new UserInTalk(user, talk);
            }
        }

    }

    public ActiveUser getUserActive(User user) {
        IDFactory idFactory = new IDFactory();
        ArrayList<Object> list = idFactory.getRelatedObjects(ActiveUser.class, user.getID());
        for (Object o : list) {
            ActiveUser activeUser = (ActiveUser) o;
            if (activeUser.isCondition()) return activeUser;
        }
        return null;
    }

    public TalkShow getTalkShow(Message mesg, String talkOwner) {
        if (TalkShow.talkShowList.containsKey(talkOwner)) {
            TalkShow talk = TalkShow.talkShowList.get(talkOwner);
            if (mesg.getIDTalkShow() == 0L) {
                mesg.setIDTalkShow(talk.getIDTalkShow());
                return talk;
            } else {
                if (mesg.getIDTalkShow() == talk.getIDTalkShow()) return talk;
            }
        }
        return null;
    }

    public ParseStringMessage getParseStringMessage(long id) {
        ArrayList<Object> list =
                (new IDFactory()).getRelatedObjects(ParseStringMessage.class, id);
        return (list != null) ? (ParseStringMessage) list.get(0) : null;
    }
}
