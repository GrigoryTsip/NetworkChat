package user;


import server.IDFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ������ ������������ ����.
 *
 * @author gntsi
 * @version 1.0
 * @created 01-���-2023 16:35:31
 */
public class User {

    /**
     * ���������� ������������� ������������.
     */
    private  long userID;
    /**
     * ���������� ��� ������������ ����, ������������ � @.
     */
    private String userNickname;
    /**
     * ��� ������������ ����.
     */
    private String userName;

    /**
     * �����������, ��������� ������ � ��������� ������.
     */
    public User() {
        IDFactory idFactory = new IDFactory();
        this.userID = idFactory.buildID((Object) this);
    }

    /**
     * ����������� ��� ������������ ������������ ��� ������ ����������� �������������
     * �� ����� ��� ������������� �������.
     */
    public User(long userID, String userNickname, String userName) {
        IDFactory idFactory = new IDFactory();
        this.userID = idFactory.buildID((Object) this);

        this.userNickname = userNickname;
        this.userName = userName;
    }

    /**
     * �������� ��� ��������� ����.
     */
    public String getName() {
        return this.userName;
    }

    /**
     * ��������� ��� �������.
     */
    public void setName(String newVal) {
        this.userName = newVal;
    }

    /**
     * �������� ���������� ��� ��������� ����, ������������ � @.
     */
    public String getNickName() {
        return userNickname;
    }

    /**
     * ��������� ��� ��������� ����.
     */
    public void setNickName(String newVal) {
        userNickname = newVal;
    }

    /**
     * �������� ���������� ������������� �������.
     */
    public long getID() {
        return userID;
    }

}


