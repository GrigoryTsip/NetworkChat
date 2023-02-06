package user;

/**
 * Участник беседы.
 *
 * @author gntsi
 * @version 1.0
 * @created 30-янв-2023 16:19:27
 */
public class UserInTalk {

    /**
     * Идентификатор участника.
     */
    private final long userID;
    /**
     * Идентификатор беседы.
     */
    private final long idTalkShow;

    public UserInTalk(long userID, long idTalkShow) {
        this.userID = userID;
        this.idTalkShow = idTalkShow;
    }

    /**
     * Идентификатор беседы.
     */
    public long getIDTalkShow() {
        return idTalkShow;
    }

    /**
     * Идентификатор беседы.

    /**
     * Идентификатор участника.
     */
    public long getUserID() {
        return userID;
    }

 }