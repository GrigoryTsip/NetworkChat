//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package user;

import java.net.Socket;
import server.IDFactory;

import static user.RWUserFile.userList;

public class User {
    private final long userID;
    private String userNickname;
    private String userName;

    public User(Socket socket) {
        IDFactory idFactory = new IDFactory();
        this.userID = idFactory.buildID(this);
        ActiveUser activeUser = (ActiveUser)ActiveUser.activeUserList.get(socket);
        activeUser.setUserID(this.userID);
        idFactory.objectConnection(this.userID, activeUser.getID());
    }

    public User(String userNickname, String userName) {
        IDFactory idFactory = new IDFactory();
        this.userID = idFactory.buildID(this);
        this.userNickname = userNickname;
        this.userName = userName;
        userList.put(userNickname, this);
        ActiveUser activeUser = new ActiveUser();
        idFactory.objectConnection(this.userID, activeUser.getID());
    }

    public String getName() {
        return this.userName;
    }

    public void setName(String newVal) {
        this.userName = newVal;
    }

    public String getNickName() {
        return this.userNickname;
    }

    public void setNickName(String newVal) {
        this.userNickname = newVal;
    }

    public long getID() {
        return this.userID;
    }
}
