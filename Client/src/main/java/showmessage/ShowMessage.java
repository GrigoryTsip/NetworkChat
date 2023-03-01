package showmessage;

import membermessage.MemberMessage;
import membermessage.ParseMemberMessage;
import server.IDFactory;

import java.util.concurrent.ArrayBlockingQueue;

import static client.ClientThreadService.ABQ_CAPACITY;
import static client.ClientThreadService.parseMessages;
import static showmessage.ColorsType.*;

public class ShowMessage {

    public static final int STRING_LENGTH = 70;
    public static final int INPUT_ZONE_LENGTH = 100;
    private static final String INDENT = "\t\t\t\t";

    public static final ArrayBlockingQueue<String> talkShowList = new ArrayBlockingQueue<>(ABQ_CAPACITY);


    public void myTalkShows() throws InterruptedException {

        MemberMessage mesg = parseMessages.take();
        String talkNick = mesg.getTalkOwnerNick();

        int index = 0;
        if (talkShowList.contains(talkNick)) {
            String[] nicksOfTalk = new String[talkShowList.size()];
            talkShowList.toArray(nicksOfTalk);

            while (index < nicksOfTalk.length) {
                if (nicksOfTalk[index].equals(talkNick)) break;
                index++;
            }
        } else {
            talkShowList.put(talkNick);
            index = talkShowList.size() - 1;
        }
        String consoleText = formTextToConsole(mesg, index);
        System.out.println(consoleText);

    }

    public String formTextToConsole(MemberMessage talk, int index) {

        ParseMemberMessage prs = (ParseMemberMessage) new IDFactory()
                .getRelatedObjects(ParseMemberMessage.class, talk.getMemberMessageID())
                .get(0);

        String firstPosition = " ".repeat(INPUT_ZONE_LENGTH);

        String talkMarker = getColorText(WHITE, BOLD_HIGH_INTENSITY) +
                getColorBack(BLACK, false) +
                talk.getTalkOwnerNick() +
                prs.getParseMessageSender() +
                "> " +
                resetColor();
        String text = talkMarker + prs.getParseMessageBody();
        String indent = INDENT.repeat(Math.max(0, index)) + firstPosition;
        return breakString(text, indent);
    }

    private String breakString(String text, String indent) {
        String[] words = text.split(" ");
        StringBuilder newText = new StringBuilder();

        int index = 0;
        while (index < words.length) {
            StringBuilder oneString = new StringBuilder();
            while (oneString.length() < STRING_LENGTH && index < words.length) {
                String[] word = words[index].split("\n");
                for (String w : word) {
                    oneString.append(w).append(" ");
                }
                index++;
            }
            int hh = oneString.length();
            newText.append(indent).append(oneString).append("\n");
        }
        return newText.toString();
    }
}
