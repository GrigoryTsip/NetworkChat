//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package talkshow;

import server.IDFactory;
import user.User;

public class UserInTalk {
    private long userInTalkID;
    private long userID;
    private long idTalkShow;

    public UserInTalk() {
        this.userInTalkID = (new IDFactory()).buildID(this);
    }

    public UserInTalk(User user, TalkShow talkShow) {
        new UserInTalk();
        IDFactory idFactory = new IDFactory();
        idFactory.objectConnection(user.getID(), this.userInTalkID);
        idFactory.objectConnection(this.userInTalkID, talkShow.getIDTalkShow());
        talkShow.setActive(true);
        this.userID = user.getID();
        this.idTalkShow = talkShow.getIDTalkShow();
        idFactory.objectConnection(user.getID(), talkShow.getIDTalkShow());
    }

    public long getIDTalkShow() {
        return this.idTalkShow;
    }

    public long getUserID() {
        return this.userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
}
