//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package talkshow;

import server.IDFactory;
import user.User;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ConcurrentHashMap;

public class TalkShow {
    private long idTalkShow;
    private Date dataStartOfTalk = new Date();
    private Date dataEndOfTalk = new Date();
    private boolean isActive;
    private String ownerNick;
    public static ConcurrentHashMap<String, TalkShow> talkShowList = new ConcurrentHashMap<>();

    public TalkShow() {
    }

    public TalkShow(User user) {
        IDFactory idFactory = new IDFactory();
        this.idTalkShow = idFactory.buildID(this);
        this.dataStartOfTalk = (new GregorianCalendar()).getTime();
        this.ownerNick = user.getNickName();
        talkShowList.put(this.ownerNick, this);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean newVal) {
        this.isActive = newVal;
    }

    public long getIDTalkShow() {
        return this.idTalkShow;
    }

    public Date getDataStartOfTalk() {
        return this.dataStartOfTalk;
    }

    public void setDataStartOfTalk(Date newVal) {
        this.dataStartOfTalk = newVal;
    }

    public Date getDataEndOfTalk() {
        return this.dataEndOfTalk;
    }

    public void setDataEndOfTalk(Date newVal) {
        this.dataEndOfTalk = newVal;
    }

    public String getOwnerNick() {
        return this.ownerNick;
    }

    public void setOwnerNick(String newVal) {
        this.ownerNick = newVal;
    }
}
