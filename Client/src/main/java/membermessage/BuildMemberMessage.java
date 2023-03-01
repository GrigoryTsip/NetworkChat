package membermessage;


import static server.Settings.messageSeparator;

/**
 * Класс, отвечающий за подготовку и отправку сообщений в потоке передачи.
 *
 * @author gntsi
 * @version 1.0
 * @created 24-фев-2023 19:52:18
 */
public class BuildMemberMessage {

    private String memberMessage;

    public BuildMemberMessage() {

    }

    /**
     * Метод подготовки сообщения к передаче.
     */
    public String prepareSendMessage(ParseMemberMessage message) throws InterruptedException {
        // ParseMemberMessage message = sendMessages.take();

        StringBuilder stringMessage = new StringBuilder(messageSeparator);
        if (message.getParseMessageType() != null) stringMessage.append(message.getParseMessageType())
                .append(messageSeparator);
        if (message.getParseMessageData() != null) stringMessage.append(message.getParseMessageData())
                .append(messageSeparator);
        if (message.getParseMessageTalk() != null) stringMessage.append(message.getParseMessageTalk())
                .append(messageSeparator);
        if (message.getParseMessageSender() != null) stringMessage.append(message.getParseMessageSender())
                .append(messageSeparator);
        if (message.getParseMessageRecipient() != null) stringMessage.append(message.getParseMessageRecipient())
                .append(messageSeparator);
        if (message.getParseMessageBody() != null) stringMessage.append(message.getParseMessageBody())
                .append(messageSeparator);
        if (message.getParseMessageExitCode() != null) stringMessage.append(message.getParseMessageExitCode())
                .append(messageSeparator);
        return stringMessage.toString();
    }

    /**
     * Инициация передачи сообщения.
     */
    public void letSendMessage(String message) {

    }

}