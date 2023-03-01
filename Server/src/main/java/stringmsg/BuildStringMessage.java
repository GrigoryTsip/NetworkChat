//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package stringmsg;

import server.IDFactory;
import server.Server;
import server.Settings;
import server.ThreadService;
import talkshow.Message;
import talkshow.MessageFactory;
import talkshow.MessageType;
import talkshow.TalkShow;
import user.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static talkshow.ExitCode.*;

public class BuildStringMessage {
    private String buildMessage;
    private Socket recipientSocket;
    private long idBuildStringMessage;

    public BuildStringMessage() {
        //IDFactory idFactory = new IDFactory();
        this.idBuildStringMessage = (new IDFactory()).buildID(this);
    }

    public BuildStringMessage(Message mes) {
        new BuildStringMessage();
        IDFactory idFactory = new IDFactory();
        idFactory.objectConnection(this.getIdBuildStringMessage(), mes.getIDMessage());
    }

    public long getIdBuildStringMessage() {
        return this.idBuildStringMessage;
    }

    public Socket getRecipientSocket() {
        return this.recipientSocket;
    }

    public void setRecipientSocket(Socket newVal) {
        this.recipientSocket = newVal;
    }

    public String getBuildMessage() {
        return this.buildMessage;
    }

    public void setBuildMessage(String newVal) {
        this.buildMessage = newVal;
    }

    public void sendMessageList() throws InterruptedException, ExecutionException {
        Callable<ArrayList<Message>> outMsg = () -> {
            Message mes = ThreadService.prepareMessages.take();
            ArrayList<Message> sendMsg = this.prepareSendMessage(mes);
            Server server = new Server();
            Iterator<Message> messageIterator = sendMsg.iterator();

            while (messageIterator.hasNext()) {
                Message m = messageIterator.next();
                server.sendChatMessage(this.messageBuilder(m));
            }
            return sendMsg;
        };

        ThreadService threadSevice = new ThreadService();
        Future<ArrayList<Message>> task = ThreadService.threadPool.submit(outMsg);
        ThreadService.sendMessageTask.put(task);
        threadSevice.controlSendLogTask();
    }

    public ArrayList<Message> prepareSendMessage(Message mes) {
        IDFactory idFactory = new IDFactory();
        MessageFactory msgFactory = new MessageFactory();
        ArrayList<Message> sendMsg = new ArrayList();

        Message msg = new Message(mes);

        ArrayList<Long> recipients = msgFactory.getActiveRecipient(msg, mes.getUserNotInChat());

        MessageType type = mes.getType();
        if (type == MessageType.RECEIVED_MESSAGE) {
            if (!recipients.isEmpty()) {
                for (long id : recipients) {
                    Message msgClone = new Message(mes);

                    msgClone.setType(MessageType.SEND_MESSAGE);
                    msgClone.setRecipientID(id);
                    msgClone.setDirection(false);
                    msgClone.setResult(SUCCESS);
                    sendMsg.add(msgClone);
                }
            }
            Message mesClone;
            if (msg.getUsersNotActive() != null) {
                mesClone = new Message(msg);
                mesClone.setRecipientID(msg.getSenderID());
                mesClone.setBody(msg.getUsersNotActive());
                mesClone.setResult(NOT_ACTIVE_USERS);
                mesClone.setType(MessageType.ERROR_MESSAGE);
                sendMsg.add(mesClone);
            }

            if (msg.getUserNotInChat() != null) {
                mesClone = new Message(msg);
                mesClone.setRecipientID(msg.getSenderID());
                mesClone.setBody(msg.getUserNotInChat());
                mesClone.setResult(USERS_NOT_IN_CHAT);
                mesClone.setType(MessageType.ERROR_MESSAGE);
                sendMsg.add(mesClone);
            }
        } else {
            msg.setRecipientID(msg.getSenderID());
            msg.setResult(SUCCESS);
            sendMsg.add(msg);
        }

        return sendMsg;
    }

    public BuildStringMessage messageBuilder(Message mesg) {
        IDFactory idFactory = new IDFactory();
        String separator = (new Settings()).getMessageSeparator();
        StringBuilder msgSend = new StringBuilder(separator);

        msgSend.append(mesg.getType()).append(separator)
                .append(Settings.formatter.format(mesg.getDataOfMessage())).append(separator);

        if (mesg.getIDTalkShow() == 0L) {
            msgSend.append(separator);
        } else {
            TalkShow talk = (TalkShow) idFactory.getObject(mesg.getIDTalkShow());
            msgSend.append(talk.getOwnerNick()).append(separator);
        }

        User sender = (User) idFactory.getObject(mesg.getSenderID());
        User recipient = (User) idFactory.getObject(mesg.getRecipientID());
        msgSend.append(sender.getNickName()).append(separator)
                .append(recipient.getNickName()).append(separator)
                .append(mesg.getBody()).append(separator)
                .append(mesg.getResult()).append(separator);

        BuildStringMessage bsm = new BuildStringMessage(mesg);
        StringMessage stringMessage = (StringMessage) idFactory.getObject(mesg.getIDStringMessage());

        bsm.setBuildMessage(msgSend.toString());
        bsm.setRecipientSocket(stringMessage.getUserSocket());

        return bsm;
    }
}
