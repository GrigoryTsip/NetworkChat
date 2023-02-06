package user;

/**
 * �������� ������.
 *
 * @author gntsi
 * @version 1.0
 * @created 30-���-2023 16:19:27
 */
public class UserInTalk {

    /**
     * ������������� ���������.
     */
    private final long userID;
    /**
     * ������������� ������.
     */
    private final long idTalkShow;

    public UserInTalk(long userID, long idTalkShow) {
        this.userID = userID;
        this.idTalkShow = idTalkShow;
    }

    /**
     * ������������� ������.
     */
    public long getIDTalkShow() {
        return idTalkShow;
    }

    /**
     * ������������� ������.

    /**
     * ������������� ���������.
     */
    public long getUserID() {
        return userID;
    }

 }