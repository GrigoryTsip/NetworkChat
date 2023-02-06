package talkshow;


import server.IDFactory;
import user.User;

import java.lang.reflect.AnnotatedType;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Сеанс взаимодействия нескольких участников чата (беседа).
 *
 * @author gntsi
 * @version 1.0
 * @created 30-янв-2023 16:19:27
 */
public class TalkShow {

    /**
     * Уникальный идентификатор беседы (talkshow).
     */
    private long idTalkShow;
    /**
     * Дата и время начала беседы.
     */
    private Date dataStartOfTalk;
    /**
     * Дата и время окончания беседы.
     */
    private Date dataEndOfTalk;
    /**
     * Признак активности беседы:
     * true - активна
     * false - не активна.
     */
    private boolean isActive;

    /**
     * Конструктор, осуществляющий начальную инициацию счетчика
     * идентификаторов объектов (begin = true), и установку идентификатора
     * создаваемого объекта (begin = false).
     */
    public TalkShow(User user) {
        IDFactory idFactory = new IDFactory();
        this.idTalkShow = idFactory.buildID((Object) this);
    }

    /**
     * Признак активности беседы:
     */
    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean newVal) {
        this.isActive = newVal;
    }

    /**
     * Уникальный идентификатор беседы (talkshow).
     */
    public long getIDTalkShow() {
        return this.idTalkShow;
    }

    /**
     * Дата и время начала беседы.
     */
    public Date getDataStartOfTalk() {
        return this.dataStartOfTalk;
    }

    public void setDataStartOfTalk(Date newVal) {
        this.dataStartOfTalk = newVal;
    }

    /**
     * Дата и время окончания беседы.
     */
    public Date getDataEndOfTalk() {
        return this.dataEndOfTalk;
    }

    public void setDataEndOfTalk(Date newVal) {
        this.dataEndOfTalk = newVal;
    }

}