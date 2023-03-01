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
                s = "Операция успешна";
                break;
            case SERVER_NOT_INITIALIZE:
                s = "Ошибка инициализации сервера";
                break;
            case CLIENT_SOCKET_NOT_INITIALIZE:
                s = "Ошибка инициализации сокета клиента";
                break;
            case WRONG_MESSAGE_FORMAT:
                s = "Неверный формат сообщения";
                break;
            case NICK_ALREADY_USED:
                s = "Пользователь с таким ником уже зарегистрирован";
                break;
            case MISSING_USER_NAME:
                s = "Для регистрации в чате необходимо указать имя пользователя";
                break;
            case USER_NOT_REGISTER:
                s = "Для участия в чате необходимо зарегистрироваться";
                break;
            case USER_NOT_ACTIVE:
                s = "Сначала присоединитесь к чату";
                break;
            case FALSE_TALKSHOW_IDENTIFIER:
                s = "Неверный идентификатор беседы";
                break;
            case USERS_NOT_IN_CHAT:
                s = "Пользователи со следующими никами не зарегистрированы в чате: ";
                break;
            case NOT_ACTIVE_USERS:
                s = "Пользователи со следующими никами не активны в чате: ";
                break;
            case WRONG_RECIPIENTS:
                s = "Нет корректно определенных получателей сообщения";
                break;
            case SERVER_FAULT:
                s = "Ошибка сервера. Участник чата не определяем";
        }

        return s;
    }
}
