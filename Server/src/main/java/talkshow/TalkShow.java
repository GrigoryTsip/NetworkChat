package talkshow;


import server.IDFactory;
import user.User;

import java.lang.reflect.AnnotatedType;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ����� �������������� ���������� ���������� ���� (������).
 *
 * @author gntsi
 * @version 1.0
 * @created 30-���-2023 16:19:27
 */
public class TalkShow {

    /**
     * ���������� ������������� ������ (talkshow).
     */
    private long idTalkShow;
    /**
     * ���� � ����� ������ ������.
     */
    private Date dataStartOfTalk;
    /**
     * ���� � ����� ��������� ������.
     */
    private Date dataEndOfTalk;
    /**
     * ������� ���������� ������:
     * true - �������
     * false - �� �������.
     */
    private boolean isActive;

    /**
     * �����������, �������������� ��������� ��������� ��������
     * ��������������� �������� (begin = true), � ��������� ��������������
     * ������������ ������� (begin = false).
     */
    public TalkShow(User user) {
        IDFactory idFactory = new IDFactory();
        this.idTalkShow = idFactory.buildID((Object) this);
    }

    /**
     * ������� ���������� ������:
     */
    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean newVal) {
        this.isActive = newVal;
    }

    /**
     * ���������� ������������� ������ (talkshow).
     */
    public long getIDTalkShow() {
        return this.idTalkShow;
    }

    /**
     * ���� � ����� ������ ������.
     */
    public Date getDataStartOfTalk() {
        return this.dataStartOfTalk;
    }

    public void setDataStartOfTalk(Date newVal) {
        this.dataStartOfTalk = newVal;
    }

    /**
     * ���� � ����� ��������� ������.
     */
    public Date getDataEndOfTalk() {
        return this.dataEndOfTalk;
    }

    public void setDataEndOfTalk(Date newVal) {
        this.dataEndOfTalk = newVal;
    }

}