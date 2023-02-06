package user;

import server.IDFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Дополнительные данные участника чата, характеризующие его активность.
 *
 * @author gntsi
 * @version 1.0
 * @updated 01-фев-2023 16:36:16
 */

public class ActiveUser {

    /**
     * Уникальный идентификатор записи.
     */
    private final long activeUserID;
    /**
     * Идентификатор участника чата, с которым связан ActiveUser.
     */
    private long userID;
    /**
     * Состояние Пользователя в чате:
     * true - есть в чате;
     * false - нет в чате.
     */
    private boolean userCondition;
    /**
     * Ссылка на выходной канал для участника чата. Формируется при подключении
     * участника к чату.
     */
    private PrintWriter userCanalOut;
    /**
     * Объект (сокет), определяющий каналы взаимодействия Сервера
     * и каждого участника чата.
     */
    private Socket userSocket;

     /**
     * Конструктор.
     */
    public ActiveUser (User user) {
        IDFactory idFactory = new IDFactory();
        this.activeUserID = idFactory.buildID((Object) this);
        idFactory.objectConnection(this.activeUserID, userID);
    }

    /**
     * Участник чата в активном состоянии (true)?
     */
    public boolean isCondition() {
        return this.userCondition;
    }

    /**
     * Установить состояние объекта:
     * true - активен;
     * false - неактивен.
     */
    public void setCondition(boolean newVal) {
        this.userCondition = newVal;
    }

    /**
     * Получить уникальный идентификатор объекта.
     */
    public long getID() {
        return activeUserID;
    }


     /**
     * Объект (сокет), определяющий каналы взаимодействия Сервера
      * и каждого участника чата.
     */
    public void setUserSocket(Socket newVal) {
        userSocket = newVal;
    }

    public Socket getUserSocket() { return userSocket; }

}
