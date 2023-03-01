//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package user;

import server.IDFactory;
import server.ThreadService;
import stringmsg.StringMessage;
import talkshow.ExitCode;
import talkshow.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ActiveUser {
    private final long activeUserID;
    private long userID;
    private boolean userCondition = false;
    private Socket userSocket = null;
    public static ConcurrentHashMap<Socket, ActiveUser> activeUserList = new ConcurrentHashMap<>();

    public ActiveUser() {
        IDFactory idFactory = new IDFactory();
        this.activeUserID = idFactory.buildID(this);
    }

    public ActiveUser(Socket socket) {
        IDFactory idFactory = new IDFactory();
        this.activeUserID = idFactory.buildID(this);
        this.userSocket = socket;
        activeUserList.put(socket, this);
    }

    public ActiveUser(User user) {
        IDFactory idFactory = new IDFactory();
        this.activeUserID = idFactory.buildID(this);
        this.userID = user.getID();
        idFactory.objectConnection(this.activeUserID, user.getID());
    }

    public void runUserThreadIn() throws InterruptedException, IOException {
        Callable<MessageType> userThread = () -> {
            StringMessage clientMessage = new StringMessage();

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(this.userSocket.getInputStream()));

                try {
                    this.setCondition(true);
                    clientMessage.setUserSocket(this.userSocket);

                    while (this.isCondition()) {
                        clientMessage.setMessage(in.readLine());
                        ThreadService.stringMessages.put(clientMessage);
                        clientMessage.takeStringMessage();
                    }
                } catch (Throwable e) {
                    try {
                        in.close();
                    } catch (Throwable ex) {
                        e.addSuppressed(ex);
                    }
                    throw e;
                }
                in.close();
            } catch (IOException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            this.userSocket.close();
            return MessageType.EXIT_THE_CHAT;
        };

        ThreadService threadSevice = new ThreadService();
        Future<MessageType> task = ThreadService.threadPool.submit(userThread);
        ThreadService.readMessageTask.put(task);
        threadSevice.controlReadTask();
    }



        public void runUserThreadOut (String mesg) throws InterruptedException {
            Callable<ExitCode> userThread = () -> {
                ExitCode exitCode;
                try {
                    PrintWriter out = new PrintWriter(this.userSocket.getOutputStream(), true);

                    try {
                        out.println(mesg);
                        exitCode = ExitCode.SUCCESS;
                    } catch (Throwable e) {
                        try {
                            out.close();
                        } catch (Throwable ex) {
                            e.addSuppressed(ex);
                        }
                        throw e;
                    }

                    out.close();
                } catch (IOException var8) {
                    this.userSocket.close();
                    exitCode = ExitCode.SERVER_FAULT;
                }

                return exitCode;
            };
            Future<ExitCode> task = ThreadService.threadPool.submit(userThread);
            ThreadService.outMessageTask.put(task);
        }

        public boolean isCondition () {
            return this.userCondition;
        }

        public void setCondition ( boolean newVal){
            this.userCondition = newVal;
        }

        public long getID () {
            return this.activeUserID;
        }

        public void setUserID ( long newVal){
            this.userID = newVal;
        }

        public long getUserID () {
            return this.userID;
        }

        public void setUserSocket (Socket newVal){
            this.userSocket = newVal;
        }

        public Socket getUserSocket () {
            return this.userSocket;
        }
    }
