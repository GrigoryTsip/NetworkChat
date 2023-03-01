package client;

import membermessage.BuildMemberMessage;
import membermessage.MemberMessage;
import membermessage.ParseMemberMessage;
import server.Settings;
import showmessage.InputAnswer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.GregorianCalendar;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static client.ClientThreadService.*;
import static showmessage.ShowMessage.talkShowList;
import static talkshow.MessageType.CHAT_CONNECTION;
import static talkshow.MessageType.CHAT_REGISTRATION;


/**
 * @author gntsi
 * @version 1.0
 * @created 21-пїЅпїЅпїЅ-2023 18:08:13
 */

public class Client {

    /**
     * Имя Клиента - имя хоста.
     */
    public static final String hostName = "@Client1";
    /**
     * Номер порта.
     */
    public static final int port = 8089;
    /**
     * Признак инициации Клиента.
     * Устанавливается true, когда участник зарегистрирован в чате и произвел коннект.
     */
    public static boolean clientInit = false;
    /**
     * Сокет Клиента.
     */
    protected static Socket clientSocket;
    protected static PrintWriter socketOut = null;
    protected static BufferedReader socketIn = null;

    public static InputAnswer memberAnswer;


    public Client() {

        try {
            clientSocket = new Socket("localhost", port);
            try {
                socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
                socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while (true) {
                if (!clientInit) {
                    setRegistration();
                    setConnection();
                    clientInit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Зарегистрироваться в чате.
     */
    public void setRegistration() throws InterruptedException, IOException {
        ParseMemberMessage register = new ParseMemberMessage();
        register.setParseMessageType(String.valueOf(CHAT_REGISTRATION));
        register.setParseMessageData(Settings.formatter.format(new GregorianCalendar().getTime()));
        register.setParseMessageSender(hostName);
        register.setParseMessageBody("Grigory");

        BuildMemberMessage bld = new BuildMemberMessage();
        String mm = bld.prepareSendMessage(register);
        outMsg(mm + "\n");

        String ss = inpMsg();
        register.mesStringParse(ss);
        String result = register.getParseMessageExitCode();
        System.out.println("Регистрация в чате: " + result);
    }

    /**
     * Законнектиться в чате.
     */
    public void setConnection() throws InterruptedException, IOException {
        ParseMemberMessage connect = new ParseMemberMessage();
        connect.setParseMessageType(String.valueOf(CHAT_CONNECTION));
        connect.setParseMessageData(Settings.formatter.format(new GregorianCalendar().getTime()));
        connect.setParseMessageSender(hostName);

        BuildMemberMessage bld = new BuildMemberMessage();
        outMsg(bld.prepareSendMessage(connect));

        connect.mesStringParse(inpMsg());
        String result = connect.getParseMessageExitCode();
        System.out.println("Коннект в чате: " + result);

        memberAnswer = new InputAnswer();
        memberAnswer.setActiveTalk(connect.getParseMessageTalk());
        talkShowList.put(memberAnswer.getActiveTalk());
    }

    /**
     * Получение сообщения
     */
    private String inpMsg() {
        String mes = null;
        try {
            mes = socketIn.readLine();
        } catch (Throwable e) {
            try {
                clientSocket.close();
            } catch (Throwable ex) {
                e.addSuppressed(ex);
            }
        }
        return mes;
    }

    public void inputMessage() {

         while (true) {
            try {
                runMemberThreadIn(inpMsg());
            } catch (Throwable e) {
                try {
                    clientSocket.close();
                } catch (Throwable ex) {
                    e.addSuppressed(ex);
                }
            }
        }
    }

    /**
     * Отправка сообщения
     */
    private void outMsg(String message) {
        try {
            socketOut.println(message);
        } catch (Throwable e) {
            try {
                clientSocket.close();
            } catch (Throwable ex) {
                e.addSuppressed(ex);
            }
        }
    }

    public void outputMessage() throws InterruptedException {

         while (true) {
            outMsg(new InputAnswer().setInputAnswer());
            //здесь проверять на выход из беседы и чата
        }
    }

    /**
     * Метод, организующий поток ввода сообщений от участников чата.
     */

    public void runMemberThreadIn(String message) throws InterruptedException, ExecutionException {
        Callable<MemberMessage> memberThread = () -> {
            MemberMessage clientMessage = new MemberMessage();
            clientMessage.setMemberMessage(message);
            receiveMessages.put(clientMessage);
            return clientMessage;
        };

        ClientThreadService threadService = new ClientThreadService();
        Future<MemberMessage> task = threadPool.submit(memberThread);
        receiveMessageTask.put(task);
        threadService.controlReadTask();
    }
}
