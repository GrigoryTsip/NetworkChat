package user;

import server.IDFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

/**
 * �������������� ������ ��������� ����, ��������������� ��� ����������.
 *
 * @author gntsi
 * @version 1.0
 * @updated 01-���-2023 16:36:16
 */

public class ActiveUser {

    /**
     * ���������� ������������� ������.
     */
    private final long activeUserID;
    /**
     * ������������� ��������� ����, � ������� ������ ActiveUser.
     */
    private long userID;
    /**
     * ��������� ������������ � ����:
     * true - ���� � ����;
     * false - ��� � ����.
     */
    private boolean userCondition;
    /**
     * ������ �� �������� ����� ��� ��������� ����. ����������� ��� �����������
     * ��������� � ����.
     */
    private PrintWriter userCanalOut;
    /**
     * ������ (�����), ������������ ������ �������������� �������
     * � ������� ��������� ����.
     */
    private Socket userSocket;

     /**
     * �����������.
     */
    public ActiveUser (User user) {
        IDFactory idFactory = new IDFactory();
        this.activeUserID = idFactory.buildID((Object) this);
        idFactory.objectConnection(this.activeUserID, userID);
    }

    /**
     * �������� ���� � �������� ��������� (true)?
     */
    public boolean isCondition() {
        return this.userCondition;
    }

    /**
     * ���������� ��������� �������:
     * true - �������;
     * false - ���������.
     */
    public void setCondition(boolean newVal) {
        this.userCondition = newVal;
    }

    /**
     * �������� ���������� ������������� �������.
     */
    public long getID() {
        return activeUserID;
    }


     /**
     * ������ (�����), ������������ ������ �������������� �������
      * � ������� ��������� ����.
     */
    public void setUserSocket(Socket newVal) {
        userSocket = newVal;
    }

    public Socket getUserSocket() { return userSocket; }

}
