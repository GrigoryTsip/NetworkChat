package showmessage;

import membermessage.BuildMemberMessage;
import membermessage.ParseMemberMessage;
import server.Settings;

import java.io.UnsupportedEncodingException;
import java.util.GregorianCalendar;
import java.util.Scanner;

import static client.Client.hostName;
import static showmessage.ColorsType.*;
import static showmessage.ShowMessage.talkShowList;
import static talkshow.ExitCode.SUCCESS;
import static talkshow.MessageType.SEND_MESSAGE;

public class InputAnswer {

    private String inputPosition;
    private String activeTalk;
    private String currentAddressee = "";
    private String bodyOfMessage;
    public boolean endActiveTalk = false;
    public boolean exitChat = false;

    private String[] nicksOfTalk;


    public String setInputAnswer() throws InterruptedException {

        Scanner scan = new Scanner(System.in);

        nicksOfTalk = new String[talkShowList.size()];
        talkShowList.toArray(nicksOfTalk);

        synchronized (talkShowList) {
            try {
                printTalk();
                inpTalk();
                printAddr();
                positionInput();
                System.out.print(inputPosition);
                bodyOfMessage = scan.nextLine();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        talkShowList.notifyAll();

        ParseMemberMessage stringSend = new ParseMemberMessage();
        stringSend.setParseMessageType(String.valueOf(SEND_MESSAGE));
        stringSend.setParseMessageData(Settings.formatter.format(new GregorianCalendar().getTime()));
        stringSend.setParseMessageTalk(activeTalk);
        stringSend.setParseMessageSender(hostName);
        stringSend.setParseMessageRecipient(currentAddressee);
        stringSend.setParseMessageBody(bodyOfMessage);
        stringSend.setParseMessageExitCode(String.valueOf(SUCCESS));

        return new BuildMemberMessage().prepareSendMessage(stringSend);
    }

    private void printTalk() {
        String out = formTalkList();
        System.out.print("Беседы:  " + out + "\n\n");
    }

    private void inpTalk() {

        Scanner scan = new Scanner(System.in);
        String nickTalk;

        boolean talk = false;
        while (!talk) {
            if (talkShowList.size() > 1) {
                System.out.print("В какую беседу писать сообщение?>");
                nickTalk = scan.nextLine();

                talk = getActiveTalk(nickTalk);
                if (!talk) {
                    System.out.println("Вы ввели что-то непонятное. Повторите, пожалуйста\n");
                }
            } else {
                talk = getActiveTalk(nicksOfTalk[0]);
            }
        }
    }

    private void printAddr() throws UnsupportedEncodingException {

        Scanner scan = new Scanner(System.in);
        boolean addr = false; // адресов нет

        while (!addr) {
            String myString = "Введите один или несколько ников адресатов сообщения (через точку с запятой): ";
            String addrNick;
            System.out.print(myString);
            addrNick = scan.nextLine();
            addr = getAddressee(addrNick);
        }
    }

    public String formTalkList() {

        StringBuilder s = new StringBuilder();

        for (String talk : nicksOfTalk) {
            String marker = "<" + talk + ">";
            if (talk.equals(activeTalk)) {
                s.append(getColorText(WHITE, BOLD_HIGH_INTENSITY))
                        .append(getColorBack(BLACK, false))
                        .append(marker)
                        .append(resetColor());
            } else {
                s.append(marker);
            }
            s.append("  ");
        }
        return s.toString();
    }

    public void positionInput() throws UnsupportedEncodingException {

        StringBuilder s = new StringBuilder();
        if (!(endActiveTalk || exitChat)) {
            s.append(getColorText(WHITE, BOLD_HIGH_INTENSITY))
                    .append(getColorBack(BLUE, false))
                    .append(activeTalk).append("|").append(currentAddressee).append(">")
                    .append(resetColor()).append(" ");
        }
        inputPosition = s.toString();
    }

    public boolean getActiveTalk(String nickTalk) {

        String talkNick = nickTalk;
        boolean result = true;

        if (talkShowList.size() > 1) {

            // по умолчанию пишем в текущую беседу, а если ее нет - в беседу участника
            if (talkNick.isEmpty()) {
                if (activeTalk.isEmpty()) {
                    talkNick = hostName;
                } else {
                    talkNick = activeTalk;
                }
            } else {
                if (talkShowList.contains(talkNick)) {
                    activeTalk = talkNick;
                    printTalk();
                } else {
                    if (talkNick.equals("/end")) {
                        //здесь вызвать метод завершения беседы
                        endActiveTalk = true;
                    } else {
                        if (talkNick.equals("/exit")) {
                            //здесь вызвать метод закрытия чата
                            exitChat = true;
                        } else {
                            result = false;
                        }
                    }
                }
            }
        } else {
            talkNick = hostName;
            activeTalk = talkNick;
        }
        return result;
    }

    private boolean getAddressee(String addrNick) {

        if (addrNick.isEmpty()) { // по умолчанию - предыдущий список
            if (!currentAddressee.isEmpty()) addrNick = currentAddressee;
        } else {
            currentAddressee = addrNick;
        }
        return !addrNick.isEmpty();
    }

    public void inputText() {
        Scanner scan = new Scanner(System.in);
        bodyOfMessage = scan.nextLine();
    }

    public void setActiveTalk(String talkNick) {
        this.activeTalk = talkNick;
    }

    public String getActiveTalk() {
        return this.activeTalk;
    }

    public String getInputPosition() {
        return this.inputPosition;
    }
}
