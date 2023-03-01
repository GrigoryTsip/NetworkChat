//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package talkshow;

public enum ExitCode {
    SUCCESS(0),
    SERVER_NOT_INITIALIZE(-1),
    CLIENT_SOCKET_NOT_INITIALIZE(-2),
    WRONG_MESSAGE_FORMAT(-3),
    NICK_ALREADY_USED(-4),
    MISSING_USER_NAME(-5),
    USER_NOT_REGISTER(-6),
    USER_NOT_ACTIVE(-7),
    FALSE_TALKSHOW_IDENTIFIER(-8),
    USERS_NOT_IN_CHAT(-9),
    NOT_ACTIVE_USERS(-10),
    WRONG_RECIPIENTS(-11),
    SERVER_FAULT(12);

    private final int digitExitCode;

    private ExitCode(int i) {
        this.digitExitCode = i;
    }

    public static int getDigitExitCode(ExitCode code) {
        return code.digitExitCode;
    }

    public static String toString(ExitCode code) {
        String s = "";
        switch (code) {
            case SUCCESS:
                s = "�������� �������";
                break;
            case SERVER_NOT_INITIALIZE:
                s = "������ ������������� �������";
                break;
            case CLIENT_SOCKET_NOT_INITIALIZE:
                s = "������ ������������� ������ �������";
                break;
            case WRONG_MESSAGE_FORMAT:
                s = "�������� ������ ���������";
                break;
            case NICK_ALREADY_USED:
                s = "������������ � ����� ����� ��� ���������������";
                break;
            case MISSING_USER_NAME:
                s = "��� ����������� � ���� ���������� ������� ��� ������������";
                break;
            case USER_NOT_REGISTER:
                s = "��� ������� � ���� ���������� ������������������";
                break;
            case USER_NOT_ACTIVE:
                s = "������� �������������� � ����";
                break;
            case FALSE_TALKSHOW_IDENTIFIER:
                s = "�������� ������������� ������";
                break;
            case USERS_NOT_IN_CHAT:
                s = "������������ �� ���������� ������ �� ���������������� � ����: ";
                break;
            case NOT_ACTIVE_USERS:
                s = "������������ �� ���������� ������ �� ������� � ����: ";
                break;
            case WRONG_RECIPIENTS:
                s = "��� ��������� ������������ ����������� ���������";
                break;
            case SERVER_FAULT:
                s = "������ �������. �������� ���� �� ����������";
        }

        return s;
    }
}
