package user;


import server.IDFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Данные Пользователя чата.
 *
 * @author gntsi
 * @version 1.0
 * @created 01-фев-2023 16:35:31
 */
public class User {

    /**
     * Уникальный идентификатор Пользователя.
     */
    private  long userID;
    /**
     * Уникальный ник Пользователя чата, начинающийся с @.
     */
    private String userNickname;
    /**
     * Имя Пользователя чата.
     */
    private String userName;

    /**
     * Конструктор, создающий объект в потоковом режиме.
     */
    public User() {
        IDFactory idFactory = new IDFactory();
        this.userID = idFactory.buildID((Object) this);
    }

    /**
     * Конструктор для формирования пользователя при чтении справочника пользователей
     * из файла при инициализации Сервера.
     */
    public User(long userID, String userNickname, String userName) {
        IDFactory idFactory = new IDFactory();
        this.userID = idFactory.buildID((Object) this);

        this.userNickname = userNickname;
        this.userName = userName;
    }

    /**
     * Получить имя участника чата.
     */
    public String getName() {
        return this.userName;
    }

    /**
     * Сохранить имя объекта.
     */
    public void setName(String newVal) {
        this.userName = newVal;
    }

    /**
     * Получить уникальный ник участника чата, начинающийся с @.
     */
    public String getNickName() {
        return userNickname;
    }

    /**
     * Сохранить ник участника чата.
     */
    public void setNickName(String newVal) {
        userNickname = newVal;
    }

    /**
     * Получить уникальный идентификатор объекта.
     */
    public long getID() {
        return userID;
    }

}


