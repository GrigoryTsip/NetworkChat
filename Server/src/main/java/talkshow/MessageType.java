package talkshow;


/**
 * ������������ ����� ���������.
 *
 * @author gntsi
 * @version 1.0
 * @created 30-���-2023 16:19:26
 */
public enum MessageType {
    CHAT_REGISTRATION,
    CHAT_CONNECTION,
    RECEIVED_MESSAGE,
    SEND_MESSAGE,
    ERROR_MESSAGE,
    EXIT_THE_CHAT,
    START_SERVER;

    public MessageType getMessageTypeValue(int num) {
        MessageType mt = null;
        switch (num) {
            case 1 -> mt = CHAT_REGISTRATION;
            case 2 -> mt = CHAT_CONNECTION;
            case 3 -> mt = RECEIVED_MESSAGE;
            case 4 -> mt = SEND_MESSAGE;
            case 5 -> mt = ERROR_MESSAGE;
            case 6 -> mt = EXIT_THE_CHAT;
            case 7 -> mt = START_SERVER;
        }
        return mt;
    }

    public String toString(MessageType type) {
        String s;
        switch (type) {
            case CHAT_REGISTRATION -> s = "����������� � ����";
            case CHAT_CONNECTION -> s = "������ ������� � ����";
            case RECEIVED_MESSAGE -> s = "���������� ���������";
            case SEND_MESSAGE -> s = "���������� ���������";
            case ERROR_MESSAGE -> s = "��������� �� ������";
            case EXIT_THE_CHAT -> s = "���������� ������� � ����";
            case START_SERVER -> s = "������ �������";
            default -> s = "�������� ��� ���������";
        }
        return s;
    }
}